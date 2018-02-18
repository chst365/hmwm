/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-dao
 * 文件名：	JobPointGatewayDaoImpl.java
 * 模块说明：
 * 修改历史：
 * 2016-7-27 - xiepingping - 创建。
 */
package com.hd123.hema.store.dao.facility.jobpoint.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.hd123.hema.store.bean.facility.jobpoint.JobPointGateway;
import com.hd123.hema.store.dao.facility.jobpoint.JobPointGatewayDao;
import com.hd123.wms.antman.common.query.QueryParam;

/**
 * @author xiepingping
 * 
 */
public class JobPointGatewayDaoImpl extends SqlSessionDaoSupport implements JobPointGatewayDao {

  @Override
  public int insertBatch(List<JobPointGateway> jobPointGateways) {
    return getSqlSession().getMapper(JobPointGatewayDao.class).insertBatch(jobPointGateways);
  }

  @Override
  public int deleteByJobPoint(String jobPointUuid) {
    return getSqlSession().getMapper(JobPointGatewayDao.class).deleteByJobPoint(jobPointUuid);
  }

  @Override
  public List<JobPointGateway> queryByList(QueryParam params) {
    return getSqlSession().getMapper(JobPointGatewayDao.class).queryByList(params);
  }

}
