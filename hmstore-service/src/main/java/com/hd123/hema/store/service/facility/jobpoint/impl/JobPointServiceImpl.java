/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-service
 * 文件名：	JobPointServiceImpl.java
 * 模块说明：
 * 修改历史：
 * 2016-6-27 - xiepingping - 创建。
 */
package com.hd123.hema.store.service.facility.jobpoint.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.hd123.hema.store.bean.facility.gateway.Gateway;
import com.hd123.hema.store.bean.facility.jobpoint.JobPoint;
import com.hd123.hema.store.bean.facility.jobpoint.JobPointGateway;
import com.hd123.hema.store.bean.facility.jobpoint.PickArea;
import com.hd123.hema.store.bean.facility.jobpoint.SGateway;
import com.hd123.hema.store.dao.facility.gateway.GatewayDao;
import com.hd123.hema.store.dao.facility.jobpoint.JobPointDao;
import com.hd123.hema.store.dao.facility.jobpoint.JobPointGatewayDao;
import com.hd123.hema.store.service.facility.jobpoint.JobPointService;
import com.hd123.hema.store.service.facility.jobpoint.PickAreaService;
import com.hd123.rumba.commons.lang.Assert;
import com.hd123.wms.antman.common.query.QueryParam;
import com.hd123.wms.antman.common.utils.ListUtils;
import com.hd123.wms.antman.common.utils.UUIDGenerator;

/**
 * @author xiepingping
 * 
 */
public class JobPointServiceImpl implements JobPointService {

  @Autowired
  private JobPointDao jobPointDao;
  @Autowired
  private JobPointGatewayDao jobPointGatewayDao;
  @Autowired
  private GatewayDao gatewayDao;
  @Autowired
  private PickAreaService pickAreaService;

  @Override
  public String insert(JobPoint jobPoint) {
    Assert.assertArgumentNotNull(jobPoint, "jobPoint");

    jobPoint.validate();
    jobPoint.setUuid(null);
    validByUniqueKey(jobPoint);

    jobPoint.setUuid(UUIDGenerator.genUUID());
    jobPointDao.insert(jobPoint);

    insertItems(jobPoint);

    return jobPoint.getUuid();
  }

  private void insertItems(JobPoint jobPoint) {
    if (CollectionUtils.isEmpty(jobPoint.getPickAreas()) == false) {
      for (PickArea area : jobPoint.getPickAreas())
        area.setJobPointUuid(jobPoint.getUuid());
      pickAreaService.insertBatch(jobPoint.getPickAreas());
    }

    insertJGateway(jobPoint);
  }

  private void insertJGateway(JobPoint jobPoint) {
    if (jobPoint.getsGateway() == null)
      return;

    List<JobPointGateway> jGateways = new ArrayList<JobPointGateway>();
    JobPointGateway jGateway = new JobPointGateway();
    jGateway.setGatewayUuid(jobPoint.getsGateway().getUuid());
    jGateway.setJobPointUuid(jobPoint.getUuid());
    jGateway.setUuid(UUIDGenerator.genUUID());
    jGateways.add(jGateway);
    jobPointGatewayDao.insertBatch(jGateways);
  }

  @Override
  public void insertBatch(List<JobPoint> jobPoints) {
    Assert.assertArgumentNotNull(jobPoints, "jobPoints");

    for (JobPoint jobPoint : jobPoints) {
      Assert.assertArgumentNotNull(jobPoint, "jobPoint");

      jobPoint.validate();
      jobPoint.setUuid(null);
      validByUniqueKey(jobPoint);

      jobPoint.setUuid(UUIDGenerator.genUUID());

      insertItems(jobPoint);
    }

    for (List<JobPoint> list : ListUtils.splitList(jobPoints, 1000))
      jobPointDao.insertBatch(list);
  }

  @Override
  public void update(JobPoint jobPoint) {
    Assert.assertArgumentNotNull(jobPoint, "jobPoint");

    jobPoint.validate();
    JobPoint dbJobPoint = jobPointDao.getByUuid(jobPoint.getUuid());
    if (dbJobPoint == null)
      throw new IllegalArgumentException("作业点不存在");

    validByUniqueKey(jobPoint);

    jobPointDao.update(jobPoint);
    jobPointGatewayDao.deleteByJobPoint(jobPoint.getUuid());
    insertJGateway(jobPoint);
  }

  private void validByUniqueKey(JobPoint jobPoint) {
    assert jobPoint != null;

    JobPoint dbJobPoint = jobPointDao.getByCode(jobPoint.getCode(), jobPoint.getTemplateUuid(),
        jobPoint.getOrgUuid());
    if (dbJobPoint != null && dbJobPoint.getUuid().equals(jobPoint.getUuid()) == false)
      throw new IllegalArgumentException("已存在代码为" + jobPoint.getCode() + "的作业点");
  }

  @Override
  public void delete(String uuid) {
    if (StringUtils.isEmpty(uuid))
      return;

    jobPointDao.delete(uuid);
    deleteItems(uuid);
  }

  private void deleteItems(String uuid) {
    if (StringUtils.isEmpty(uuid))
      return;

    jobPointGatewayDao.deleteByJobPoint(uuid);
    pickAreaService.deleteByJobPoint(uuid);
  }

  @Override
  public JobPoint getByUuid(String uuid) {
    if (StringUtils.isEmpty(uuid))
      return null;

    JobPoint jobPoint = jobPointDao.getByUuid(uuid);
    setItems(jobPoint);

    return jobPoint;
  }

  private void setItems(JobPoint jobPoint) {
    if (jobPoint == null)
      return;

    QueryParam jobPointGatewayFilter = new QueryParam();
    jobPointGatewayFilter.put("jobPointUuid", jobPoint.getUuid());
    List<JobPointGateway> jobPointGateways = jobPointGatewayDao.queryByList(jobPointGatewayFilter);
    if (CollectionUtils.isEmpty(jobPointGateways) == false) {
      Gateway gateway = gatewayDao.getByUuid(jobPointGateways.get(0).getGatewayUuid());
      if (gateway != null)
        jobPoint.setsGateway(new SGateway(gateway.getUuid(), gateway.getCode(), gateway.getIp(),
            gateway.getPort()));
    }

    QueryParam pickAreaFilter = new QueryParam();
    pickAreaFilter.put("jobPointUuid", jobPoint.getUuid());
    jobPoint.setPickAreas(pickAreaService.queryByList(pickAreaFilter));
  }

  @Override
  public List<JobPoint> queryByList(QueryParam params) {
    List<JobPoint> jobPoints = jobPointDao.queryByList(params);
    for (JobPoint jobPoint : jobPoints) {
      setItems(jobPoint);
    }

    return jobPoints;
  }

  @Override
  public List<String> queryAvaliableEleTags(QueryParam param) {
    Assert.assertArgumentNotNull(param.get("orgUuid"), "orgUuid");
    Assert.assertArgumentNotNull(param.get("type"), "type");
    Assert.assertArgumentNotNull(param.get("gatewayUuid"), "gatewayUuid");

    return jobPointDao.queryAvaliableEleTags(param);
  }

  @Override
  public void deleteByOrg(String orgUuid) {
    Assert.assertArgumentNotNull(orgUuid, "orgUuid");

    jobPointDao.deleteByOrg(orgUuid);
  }

}
