/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-service
 * 文件名：	PickAreaService.java
 * 模块说明：
 * 修改历史：
 * 2016-6-27 - xiepingping - 创建。
 */
package com.hd123.hema.store.service.facility.jobpoint;

import java.util.List;

import com.hd123.hema.store.bean.facility.jobpoint.PickArea;
import com.hd123.wms.antman.common.query.QueryParam;

/**
 * @author xiepingping
 *
 */
public interface PickAreaService {
  String insert(PickArea pickArea);

  void insertBatch(List<PickArea> pickAreas);

  /**
   * 明细为空，不做删除
   */
  void update(PickArea pickArea);

  void delete(String uuid);

  void deleteByJobPoint(String jobPointUuid);

  PickArea getByUuid(String uuid);

  List<PickArea> queryByList(QueryParam param);
}
