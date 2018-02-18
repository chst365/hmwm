/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-plugins
 * 文件名：	PullPickSchemeResponseBean.java
 * 模块说明：
 * 修改历史：
 * 2016-8-1 - xiepingping - 创建。
 */
package com.hd123.hema.store.plugins.pickscheme.send.response;

import java.util.ArrayList;
import java.util.List;

import com.hd123.hema.store.bean.facility.pickscheme.PickScheme;
import com.hd123.wms.mm.wdk.common.bean.WDKResponseBean;

/**
 * @author xiepingping
 * 
 */
public class PullPickSchemeResponseBean extends WDKResponseBean {
  private static final long serialVersionUID = 6930280060779326061L;

  private List<PickScheme> items = new ArrayList<PickScheme>();

  public PullPickSchemeResponseBean() {
    super();
  }

  public PullPickSchemeResponseBean(int code, String message) {
    super(code, message);
  }

  public List<PickScheme> getItems() {
    return items;
  }

  public void setItems(List<PickScheme> items) {
    this.items = items;
  }

}
