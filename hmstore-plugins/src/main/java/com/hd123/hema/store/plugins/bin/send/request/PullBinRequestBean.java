/**
 * 业务公用实现。
 * 
 * 项目名： hmstore-plugins
 * 文件名： PullBinRequestBean.java
 * 模块说明：
 * 修改历史：
 * 2016-7-21 - xiepingping - 创建。
 */
package com.hd123.hema.store.plugins.bin.send.request;

import com.hd123.wms.mm.wdk.common.bean.WDKRequestBean;

/**
 * @author xiepingping
 * 
 */
public class PullBinRequestBean extends WDKRequestBean {
  private static final long serialVersionUID = 9198105615779640552L;

  private String storeCode;

  public String getStoreCode() {
    return storeCode;
  }

  public void setStoreCode(String storeCode) {
    this.storeCode = storeCode;
  }
}
