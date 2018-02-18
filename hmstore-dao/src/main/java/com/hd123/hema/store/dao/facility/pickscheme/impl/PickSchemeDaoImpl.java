/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-dao
 * 文件名：	PickSchemeDaoImpl.java
 * 模块说明：
 * 修改历史：
 * 2016-6-16 - xiepingping - 创建。
 */
package com.hd123.hema.store.dao.facility.pickscheme.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.hd123.hema.store.bean.facility.pickscheme.PickScheme;
import com.hd123.hema.store.dao.facility.pickscheme.PickSchemeDao;
import com.hd123.wms.antman.common.query.PageQueryDefinition;
import com.hd123.wms.antman.common.query.QueryParam;

/**
 * @author xiepingping
 * 
 */
public class PickSchemeDaoImpl extends SqlSessionDaoSupport implements PickSchemeDao {

  @Override
  public int insert(PickScheme t) {
    return getSqlSession().getMapper(PickSchemeDao.class).insert(t);
  }

  @Override
  public int update(PickScheme t) {
    return getSqlSession().getMapper(PickSchemeDao.class).update(t);
  }

  @Override
  public int delete(String uuid) {
    return getSqlSession().getMapper(PickSchemeDao.class).delete(uuid);
  }

  @Override
  public PickScheme getByUuid(String uuid) {
    return getSqlSession().getMapper(PickSchemeDao.class).getByUuid(uuid);
  }

  @Override
  public List<PickScheme> queryByPage(PageQueryDefinition params) {
    return getSqlSession().getMapper(PickSchemeDao.class).queryByPage(params);
  }

  @Override
  public List<PickScheme> queryByList(QueryParam params) {
    return getSqlSession().getMapper(PickSchemeDao.class).queryByList(params);
  }

  @Override
  public PickScheme getByCode(String code, String orgUuid, String jobPointUuid) {
    Map<String, String> map = new HashMap<String, String>();
    map.put("code", code);
    map.put("orgUuid", orgUuid);
    map.put("jobPointUuid", jobPointUuid);
    return getSqlSession().selectOne("getPickSchemeByCode", map);
  }

  @Override
  public void insertBatch(List<PickScheme> pickSchemes) {
    getSqlSession().getMapper(PickSchemeDao.class).insertBatch(pickSchemes);
  }

  @Override
  public void deleteByOrg(String orgUuid) {
    getSqlSession().getMapper(PickSchemeDao.class).deleteByOrg(orgUuid);
  }

}
