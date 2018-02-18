package com.hd123.hema.store.plugins.eletagstatus.receive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hd123.hema.store.service.facility.gateway.GatewayService;
import com.hd123.hema.store.service.facility.gateway.dto.StoreELeTagStatus;
import com.hd123.wms.mm.common.entity.BaseStandardBean;
import com.hd123.wms.mm.common.entity.ResponseBean;
import com.hd123.wms.mm.process.exception.ProcessMMException;
import com.hd123.wms.mm.process.receive.ReceiveWithValidateProcessor;
import com.hd123.wms.mm.wdk.common.bean.WDKResponseBean;

/**
 * @author xiepingping
 * 
 */
public class EleTagStatusReceiveProcessor extends ReceiveWithValidateProcessor<StoreELeTagStatus> {
  private static final Logger logger = LoggerFactory.getLogger(EleTagStatusReceiveProcessor.class);

  @Autowired
  private GatewayService gatewayService;

  @Override
  protected ResponseBean doProcess(BaseStandardBean<StoreELeTagStatus> s) throws ProcessMMException {
    try {
      gatewayService.receiveELeTagStatus(s.getData().getStoreCode(), s.getData().getInfos());
      return WDKResponseBean.success("成功");
    } catch (Exception e) {
      logger.error("接收电子标签状态出错", e);
      return WDKResponseBean.failure("接收失败，联系管理员");
    }
  }

}
