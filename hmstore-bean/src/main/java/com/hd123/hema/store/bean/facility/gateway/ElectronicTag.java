/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-bean
 * 文件名：	ElectronicTag.java
 * 模块说明：
 * 修改历史：
 * 2016-6-15 - xiepingping - 创建。
 */
package com.hd123.hema.store.bean.facility.gateway;

import java.util.Date;

import com.hd123.hema.store.bean.common.FieldLength;
import com.hd123.hema.store.bean.common.Validator;
import com.hd123.rumba.commons.biz.entity.Entity;
import com.hd123.rumba.commons.lang.Assert;

/**
 * 电子标签
 * 
 * @author xiepingping
 * 
 */
public class ElectronicTag extends Entity implements Validator {
  private static final long serialVersionUID = 1163275122379451466L;

  /** 节点代码 */
  private String nodeCode;
  /** 节点地址 */
  private String nodeAddress;
  /** 节点类型 */
  private NodeType nodeType = NodeType.PickTag;
  /** 节点用途 */
  private NodeUsage nodeUsage = NodeUsage.PickDisplayQty;
  /** 节点状态 */
  private NodeState nodeState = NodeState.Normal;
  /** 检测时间 */
  private Date checkTime;
  /** 说明 */
  private String remark;

  /** 网关UUID */
  private String gatewayUuid;

  public String getNodeCode() {
    return nodeCode;
  }

  public void setNodeCode(String nodeCode) {
    Assert.assertStringNotTooLong(nodeCode, FieldLength.LENGTH_10, "electronictag.nodeCode");
    Assert.assertArgumentNotNull(nodeCode, "electronictag.nodeCode");
    this.nodeCode = nodeCode;
  }

  public String getNodeAddress() {
    return nodeAddress;
  }

  public void setNodeAddress(String nodeAddress) {
    Assert.assertStringNotTooLong(nodeAddress, FieldLength.LENGTH_10, "electronictag.nodeAddress");
    Assert.assertArgumentNotNull(nodeAddress, "electronictag.nodeAddress");
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

  public NodeState getNodeState() {
    return nodeState;
  }

  public void setNodeState(NodeState nodeState) {
    this.nodeState = nodeState;
  }

  public Date getCheckTime() {
    return checkTime;
  }

  public void setCheckTime(Date checkTime) {
    this.checkTime = checkTime;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getGatewayUuid() {
    return gatewayUuid;
  }

  public void setGatewayUuid(String gatewayUuid) {
    Assert.assertStringNotTooLong(gatewayUuid, FieldLength.LENGTH_32, "electronictag.categoryName");
    Assert.assertArgumentNotNull(gatewayUuid, "electronictag.gatewayUuid");
    this.gatewayUuid = gatewayUuid;
  }

  @Override
  public void validate() {
    Assert.assertStringNotTooLong(nodeCode, FieldLength.LENGTH_10, "electronictag.nodeCode");
    Assert.assertStringNotTooLong(nodeAddress, FieldLength.LENGTH_10, "electronictag.nodeAddress");
    Assert.assertStringNotTooLong(gatewayUuid, FieldLength.LENGTH_32, "electronictag.categoryName");

    Assert.assertArgumentNotNull(nodeCode, "electronictag.nodeCode");
    Assert.assertArgumentNotNull(nodeAddress, "electronictag.nodeAddress");
    Assert.assertArgumentNotNull(gatewayUuid, "electronictag.gatewayUuid");

  }

}
