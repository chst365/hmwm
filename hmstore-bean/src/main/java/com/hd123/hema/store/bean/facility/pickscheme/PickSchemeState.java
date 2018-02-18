/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-bean
 * 文件名：	PickSchemeState.java
 * 模块说明：
 * 修改历史：
 * 2016-6-15 - xiepingping - 创建。
 */
package com.hd123.hema.store.bean.facility.pickscheme;

/**
 * 拣货分区状态
 * 
 * @author xiepingping
 *
 */
public enum PickSchemeState {
  Using("使用中"), UnEffective("待生效");
  private String caption;

  private PickSchemeState(String caption) {
    this.caption = caption;
  }

  public String getCaption() {
    return caption;
  }

}
