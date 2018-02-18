package com.hd123.hema.store.plugins.pickstatus.receive;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hd123.hema.store.bean.query.SStorePickStatus;
import com.hd123.hema.store.service.query.StorePickStatusService;
import com.hd123.wms.mm.common.entity.BaseStandardBean;
import com.hd123.wms.mm.common.entity.ResponseBean;
import com.hd123.wms.mm.process.exception.ProcessMMException;
import com.hd123.wms.mm.process.receive.ReceiveWithValidateProcessor;
import com.hd123.wms.mm.wdk.common.bean.WDKResponseBean;

/**
 * @author xiepingping
 * 
 */
public class PickStatusReceiveProcessor extends
    ReceiveWithValidateProcessor<List<SStorePickStatus>> {
  private static final Logger logger = LoggerFactory.getLogger(PickStatusReceiveProcessor.class);

  @Autowired
  private StorePickStatusService pickStatusService;

  @Override
  protected ResponseBean doProcess(BaseStandardBean<List<SStorePickStatus>> s)
      throws ProcessMMException {
    try {
      pickStatusService.receivePickStatus(s.getData());
      return WDKResponseBean.success("成功");
    } catch (Exception e) {
      logger.error("接收拣货进度情况出错", e);
      return WDKResponseBean.failure("接收失败，联系管理员");
    }
  }

}
