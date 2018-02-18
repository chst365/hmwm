package com.hd123.hema.store.web.system.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SSFunction implements Serializable {
  private static final long serialVersionUID = 2849091839199253268L;

  private String name;
  private String url;
  private String iconClass;

  private List<SSFunction> sChild = new ArrayList<SSFunction>();

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

  public List<SSFunction> getsChild() {
    return sChild;
  }

  public void setsChild(List<SSFunction> sChild) {
    this.sChild = sChild;
  }

}
