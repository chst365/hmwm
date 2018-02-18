package com.hd123.hema.store.web.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hd123.hema.store.bean.query.StorePickStatus;
import com.hd123.hema.store.service.query.StorePickStatusService;
import com.hd123.hema.store.web.common.response.RespBean;
import com.hd123.wms.antman.common.query.QueryParam;
import com.hd123.wms.antman.common.utils.DateUtils;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * @author xiepingping
 * 
 */
@Controller
@RequestMapping("/storepickstatus")
public class StorePickStatusController {

  @Autowired
  private StorePickStatusService statusService;

  @ApiOperation(value = "门店拣货情况查询")
  @RequestMapping(value = "/listdate", method = RequestMethod.GET)
  public @ResponseBody
  RespBean getStorePickDate(@RequestParam(required = false)
  List<String> storeCodes, @RequestParam
  String pickDate) {
    RespBean resp = new RespBean();
    QueryParam param = new QueryParam();
    param.put("storeCodes", storeCodes);
    if (StringUtils.isEmpty(pickDate) == false)
      param.put("pickDate", DateUtils.strToDate(pickDate));

    try {
      List<StorePickStatus> status = statusService.queryByList(param);
      StorePickStatusGroup result = convertStatus(status);
      resp.setObj(result);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  private StorePickStatusGroup convertStatus(List<StorePickStatus> status) {
    Map<Integer, Integer> map = new HashMap<Integer, Integer>();
    for (StorePickStatus source : status) {
      @SuppressWarnings("deprecation")
      int key = source.getRecordCreateTime().getHours();
      map.put(key,
          map.get(key) == null ? source.getOrderCount() : source.getOrderCount() + map.get(key));
    }

    StorePickStatusGroup result = new StorePickStatusGroup();

    List<StorePickStatusChart> charts = new ArrayList<StorePickStatusChart>();
    for (int hours : map.keySet()) {
      StorePickStatusChart chart = new StorePickStatusChart();
      chart.setCreateHours(hours);
      chart.setOrderCount(map.get(hours));
      charts.add(chart);
    }
    result.setCharts(charts);
    result.setStatus(status);
    return result;
  }

}
