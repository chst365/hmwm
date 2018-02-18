package com.hd123.hema.store.web.facility.pickscheme.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hd123.hema.store.bean.facility.pickscheme.BinArticle;
import com.hd123.hema.store.bean.facility.pickscheme.PickSchemeState;
import com.hd123.rumba.commons.biz.entity.HasVersion;

public class SPickScheme {
  
  /** 默认以及起始版本号。 */
  public static final long DEFAULT_VERSION = HasVersion.START_VERSION;
  
  private long version = HasVersion.START_VERSION;
  private Date versionTime;
  
  private String uuid;
  /** 代码 */
  private String code;
  /** 名称 */
  private String name;
  /** 状态 */
  private PickSchemeState state = PickSchemeState.UnEffective;
  /** 组织UUID */
  private String orgUuid;

  /** 作业点 */
  private String jobPoint;
  /** 货位商品明细 */
  private List<BinArticle> binArticle = new ArrayList<BinArticle>();
  
  
  public long getVersion() {
    return version;
  }
  public void setVersion(long version) {
    this.version = version;
  }
  public Date getVersionTime() {
    return versionTime;
  }
  public void setVersionTime(Date versionTime) {
    this.versionTime = versionTime;
  }
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
  public PickSchemeState getState() {
    return state;
  }
  public void setState(PickSchemeState state) {
    this.state = state;
  }
  public String getOrgUuid() {
    return orgUuid;
  }
  public void setOrgUuid(String orgUuid) {
    this.orgUuid = orgUuid;
  }
  public String getJobPoint() {
    return jobPoint;
  }
  public void setJobPoint(String jobPoint) {
    this.jobPoint = jobPoint;
  }
  public List<BinArticle> getBinArticle() {
    return binArticle;
  }
  public void setBinArticle(List<BinArticle> binArticle) {
    this.binArticle = binArticle;
  }


}
