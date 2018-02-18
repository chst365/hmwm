/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-dao
 * 文件名：	PickSchemeTemplateStoreDaoImpl.java
 * 模块说明：
 * 修改历史：
 * 2016-6-16 - xiepingping - 创建。
 */
package com.hd123.hema.store.dao.facility.pickscheme.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.hd123.hema.store.bean.facility.pickscheme.PickSchemeTemplateStore;
import com.hd123.hema.store.dao.facility.pickscheme.PickSchemeTemplateStoreDao;
import com.hd123.wms.antman.common.query.PageQueryDefinition;
import com.hd123.wms.antman.common.query.QueryParam;

/**
 * @author xiepingping
 *
 */
public class PickSchemeTemplateStoreDaoImpl extends SqlSessionDaoSupport implements PickSchemeTemplateStoreDao {

  @Override
  public int insert(PickSchemeTemplateStore t) {
    return getSqlSession().getMapper(PickSchemeTemplateStoreDao.class).insert(t);
  }

  @Override
  public int update(PickSchemeTemplateStore t) {
    return getSqlSession().getMapper(PickSchemeTemplateStoreDao.class).update(t);
  }

  @Override
  public int delete(String uuid) {
    return getSqlSession().getMapper(PickSchemeTemplateStoreDao.class).delete(uuid);
  }

  @Override
  public PickSchemeTemplateStore getByUuid(String uuid) {
    return getSqlSession().getMapper(PickSchemeTemplateStoreDao.class).getByUuid(uuid);
  }

  @Override
  public List<PickSchemeTemplateStore> queryByPage(PageQueryDefinition params) {
    return getSqlSession().getMapper(PickSchemeTemplateStoreDao.class).queryByPage(params);
  }

  @Override
  public List<PickSchemeTemplateStore> queryByList(QueryParam params) {
    return getSqlSession().getMapper(PickSchemeTemplateStoreDao.class).queryByList(params);
  }

  @Override
  public void insertBatch(List<PickSchemeTemplateStore> templateStores) {
    getSqlSession().getMapper(PickSchemeTemplateStoreDao.class).insertBatch(templateStores);
  }

  @Override
  public void deleteByTemplate(String pickSchemeTemplateUuid) {
    getSqlSession().getMapper(PickSchemeTemplateStoreDao.class).deleteByTemplate(pickSchemeTemplateUuid);
  }

}
