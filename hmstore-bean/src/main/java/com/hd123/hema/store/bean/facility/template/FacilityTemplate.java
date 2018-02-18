/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-bean
 * 文件名：	FacilityTemplate.java
 * 模块说明：
 * 修改历史：
 * 2016-6-15 - xiepingping - 创建。
 */
package com.hd123.hema.store.bean.facility.template;

import java.util.ArrayList;
import java.util.List;

import com.hd123.hema.store.bean.common.FieldLength;
import com.hd123.hema.store.bean.common.Validator;
import com.hd123.hema.store.bean.facility.gateway.Gateway;
import com.hd123.hema.store.bean.facility.jobpoint.JobPoint;
import com.hd123.rumba.commons.biz.entity.Entity;
import com.hd123.rumba.commons.lang.Assert;

/**
 * 设施模板
 * 
 * @author xiepingping
 * 
 */
public class FacilityTemplate extends Entity implements Validator {
  private static final long serialVersionUID = -8540634935620195L;

  /** 代码 */
  private String code;
  /** 名称 */
  private String name;

  /** 网关 */
  private List<Gateway> gateways = new ArrayList<Gateway>();
  /** 作业点 */
  private List<JobPoint> jobPoints = new ArrayList<JobPoint>();

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    Assert.assertStringNotTooLong(code, FieldLength.LENGTH_30, "facilityTemplate.code");
    Assert.assertArgumentNotNull(code, "facilityTemplate.code");
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    Assert.assertStringNotTooLong(name, FieldLength.LENGTH_30, "facilityTemplate.name");
    Assert.assertArgumentNotNull(name, "facilityTemplate.name");
    this.name = name;
  }

  public List<Gateway> getGateways() {
    return gateways;
  }

  public void setGateways(List<Gateway> gateways) {
    this.gateways = gateways;
  }

  public List<JobPoint> getJobPoints() {
    return jobPoints;
  }

  public void setJobPoints(List<JobPoint> jobPoints) {
    this.jobPoints = jobPoints;
  }

  @Override
  public void validate() {
    
    Assert.assertStringNotTooLong(code, FieldLength.LENGTH_30, "facilityTemplate.code");
    Assert.assertStringNotTooLong(name, FieldLength.LENGTH_30, "facilityTemplate.name");

    Assert.assertArgumentNotNull(code, "facilityTemplate.code");
    Assert.assertArgumentNotNull(name, "facilityTemplate.name");
    
    for (int i = 0; i < gateways.size(); i++) {
      Gateway iItem = gateways.get(i);
      for (int j = i + 1; j < gateways.size(); j++) {
        Gateway jItem = gateways.get(j);
        if (iItem.getCode().equals(jItem.getCode()))
          throw new IllegalArgumentException("设施模板" + code + "网关明细第" + (i + 1) + "行与第" + (j + 1)
              + "行代码重复。");
      }
    }

    for (int i = 0; i < jobPoints.size(); i++) {
      JobPoint iPoint = jobPoints.get(i);
      for (int j = i + 1; j < jobPoints.size(); j++) {
        JobPoint jPoint = jobPoints.get(j);
        if (iPoint.getCode().equals(jPoint.getCode()))
          throw new IllegalArgumentException("设施模板" + code + "作业点明细第" + (i + 1) + "行与第" + (j + 1)
              + "行代码重复。");
      }
    }
  }

}
