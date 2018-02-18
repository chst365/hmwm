/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-web
 * 文件名：	RespBean.java
 * 模块说明：
 * 修改历史：
 * 2016-6-17 - xiepingping - 创建。
 */
package com.hd123.hema.store.web.common.response;

import java.io.Serializable;
import java.util.Map;

/**
 * @author xiepingping
 *
 */
public class RespBean implements Serializable {
  private static final long serialVersionUID = -3795643007486442978L;

  private boolean success = true;// 是否成功
  private String msg = "操作成功";// 提示信息
  private Object obj = null;// 回传对象
  private Map<String, Object> attributes;// 其他参数

  public boolean isSuccess() {
      return success;
  }

  public void setSuccess(boolean success) {
      this.success = success;
  }

  public String getMsg() {
      return msg;
  }

  public void setMsg(String msg) {
      this.msg = msg;
  }

  public Object getObj() {
      return obj;
  }

  public void setObj(Object obj) {
      this.obj = obj;
  }

  public Map<String, Object> getAttributes() {
    return attributes;
  }

  public void setAttributes(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

}
