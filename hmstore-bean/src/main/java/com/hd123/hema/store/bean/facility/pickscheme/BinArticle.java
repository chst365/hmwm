/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-bean
 * 文件名：	BinArticle.java
 * 模块说明：
 * 修改历史：
 * 2016-6-15 - xiepingping - 创建。
 */
package com.hd123.hema.store.bean.facility.pickscheme;

import com.hd123.hema.store.bean.common.FieldLength;
import com.hd123.hema.store.bean.common.Validator;
import com.hd123.rumba.commons.biz.entity.Entity;
import com.hd123.rumba.commons.biz.entity.UCN;
import com.hd123.rumba.commons.lang.Assert;

/**
 * 拣货方案货位商品对应关系
 * 
 * @author xiepingping
 * 
 */
public class BinArticle extends Entity implements Validator {
  private static final long serialVersionUID = 3252039163742198667L;

  /** 货位代码 */
  private String binCode;
  /** 商品 */
  private UCN article;
  /** 拣货方案UUID */
  private String pickSchemeUuid;

  public String getBinCode() {
    return binCode;
  }

  public void setBinCode(String binCode) {
    Assert.assertStringNotTooLong(binCode, FieldLength.LENGTH_10, "binArticle.binCode");
    Assert.assertArgumentNotNull(binCode, "binArticle.binCode");
    this.binCode = binCode;
  }

  public UCN getArticle() {
    return article;
  }

  public void setArticle(UCN article) {
    this.article = article;
  }

  public String getPickSchemeUuid() {
    return pickSchemeUuid;
  }

  public void setPickSchemeUuid(String pickSchemeUuid) {
    Assert.assertStringNotTooLong(pickSchemeUuid, FieldLength.LENGTH_32, "binArticle.pickSchemeUuid");
    Assert.assertArgumentNotNull(pickSchemeUuid, "binArticle.pickSchemeUuid");
    this.pickSchemeUuid = pickSchemeUuid;
  }

  @Override
  public void validate() {
    Assert.assertStringNotTooLong(binCode, FieldLength.LENGTH_10, "binArticle.binCode");
    Assert.assertStringNotTooLong(pickSchemeUuid, FieldLength.LENGTH_32, "binArticle.pickSchemeUuid");

    Assert.assertArgumentNotNull(binCode, "binArticle.binCode");
    Assert.assertArgumentNotNull(pickSchemeUuid, "binArticle.pickSchemeUuid");
  }

}
