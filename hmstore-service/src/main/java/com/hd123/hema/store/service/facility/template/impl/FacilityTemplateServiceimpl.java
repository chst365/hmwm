/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-service
 * 文件名：	FacilityTemplateServiceimpl.java
 * 模块说明：
 * 修改历史：
 * 2016-6-27 - xiepingping - 创建。
 */
package com.hd123.hema.store.service.facility.template.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hd123.hema.store.bean.facility.gateway.Gateway;
import com.hd123.hema.store.bean.facility.jobpoint.JobPoint;
import com.hd123.hema.store.bean.facility.template.FacilityTemplate;
import com.hd123.hema.store.dao.facility.template.FacilityTemplateDao;
import com.hd123.hema.store.service.facility.gateway.GatewayService;
import com.hd123.hema.store.service.facility.jobpoint.JobPointService;
import com.hd123.hema.store.service.facility.pickscheme.PickSchemeService;
import com.hd123.hema.store.service.facility.template.FacilityTemplateService;
import com.hd123.rumba.commons.lang.Assert;
import com.hd123.wms.antman.common.query.QueryParam;
import com.hd123.wms.antman.common.utils.ListUtils;
import com.hd123.wms.antman.common.utils.UUIDGenerator;
import com.hd123.wms.antman.system.bean.Enterprise;
import com.hd123.wms.antman.system.dao.EnterpriseDao;

/**
 * @author xiepingping
 * 
 */
@Service
public class FacilityTemplateServiceimpl implements FacilityTemplateService {

  @Autowired
  private FacilityTemplateDao templateDao;
  @Autowired
  private GatewayService gatewayService;
  @Autowired
  private JobPointService jobPointService;
  @Autowired
  private PickSchemeService pickSchemeService;
  @Autowired
  private EnterpriseDao enterpriseDao;

  @Override
  public String insert(FacilityTemplate template) {
    Assert.assertArgumentNotNull(template, "facilityTemplate");
    template.validate();

    validByUniqueKey(template.getCode(), null);

    template.setUuid(UUIDGenerator.genUUID());
    for (Gateway gateway : template.getGateways())
      gateway.setTemplateUuid(template.getUuid());
    for (JobPoint jobPoint : template.getJobPoints())
      jobPoint.setTemplateUuid(template.getUuid());

    templateDao.insert(template);

    gatewayService.insertBatch(template.getGateways());
    jobPointService.insertBatch(template.getJobPoints());

    return template.getUuid();
  }

  @Override
  public void insertBatch(List<FacilityTemplate> templates) {
    Assert.assertArgumentNotNull(templates, "templates");

    for (int i = 0; i < templates.size(); i++) {
      FacilityTemplate iFacilityTemplate = templates.get(i);
      for (int j = i + 1; j < templates.size(); j++) {
        FacilityTemplate jFacilityTemplate = templates.get(j);
        if (iFacilityTemplate.getCode().equals(jFacilityTemplate.getCode()))
          throw new IllegalArgumentException("设施模板第" + (i + 1) + "行与第" + (j + 1) + "行代码重复。");
      }
    }

    for (FacilityTemplate template : templates) {
      Assert.assertArgumentNotNull(template, "facilityTemplate");
      template.validate();

      validByUniqueKey(template.getCode(), null);

      template.setUuid(UUIDGenerator.genUUID());

      for (Gateway gateway : template.getGateways())
        gateway.setTemplateUuid(template.getUuid());
      gatewayService.insertBatch(template.getGateways());

      for (JobPoint jobPoint : template.getJobPoints()) {
        jobPoint.setTemplateUuid(template.getUuid());

        for (Gateway gateway : template.getGateways())
          if (gateway.getCode().equals(jobPoint.getsGateway().getCode())) {
            jobPoint.getsGateway().setUuid(gateway.getUuid());
            break;
          }
      }
      jobPointService.insertBatch(template.getJobPoints());
    }

    for (List<FacilityTemplate> list : ListUtils.splitList(templates, 1000))
      templateDao.insertBatch(list);
  }

