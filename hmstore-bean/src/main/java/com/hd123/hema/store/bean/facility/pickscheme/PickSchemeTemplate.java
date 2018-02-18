/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-bean
 * 文件名：	PickScheme.java
 * 模块说明：
 * 修改历史：
 * 2016-6-15 - xiepingping - 创建。
 */
package com.hd123.hema.store.bean.facility.pickscheme;

import java.util.ArrayList;
import java.util.List;

import com.hd123.rumba.commons.biz.entity.UCN;

/**
 * 拣货方案模板
 * 
 * @author xiepingping
 * 
 */
public class PickSchemeTemplate extends PickScheme {
  private static final long serialVersionUID = -3420815033949976564L;

  /** 设施模板 */
  private UCN facilityTemplate;

  /** 拣货方案门店明细 */
  private List<PickSchemeTemplateStore> pickSchemeStores = new ArrayList<PickSchemeTemplateStore>();

  public UCN getFacilityTemplate() {
    return facilityTemplate;
  }

  public void setFacilityTemplate(UCN facilityTemplate) {
    this.facilityTemplate = facilityTemplate;
  }

  public List<PickSchemeTemplateStore> getPickSchemeStores() {
    return pickSchemeStores;
  }

  public void setPickSchemeStores(List<PickSchemeTemplateStore> pickSchemeStores) {
    this.pickSchemeStores = pickSchemeStores;
  }

  @Override
  public void validate() {
    super.validate();
    for (int i = 0; i < pickSchemeStores.size(); i++) {
      PickSchemeTemplateStore iItem = pickSchemeStores.get(i);
      for (int j = i + 1; j < pickSchemeStores.size(); j++) {
        PickSchemeTemplateStore jItem = pickSchemeStores.get(j);
        if (iItem.getStore().equals(jItem.getStore()))
          throw new IllegalArgumentException("分拣方案模板" + super.getCode() + "分发门店明细第" + (i + 1)
              + "行与第" + (j + 1) + "行重复。");
      }
    }
  }
}
