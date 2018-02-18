/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-service
 * 文件名：	GatewayServiceImpl.java
 * 模块说明：
 * 修改历史：
 * 2016-6-24 - xiepingping - 创建。
 */
package com.hd123.hema.store.service.facility.gateway.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.hd123.hema.store.bean.facility.gateway.ElectronicTag;
import com.hd123.hema.store.bean.facility.gateway.Gateway;
import com.hd123.hema.store.bean.material.Store;
import com.hd123.hema.store.dao.facility.gateway.GatewayDao;
import com.hd123.hema.store.dao.material.StoreDao;
import com.hd123.hema.store.service.facility.gateway.ElectronicTagService;
import com.hd123.hema.store.service.facility.gateway.GatewayService;
import com.hd123.hema.store.service.facility.gateway.dto.ElectronicTagStateInfo;
import com.hd123.rumba.commons.lang.Assert;
import com.hd123.wms.antman.common.query.QueryParam;
import com.hd123.wms.antman.common.utils.ListUtils;
import com.hd123.wms.antman.common.utils.UUIDGenerator;

/**
 * @author xiepingping
 * 
 */
public class GatewayServiceImpl implements GatewayService {

  @Autowired
  private GatewayDao gatewayDao;
  @Autowired
  private ElectronicTagService eleTagService;
  @Autowired
  private StoreDao storeDao;

  @Override
  public String insert(Gateway gateway) {
    Assert.assertArgumentNotNull(gateway, "gateway");

    gateway.validate();
    gateway.setUuid(null);
    validByUniqueKey(gateway);

    gateway.setUuid(UUIDGenerator.genUUID());
    for (ElectronicTag tag : gateway.getTags())
      tag.setGatewayUuid(gateway.getUuid());

    gatewayDao.insert(gateway);
    eleTagService.insertBatch(gateway.getTags());

    return gateway.getUuid();
  }

  @Override
  public void insertBatch(List<Gateway> gateways) {
    Assert.assertArgumentNotNull(gateways, "gateways");

    for (Gateway gateway : gateways) {
      Assert.assertArgumentNotNull(gateway, "gateway");

      gateway.validate();
      gateway.setUuid(null);
      validByUniqueKey(gateway);

      gateway.setUuid(UUIDGenerator.genUUID());
      for (ElectronicTag eleTag : gateway.getTags())
        eleTag.setGatewayUuid(gateway.getUuid());
      eleTagService.insertBatch(gateway.getTags());
    }

    for (List<Gateway> list : ListUtils.splitList(gateways, 1000))
      gatewayDao.insertBatch(list);
  }

  @Override
  public void update(Gateway gateway) {
    Assert.assertArgumentNotNull(gateway, "gateway");
    gateway.validate();

    Gateway dbGateway = gatewayDao.getByUuid(gateway.getUuid());
    if (dbGateway == null)
      throw new IllegalArgumentException("网关不存在");

    validByUniqueKey(gateway);

    gatewayDao.update(gateway);

    eleTagService.deleteByGateway(gateway.getUuid());
    eleTagService.insertBatch(gateway.getTags());
  }

  private void validByUniqueKey(Gateway gateway) {
    assert gateway != null;

    Gateway dbGateway = gatewayDao.getByCode(gateway.getCode(), gateway.getTemplateUuid(), gateway.getOrgUuid());
    if (dbGateway != null && dbGateway.getUuid().equals(gateway.getUuid()) == false)
      throw new IllegalArgumentException("已存在代码为" + gateway.getCode() + "的网关");
  }

  @Override
  public void delete(String uuid) {
    if (StringUtils.isEmpty(uuid))
      return;

    gatewayDao.delete(uuid);
    eleTagService.deleteByGateway(uuid);
  }

  @Override
  public Gateway getByUuid(String uuid) {
    if (StringUtils.isEmpty(uuid))
      return null;

    Gateway gateway = gatewayDao.getByUuid(uuid);
    setItems(gateway);

    return gateway;
  }

  private void setItems(Gateway gateway) {
    if (gateway == null)
      return;

    QueryParam param = new QueryParam();
    param.put("gatewayUuid", gateway.getUuid());
    gateway.setTags(eleTagService.queryByList(param));
  }

  @Override
  public List<Gateway> queryByList(QueryParam param) {
    List<Gateway> gateways = gatewayDao.queryByList(param);
    for (Gateway gateway : gateways) {
      setItems(gateway);
    }
    return gateways;
  }

  @Override
  public void receiveELeTagStatus(String storeCode, List<ElectronicTagStateInfo> infos) {
    Assert.assertArgumentNotNull(storeCode, "storeCode");
    Assert.assertArgumentNotNull(infos, "infos");

    Store store = storeDao.getByCode(storeCode);
    if (store == null)
      throw new IllegalArgumentException("门店" + storeCode + "不存在");

    QueryParam param = new QueryParam();
    param.put("orgUuid", store.getUuid());
    List<Gateway> gateways = queryByList(param);
    for (Gateway gateway : gateways) {
      for (ElectronicTag tag : gateway.getTags())
        changeELeTagStatus(tag, infos);
    }

  }

  private void changeELeTagStatus(ElectronicTag tag, List<ElectronicTagStateInfo> infos) {
    for (ElectronicTagStateInfo info : infos) {
      if (tag.getNodeCode().equals(info.getNodeCode())) {
        tag.setNodeState(info.getNodeState());
        tag.setCheckTime(info.getCheckTime());
        tag.setRemark(info.getRemark());

        eleTagService.update(tag);
      }
    }
  }

  @Override
  public void deleteByOrg(String orgUuid) {
    Assert.assertArgumentNotNull(orgUuid, "orgUuid");

    gatewayDao.deleteByOrg(orgUuid);
  }

  @Override
  public Gateway getByCode(String code, String templateUuid, String orgUuid) {
    Assert.assertArgumentNotNull(code, "code");
    Assert.assertArgumentNotNull(orgUuid, "orgUuid");

    return gatewayDao.getByCode(code, templateUuid, orgUuid);
  }

}
