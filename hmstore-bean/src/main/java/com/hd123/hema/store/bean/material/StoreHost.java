/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-bean
 * 文件名：	StoreHost.java
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
 * 门店主机
 * 
 * @author xiepingping
 * 
 */
public class StoreHost extends Entity implements Validator {
  private static final long serialVersionUID = -3931342189876090775L;

  /** 名称 */
  private String name;
  /** IP */
  private String ip;
  /** MAC地址 */
  private String macAddress;
  /** 允许访问API */
  private boolean allowAccess;
  /** 门店UUID */
  private String storeUuid;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    Assert.assertStringNotTooLong(name, FieldLength.LENGTH_30, "storehost.name");
    Assert.assertArgumentNotNull(name, "storehost.name");
    this.name = name;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    Assert.assertStringNotTooLong(ip, FieldLength.LENGTH_15, "storehost.ip");
    Assert.assertArgumentNotNull(ip, "storehost.ip");
    this.ip = ip;
  }

  public String getMacAddress() {
    return macAddress;
  }

  public void setMacAddress(String macAddress) {
    Assert.assertStringNotTooLong(macAddress, FieldLength.LENGTH_17, "storehost.macAddress");
    Assert.assertArgumentNotNull(macAddress, "storehost.macAddress");
    this.macAddress = macAddress;
  }

  public boolean isAllowAccess() {
    return allowAccess;
  }

  public void setAllowAccess(boolean allowAccess) {
    this.allowAccess = allowAccess;
  }

  public String getStoreUuid() {
    return storeUuid;
  }

  public void setStoreUuid(String storeUuid) {
    Assert.assertStringNotTooLong(storeUuid, FieldLength.LENGTH_32, "storehost.storeUuid");
    Assert.assertArgumentNotNull(storeUuid, "storehost.storeUuid");
    this.storeUuid = storeUuid;
  }

  @Override
  public void validate() {
    Assert.assertStringNotTooLong(name, FieldLength.LENGTH_30, "storehost.name");
    Assert.assertStringNotTooLong(ip, FieldLength.LENGTH_15, "storehost.ip");
    Assert.assertStringNotTooLong(macAddress, FieldLength.LENGTH_17, "storehost.macAddress");
    Assert.assertStringNotTooLong(storeUuid, FieldLength.LENGTH_32, "storehost.storeUuid");

    Assert.assertArgumentNotNull(name, "storehost.name");
    Assert.assertArgumentNotNull(ip, "storehost.ip");
    Assert.assertArgumentNotNull(macAddress, "storehost.macAddress");
    Assert.assertArgumentNotNull(storeUuid, "storehost.storeUuid");
  }

}
