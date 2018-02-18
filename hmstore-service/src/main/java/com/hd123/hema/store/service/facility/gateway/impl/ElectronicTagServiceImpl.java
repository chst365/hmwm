/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-service
 * 文件名：	ElectronicTagServiceImpl.java
 * 模块说明：
 * 修改历史：
 * 2016-6-28 - xiepingping - 创建。
 */
package com.hd123.hema.store.service.facility.gateway.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.hd123.hema.store.bean.facility.gateway.ElectronicTag;
import com.hd123.hema.store.bean.facility.gateway.Gateway;
import com.hd123.hema.store.bean.material.Store;
import com.hd123.hema.store.dao.facility.gateway.ElectronicTagDao;
import com.hd123.hema.store.service.facility.gateway.ElectronicTagService;
import com.hd123.hema.store.service.facility.gateway.GatewayService;
import com.hd123.hema.store.service.facility.gateway.dto.ElectronicTagStateInfo;
import com.hd123.hema.store.service.material.StoreService;
import com.hd123.rumba.commons.biz.entity.UCN;
import com.hd123.rumba.commons.lang.Assert;
import com.hd123.wms.antman.common.query.QueryParam;
import com.hd123.wms.antman.common.utils.ListUtils;
import com.hd123.wms.antman.common.utils.UUIDGenerator;

/**
 * @author xiepingping
 * 
 */
public class ElectronicTagServiceImpl implements ElectronicTagService {

  @Autowired
  private ElectronicTagDao electronicTagDao;
  @Autowired
  private GatewayService gatewayService;
  @Autowired
  private StoreService storeService;

  @Override
  public String insert(ElectronicTag electronicTag) {
    Assert.assertArgumentNotNull(electronicTag, "electronicTag");
    electronicTag.validate();

    electronicTag.setUuid(UUIDGenerator.genUUID());

    electronicTagDao.insert(electronicTag);

    return electronicTag.getUuid();
  }

  @Override
  public void insertBatch(List<ElectronicTag> electronicTags) {
    Assert.assertArgumentNotNull(electronicTags, "electronicTags");

    for (ElectronicTag electronicTag : electronicTags) {
      Assert.assertArgumentNotNull(electronicTag, "electronicTag");
      electronicTag.validate();

      electronicTag.setUuid(UUIDGenerator.genUUID());
    }

    for (List<ElectronicTag> list : ListUtils.splitList(electronicTags, 1000))
      electronicTagDao.insertBatch(list);
  }

  @Override
  public void update(ElectronicTag electronicTag) {
    Assert.assertArgumentNotNull(electronicTag, "electronicTag");
    electronicTag.validate();

    ElectronicTag dbElectronicTag = electronicTagDao.getByUuid(electronicTag.getUuid());
    if (dbElectronicTag == null)
      throw new IllegalArgumentException("电子标签不存在");

    electronicTagDao.update(electronicTag);
  }

  @Override
  public void delete(String uuid) {
    if (StringUtils.isEmpty(uuid))
      return;

    electronicTagDao.delete(uuid);
  }

  @Override
  public ElectronicTag getByUuid(String uuid) {
    if (StringUtils.isEmpty(uuid))
      return null;

    ElectronicTag electronicTag = electronicTagDao.getByUuid(uuid);

    return electronicTag;
  }

  @Override
  public List<ElectronicTag> queryByList(QueryParam param) {
    List<ElectronicTag> electronicTags = electronicTagDao.queryByList(param);
    return electronicTags;
  }

  @Override
  public void deleteByGateway(String gatewayUuid) {
    if (StringUtils.isEmpty(gatewayUuid))
      return;

    electronicTagDao.deleteByGateway(gatewayUuid);
  }

  @Override
  public Map<UCN, List<ElectronicTagStateInfo>> queryStateInfoByList(QueryParam param) {
    Map<UCN, List<ElectronicTagStateInfo>> result = new HashMap<UCN, List<ElectronicTagStateInfo>>();
    List<Gateway> gateways = gatewayService.queryByList(param);
    for (Gateway gateway : gateways) {
      Store store = storeService.getByUuid(gateway.getOrgUuid());
      if (store == null)
        continue;

      UCN key = new UCN(store.getUuid(), store.getCode(), store.getName());

      QueryParam eleTagFilter = new QueryParam();
      eleTagFilter.put("gatewayUuid", gateway.getUuid());
      List<ElectronicTag> tags = electronicTagDao.queryByList(eleTagFilter);
      if (CollectionUtils.isEmpty(result.get(key)))
        result.put(key, new ArrayList<ElectronicTagStateInfo>());
      for (ElectronicTag tag : tags)
        result.get(key).add(convertEleTag(tag));

    }
    return result;
  }

  private ElectronicTagStateInfo convertEleTag(ElectronicTag tag) {
    ElectronicTagStateInfo result = new ElectronicTagStateInfo();
    result.setCheckTime(tag.getCheckTime());
    result.setNodeCode(tag.getNodeCode());
    result.setNodeAddress(tag.getNodeAddress());
    result.setNodeState(tag.getNodeState());
    result.setRemark(tag.getRemark());
    return result;
  }

}
