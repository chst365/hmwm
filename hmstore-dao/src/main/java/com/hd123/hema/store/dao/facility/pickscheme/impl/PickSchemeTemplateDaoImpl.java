/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-dao
 * 文件名：	PickSchemeTemplateDaoImpl.java
 * 模块说明：
 * 修改历史：
 * 2016-6-16 - xiepingping - 创建。
 */
package com.hd123.hema.store.dao.facility.pickscheme.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.hd123.hema.store.bean.facility.pickscheme.PickSchemeTemplate;
import com.hd123.hema.store.dao.facility.pickscheme.PickSchemeTemplateDao;
import com.hd123.wms.antman.common.query.PageQueryDefinition;
import com.hd123.wms.antman.common.query.QueryParam;

/**
 * @author xiepingping
 * 
 */
public class PickSchemeTemplateDaoImpl extends SqlSessionDaoSupport implements
    PickSchemeTemplateDao {

  @Override
  public int insert(PickSchemeTemplate t) {
    return getSqlSession().getMapper(PickSchemeTemplateDao.class).insert(t);
  }

  @Override
  public int update(PickSchemeTemplate t) {
    return getSqlSession().getMapper(PickSchemeTemplateDao.class).update(t);
  }

  @Override
  public int delete(String uuid) {
    return getSqlSession().getMapper(PickSchemeTemplateDao.class).delete(uuid);
  }

  @Override
  public PickSchemeTemplate getByUuid(String uuid) {
    return getSqlSession().getMapper(PickSchemeTemplateDao.class).getByUuid(uuid);
  }

  @Override
  public List<PickSchemeTemplate> queryByPage(PageQueryDefinition params) {
    return getSqlSession().getMapper(PickSchemeTemplateDao.class).queryByPage(params);
  }

  @Override
  public List<PickSchemeTemplate> queryByList(QueryParam params) {
    return getSqlSession().getMapper(PickSchemeTemplateDao.class).queryByList(params);
  }

  @Override
  public PickSchemeTemplate getByCode(String code, String orgUuid) {
    Map<String, String> map = new HashMap<String, String>();
    map.put("code", code);
    map.put("orgUuid", orgUuid);
    return getSqlSession().selectOne("getPickSchemeTemplateByCode", map);
  }

  @Override
  public void insertBatch(List<PickSchemeTemplate> templates) {
    getSqlSession().getMapper(PickSchemeTemplateDao.class).insertBatch(templates);
  }

}
