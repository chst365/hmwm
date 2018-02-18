package com.hd123.hema.store.plugins.pickstatus.receive.request;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.util.StringUtils;

import com.hd123.wms.mm.common.util.JsonUtils;
import com.hd123.wms.mm.common.util.SignUtils;
import com.hd123.wms.mm.wdk.common.bean.WDKRequestBean;
import com.hd123.wms.mm.wdk.common.util.AppKeyFactory;

/**
 * @author xiepingping
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PickStatusRequestBean extends WDKRequestBean {
  private static final long serialVersionUID = -8808676448445608720L;

  private PickStatus items;

  public PickStatus getItems() {
    return items;
  }

  public void setItems(PickStatus items) {
    this.items = items;
  }

  public static void main(String[] arg) {
    PickStatusRequestBean bean = new PickStatusRequestBean();
      
      String appId = "wdk";
      String secet = AppKeyFactory.getSecretByKey(appId);
      if (StringUtils.isEmpty(secet)) {
          secet = "default";
      }
      Date requestTime = new Date();
      
      String source = appId + "&&" + secet + "&&" + requestTime.getTime();
      String sign = SignUtils.getSign(source);
      
      bean.setAppId(appId);
      bean.setRequestTime(String.valueOf(requestTime.getTime()));
      bean.setSign(sign);
      
      PickStatus order = new PickStatus();
      order.setStoreCode("WH001");
      PickStatusItem item = new PickStatusItem();
      item.setBatchId("123");
      item.setState(0);
      order.getStatusItems().add(item);
      bean.setItems(order);

      System.out.println(JsonUtils.objectToString(bean));
  }
}
