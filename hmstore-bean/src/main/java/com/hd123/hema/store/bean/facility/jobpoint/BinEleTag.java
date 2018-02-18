/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-bean
 * 文件名：	BinEleTag.java
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
 * 货位标签
 * 
 * @author xiepingping
 * 
 */
public class BinEleTag extends Entity implements Validator {
  private static final long serialVersionUID = -3011673047316379542L;

  /** 货位代码 */
  private String binCode;
  /** 节点代码 */
  private String nodeCode;
  /** 节点地址 */
  private String nodeAddress;
  /** 节点类型 */
  private NodeType nodeType;
  /** 节点用途 */
  private NodeUsage nodeUsage;
  /** 区段UUID */
  private String sectionUuid;

  public String getBinCode() {
    return binCode;
  }

  public void setBinCode(String binCode) {
    Assert.assertStringNotTooLong(binCode, FieldLength.LENGTH_10, "bineletag.binCode");
    Assert.assertArgumentNotNull(binCode, "bineletag.binCode");
    this.binCode = binCode;
  }

  public String getNodeCode() {
    return nodeCode;
  }

  public void setNodeCode(String nodeCode) {
    Assert.assertStringNotTooLong(nodeCode, FieldLength.LENGTH_10, "bineletag.nodeCode");
    Assert.assertArgumentNotNull(nodeCode, "bineletag.nodeCode");
    this.nodeCode = nodeCode;
  }

  public String getNodeAddress() {
    return nodeAddress;
  }

  public void setNodeAddress(String nodeAddress) {
    Assert.assertStringNotTooLong(nodeAddress, FieldLength.LENGTH_10, "bineletag.nodeAddress");
    Assert.assertArgumentNotNull(nodeAddress, "bineletag.nodeAddress");
    this.nodeAddress = nodeAddress;
  }

  public NodeType getNodeType() {
    return nodeType;
  }

  public void setNodeType(NodeType nodeType) {
    this.nodeType = nodeType;
  }

  public NodeUsage getNodeUsage() {
    return nodeUsage;
  }

  public void setNodeUsage(NodeUsage nodeUsage) {
    this.nodeUsage = nodeUsage;
  }

  public String getSectionUuid() {
    return sectionUuid;
  }

  public void setSectionUuid(String sectionUuid) {
    Assert.assertStringNotTooLong(sectionUuid, FieldLength.LENGTH_32, "bineletag.sectionUuid");
    Assert.assertArgumentNotNull(sectionUuid, "bineletag.sectionUuid");
    this.sectionUuid = sectionUuid;
  }

  @Override
  public void validate() {

    Assert.assertStringNotTooLong(binCode, FieldLength.LENGTH_10, "bineletag.binCode");
    Assert.assertStringNotTooLong(nodeCode, FieldLength.LENGTH_10, "bineletag.nodeCode");
    Assert.assertStringNotTooLong(nodeAddress, FieldLength.LENGTH_10, "bineletag.nodeAddress");
    Assert.assertStringNotTooLong(sectionUuid, FieldLength.LENGTH_32, "bineletag.sectionUuid");

    Assert.assertArgumentNotNull(binCode, "bineletag.binCode");
    Assert.assertArgumentNotNull(nodeCode, "bineletag.nodeCode");
    Assert.assertArgumentNotNull(nodeAddress, "bineletag.nodeAddress");
    Assert.assertArgumentNotNull(sectionUuid, "bineletag.sectionUuid");

  }

}
