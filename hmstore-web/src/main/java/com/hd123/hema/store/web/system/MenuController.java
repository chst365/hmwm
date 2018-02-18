package com.hd123.hema.store.web.system;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hd123.hema.store.web.common.response.RespBean;
import com.hd123.hema.store.web.system.dto.SSFunction;
import com.hd123.wms.antman.system.bean.Function;
import com.hd123.wms.antman.system.bean.FunctionTree;
import com.hd123.wms.antman.system.service.MenuService;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * @author xiepingping
 * 
 */
@Controller
@RequestMapping("/menu")
public class MenuController {

  @Autowired
  private MenuService menuService;
  
  @ApiOperation(value = "根据用户code获取权限菜单")
  @RequestMapping(value = "/{loginId}", method = RequestMethod.GET)
  public @ResponseBody
  RespBean getMenu(@PathVariable("loginId")
  String loginId) {
    RespBean resp = new RespBean();
    try {
      List<Function> funcs = menuService.getByUuid(loginId);
      FunctionTree tree = new FunctionTree(funcs); 
      List<Function> treerootlist=new ArrayList<Function>();
      treerootlist=tree.buildTree();
      List<SSFunction> sfuncs=convertListFunction(treerootlist);
      
      resp.setObj(sfuncs);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }
    return resp;
  }
  
  
  private List<SSFunction> convertListFunction(List<Function> source) {
    if (source == null)
      return null;

    List<SSFunction> sfunclist = new ArrayList<SSFunction>();
    for(Function func:source)
    {
      SSFunction ssfunc=new SSFunction();
      ssfunc.setName(func.getName());
      ssfunc.setIconClass(func.getIconClass());
      ssfunc.setUrl(func.getUrl());
      ssfunc.setsChild(convertListFunction(func.getChild()));
      sfunclist.add(ssfunc);
    }

    return sfunclist;
  }
  
}//class--end
