/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-web
 * 文件名：	GatewayController.java
 * 模块说明：
 * 修改历史：
 * 2016-6-23 - xiepingping - 创建。
 */
package com.hd123.hema.store.web.facility.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hd123.hema.store.bean.facility.gateway.Gateway;
import com.hd123.hema.store.service.facility.gateway.ElectronicTagService;
import com.hd123.hema.store.service.facility.gateway.GatewayService;
import com.hd123.hema.store.service.facility.gateway.dto.ElectronicTagStateInfo;
import com.hd123.hema.store.web.common.response.RespBean;
import com.hd123.hema.store.web.facility.gateway.dto.EleTagStateInfo;
import com.hd123.rumba.commons.biz.entity.UCN;
import com.hd123.wms.antman.common.query.QueryParam;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * @author xiepingping
 * 
 */
@Controller
@RequestMapping("/gateway")
public class GatewayController {

  @Autowired
  private GatewayService gatewayService;
  @Autowired
  private ElectronicTagService eleTagService;

  @ApiOperation(value = "查询网关列表")
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public @ResponseBody
  RespBean getGateways(@RequestParam("orgUuid")
  String orgUuid) {
    RespBean resp = new RespBean();

    try {
      QueryParam param = new QueryParam();
      param.put("orgUuid", orgUuid);
      List<Gateway> gateways = gatewayService.queryByList(param);
      resp.setObj(gateways);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "新增网关")
  @RequestMapping(method = RequestMethod.POST)
  public @ResponseBody
  RespBean addGateway(@RequestBody
  Gateway gateway) {
    RespBean resp = new RespBean();

    try {
      String gatewayUuid = gatewayService.insert(gateway);
      resp.setObj(gatewayUuid);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "修改网关")
  @RequestMapping(method = RequestMethod.PUT)
  public @ResponseBody
  RespBean editGateway(@RequestBody
  Gateway gateway) {
    RespBean resp = new RespBean();

    try {
      gatewayService.update(gateway);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "根据UUID获取网关")
  @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
  public @ResponseBody
  RespBean getGateway(@PathVariable("uuid")
  String uuid) {
    RespBean resp = new RespBean();

    try {
      Gateway gateway = gatewayService.getByUuid(uuid);
      resp.setObj(gateway);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "删除网关")
  @RequestMapping(value = "/{uuid}", method = RequestMethod.DELETE)
  public @ResponseBody
  RespBean deleteGateway(@PathVariable("uuid")
  String uuid) {
    RespBean resp = new RespBean();

    try {
      gatewayService.delete(uuid);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }
//
//  @ApiOperation(value = "根据网关查询电子标签列表")
//  @RequestMapping(value = "/eletag/list", method = RequestMethod.GET)
//  public @ResponseBody
//  RespBean getElectronicTagsByGateway(@RequestParam("gatewayUuid")
//  String gatewayUuid) {
//    RespBean resp = new RespBean();
//
//    try {
//      QueryParam param = new QueryParam();
//      param.put("gatewayUuid", gatewayUuid);
//      List<Gateway> gateways = gatewayService.queryByList(param);
//      resp.setObj(gateways);
//    } catch (Exception e) {
//      resp.setSuccess(false);
//      resp.setMsg(e.getMessage());
//    }
//
//    return resp;
//  }

  @ApiOperation(value = "根据门店查询电子标签列表，参数为空表示查询所有门店（总部查询用）果集包括标签所属门店")
  @RequestMapping(value = "/eletag/lists", method = RequestMethod.GET)
  public @ResponseBody
  RespBean getElectronicTags(@RequestParam(required = false)
  String orgUuid) {
    RespBean resp = new RespBean();

    try {
      QueryParam param = new QueryParam();
      param.put("orgUuid", orgUuid);
      Map<UCN, List<ElectronicTagStateInfo>> map = eleTagService.queryStateInfoByList(param);
      List<EleTagStateInfo> result = new ArrayList<EleTagStateInfo>();
      for (UCN store : map.keySet()) {
        EleTagStateInfo target = new EleTagStateInfo();
        target.setStoreName(store.getName());
        target.setInfos(map.get(store));
        result.add(target);
      }
      resp.setObj(result);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

//  @ApiOperation(value = "新增电子标签")
//  @RequestMapping(value = "/eletag", method = RequestMethod.POST)
//  public @ResponseBody
//  RespBean addElectronicTag(@RequestBody
//  ElectronicTag electronicTag) {
//    RespBean resp = new RespBean();
//
//    try {
//      String eleTagUuid = eleTagService.insert(electronicTag);
//      resp.setObj(eleTagUuid);
//    } catch (Exception e) {
//      resp.setSuccess(false);
//      resp.setMsg(e.getMessage());
//    }
//
//    return resp;
//  }
//
//  @ApiOperation(value = "修改电子标签")
//  @RequestMapping(value = "/eletag", method = RequestMethod.PUT)
//  public @ResponseBody
//  RespBean editElectronicTag(@RequestBody
//  ElectronicTag electronicTag) {
//    RespBean resp = new RespBean();
//
//    try {
//      eleTagService.update(electronicTag);
//    } catch (Exception e) {
//      resp.setSuccess(false);
//      resp.setMsg(e.getMessage());
//    }
//
//    return resp;
//  }
//
//  @ApiOperation(value = "删除电子标签")
//  @RequestMapping(value = "/eletag/{uuid}", method = RequestMethod.DELETE)
//  public @ResponseBody
//  RespBean deleteElectronicTag(@PathVariable("uuid")
//  String uuid) {
//    RespBean resp = new RespBean();
//
//    try {
//      eleTagService.delete(uuid);
//    } catch (Exception e) {
//      resp.setSuccess(false);
//      resp.setMsg(e.getMessage());
//    }
//
//    return resp;
//  }

}