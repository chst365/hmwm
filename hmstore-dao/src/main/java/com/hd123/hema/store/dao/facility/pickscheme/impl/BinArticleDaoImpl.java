/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-dao
 * 文件名：	BinArticleDaoImpl.java
 * 模块说明：
 * 修改历史：
 * 2016-6-16 - xiepingping - 创建。
 */
package com.hd123.hema.store.dao.facility.pickscheme.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.hd123.hema.store.bean.facility.pickscheme.BinArticle;
import com.hd123.hema.store.dao.facility.pickscheme.BinArticleDao;
import com.hd123.wms.antman.common.query.PageQueryDefinition;
import com.hd123.wms.antman.common.query.QueryParam;

/**
 * @author xiepingping
 * 
 */
public class BinArticleDaoImpl extends SqlSessionDaoSupport implements BinArticleDao {

  @Override
  public int insert(BinArticle t) {
    return getSqlSession().getMapper(BinArticleDao.class).insert(t);
  }

  @Override
  public int update(BinArticle t) {
    return getSqlSession().getMapper(BinArticleDao.class).update(t);
  }

  @Override
  public int delete(String uuid) {
    return getSqlSession().getMapper(BinArticleDao.class).delete(uuid);
  }

  @Override
  public BinArticle getByUuid(String uuid) {
    return getSqlSession().getMapper(BinArticleDao.class).getByUuid(uuid);
  }

  @Override
  public List<BinArticle> queryByPage(PageQueryDefinition params) {
    return getSqlSession().getMapper(BinArticleDao.class).queryByPage(params);
  }

  @Override
  public List<BinArticle> queryByList(QueryParam params) {
    return getSqlSession().getMapper(BinArticleDao.class).queryByList(params);
  }

  @Override
  public void insertBatch(List<BinArticle> binArticles) {
    getSqlSession().getMapper(BinArticleDao.class).insertBatch(binArticles);
  }

  @Override
  public void deleteByPickScheme(String pickSchemeUuid) {
    getSqlSession().getMapper(BinArticleDao.class).deleteByPickScheme(pickSchemeUuid);
  }

  @Override
  public List<BinArticle> getByArticleUuid(String articleUuid) {
    return getSqlSession().getMapper(BinArticleDao.class).getByArticleUuid(articleUuid);
  }

}
