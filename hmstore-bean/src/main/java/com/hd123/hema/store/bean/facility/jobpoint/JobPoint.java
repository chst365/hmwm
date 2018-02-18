/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-bean
 * 文件名：	JobPoint.java
 * 模块说明：
 * 修改历史：
 * 2016-6-15 - xiepingping - 创建。
 */
package com.hd123.hema.store.bean.facility.jobpoint;

import java.util.ArrayList;
import java.util.List;

import com.hd123.hema.store.bean.common.FieldLength;
import com.hd123.hema.store.bean.common.Validator;
import com.hd123.rumba.commons.biz.entity.Entity;
import com.hd123.rumba.commons.lang.Assert;

/**
 * 作业点
 * 
 * @author xiepingping
 * 
 */
public class JobPoint extends Entity implements Validator {
  private static final long serialVersionUID = -5315612950653873859L;

  /** 代码 */
  private String code;
  /** 名称 */
  private String name;
  /** 模板UUID */
  private String templateUuid;
  /** 组织UUID */
  private String orgUuid;

  /** 网关 */
  private SGateway sGateway;
  /** 分区明细 */
  private List<PickArea> pickAreas = new ArrayList<PickArea>();

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    Assert.assertStringNotTooLong(code, FieldLength.LENGTH_10, "jobpoint.code");
    Assert.assertArgumentNotNull(code, "jobpoint.code");
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    Assert.assertStringNotTooLong(name, FieldLength.LENGTH_20, "jobpoint.name");
    Assert.assertArgumentNotNull(name, "jobpoint.name");
    this.name = name;
  }

  public String getTemplateUuid() {
    return templateUuid;
  }

  public void setTemplateUuid(String templateUuid) {
    Assert.assertStringNotTooLong(templateUuid, FieldLength.LENGTH_32, "jobpoint.templateUuid");

    this.templateUuid = templateUuid;
  }

  public String getOrgUuid() {
    return orgUuid;
  }

  public void setOrgUuid(String orgUuid) {
    Assert.assertStringNotTooLong(orgUuid, FieldLength.LENGTH_32, "jobpoint.orgUuid");
    Assert.assertArgumentNotNull(orgUuid, "jobpoint.orgUuid");
    this.orgUuid = orgUuid;
  }

  public SGateway getsGateway() {
    return sGateway;
  }

  public void setsGateway(SGateway sGateway) {
    sGateway.validate();
    this.sGateway = sGateway;
  }

  public List<PickArea> getPickAreas() {
    return pickAreas;
  }

  public void setPickAreas(List<PickArea> pickAreas) {
    this.pickAreas = pickAreas;
  }

  @Override
  public void validate() {
    Assert.assertStringNotTooLong(code, FieldLength.LENGTH_10, "jobpoint.code");
    Assert.assertStringNotTooLong(name, FieldLength.LENGTH_20, "jobpoint.name");
    Assert.assertStringNotTooLong(templateUuid, FieldLength.LENGTH_32, "jobpoint.templateUuid");
    Assert.assertStringNotTooLong(orgUuid, FieldLength.LENGTH_32, "jobpoint.orgUuid");

    Assert.assertArgumentNotNull(code, "jobpoint.code");
    Assert.assertArgumentNotNull(name, "jobpoint.name");
    Assert.assertArgumentNotNull(orgUuid, "jobpoint.orgUuid");
    sGateway.validate();

    for (int i = 0; i < pickAreas.size(); i++) {
      PickArea iPickArea = pickAreas.get(i);
      for (int j = i + 1; j < pickAreas.size(); j++) {
        PickArea jPickArea = pickAreas.get(j);
        if (iPickArea.getCode().equals(jPickArea.getCode()))
          throw new IllegalArgumentException("作业点" + code + "第" + (i + 1) + "行与第" + (j + 1)
              + "行分区代码重复。");
      }
    }
  }
}
