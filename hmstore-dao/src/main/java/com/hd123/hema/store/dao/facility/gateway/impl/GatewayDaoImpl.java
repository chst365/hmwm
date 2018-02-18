/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-dao
 * 文件名：	GatewayDaoImpl.java
 * 模块说明：
 * 修改历史：
 * 2016-6-16 - xiepingping - 创建。
 */
package com.hd123.hema.store.dao.facility.gateway.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.hd123.hema.store.bean.facility.gateway.Gateway;
import com.hd123.hema.store.dao.facility.gateway.GatewayDao;
import com.hd123.wms.antman.common.query.PageQueryDefinition;
import com.hd123.wms.antman.common.query.QueryParam;

/**
 * @author xiepingping
 * 
 */
public class GatewayDaoImpl extends SqlSessionDaoSupport implements GatewayDao {

  @Override
  public int insert(Gateway t) {
    return getSqlSession().getMapper(GatewayDao.class).insert(t);
  }

  @Override
  public int update(Gateway t) {
    return getSqlSession().getMapper(GatewayDao.class).update(t);
  }

  @Override
  public int delete(String uuid) {
    return getSqlSession().getMapper(GatewayDao.class).delete(uuid);
  }

  @Override
  public Gateway getByUuid(String uuid) {
    return getSqlSession().getMapper(GatewayDao.class).getByUuid(uuid);
  }

  @Override
  public List<Gateway> queryByPage(PageQueryDefinition params) {
    return getSqlSession().getMapper(GatewayDao.class).queryByPage(params);
  }

  @Override
  public List<Gateway> queryByList(QueryParam params) {
    return getSqlSession().getMapper(GatewayDao.class).queryByList(params);
  }

  @Override
  public Gateway getByCode(String code, String templateUuid, String orgUuid) {
    Map<String, String> map = new HashMap<String, String>();
    map.put("code", code);
    map.put("templateUuid", templateUuid);
    map.put("orgUuid", orgUuid);
    return getSqlSession().selectOne("getGatewayByCode", map);
  }

  @Override
  public void insertBatch(List<Gateway> gateways) {
    getSqlSession().getMapper(GatewayDao.class).insertBatch(gateways);
  }

  @Override
  public void deleteByOrg(String orgUuid) {
    getSqlSession().getMapper(GatewayDao.class).deleteByOrg(orgUuid);
  }

}
