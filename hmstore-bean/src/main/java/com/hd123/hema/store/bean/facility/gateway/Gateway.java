/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-bean
 * 文件名：	Gateway.java
 * 模块说明：
 * 修改历史：
 * 2016-6-15 - xiepingping - 创建。
 */
package com.hd123.hema.store.bean.facility.gateway;

import java.util.ArrayList;
import java.util.List;

import com.hd123.hema.store.bean.common.FieldLength;
import com.hd123.hema.store.bean.common.Validator;
import com.hd123.rumba.commons.biz.entity.Entity;
import com.hd123.rumba.commons.lang.Assert;

/**
 * 网关
 * 
 * @author xiepingping
 * 
 */
public class Gateway extends Entity implements Validator {
  private static final long serialVersionUID = -8207096336853975446L;

  /** 代码 */
  private String code;
  /** IP */
  private String ip;
  /** 端口号 */
  private String port;
  /** 模板UUID */
  private String templateUuid;
  /** 组织UUID */
  private String orgUuid;

  /** 电子标签明细 */
  private List<ElectronicTag> tags = new ArrayList<ElectronicTag>();

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    Assert.assertStringNotTooLong(code, FieldLength.LENGTH_10, "gateway.code");
    Assert.assertArgumentNotNull(code, "gateway.code");
    this.code = code;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    Assert.assertStringNotTooLong(ip, FieldLength.LENGTH_15, "gateway.ip");
    Assert.assertArgumentNotNull(ip, "gateway.ip");
    this.ip = ip;
  }

  public String getPort() {
    return port;
  }

  public void setPort(String port) {
    Assert.assertStringNotTooLong(port, FieldLength.LENGTH_5, "gateway.port");
    Assert.assertArgumentNotNull(port, "gateway.port");
    this.port = port;
  }

  public String getTemplateUuid() {
    return templateUuid;
  }

  public void setTemplateUuid(String templateUuid) {
    Assert.assertStringNotTooLong(templateUuid, FieldLength.LENGTH_32, "gateway.templateUuid");

    this.templateUuid = templateUuid;
  }

  public String getOrgUuid() {
    return orgUuid;
  }

  public void setOrgUuid(String orgUuid) {
    Assert.assertStringNotTooLong(orgUuid, FieldLength.LENGTH_32, "gateway.orgUuid");
    Assert.assertArgumentNotNull(orgUuid, "gateway.orgUuid");
    this.orgUuid = orgUuid;
  }

  public List<ElectronicTag> getTags() {
    return tags;
  }

  public void setTags(List<ElectronicTag> tags) {
    this.tags = tags;
  }

  @Override
  public void validate() {
    Assert.assertStringNotTooLong(code, FieldLength.LENGTH_10, "gateway.code");
    Assert.assertStringNotTooLong(ip, FieldLength.LENGTH_15, "gateway.ip");
    Assert.assertStringNotTooLong(port, FieldLength.LENGTH_5, "gateway.port");
    Assert.assertStringNotTooLong(templateUuid, FieldLength.LENGTH_32, "gateway.templateUuid");
    Assert.assertStringNotTooLong(orgUuid, FieldLength.LENGTH_32, "gateway.orgUuid");

    Assert.assertArgumentNotNull(code, "gateway.code");
    Assert.assertArgumentNotNull(ip, "gateway.ip");
    Assert.assertArgumentNotNull(port, "gateway.port");
    Assert.assertArgumentNotNull(orgUuid, "gateway.orgUuid");

    for (int i = 0; i < tags.size(); i++) {
      ElectronicTag iItem = tags.get(i);
      for (int j = i + 1; j < tags.size(); j++) {
        ElectronicTag jItem = tags.get(j);
        if (iItem.getNodeCode().equals(jItem.getNodeCode()))
          throw new IllegalArgumentException("网关" + code + "电子标签明细第" + (i + 1) + "行与第" + (j + 1)
              + "行节点编号重复。");
      }
    }
  }

}
