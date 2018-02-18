/**
 * 业务公用实现。
 * 
 * 项目名： hmstore-plugins
 * 文件名： PullGoodsResponseBean.java
 * 模块说明：
 * 修改历史：
 * 2016-7-21 - xiepingping - 创建。
 */
package com.hd123.hema.store.plugins.article.send.response;

import java.util.ArrayList;
import java.util.List;

import com.hd123.hema.store.bean.material.Article;
import com.hd123.wms.mm.wdk.common.bean.WDKResponseBean;

/**
 * @author xiepingping
 * 
 */
public class PullArticleResponseBean extends WDKResponseBean {
  private static final long serialVersionUID = 491141973863492429L;

  private List<Article> items = new ArrayList<Article>();

  public PullArticleResponseBean() {
    super();
  }

  public PullArticleResponseBean(int code, String message) {
    super(code, message);
  }

  public List<Article> getItems() {
    return items;
  }

  public void setItems(List<Article> items) {
    this.items = items;
  }

}
