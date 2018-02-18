/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-web
 * 文件名：	SUser.java
 * 模块说明：
 * 修改历史：
 * 2016-7-16 - xiepingping - 创建。
 */
package com.hd123.hema.store.web.system.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.hd123.wms.antman.common.bean.UCN;

/**
 * @author xiepingping
 * 
 */
public class SUser implements Serializable {
  private static final long serialVersionUID = 5495803564654206577L;

  private String uuid;
  /** 代码 */
  private String code;
  /** 名称 */
  private String name;
  /** 密码 */
  private String password;
  /** 联系电话 */
  private String mobile;
  /** 所属组织 */
  private UCN org;
  /** 操作人 */
  private String operator;
  /** 所属角色 */
  private List<UCN> roles = new ArrayList<UCN>();

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public UCN getOrg() {
    return org;
  }

  public void setOrg(UCN org) {
    this.org = org;
  }

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public List<UCN> getRoles() {
    return roles;
  }

  public void setRoles(List<UCN> roles) {
    this.roles = roles;
  }

}
