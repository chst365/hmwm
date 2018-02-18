/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-dao
 * 文件名：	FacilityDaoImpl.java
 * 模块说明：
 * 修改历史：
 * 2016-6-16 - xiepingping - 创建。
 */
package com.hd123.hema.store.dao.facility.template.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.hd123.hema.store.bean.facility.template.FacilityTemplate;
import com.hd123.hema.store.dao.facility.template.FacilityTemplateDao;
import com.hd123.wms.antman.common.query.PageQueryDefinition;
import com.hd123.wms.antman.common.query.QueryParam;

/**
 * @author xiepingping
 * 
 */
public class FacilityTemplateDaoImpl extends SqlSessionDaoSupport implements FacilityTemplateDao {

  @Override
  public int insert(FacilityTemplate t) {
    return getSqlSession().getMapper(FacilityTemplateDao.class).insert(t);
  }

  @Override
  public int update(FacilityTemplate t) {
    return getSqlSession().getMapper(FacilityTemplateDao.class).update(t);
  }

  @Override
  public int delete(String uuid) {
    return getSqlSession().getMapper(FacilityTemplateDao.class).delete(uuid);
  }

  @Override
  public FacilityTemplate getByUuid(String uuid) {
    return getSqlSession().getMapper(FacilityTemplateDao.class).getByUuid(uuid);
  }

  @Override
  public List<FacilityTemplate> queryByPage(PageQueryDefinition params) {
    return getSqlSession().getMapper(FacilityTemplateDao.class).queryByPage(params);
  }

  @Override
  public List<FacilityTemplate> queryByList(QueryParam params) {
    return getSqlSession().getMapper(FacilityTemplateDao.class).queryByList(params);
  }

  @Override
  public FacilityTemplate getByCode(String code) {
    return getSqlSession().getMapper(FacilityTemplateDao.class).getByCode(code);
  }

  @Override
  public void insertBatch(List<FacilityTemplate> templates) {
    getSqlSession().getMapper(FacilityTemplateDao.class).insertBatch(templates);
  }

}
