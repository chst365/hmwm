/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-web
 * 文件名：	SJobPoint.java
 * 模块说明：
 * 修改历史：
 * 2016-7-21 - xiepingping - 创建。
 */
package com.hd123.hema.store.web.facility.jobpoint.dto;

import com.hd123.hema.store.bean.facility.jobpoint.SGateway;
import com.hd123.hema.store.web.common.bean.UuidBean;

/**
 * @author xiepingping
 *
 */
public class SJobPoint extends UuidBean {
  private static final long serialVersionUID = 8273469409899567139L;

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

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTemplateUuid() {
    return templateUuid;
  }

  public void setTemplateUuid(String templateUuid) {
    this.templateUuid = templateUuid;
  }

  public String getOrgUuid() {
    return orgUuid;
  }

  public void setOrgUuid(String orgUuid) {
    this.orgUuid = orgUuid;
  }

  public SGateway getsGateway() {
    return sGateway;
  }

  public void setsGateway(SGateway sGateway) {
    this.sGateway = sGateway;
  }

}
