package com.hd123.hema.store.plugins.eletagstatus.receive.request;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.hd123.hema.store.bean.facility.gateway.NodeState;

/**
 * @author xiepingping
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ELeTagStatusItem implements Serializable {
  private static final long serialVersionUID = -8806914382528240767L;

  /** 标签代码 */
  private String nodeCode;
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
