/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-bean
 * 文件名：	NodeUsage.java
 * 模块说明：
 * 修改历史：
 * 2016-6-15 - xiepingping - 创建。
 */
package com.hd123.hema.store.bean.facility.gateway;

/**
 * 节点用途
 * 
 * @author xiepingping
 * 
 */
public enum NodeUsage {
  PickDisplayQty("拣货显示数量"), Section("区段"), CompleteInstruction("完成指示"), RplRequest("补货请求"), RplResponse(
      "补货应答");
  private String caption;

  private NodeUsage(String caption) {
    this.caption = caption;
  }

  public String getCaption() {
    return caption;
  }

}
