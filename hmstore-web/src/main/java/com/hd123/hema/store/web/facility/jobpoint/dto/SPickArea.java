/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-web
 * 文件名：	SPickArea.java
 * 模块说明：
 * 修改历史：
 * 2016-7-21 - xiepingping - 创建。
 */
package com.hd123.hema.store.web.facility.jobpoint.dto;

import com.hd123.hema.store.web.common.bean.UuidBean;

/**
 * @author xiepingping
 * 
 */
public class SPickArea extends UuidBean {
  private static final long serialVersionUID = -7078605500348302666L;

  /** 代码 */
  private String code;
  /** 名称 */
  private String name;
  /** 作业点 */
  private String jobPointUuid;

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

  public String getJobPointUuid() {
    return jobPointUuid;
  }

  public void setJobPointUuid(String jobPointUuid) {
    this.jobPointUuid = jobPointUuid;
  }

}
