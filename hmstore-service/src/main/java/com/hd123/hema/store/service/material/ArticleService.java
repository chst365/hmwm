/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-service
 * 文件名：	ArticleService.java
 * 模块说明：
 * 修改历史：
 * 2016-6-17 - xiepingping - 创建。
 */
package com.hd123.hema.store.service.material;

import java.util.List;

import com.hd123.hema.store.bean.material.Article;
import com.hd123.wms.antman.common.query.PageQueryDefinition;
import com.hd123.wms.antman.common.query.PageQueryResult;

/**
 * @author xiepingping
 * 
 */
public interface ArticleService {

  /**
   * 新增商品
   * 
   * @param article
   * @return 新增商品的UUID
   */
  String insert(Article article);

  /**
   * 批量新增商品
   * 
   * @param articles
   */
  void insertBatch(List<Article> articles);

  void update(Article article);

  void delete(String uuid);

  Article getByUuid(String uuid);

  List<Article> queryByList();

  PageQueryResult<Article> queryByPage(PageQueryDefinition filter);

  Article getByCode(String string);
}
