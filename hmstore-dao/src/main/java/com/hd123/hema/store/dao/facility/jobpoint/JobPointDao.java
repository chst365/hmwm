/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-dao
 * 文件名：	JobPointDao.java
 * 模块说明：
 * 修改历史：
 * 2016-6-16 - xiepingping - 创建。
 */
package com.hd123.hema.store.dao.facility.jobpoint;

import java.util.List;

import com.hd123.hema.store.bean.facility.jobpoint.JobPoint;
import com.hd123.wms.antman.common.daosupport.BaseDao;
import com.hd123.wms.antman.common.query.QueryParam;

/**
 * @author xiepingping
 *
 */
public interface JobPointDao extends BaseDao<JobPoint> {

  public JobPoint getByCode(String code, String templateUuid, String orgUuid);

  public List<String> queryAvaliableEleTags(QueryParam param);

  public void insertBatch(List<JobPoint> jobPoints);

  public void deleteByOrg(String orgUuid);

}
