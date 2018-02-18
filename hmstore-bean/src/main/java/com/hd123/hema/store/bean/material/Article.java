/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-bean
 * 文件名：	Article.java
 * 模块说明：
 * 修改历史：
 * 2016-6-15 - xiepingping - 创建。
 */
package com.hd123.hema.store.bean.material;

import com.hd123.hema.store.bean.common.FieldLength;
import com.hd123.hema.store.bean.common.Validator;
import com.hd123.rumba.commons.biz.entity.Entity;
import com.hd123.rumba.commons.lang.Assert;

/**
 * 商品
 * 
 * @author xiepingping
 * 
 */
public class Article extends Entity implements Validator {
  private static final long serialVersionUID = 8478636124308991698L;
  public static final String CAPTION = "商品";

  /** 代码 */
  private String code;
  /** 名称 */
  private String name;
  /** 类别代码 */
  private String categoryCode;
  /** 类别名称 */
  private String categoryName;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    Assert.assertStringNotTooLong(code, FieldLength.LENGTH_20, "article.code");
    Assert.assertArgumentNotNull(code, "article.code");

    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    Assert.assertStringNotTooLong(name, FieldLength.LENGTH_80, "article.name");
    Assert.assertArgumentNotNull(code, "article.name");

    this.name = name;
  }

  public String getCategoryCode() {
    return categoryCode;
  }

  public void setCategoryCode(String categoryCode) {
    Assert.assertStringNotTooLong(categoryCode, FieldLength.LENGTH_13, "article.categoryCode");

    this.categoryCode = categoryCode;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    Assert.assertStringNotTooLong(categoryName, FieldLength.LENGTH_80, "article.categoryName");

    this.categoryName = categoryName;
  }

  @Override
  public void validate() {
    Assert.assertStringNotTooLong(code, FieldLength.LENGTH_20, "article.code");
    Assert.assertStringNotTooLong(name, FieldLength.LENGTH_80, "article.name");
    Assert.assertStringNotTooLong(categoryCode, FieldLength.LENGTH_13, "article.categoryCode");
    Assert.assertStringNotTooLong(categoryName, FieldLength.LENGTH_80, "article.categoryName");

    Assert.assertArgumentNotNull(code, "article.code");
    Assert.assertArgumentNotNull(name, "article.name");
  }

}
