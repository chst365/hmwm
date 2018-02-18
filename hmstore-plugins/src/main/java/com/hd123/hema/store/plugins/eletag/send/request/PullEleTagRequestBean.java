/**
 * 业务公用实现。
 * 
 * 项目名： hmstore-plugins
 * 文件名： PullEleTagRequestBean.java
 * 模块说明：
 * 修改历史：
 * 2016-7-21 - xiepingping - 创建。
 */
package com.hd123.hema.store.plugins.eletag.send.request;

import com.hd123.wms.mm.wdk.common.bean.WDKRequestBean;

/**
 * @author xiepingping
 * 
 */
public class PullEleTagRequestBean extends WDKRequestBean {
  private static final long serialVersionUID = -3728514249941649044L;

  private String storeCode;

  public String getStoreCode() {
    return storeCode;
  }

  public void setStoreCode(String storeCode) {
    this.storeCode = storeCode;
  }
}
