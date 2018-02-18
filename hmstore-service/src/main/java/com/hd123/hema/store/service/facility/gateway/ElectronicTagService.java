/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-service
 * 文件名：	ElectronicTagService.java
 * 模块说明：
 * 修改历史：
 * 2016-6-27 - xiepingping - 创建。
 */
package com.hd123.hema.store.service.facility.gateway;

import java.util.List;
import java.util.Map;

import com.hd123.hema.store.bean.facility.gateway.ElectronicTag;
import com.hd123.hema.store.service.facility.gateway.dto.ElectronicTagStateInfo;
import com.hd123.rumba.commons.biz.entity.UCN;
import com.hd123.wms.antman.common.query.QueryParam;

/**
 * @author xiepingping
 *
 */
public interface ElectronicTagService {
  String insert(ElectronicTag gateway);

  void insertBatch(List<ElectronicTag> gateways);

  void update(ElectronicTag gateway);

  void delete(String uuid);

  void deleteByGateway(String gatewayUuid);

  ElectronicTag getByUuid(String uuid);

  List<ElectronicTag> queryByList(QueryParam param);

  /**
   * 标签状态查询，以门店分组
   * 
   * @param param
   * @return
   */
  Map<UCN, List<ElectronicTagStateInfo>> queryStateInfoByList(QueryParam param);
}
