/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-bean
 * 文件名：	Store.java
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
 * 门店
 * 
 * @author xiepingping
 * 
 */
public class Store extends Entity implements Validator {
  private static final long serialVersionUID = 917354528720855901L;

  /** 代码 */
  private String code;
  /** 名称 */
  private String name;
  /** 地址 */
  private String address;
  /** 备注 */
  private String remark;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    Assert.assertStringNotTooLong(code, FieldLength.LENGTH_30, "store.code");
    Assert.assertArgumentNotNull(code, "store.code");
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    Assert.assertStringNotTooLong(name, FieldLength.LENGTH_30, "store.name");
    Assert.assertArgumentNotNull(name, "store.name");
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  @Override
  public void validate() {
    Assert.assertStringNotTooLong(code, FieldLength.LENGTH_30, "store.code");
    Assert.assertStringNotTooLong(name, FieldLength.LENGTH_30, "store.name");

    Assert.assertArgumentNotNull(code, "store.code");
    Assert.assertArgumentNotNull(name, "store.name");
  }

}
