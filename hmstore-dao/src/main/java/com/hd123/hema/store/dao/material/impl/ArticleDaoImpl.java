/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-dao
 * 文件名：	ArticleDaoImpl.java
 * 模块说明：
 * 修改历史：
 * 2016-6-16 - xiepingping - 创建。
 */
package com.hd123.hema.store.dao.material.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.hd123.hema.store.bean.material.Article;
import com.hd123.hema.store.dao.material.ArticleDao;
import com.hd123.wms.antman.common.query.PageQueryDefinition;
import com.hd123.wms.antman.common.query.QueryParam;

/**
 * @author xiepingping
 * 
 */
@Repository
public class ArticleDaoImpl extends SqlSessionDaoSupport implements ArticleDao {

  @Override
  public int insert(Article t) {
    return getSqlSession().getMapper(ArticleDao.class).insert(t);
  }

  @Override
  public int update(Article t) {
    return getSqlSession().getMapper(ArticleDao.class).update(t);
  }

  @Override
  public int delete(String uuid) {
    return getSqlSession().getMapper(ArticleDao.class).delete(uuid);
  }

  @Override
  public Article getByUuid(String uuid) {
    return getSqlSession().getMapper(ArticleDao.class).getByUuid(uuid);
  }

  @Override
  public List<Article> queryByPage(PageQueryDefinition params) {
    return getSqlSession().getMapper(ArticleDao.class).queryByPage(params);
  }

  @Override
  public Article getByCode(String code) {
    return getSqlSession().getMapper(ArticleDao.class).getByCode(code);
  }

  @Override
  public void insertBatch(List<Article> articles) {
    getSqlSession().getMapper(ArticleDao.class).insertBatch(articles);
  }

  @Override
  public List<Article> queryByList(QueryParam params) {
    return getSqlSession().getMapper(ArticleDao.class).queryByList(params);
  }

}
