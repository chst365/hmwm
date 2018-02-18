/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-bean
 * 文件名：	PickSchemeStore.java
 * 模块说明：
 * 修改历史：
 * 2016-6-15 - xiepingping - 创建。
 */
package com.hd123.hema.store.bean.facility.pickscheme;

import com.hd123.rumba.commons.biz.entity.Entity;
import com.hd123.rumba.commons.biz.entity.UCN;

/**
 * 拣货方案门店对应关系
 * 
 * @author xiepingping
 * 
 */
public class PickSchemeTemplateStore extends Entity {
  private static final long serialVersionUID = -8593981366257974760L;

  /** 门店 */
  private UCN store;
  /** 拣货方案模板UUID */
  private String pickSchemeTemplateUuid;

  public UCN getStore() {
    return store;
  }

  public void setStore(UCN store) {
    this.store = store;
  }

  public String getPickSchemeTemplateUuid() {
    return pickSchemeTemplateUuid;
  }

  public void setPickSchemeTemplateUuid(String pickSchemeTemplateUuid) {
    this.pickSchemeTemplateUuid = pickSchemeTemplateUuid;
  }

}
