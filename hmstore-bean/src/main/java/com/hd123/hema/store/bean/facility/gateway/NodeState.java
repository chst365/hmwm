/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-bean
 * 文件名：	NodeType.java
 * 模块说明：
 * 修改历史：
 * 2016-6-15 - xiepingping - 创建。
 */
package com.hd123.hema.store.bean.facility.gateway;

/**
 * @author xiepingping
 * 
 */
public enum NodeState {
  Normal("正常"), Warning("警告");
  private String caption;

  private NodeState(String caption) {
    this.caption = caption;
  }

  public String getCaption() {
    return caption;
  }

}
