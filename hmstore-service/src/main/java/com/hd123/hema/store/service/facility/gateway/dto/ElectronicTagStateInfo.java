/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-service
 * 文件名：	ElectronicTagResult.java
 * 模块说明：
 * 修改历史：
 * 2016-6-28 - xiepingping - 创建。
 */
package com.hd123.hema.store.service.facility.gateway.dto;

import java.io.Serializable;
import java.util.Date;

import com.hd123.hema.store.bean.facility.gateway.NodeState;

/**
 * @author xiepingping
 * 
 */
public class ElectronicTagStateInfo implements Serializable {
  private static final long serialVersionUID = -8516208441036330436L;

  private String nodeCode;
  /** 标签地址 */
  private String nodeAddress;
  /** 标签状态 */
  private NodeState nodeState;
  /** 检测时间 */
  private Date checkTime;
  /** 说明 */
  private String remark;

  public String getNodeCode() {
    return nodeCode;
  }

  public void setNodeCode(String nodeCode) {
    this.nodeCode = nodeCode;
  }

  public String getNodeAddress() {
    return nodeAddress;
  }

  public void setNodeAddress(String nodeAddress) {
    this.nodeAddress = nodeAddress;
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

}
