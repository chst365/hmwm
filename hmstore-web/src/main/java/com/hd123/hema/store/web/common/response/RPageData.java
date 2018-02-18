/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-web
 * 文件名：	RPageData.java
 * 模块说明：
 * 修改历史：
 * 2016-6-17 - xiepingping - 创建。
 */
package com.hd123.hema.store.web.common.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiepingping
 *
 */
public class RPageData<T extends Serializable> implements Serializable {
  private static final long serialVersionUID = 9008166982902002197L;

  /** 总记录数 */
  private int totalCount;

  /** 总页数 */
  private int pageCount;

  /** 当前页序号，从0计数 */
  private int currentPage;

  /** 数据 */
  private List<T> values;

  public RPageData() {
    values = new ArrayList();
  }

  /**
   * 总记录数
   * 
   */
  public int getTotalCount() {
    return totalCount;
  }

  /**
   * 设置总记录数
   * 
   * @param totalCount
   */
  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
  }

  /**
   * 总页数
   * 
   */
  public int getPageCount() {
    return pageCount;
  }

  /**
   * 设置总页数
   * 
   * @param pageCount
   */
  public void setPageCount(int pageCount) {
    this.pageCount = pageCount;
  }

  /**
   * 当前页
   * 
   */
  public int getCurrentPage() {
    return currentPage;
  }

  /**
   * 设置当前页
   * 
   * @param currentPage
   */
  public void setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
  }

  /**
   * 当前页记录数
   * 
   */
  public int getRowCount() {
    return values == null ? 0 : values.size();
  }

  /**
   * 数据
   * 
   */
  public List<T> getValues() {
    return values;
  }

  /**
   * 设置数据
   * 
   * @param values
   */
  public void setValues(List<T> values) {
    this.values = values;
  }

  /**
   * 获取一行数据
   * 
   * @param index
   *          数据行号
   */
  public T getValue(int index) {
    return values == null ? null : values.get(index);
  }
}
