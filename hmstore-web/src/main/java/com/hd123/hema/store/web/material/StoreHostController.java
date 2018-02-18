/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-web
 * 文件名：	StoreHostController.java
 * 模块说明：
 * 修改历史：
 * 2016-6-23 - xiepingping - 创建。
 */
package com.hd123.hema.store.web.material;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hd123.hema.store.bean.material.StoreHost;
import com.hd123.hema.store.service.material.StoreHostService;
import com.hd123.hema.store.web.common.response.RespBean;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * @author xiepingping
 * 
 */
@Controller
@RequestMapping("/storehost")
public class StoreHostController {

  @Autowired
  private StoreHostService storeHostService;

  @ApiOperation(value = "查询门店主机列表")
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public @ResponseBody
  RespBean getStoreHosts(@RequestParam("orgUuid")
  String orgUuid) {
    RespBean resp = new RespBean();
    try {
      List<StoreHost> storeHosts = storeHostService.queryByList(orgUuid);
      resp.setObj(storeHosts);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "新增门店主机")
  @RequestMapping(method = RequestMethod.POST)
  public @ResponseBody
  RespBean addStoreHost(@RequestBody
  StoreHost storeHost) {
    RespBean resp = new RespBean();
    try {
      String storeHostUuid = storeHostService.insert(storeHost);
      resp.setObj(storeHostUuid);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "修改门店主机")
  @RequestMapping(method = RequestMethod.PUT)
  public @ResponseBody
  RespBean editStoreHost(@RequestBody
  StoreHost storeHost) {
    RespBean resp = new RespBean();
    try {
      storeHostService.update(storeHost);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "根据UUID获取门店主机")
  @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
  public @ResponseBody
  RespBean getStoreHost(@PathVariable("uuid")
  String uuid) {
    RespBean resp = new RespBean();
    try {
      StoreHost storeHost = storeHostService.getByUuid(uuid);
      resp.setObj(storeHost);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }
    return resp;
  }

  @ApiOperation(value = "删除门店主机")
  @RequestMapping(value = "/{uuid}", method = RequestMethod.DELETE)
  public @ResponseBody
  RespBean deleteStoreHost(@PathVariable("uuid")
  String uuid) {
    RespBean resp = new RespBean();
    try {
      storeHostService.delete(uuid);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }
    return resp;
  }

}