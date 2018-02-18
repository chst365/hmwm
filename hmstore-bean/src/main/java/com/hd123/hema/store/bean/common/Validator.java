/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-bean
 * 文件名：	Validator.java
 * 模块说明：
 * 修改历史：
 * 2016-6-29 - xiepingping - 创建。
 */
package com.hd123.hema.store.bean.common;

/**
 * @author xiepingping
 *
 */
public interface Validator {

  /**
   * 执行较验。
   * 
   * @throws IllegalArgumentException
   *           较验不合法时，抛出。
   */
  void validate();
}
