/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-web
 * 文件名：	SRole.java
 * 模块说明：	
 * 修改历史：
 * 2016-7-15 - xiepingping - 创建。
 */
package com.hd123.hema.store.web.system.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author xiepingping
 * 
 */
public class SRole implements Serializable {
  private static final long serialVersionUID = -8777374548236983160L;

  private String uuid;
  private String code;
  private String name;
  private String operator;
  /** 组织 */
  private String orgUuid;
  private List<SFunction> functions = new ArrayList<SFunction>();
  


  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public String getModifier() {
    return modifier;
  }

  public void setModifier(String modifier) {
    this.modifier = modifier;
  }

  public String getOrgUuid() {
    return orgUuid;
  }

  public void setOrgUuid(String orgUuid) {
    this.orgUuid = orgUuid;
  }

  private String modifier;


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

  public List<SFunction> getFunctions() {
    return functions;
  }

  public void setFunctions(List<SFunction> functions) {
    this.functions = functions;
  }



}
