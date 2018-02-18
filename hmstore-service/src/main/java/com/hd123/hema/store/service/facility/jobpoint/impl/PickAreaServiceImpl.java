/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-service
 * 文件名：	PickAreaServiceImpl.java
 * 模块说明：
 * 修改历史：
 * 2016-6-27 - xiepingping - 创建。
 */
package com.hd123.hema.store.service.facility.jobpoint.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.hd123.hema.store.bean.facility.jobpoint.JobPoint;
import com.hd123.hema.store.bean.facility.jobpoint.PickArea;
import com.hd123.hema.store.bean.facility.jobpoint.Section;
import com.hd123.hema.store.dao.facility.jobpoint.PickAreaDao;
import com.hd123.hema.store.service.facility.jobpoint.JobPointService;
import com.hd123.hema.store.service.facility.jobpoint.PickAreaService;
import com.hd123.hema.store.service.facility.jobpoint.SectionService;
import com.hd123.rumba.commons.lang.Assert;
import com.hd123.wms.antman.common.query.QueryParam;
import com.hd123.wms.antman.common.utils.ListUtils;
import com.hd123.wms.antman.common.utils.UUIDGenerator;

/**
 * @author xiepingping
 * 
 */
public class PickAreaServiceImpl implements PickAreaService {

  @Autowired
  private PickAreaDao pickAreaDao;
  @Autowired
  private SectionService sectionService;
  @Autowired
  private JobPointService jobPointService;

  @Override
  public String insert(PickArea pickArea) {
    Assert.assertArgumentNotNull(pickArea, "pickArea");
    pickArea.validate();

    pickArea.setUuid(UUIDGenerator.genUUID());
    pickAreaDao.insert(pickArea);

    insertItems(pickArea);

    return pickArea.getUuid();
  }

  private void insertItems(PickArea pickArea) {
    if (CollectionUtils.isEmpty(pickArea.getSections()))
      return;

    for (Section section : pickArea.getSections())
      section.setPickAreaUuid(pickArea.getUuid());
    sectionService.insertBatch(pickArea.getSections());
  }

  @Override
  public void insertBatch(List<PickArea> pickAreas) {
    Assert.assertArgumentNotNull(pickAreas, "pickAreas");

    for (PickArea pickArea : pickAreas) {
      Assert.assertArgumentNotNull(pickArea, "pickArea");
      pickArea.validate();

      pickArea.setUuid(UUIDGenerator.genUUID());

      insertItems(pickArea);
    }

    for (List<PickArea> list : ListUtils.splitList(pickAreas, 1000))
      pickAreaDao.insertBatch(list);
  }

  @Override
  public void update(PickArea pickArea) {
    Assert.assertArgumentNotNull(pickArea, "pickArea");
    pickArea.validate();

    PickArea dbPickArea = pickAreaDao.getByUuid(pickArea.getUuid());
    if (dbPickArea == null)
      throw new IllegalArgumentException("分区不存在");

    pickAreaDao.update(pickArea);

    if (CollectionUtils.isEmpty(pickArea.getSections()))
      return;

    sectionService.deleteByPickArea(pickArea.getUuid());
    insertItems(dbPickArea);
  }

  @Override
  public void delete(String uuid) {
    if (StringUtils.isEmpty(uuid))
      return;

    pickAreaDao.delete(uuid);
    sectionService.deleteByPickArea(uuid);
  }

  @Override
  public PickArea getByUuid(String uuid) {
    if (StringUtils.isEmpty(uuid))
      return null;

    PickArea area = pickAreaDao.getByUuid(uuid);
    setItems(area);
    return area;
  }

  private void setItems(PickArea pickArea) {
    if (pickArea == null)
      return;

    QueryParam filter = new QueryParam();
    filter.put("pickAreaUuid", pickArea.getUuid());
    pickArea.setSections(sectionService.queryByList(filter));
  }

  @Override
  public List<PickArea> queryByList(QueryParam params) {
    List<PickArea> pickAreas = pickAreaDao.queryByList(params);
    for (PickArea pickArea : pickAreas) {
      setItems(pickArea);
    }

    return pickAreas;
  }

  @Override
  public void deleteByJobPoint(String jobPointUuid) {
    if (StringUtils.isEmpty(jobPointUuid))
      return;

    JobPoint jobPoint = jobPointService.getByUuid(jobPointUuid);
    if (jobPoint == null)
      return;

    pickAreaDao.deleteByJobPoint(jobPointUuid);
    for (PickArea area : jobPoint.getPickAreas())
      sectionService.deleteByPickArea(area.getUuid());
  }

}
