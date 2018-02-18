/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-dao
 * 文件名：	StoreHostDaoImpl.java
 * 模块说明：
 * 修改历史：
 * 2016-6-16 - xiepingping - 创建。
 */
package com.hd123.hema.store.dao.material.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.hd123.hema.store.bean.material.StoreHost;
import com.hd123.hema.store.dao.material.StoreHostDao;
import com.hd123.wms.antman.common.query.PageQueryDefinition;
import com.hd123.wms.antman.common.query.QueryParam;

/**
 * @author xiepingping
 *
 */
public class StoreHostDaoImpl extends SqlSessionDaoSupport implements StoreHostDao {

  @Override
  public int insert(StoreHost t) {
    return getSqlSession().getMapper(StoreHostDao.class).insert(t);
  }

  @Override
  public int update(StoreHost t) {
    return getSqlSession().getMapper(StoreHostDao.class).update(t);
  }

  @Override
  public int delete(String uuid) {
    return getSqlSession().getMapper(StoreHostDao.class).delete(uuid);
  }

  @Override
  public StoreHost getByUuid(String uuid) {
    return getSqlSession().getMapper(StoreHostDao.class).getByUuid(uuid);
  }

  @Override
  public List<StoreHost> queryByPage(PageQueryDefinition params) {
    return getSqlSession().getMapper(StoreHostDao.class).queryByPage(params);
  }

  @Override
  public List<StoreHost> queryByList(QueryParam params) {
    return getSqlSession().getMapper(StoreHostDao.class).queryByList(params);
  }

}
