/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-dao
 * 文件名：	ElectronicTagDao.java
 * 模块说明：
 * 修改历史：
 * 2016-6-16 - xiepingping - 创建。
 */
package com.hd123.hema.store.dao.facility.gateway;

import java.util.List;

import com.hd123.hema.store.bean.facility.gateway.ElectronicTag;
import com.hd123.wms.antman.common.daosupport.BaseDao;

/**
 * @author xiepingping
 *
 */
public interface ElectronicTagDao extends BaseDao<ElectronicTag> {

  public void insertBatch(List<ElectronicTag> eleTags);

  public void deleteByGateway(String gatewayUuid);
}
