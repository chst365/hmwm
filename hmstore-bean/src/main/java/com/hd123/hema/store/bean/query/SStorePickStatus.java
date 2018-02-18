/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-bean
 * 文件名：	PickStatus.java
 * 模块说明：
 * 修改历史：
 * 2016-7-22 - xiepingping - 创建。
 */
package com.hd123.hema.store.bean.query;

import java.io.Serializable;

/**
 * @author xiepingping
 * 
 */
public class SStorePickStatus implements Serializable {
  private static final long serialVersionUID = -1993838300101596632L;

  /** 批次号 */
  private String batchId;
  /** 状态 */
  private int state;
  /** 门店代码 */
  private String storeCode;

  public String getBatchId() {
    return batchId;
  }

  public void setBatchId(String batchId) {
    this.batchId = batchId;
  }

  public int getState() {
    return state;
  }

  public void setState(int state) {
    this.state = state;
  }

  public String getStoreCode() {
    return storeCode;
  }

  public void setStoreCode(String storeCode) {
    this.storeCode = storeCode;
  }

}
