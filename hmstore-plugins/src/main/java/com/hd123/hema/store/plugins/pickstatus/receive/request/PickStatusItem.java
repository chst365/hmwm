package com.hd123.hema.store.plugins.pickstatus.receive.request;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author xiepingping
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PickStatusItem implements Serializable {
  private static final long serialVersionUID = -8806914382528240767L;

  /** 批次号 */
  private String batchId;
  /** 状态 */
  private int state;

  public String getBatchId() {
    return batchId;
  }

  public void setBatchId(String batchId) {
    this.batchId = batchId;
  }

  public int getState() {
    return state;
  }

  public void setState(int state) {
    this.state = state;
  }

}
