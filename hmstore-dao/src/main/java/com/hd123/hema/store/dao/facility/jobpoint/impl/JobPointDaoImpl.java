/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-dao
 * 文件名：	JobPointDaoImpl.java
 * 模块说明：
 * 修改历史：
 * 2016-6-16 - xiepingping - 创建。
 */
package com.hd123.hema.store.dao.facility.jobpoint.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.hd123.hema.store.bean.facility.jobpoint.JobPoint;
import com.hd123.hema.store.dao.facility.jobpoint.JobPointDao;
import com.hd123.wms.antman.common.query.PageQueryDefinition;
import com.hd123.wms.antman.common.query.QueryParam;

/**
 * @author xiepingping
 * 
 */
public class JobPointDaoImpl extends SqlSessionDaoSupport implements JobPointDao {

  @Override
  public int insert(JobPoint t) {
    return getSqlSession().getMapper(JobPointDao.class).insert(t);
  }

  @Override
  public int update(JobPoint t) {
    return getSqlSession().getMapper(JobPointDao.class).update(t);
  }

  @Override
  public int delete(String uuid) {
    return getSqlSession().getMapper(JobPointDao.class).delete(uuid);
  }

  @Override
  public JobPoint getByUuid(String uuid) {
    return getSqlSession().getMapper(JobPointDao.class).getByUuid(uuid);
  }

  @Override
  public List<JobPoint> queryByPage(PageQueryDefinition params) {
    return getSqlSession().getMapper(JobPointDao.class).queryByPage(params);
  }

  @Override
  public List<JobPoint> queryByList(QueryParam params) {
    return getSqlSession().getMapper(JobPointDao.class).queryByList(params);
  }

  @Override
  public JobPoint getByCode(String code, String templateUuid, String orgUuid) {
    Map<String, String> map = new HashMap<String, String>();
    map.put("code", code);
    map.put("templateUuid", templateUuid);
    map.put("orgUuid", orgUuid);
    return getSqlSession().selectOne("getJobPointByCode", map);
  }

  @Override
  public void insertBatch(List<JobPoint> jobPoints) {
    getSqlSession().getMapper(JobPointDao.class).insertBatch(jobPoints);
  }

  @Override
  public List<String> queryAvaliableEleTags(QueryParam param) {
    return getSqlSession().getMapper(JobPointDao.class).queryAvaliableEleTags(param);
  }

  @Override
  public void deleteByOrg(String orgUuid) {
    getSqlSession().getMapper(JobPointDao.class).deleteByOrg(orgUuid);
  }

}
