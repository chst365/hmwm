/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-bean
 * 文件名：	RplEleTag.java
 * 模块说明：
 * 修改历史：
 * 2016-6-15 - xiepingping - 创建。
 */
package com.hd123.hema.store.bean.facility.jobpoint;

import com.hd123.hema.store.bean.common.FieldLength;
import com.hd123.hema.store.bean.common.Validator;
import com.hd123.hema.store.bean.facility.gateway.NodeType;
import com.hd123.hema.store.bean.facility.gateway.NodeUsage;
import com.hd123.rumba.commons.biz.entity.Entity;
import com.hd123.rumba.commons.lang.Assert;

/**
 * 补货标签
 * 
 * @author xiepingping
 * 
 */
public class RplEleTag extends Entity  implements Validator{
  private static final long serialVersionUID = 4488196207362539751L;

  /** 请求节点代码 */
  private String requestNodeCode;
  /** 请求节点地址 */
  private String requestNodeAddress;
  /** 请求节点类型 */
  private NodeType requestNodeType;
  /** 请求节点用途 */
  private NodeUsage requestNodeUsage;
  /** 答应节点代码 */
  private String responseNodeCode;
  /** 答应节点地址 */
  private String responseNodeAddress;
  /** 答应节点类型 */
  private NodeType responseNodeType;
  /** 答应节点用途 */
  private NodeUsage responseNodeUsage;
  /** 区段UUID */
  private String sectionUuid;

  public String getRequestNodeCode() {
    return requestNodeCode;
  }

  public void setRequestNodeCode(String requestNodeCode) {
    Assert.assertStringNotTooLong(requestNodeCode, FieldLength.LENGTH_10, "rpleletag.requestNodeCode");
    Assert.assertArgumentNotNull(requestNodeCode, "rpleletag.requestNodeCode");
    this.requestNodeCode = requestNodeCode;
  }

  public String getRequestNodeAddress() {
    return requestNodeAddress;
  }

  public void setRequestNodeAddress(String requestNodeAddress) {
    Assert.assertStringNotTooLong(requestNodeAddress, FieldLength.LENGTH_10, "rpleletag.requestNodeAddress");
    Assert.assertArgumentNotNull(requestNodeAddress, "rpleletag.requestNodeAddress");
    this.requestNodeAddress = requestNodeAddress;
  }

  public NodeType getRequestNodeType() {
    return requestNodeType;
  }

  public void setRequestNodeType(NodeType requestNodeType) {
    this.requestNodeType = requestNodeType;
  }

  public NodeUsage getRequestNodeUsage() {
    return requestNodeUsage;
  }

  public void setRequestNodeUsage(NodeUsage requestNodeUsage) {
    this.requestNodeUsage = requestNodeUsage;
  }

  public String getResponseNodeCode() {
    return responseNodeCode;
  }

  public void setResponseNodeCode(String responseNodeCode) {
    Assert.assertStringNotTooLong(responseNodeCode, FieldLength.LENGTH_10, "rpleletag.responseNodeCode");
    Assert.assertArgumentNotNull(responseNodeCode, "rpleletag.responseNodeCode");
    this.responseNodeCode = responseNodeCode;
  }

  public String getResponseNodeAddress() {
    return responseNodeAddress;
  }

  public void setResponseNodeAddress(String responseNodeAddress) {
    Assert.assertStringNotTooLong(responseNodeAddress, FieldLength.LENGTH_10, "rpleletag.responseNodeAddress");
    Assert.assertArgumentNotNull(responseNodeAddress, "rpleletag.responseNodeAddress");
    this.responseNodeAddress = responseNodeAddress;
  }

  public NodeType getResponseNodeType() {
    return responseNodeType;
  }

  public void setResponseNodeType(NodeType responseNodeType) {
    this.responseNodeType = responseNodeType;
  }

  public NodeUsage getResponseNodeUsage() {
    return responseNodeUsage;
  }

  public void setResponseNodeUsage(NodeUsage responseNodeUsage) {
    this.responseNodeUsage = responseNodeUsage;
  }

  public String getSectionUuid() {
    return sectionUuid;
  }

  public void setSectionUuid(String sectionUuid) {
    Assert.assertStringNotTooLong(sectionUuid, FieldLength.LENGTH_32, "rpleletag.sectionUuid");
    Assert.assertArgumentNotNull(sectionUuid, "rpleletag.sectionUuid");
    this.sectionUuid = sectionUuid;
  }

  @Override
  public void validate() {
    // TODO Auto-generated method stub
    Assert.assertStringNotTooLong(requestNodeCode, FieldLength.LENGTH_10, "rpleletag.requestNodeCode");
    Assert.assertStringNotTooLong(requestNodeAddress, FieldLength.LENGTH_10, "rpleletag.requestNodeAddress");
    Assert.assertStringNotTooLong(responseNodeCode, FieldLength.LENGTH_10, "rpleletag.responseNodeCode");
    Assert.assertStringNotTooLong(responseNodeAddress, FieldLength.LENGTH_10, "rpleletag.responseNodeAddress");
    Assert.assertStringNotTooLong(sectionUuid, FieldLength.LENGTH_32, "rpleletag.sectionUuid");
    
    Assert.assertArgumentNotNull(requestNodeCode, "rpleletag.requestNodeCode");
    Assert.assertArgumentNotNull(requestNodeAddress, "rpleletag.requestNodeAddress");
    Assert.assertArgumentNotNull(responseNodeCode, "rpleletag.responseNodeCode");
    Assert.assertArgumentNotNull(responseNodeAddress, "rpleletag.responseNodeAddress");
    Assert.assertArgumentNotNull(sectionUuid, "rpleletag.sectionUuid");
    
  }

}
