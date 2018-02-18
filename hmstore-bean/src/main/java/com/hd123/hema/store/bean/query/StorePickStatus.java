/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-bean
 * 文件名：	StorePickStatus.java
 * 模块说明：
 * 修改历史：
 * 2016-6-29 - xiepingping - 创建。
 */
package com.hd123.hema.store.bean.query;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiepingping
 * 
 */
public class StorePickStatus implements Serializable {
  private static final long serialVersionUID = 2820267220065796907L;

  /** 批次 */
  private String batchNumber;
  /** 批次名称 */
  private String batchName;
  /** 批次送达时间 */
  private String batchArriveTime;
  /** 订单数 */
  private int orderCount;
  /** 状态 */
  private String pickState;
  /** 创建时间 */
  private Date recordCreateTime;

  public String getBatchNumber() {
    return batchNumber;
  }

  public void setBatchNumber(String batchNumber) {
    this.batchNumber = batchNumber;
  }

  public String getBatchName() {
    return batchName;
  }

  public void setBatchName(String batchName) {
    this.batchName = batchName;
  }

  public String getBatchArriveTime() {
    return batchArriveTime;
  }

  public void setBatchArriveTime(String batchArriveTime) {
    this.batchArriveTime = batchArriveTime;
  }

  public Date getRecordCreateTime() {
    return recordCreateTime;
  }

  public void setRecordCreateTime(Date recordCreateTime) {
    this.recordCreateTime = recordCreateTime;
  }

  public int getOrderCount() {
    return orderCount;
  }

  public void setOrderCount(int orderCount) {
    this.orderCount = orderCount;
  }

  public String getPickState() {
    return String.valueOf(1).equals(pickState) ? "已完成" : "未完成";
  }

  public void setPickState(String pickState) {
    this.pickState = pickState;
  }

}
