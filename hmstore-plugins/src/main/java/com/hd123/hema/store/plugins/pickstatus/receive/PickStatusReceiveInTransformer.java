package com.hd123.hema.store.plugins.pickstatus.receive;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hd123.hema.store.bean.query.SStorePickStatus;
import com.hd123.hema.store.plugins.common.BizType;
import com.hd123.hema.store.plugins.pickstatus.receive.request.PickStatusItem;
import com.hd123.hema.store.plugins.pickstatus.receive.request.PickStatusRequestBean;
import com.hd123.wms.mm.common.entity.BaseStandardBean;
import com.hd123.wms.mm.common.util.JsonUtils;
import com.hd123.wms.mm.transformer.exception.TransformMMException;
import com.hd123.wms.mm.transformer.receive.ReceiveInTransformer;

/**
 * @author xiepingping
 * 
 */
public class PickStatusReceiveInTransformer extends
    ReceiveInTransformer<PickStatusRequestBean, List<SStorePickStatus>> {
  private final static Logger logger = LoggerFactory
      .getLogger(PickStatusReceiveInTransformer.class);

  @Override
  protected PickStatusRequestBean convert2Objecct(String s) throws TransformMMException {
    PickStatusRequestBean bean = JsonUtils.stringToObject(s, PickStatusRequestBean.class);
    if (bean == null) {
      logger.error("转换为类型[" + this.getClass().getName() + "]时失败");
      logger.error(s);
      throw new TransformMMException("转换对象时未成功");
    }
    bean.setBizType(BizType.BIZTYPE_PULL_PICKSTATUS);
    return bean;
  }

  @Override
  protected BaseStandardBean<List<SStorePickStatus>> doTransform(PickStatusRequestBean s)
      throws TransformMMException {
    BaseStandardBean<List<SStorePickStatus>> bean = new BaseStandardBean<List<SStorePickStatus>>();

    List<SStorePickStatus> result = new ArrayList<SStorePickStatus>();
    for (PickStatusItem source : s.getItems().getStatusItems()) {
      SStorePickStatus target = new SStorePickStatus();
      target.setBatchId(source.getBatchId());
      target.setState(source.getState());
      target.setStoreCode(s.getItems().getStoreCode());
      result.add(target);
    }
    bean.setData(result);

    return bean;
  }

}
