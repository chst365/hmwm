/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-plugins
 * 文件名：	PullPickSchemeRequestBean.java
 * 模块说明：
 * 修改历史：
 * 2016-8-1 - xiepingping - 创建。
 */
package com.hd123.hema.store.plugins.pickscheme.send.request;

import com.hd123.wms.mm.wdk.common.bean.WDKRequestBean;

/**
 * @author xiepingping
 * 
 */
public class PullPickSchemeRequestBean extends WDKRequestBean {
  private static final long serialVersionUID = -2414825930181239893L;

  private String storeCode;

  public String getStoreCode() {
    return storeCode;
  }

  public void setStoreCode(String storeCode) {
    this.storeCode = storeCode;
  }
}
