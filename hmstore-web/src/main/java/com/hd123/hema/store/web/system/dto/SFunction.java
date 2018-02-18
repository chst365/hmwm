package com.hd123.hema.store.web.system.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SFunction  implements Serializable{

  private static final long serialVersionUID = 2038491472739537851L;
  private String uuid;
  private String name;
  private String url;
  private String iconClass;
  private boolean check;
  private String upperFunctionUuid;

  private List<SFunction> child = new ArrayList<SFunction>();
  
  
  
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getIconClass() {
    return iconClass;
  }

  public void setIconClass(String iconClass) {
    this.iconClass = iconClass;
  }


  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public boolean isCheck() {
    return check;
  }

  public void setCheck(boolean check) {
    this.check = check;
  }

  public String getUpperFunctionUuid() {
    return upperFunctionUuid;
  }

  public void setUpperFunctionUuid(String upperFunctionUuid) {
    this.upperFunctionUuid = upperFunctionUuid;
  }

  public List<SFunction> getChild() {
    return child;
  }

  public void setChild(List<SFunction> child) {
    this.child = child;
  }

}
