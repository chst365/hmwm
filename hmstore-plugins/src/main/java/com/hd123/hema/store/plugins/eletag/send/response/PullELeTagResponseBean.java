/**
 * 业务公用实现。
 * 
 * 项目名： hmstore-plugins
 * 文件名： PullGoodsResponseBean.java
 * 模块说明：
 * 修改历史：
 * 2016-7-21 - xiepingping - 创建。
 */
package com.hd123.hema.store.plugins.eletag.send.response;

import java.util.ArrayList;
import java.util.List;

import com.hd123.hema.store.bean.facility.gateway.Gateway;
import com.hd123.wms.mm.wdk.common.bean.WDKResponseBean;

/**
 * @author xiepingping
 * 
 */
public class PullELeTagResponseBean extends WDKResponseBean {
  private static final long serialVersionUID = -1314878631429771275L;

  private List<Gateway> items = new ArrayList<Gateway>();

  public PullELeTagResponseBean() {
    super();
  }

  public PullELeTagResponseBean(int code, String message) {
    super(code, message);
  }

  public List<Gateway> getItems() {
    return items;
  }

  public void setItems(List<Gateway> items) {
    this.items = items;
  }

}
