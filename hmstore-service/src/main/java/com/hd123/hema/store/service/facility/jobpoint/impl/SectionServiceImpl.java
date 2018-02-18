/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-service
 * 文件名：	SectionServiceImpl.java
 * 模块说明：
 * 修改历史：
 * 2016-6-27 - xiepingping - 创建。
 */
package com.hd123.hema.store.service.facility.jobpoint.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.hd123.hema.store.bean.facility.jobpoint.BinEleTag;
import com.hd123.hema.store.bean.facility.jobpoint.PickArea;
import com.hd123.hema.store.bean.facility.jobpoint.RplEleTag;
import com.hd123.hema.store.bean.facility.jobpoint.Section;
import com.hd123.hema.store.bean.facility.jobpoint.SectionEleTag;
import com.hd123.hema.store.dao.facility.jobpoint.BinEleTagDao;
import com.hd123.hema.store.dao.facility.jobpoint.RplEleTagDao;
import com.hd123.hema.store.dao.facility.jobpoint.SectionDao;
import com.hd123.hema.store.dao.facility.jobpoint.SectionEleTagDao;
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
public class SectionServiceImpl implements SectionService {

  @Autowired
  private SectionDao sectionDao;
  @Autowired
  private BinEleTagDao binEleTagDao;
  @Autowired
  private SectionEleTagDao sectionEleTagDao;
  @Autowired
  private RplEleTagDao rplEleTagDao;
  @Autowired
  private PickAreaService pickAreaService;

  @Override
  public String insert(Section section) {
    Assert.assertArgumentNotNull(section, "section");
    section.validate();

    section.setUuid(UUIDGenerator.genUUID());
    sectionDao.insert(section);

    insertItems(section);

    return section.getUuid();
  }

  private void insertItems(Section section) {
    assert section != null;

    for (BinEleTag binEleTag : section.getBinEleTags()) {
      binEleTag.setSectionUuid(section.getUuid());
      binEleTag.setUuid(UUIDGenerator.genUUID());
    }

    for (SectionEleTag sectionEleTag : section.getSectionEleTags()) {
      sectionEleTag.setSectionUuid(section.getUuid());
      sectionEleTag.setUuid(UUIDGenerator.genUUID());
    }

    for (RplEleTag rplEleTag : section.getRplEleTags()) {
      rplEleTag.setSectionUuid(section.getUuid());
      rplEleTag.setUuid(UUIDGenerator.genUUID());
    }

    if (CollectionUtils.isEmpty(section.getBinEleTags()) == false)
      binEleTagDao.insertBatch(section.getBinEleTags());
    if (CollectionUtils.isEmpty(section.getSectionEleTags()) == false)
      sectionEleTagDao.insertBatch(section.getSectionEleTags());
    if (CollectionUtils.isEmpty(section.getRplEleTags()) == false)
      rplEleTagDao.insertBatch(section.getRplEleTags());
  }

  @Override
  public void insertBatch(List<Section> sections) {
    Assert.assertArgumentNotNull(sections, "sections");

    for (Section section : sections) {
      Assert.assertArgumentNotNull(section, "section");
      section.validate();

      section.setUuid(UUIDGenerator.genUUID());

      insertItems(section);
    }

    for (List<Section> list : ListUtils.splitList(sections, 1000))
      sectionDao.insertBatch(list);
  }

  @Override
  public void update(Section section) {
    Assert.assertArgumentNotNull(section, "section");
    section.validate();

    Section dbSection = sectionDao.getByUuid(section.getUuid());
    if (dbSection == null)
      throw new IllegalArgumentException("区段不存在");

    sectionDao.update(section);

    deleteItems(section.getUuid());
    insertItems(section);
  }

  private void deleteItems(String sectionUuid) {
    binEleTagDao.deleteBySection(sectionUuid);
    sectionEleTagDao.deleteBySection(sectionUuid);
    rplEleTagDao.deleteBySection(sectionUuid);
  }

  @Override
  public void delete(String uuid) {
    if (StringUtils.isEmpty(uuid))
      return;

    sectionDao.delete(uuid);
    deleteItems(uuid);
  }

  @Override
  public Section getByUuid(String uuid) {
    if (StringUtils.isEmpty(uuid))
      return null;

    Section section = sectionDao.getByUuid(uuid);
    getItems(section);

    return section;
  }

  private void getItems(Section section) {
    if (section == null)
      return;

    QueryParam param = new QueryParam();
    param.put("sectionUuid", section.getUuid());
    section.setBinEleTags(binEleTagDao.queryByList(param));
    section.setSectionEleTags(sectionEleTagDao.queryByList(param));
    section.setRplEleTags(rplEleTagDao.queryByList(param));
  }

  @Override
  public List<Section> queryByList(QueryParam params) {
    List<Section> sections = sectionDao.queryByList(params);
    for (Section section : sections)
      getItems(section);

    return sections;
  }

  @Override
  public void deleteByPickArea(String pickAreaUuid) {
    if (StringUtils.isEmpty(pickAreaUuid))
      return;

    PickArea area = pickAreaService.getByUuid(pickAreaUuid);
    if (area == null)
      return;

    sectionDao.deleteByPickArea(pickAreaUuid);
    for (Section section : area.getSections())
      deleteItems(section.getUuid());
  }

}
