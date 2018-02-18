/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-web
 * 文件名：	UuidBean.java
 * 模块说明：
 * 修改历史：
 * 2016-7-21 - xiepingping - 创建。
 */
package com.hd123.hema.store.web.common.bean;

import java.io.Serializable;

/**
 * @author xiepingping
 *
 */
public class UuidBean implements Serializable {
  private static final long serialVersionUID = -8069947656768833734L;

  private String uuid;

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }
  
}
