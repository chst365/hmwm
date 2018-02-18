package com.hd123.hema.store.web.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hd123.hema.store.web.common.response.RespBean;
import com.hd123.wms.antman.system.bean.Enterprise;
import com.hd123.wms.antman.system.service.EnterpriseService;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * @author xiepingping
 * 
 */
@Controller
@RequestMapping("/enterprise")
public class EnterpriseController {

  @Autowired
  private EnterpriseService enterpriseService;

  @ApiOperation(value = "根据UUID获取企业")
  @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
  public @ResponseBody
  RespBean getEnterprise(@PathVariable("uuid")
  String uuid) {
    //注释
    RespBean resp = new RespBean();
    try {
      Enterprise enterprise = enterpriseService.getByUuid(uuid);
      resp.setObj(enterprise);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }
    return resp;
  }

  @ApiOperation(value = "查询所有企业")
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public @ResponseBody
  RespBean getEnterprises() {
    RespBean resp = new RespBean();

    try {
      List<Enterprise> sqllist = enterpriseService.queryAllByList();
      resp.setObj(sqllist);

    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }
    return resp;
  }

}// class end
