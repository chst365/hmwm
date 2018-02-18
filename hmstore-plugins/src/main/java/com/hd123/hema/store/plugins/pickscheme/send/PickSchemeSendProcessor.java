/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-plugins
 * 文件名：	PickSchemeSendProcessor.java
 * 模块说明：
 * 修改历史：
 * 2016-8-1 - xiepingping - 创建。
 */
package com.hd123.hema.store.plugins.pickscheme.send;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hd123.hema.store.bean.facility.pickscheme.PickScheme;
import com.hd123.hema.store.bean.facility.pickscheme.PickSchemeState;
import com.hd123.hema.store.bean.material.Store;
import com.hd123.hema.store.plugins.pickscheme.send.response.PullPickSchemeResponseBean;
import com.hd123.hema.store.service.facility.pickscheme.PickSchemeService;
import com.hd123.hema.store.service.material.StoreService;
import com.hd123.wms.antman.common.query.QueryParam;
import com.hd123.wms.mm.common.entity.BaseStandardBean;
import com.hd123.wms.mm.common.entity.ResponseBean;
import com.hd123.wms.mm.process.exception.ProcessMMException;
import com.hd123.wms.mm.process.receive.ReceiveWithValidateProcessor;
import com.hd123.wms.mm.wdk.common.bean.WDKResponseBean;

/**
 * @author xiepingping
 * 
 */
public class PickSchemeSendProcessor extends ReceiveWithValidateProcessor<String> {
  private static final Logger logger = LoggerFactory.getLogger(PickSchemeSendProcessor.class);

  @Autowired
  private PickSchemeService pickSchemeService;
  @Autowired
  private StoreService storeService;

  @Override
  protected ResponseBean doProcess(BaseStandardBean<String> s) throws ProcessMMException {
    try {
      PullPickSchemeResponseBean response = new PullPickSchemeResponseBean(
          PullPickSchemeResponseBean.STATE_CODE_SUCCESS, "成功");

      Store store = storeService.getByCode(s.getData());
      if (store == null)
        throw new IllegalArgumentException("门店" + s.getData() + "不存在");

      QueryParam param = new QueryParam();
      param.put("orgUuid", store.getUuid());
      param.put("state", PickSchemeState.Using.name());
      List<PickScheme> list = pickSchemeService.queryByList(param);
      for (PickScheme scheme : list) {
        response.getItems().add(scheme);
      }

      if (response.getItems().size() <= 0)
        response.setMsg("未找到可恢复分拣方案");

      return response;
    } catch (Exception e) {
      logger.error("恢复商品分拣方案出错", e);
      return WDKResponseBean.failure("接收失败，联系管理员");
    }
  }

}
