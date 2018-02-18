/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-service
 * 文件名：	StoreService.java
 * 模块说明：
 * 修改历史：
 * 2016-6-23 - xiepingping - 创建。
 */
package com.hd123.hema.store.service.material;

import java.util.List;

import com.hd123.hema.store.bean.material.Store;
import com.hd123.wms.antman.common.query.PageQueryDefinition;
import com.hd123.wms.antman.common.query.PageQueryResult;

/**
 * @author xiepingping
 * 
 */
public interface StoreService {

  /**
   * 新增商品
   * 
   * @param article
   * @return 新增商品的UUID
   */
  String insert(Store article);

  /**
   * 批量新增商品
   * 
   * @param articles
   */
  void insertBatch(List<Store> articles);

  void update(Store article);

  void delete(String uuid);

  Store getByUuid(String uuid);

  Store getByCode(String code);

  PageQueryResult<Store> queryByPage(PageQueryDefinition filter);
}
