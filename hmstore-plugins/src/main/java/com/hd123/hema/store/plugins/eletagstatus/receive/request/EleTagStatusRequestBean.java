package com.hd123.hema.store.plugins.eletagstatus.receive.request;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.util.StringUtils;

import com.hd123.hema.store.bean.facility.gateway.NodeState;
import com.hd123.wms.mm.common.util.JsonUtils;
import com.hd123.wms.mm.common.util.SignUtils;
import com.hd123.wms.mm.wdk.common.bean.WDKRequestBean;
import com.hd123.wms.mm.wdk.common.util.AppKeyFactory;

/**
 * @author xiepingping
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EleTagStatusRequestBean extends WDKRequestBean {
  private static final long serialVersionUID = -2773303760957333389L;

  private EleTagStatus items;

  public EleTagStatus getItems() {
    return items;
  }

  public void setItems(EleTagStatus items) {
    this.items = items;
  }

  public static void main(String[] arg) {
    EleTagStatusRequestBean bean = new EleTagStatusRequestBean();
      
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
      
      EleTagStatus order = new EleTagStatus();
      order.setStoreCode("WH001");
      ELeTagStatusItem item = new ELeTagStatusItem();
      item.setCheckTime(new Date());
      item.setNodeCode("111");
      item.setNodeState(NodeState.Normal);
      item.setRemark("222");
      order.getStatusItems().add(item);
      bean.setItems(order);

      System.out.println(JsonUtils.objectToString(bean));
  }
}
