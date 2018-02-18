/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-bean
 * 文件名：	SectionEleTag.java
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
 * 区段标签
 * 
 * @author xiepingping
 * 
 */
public class SectionEleTag extends Entity implements Validator {
  private static final long serialVersionUID = -1177896127908976958L;

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

  public String getNodeCode() {
    return nodeCode;
  }

  public void setNodeCode(String nodeCode) {
    Assert.assertStringNotTooLong(nodeCode, FieldLength.LENGTH_10, "sectionEleTag.nodeCode");
    Assert.assertArgumentNotNull(nodeCode, "sectionEleTag.nodeCode");
    this.nodeCode = nodeCode;
  }

  public String getNodeAddress() {
    return nodeAddress;
  }

  public void setNodeAddress(String nodeAddress) {
    Assert.assertStringNotTooLong(nodeAddress, FieldLength.LENGTH_10, "sectionEleTag.nodeAddress");
    Assert.assertArgumentNotNull(nodeAddress, "sectionEleTag.nodeAddress");
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
    Assert.assertStringNotTooLong(sectionUuid, FieldLength.LENGTH_32, "sectionEleTag.sectionUuid");
    Assert.assertArgumentNotNull(sectionUuid, "sectionEleTag.sectionUuid");
    this.sectionUuid = sectionUuid;
  }

  @Override
  public void validate() {
    Assert.assertStringNotTooLong(nodeCode, FieldLength.LENGTH_10, "sectionEleTag.nodeCode");
    Assert.assertStringNotTooLong(nodeAddress, FieldLength.LENGTH_10, "sectionEleTag.nodeAddress");
    Assert.assertStringNotTooLong(sectionUuid, FieldLength.LENGTH_32, "sectionEleTag.sectionUuid");

    Assert.assertArgumentNotNull(nodeCode, "sectionEleTag.nodeCode");
    Assert.assertArgumentNotNull(nodeAddress, "sectionEleTag.nodeAddress");
    Assert.assertArgumentNotNull(sectionUuid, "sectionEleTag.sectionUuid");
  }

}
