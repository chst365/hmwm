/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-plugins
 * 文件名：	EleTagStatus.java
 * 模块说明：
 * 修改历史：
 * 2016-7-22 - xiepingping - 创建。
 */
package com.hd123.hema.store.plugins.pickstatus.receive.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiepingping
 * 
 */
public class PickStatus implements Serializable {
  private static final long serialVersionUID = -7550446039870391819L;

  private String storeCode;
  private List<PickStatusItem> statusItems = new ArrayList<PickStatusItem>();

  public String getStoreCode() {
    return storeCode;
  }

  public void setStoreCode(String storeCode) {
    this.storeCode = storeCode;
  }

  public List<PickStatusItem> getStatusItems() {
    return statusItems;
  }

  public void setStatusItems(List<PickStatusItem> statusItems) {
    this.statusItems = statusItems;
  }

}
