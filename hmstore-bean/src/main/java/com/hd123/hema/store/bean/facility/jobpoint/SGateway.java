/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-bean
 * 文件名：	SGateway.java
 * 模块说明：
 * 修改历史：
 * 2016-7-27 - xiepingping - 创建。
 */
package com.hd123.hema.store.bean.facility.jobpoint;

import com.hd123.hema.store.bean.common.FieldLength;
import com.hd123.hema.store.bean.common.Validator;
import com.hd123.rumba.commons.lang.Assert;
import com.hd123.wms.antman.common.bean.UuidBean;

/**
 * @author xiepingping
 * 
 */
public class SGateway extends UuidBean implements Validator {
  private static final long serialVersionUID = -1078710561059053829L;

  /** 代码 */
  private String code;
  /** IP */
  private String ip;
  /** 端口号 */
  private String port;

  public SGateway() {
    super();
  }

  public SGateway(String uuid, String code, String ip, String port) {
    super();
    super.setUuid(uuid);
    this.code = code;
    this.ip = ip;
    this.port = port;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    Assert.assertStringNotTooLong(code, FieldLength.LENGTH_10, "SGateway.code");
    this.code = code;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    Assert.assertStringNotTooLong(ip, FieldLength.LENGTH_15, "SGateway.ip");
    this.ip = ip;
  }

  public String getPort() {
    return port;
  }

  public void setPort(String port) {
    Assert.assertStringNotTooLong(port, FieldLength.LENGTH_5, "SGateway.port");
    this.port = port;
  }

  @Override
  public void validate() {
    Assert.assertStringNotTooLong(code, FieldLength.LENGTH_10, "SGateway.code");
    Assert.assertStringNotTooLong(ip, FieldLength.LENGTH_15, "SGateway.ip");
    Assert.assertStringNotTooLong(port, FieldLength.LENGTH_5, "SGateway.port");

    Assert.assertArgumentNotNull(getUuid(), "SGateway.uuid");
  }

}
