package com.hd123.hema.store.plugins.eletagstatus.receive;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hd123.hema.store.plugins.common.BizType;
import com.hd123.hema.store.plugins.eletagstatus.receive.request.ELeTagStatusItem;
import com.hd123.hema.store.plugins.eletagstatus.receive.request.EleTagStatusRequestBean;
import com.hd123.hema.store.service.facility.gateway.dto.ElectronicTagStateInfo;
import com.hd123.hema.store.service.facility.gateway.dto.StoreELeTagStatus;
import com.hd123.wms.mm.common.entity.BaseStandardBean;
import com.hd123.wms.mm.common.util.JsonUtils;
import com.hd123.wms.mm.transformer.exception.TransformMMException;
import com.hd123.wms.mm.transformer.receive.ReceiveInTransformer;

/**
 * @author xiepingping
 * 
 */
public class EleTagStatusReceiveInTransformer extends
    ReceiveInTransformer<EleTagStatusRequestBean, StoreELeTagStatus> {
  private final static Logger logger = LoggerFactory
      .getLogger(EleTagStatusReceiveInTransformer.class);

  @Override
  protected EleTagStatusRequestBean convert2Objecct(String s) throws TransformMMException {
    EleTagStatusRequestBean bean = JsonUtils.stringToObject(s, EleTagStatusRequestBean.class);
    if (bean == null) {
      logger.error("转换为类型[" + this.getClass().getName() + "]时失败");
      logger.error(s);
      throw new TransformMMException("转换对象时未成功");
    }
    bean.setBizType(BizType.BIZTYPE_PULL_ELETAGSTATUS);
    return bean;
  }

  @Override
  protected BaseStandardBean<StoreELeTagStatus> doTransform(EleTagStatusRequestBean s)
      throws TransformMMException {
    BaseStandardBean<StoreELeTagStatus> bean = new BaseStandardBean<StoreELeTagStatus>();

    StoreELeTagStatus result = new StoreELeTagStatus();
    List<ElectronicTagStateInfo> infos = new ArrayList<ElectronicTagStateInfo>();
    for (ELeTagStatusItem source : s.getItems().getStatusItems()) {
      ElectronicTagStateInfo target = new ElectronicTagStateInfo();
      target.setCheckTime(source.getCheckTime());
      target.setNodeCode(source.getNodeCode());
      target.setNodeState(source.getNodeState());
      target.setRemark(source.getRemark());
      infos.add(target);
    }
    result.setInfos(infos);
    result.setStoreCode(s.getItems().getStoreCode());
    bean.setData(result);

    return bean;
  }

}
