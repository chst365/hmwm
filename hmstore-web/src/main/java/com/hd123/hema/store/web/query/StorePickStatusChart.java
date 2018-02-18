/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-web
 * 文件名：	StorePickStatusChart.java
 * 模块说明：
 * 修改历史：
 * 2016-7-13 - xiepingping - 创建。
 */
package com.hd123.hema.store.web.query;

import java.io.Serializable;

/**
 * @author xiepingping
 * 
 */
public class StorePickStatusChart implements Serializable {
  private static final long serialVersionUID = -1833569302215917199L;

  /** 创建小时 */
  private int createHours;
  /** 订单数量 */
  private int orderCount;

  public int getCreateHours() {
    return createHours;
  }

  public void setCreateHours(int createHours) {
    this.createHours = createHours;
  }

  public int getOrderCount() {
    return orderCount;
  }

  public void setOrderCount(int orderCount) {
    this.orderCount = orderCount;
  }

}