  @Override
  public void update(FacilityTemplate template) {
    Assert.assertArgumentNotNull(template, "facilityTemplate");
    template.validate();

    FacilityTemplate dbFacilityTemplate = templateDao.getByUuid(template.getUuid());
    if (dbFacilityTemplate == null)
      throw new IllegalArgumentException("设施模板不存在");

    validByUniqueKey(template.getCode(), template.getUuid());

    templateDao.update(template);

    for (Gateway gateway : template.getGateways())
      gatewayService.delete(gateway.getUuid());
    for (JobPoint jobPoint : template.getJobPoints())
      jobPointService.delete(jobPoint.getUuid());

    gatewayService.insertBatch(template.getGateways());
    jobPointService.insertBatch(template.getJobPoints());
  }

  private void validByUniqueKey(String code, String uuid) {
    assert code != null;

    FacilityTemplate dbFacilityTemplate = templateDao.getByCode(code);
    if (dbFacilityTemplate != null && dbFacilityTemplate.getUuid().equals(uuid) == false)
      throw new IllegalArgumentException("已存在代码为" + code + "的设施模板");
  }

  @Override
  public void delete(String uuid) {
    if (StringUtils.isEmpty(uuid))
      return;

    FacilityTemplate template = getByUuid(uuid);
    if (template == null)
      return;

    templateDao.delete(uuid);
    for (Gateway gateway : template.getGateways())
      gatewayService.delete(gateway.getUuid());
    for (JobPoint jobPoint : template.getJobPoints())
      jobPointService.delete(jobPoint.getUuid());
  }

  @Override
  public FacilityTemplate getByUuid(String uuid) {
    if (StringUtils.isEmpty(uuid))
      return null;

    FacilityTemplate template = templateDao.getByUuid(uuid);
    setItems(template);

    return template;
  }

  private void setItems(FacilityTemplate template) {
    if (template == null)
      return;

    QueryParam param = new QueryParam();
    param.put("templateUuid", template.getUuid());
    template.setGateways(gatewayService.queryByList(param));
    template.setJobPoints(jobPointService.queryByList(param));
  }

  @Override
  public List<FacilityTemplate> queryByList() {
    QueryParam params = new QueryParam();
    return templateDao.queryByList(params);
  }

  @Override
  public void generateFacility(String templateUuid, String orgUuid) {
    Assert.assertArgumentNotNull(templateUuid, "templateUuid");
    Assert.assertArgumentNotNull(orgUuid, "orgUuid");

    Enterprise enterprise = enterpriseDao.get(orgUuid);
    if (enterprise != null)
      throw new IllegalArgumentException("从模板生成设施不是总部功能，请联系管理员");

    FacilityTemplate template = getByUuid(templateUuid);
    if (template == null)
      throw new IllegalArgumentException("设施模板不存在");
    generateGateway(template, orgUuid);
    generateJobPoint(template, orgUuid);
    clearPickScheme(orgUuid);

  }

  private void clearPickScheme(String orgUuid) {
    pickSchemeService.deleteByOrg(orgUuid);
  }

  private void generateJobPoint(FacilityTemplate template, String orgUuid) {
    jobPointService.deleteByOrg(orgUuid);

    List<JobPoint> result = new ArrayList<JobPoint>();
    for (JobPoint source : template.getJobPoints()) {
      JobPoint target = new JobPoint();
      target.setCode(source.getCode());
      target.setName(source.getName());
      target.setOrgUuid(orgUuid);
      target.setPickAreas(source.getPickAreas());
      target.setsGateway(source.getsGateway());

      result.add(target);
    }
    jobPointService.insertBatch(result);
  }

  private void generateGateway(FacilityTemplate template, String orgUuid) {
    gatewayService.deleteByOrg(orgUuid);

    List<Gateway> result = new ArrayList<Gateway>();
    for (Gateway source : template.getGateways()) {
      Gateway target = new Gateway();
      target.setCode(source.getCode());
      target.setIp(source.getIp());
      target.setOrgUuid(orgUuid);
      target.setPort(source.getPort());
      target.setTags(source.getTags());

      result.add(target);
    }
    gatewayService.insertBatch(result);
  }

  @Override
  public FacilityTemplate getByCode(String code) {
    if (StringUtils.isEmpty(code))
      return null;

    return templateDao.getByCode(code);
  }

}
