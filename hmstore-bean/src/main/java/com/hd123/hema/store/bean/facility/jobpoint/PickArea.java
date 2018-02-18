/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-bean
 * 文件名：	PickArea.java
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
 * 分区
 * 
 * @author xiepingping
 * 
 */
public class PickArea extends Entity implements Validator {
  private static final long serialVersionUID = 8972052319864481L;

  /** 代码 */
  private String code;
  /** 名称 */
  private String name;
  /** 作业点 */
  private String jobPointUuid;

  /** 区段明细 */
  private List<Section> sections = new ArrayList<Section>();

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    Assert.assertStringNotTooLong(code, FieldLength.LENGTH_10, "pickarea.code");
    Assert.assertArgumentNotNull(code, "pickarea.code");
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    Assert.assertStringNotTooLong(name, FieldLength.LENGTH_20, "pickarea.name");
    Assert.assertArgumentNotNull(name, "pickarea.name");
    this.name = name;
  }

  public String getJobPointUuid() {
    return jobPointUuid;
  }

  public void setJobPointUuid(String jobPointUuid) {
    Assert.assertStringNotTooLong(jobPointUuid, FieldLength.LENGTH_32, "pickarea.jobPointUuid");
    Assert.assertArgumentNotNull(jobPointUuid, "pickarea.jobPointUuid");
    this.jobPointUuid = jobPointUuid;
  }

  public List<Section> getSections() {
    return sections;
  }

  public void setSections(List<Section> sections) {
    this.sections = sections;
  }

  @Override
  public void validate() {
    Assert.assertStringNotTooLong(code, FieldLength.LENGTH_10, "pickarea.code");
    Assert.assertStringNotTooLong(name, FieldLength.LENGTH_20, "pickarea.name");
    Assert.assertStringNotTooLong(jobPointUuid, FieldLength.LENGTH_32, "pickarea.jobPointUuid");

    Assert.assertArgumentNotNull(code, "pickarea.code");
    Assert.assertArgumentNotNull(name, "pickarea.name");
    Assert.assertArgumentNotNull(jobPointUuid, "pickarea.jobPointUuid");

    for (int i = 0; i < sections.size(); i++) {
      Section iItem = sections.get(i);
      for (int j = i + 1; j < sections.size(); j++) {
        Section jItem = sections.get(j);
        if (iItem.getCode().equals(jItem.getCode()))
          throw new IllegalArgumentException("分区" + code + "区段明细第" + (i + 1) + "行与第" + (j + 1)
              + "行代码重复。");
      }
    }
  }

}
