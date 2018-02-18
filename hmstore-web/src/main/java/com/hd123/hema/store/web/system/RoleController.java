/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-web
 * 文件名：	RoleController.java
 * 模块说明：
 * 修改历史：
 * 2016-6-23 - xiepingping - 创建。
 */
package com.hd123.hema.store.web.system;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hd123.hema.store.web.common.response.RespBean;
import com.hd123.hema.store.web.system.dto.SFunction;
import com.hd123.hema.store.web.system.dto.SRole;
import com.hd123.wms.antman.common.query.QueryParam;
import com.hd123.wms.antman.system.bean.Function;
import com.hd123.wms.antman.system.bean.FunctionTree;
import com.hd123.wms.antman.system.bean.Role;
import com.hd123.wms.antman.system.dao.FunctionDao;
import com.hd123.wms.antman.system.dao.RoleDao;
import com.hd123.wms.antman.system.service.FunctionService;
import com.hd123.wms.antman.system.service.RoleFunctionService;
import com.hd123.wms.antman.system.service.RoleService;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * @author xiepingping
 * 
 */
@Controller
@RequestMapping("/role")
public class RoleController {

  private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

  @Autowired
  private RoleService roleService;
  
  @Autowired
  private RoleFunctionService rolefunctionService;
  
  @Autowired
  private FunctionService functionService;
  
  @Autowired
  private FunctionDao functionDao;
  
  @Autowired
  private RoleDao roleDao;

  @ApiOperation(value = "查询角色列表，结果集不包含权限")
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public @ResponseBody
  RespBean getRoles(@RequestParam("orgUuid")
  String orgUuid) {
    RespBean resp = new RespBean();
    QueryParam param = new QueryParam();
    try {
      param.put("tenantUuid", orgUuid);
      List<Role> list = roleService.queryByList(param);
      List<SRole> sRoles = new ArrayList<SRole>();
      for (Role source : list) {
        SRole target = new SRole();
        target.setUuid(source.getUuid());
        target.setCode(source.getCode());
        target.setName(source.getName());
        sRoles.add(target);
      }

      resp.setObj(sRoles);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }
    return resp;
  }
  
  //--------------------------------------初始化给所有权限--------------------------
  @ApiOperation(value = "查询角色列表，给所有权限")
  @RequestMapping(value = "/func", method = RequestMethod.GET)
  public @ResponseBody
  RespBean getRolesFunc() {
    RespBean resp = new RespBean();
    try {
      List<Function> sqllist = functionDao.queryAllBylist();
      FunctionTree tree = new FunctionTree(sqllist); 
      List<Function> treerootlist=new ArrayList<Function>();
      
      treerootlist=tree.buildTree();
      resp.setObj(treerootlist);
      
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }
    return resp;
  }
  //-------------------------end----------初始化给所有权限--------------------------
  
  @ApiOperation(value = "新增角色")
  @RequestMapping(method = RequestMethod.POST)
  public @ResponseBody
  RespBean addRole(@RequestBody
   SRole srole) {
    RespBean resp = new RespBean();
    try {
      if (CollectionUtils.isEmpty(srole.getFunctions()))
        throw new IllegalArgumentException("权限不能为空");

      Role role=convertSRole(srole);
      
      String uuid = roleService.insert(role);
      resp.setObj(uuid);

    } catch (Exception e) {
      logger.info(e.getMessage());
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }
    return resp;
  }
 

  @ApiOperation(value = "修改角色")
  @RequestMapping(method = RequestMethod.PUT)
  public @ResponseBody
  RespBean editRole(@RequestBody
  SRole srole) {
    RespBean resp = new RespBean();
    try {
      Role role=convertSRole(srole);
      roleService.update(role);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }
    return resp;
  }

  @ApiOperation(value = "根据UUID获取角色，结果集包含角色权限")
  @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
  public @ResponseBody
  RespBean getRole(@PathVariable("uuid")
  String uuid) {
    RespBean resp = new RespBean();
    try {
      Role role = roleService.getByUuid(uuid);
      SRole srole=convertRole(role);
      resp.setObj(srole);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }
    return resp;
  }

  @ApiOperation(value = "删除角色")
  @RequestMapping(value = "/{uuid}", method = RequestMethod.DELETE)
  public @ResponseBody
  RespBean deleteRole(@PathVariable("uuid")
  String uuid) {
    RespBean resp = new RespBean();
    try {
      roleService.delete(uuid);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }
    return resp;
  }

  
  //-------------SRole转成Role的页面展示
  private Role convertSRole(SRole source) {
    if (source == null)
      return null;

    Role role = new Role();
    role.setUuid(source.getUuid());
    role.setCode(source.getCode());
    role.setName(source.getName());
    role.setCreator(source.getOperator());
    role.setModifier(source.getOperator());
    role.setTenantUuid(source.getOrgUuid());
    role.setFunctions(convertListSFunction(source.getFunctions()));
    return role;
  }
  
  private List<Function> convertListSFunction(List<SFunction> source) {
    if (source == null)
      return new ArrayList<Function>();
    //用set对权限,去重
    Set<String> funclist = new HashSet<String>();
    for(SFunction sfunc:source) {
      funclist.add(sfunc.getUuid());
    }
    List<Function> result = new ArrayList<Function>();
    for (String uuid : funclist) {
      Function function = new Function();
      function.setUuid(uuid);
      result.add(function);
    }

    return result;
  }

  //-------------Role转成SRole------------------------------

  private SRole convertRole(Role source) {
    if (source == null)
      return null;

    SRole srole = new SRole();
    srole.setUuid(source.getUuid());
    srole.setCode(source.getCode());
    srole.setName(source.getName());
    srole.setOperator(source.getModifier());
    srole.setOrgUuid(source.getTenantUuid());
    srole.setFunctions(convertListFunction(source.getFunctions()));
    return srole;
  }
  
  private List<SFunction> convertListFunction(List<Function> source) {
    if (source == null)
      return null;

    List<SFunction> sfunclist = new ArrayList<SFunction>();
    for(Function func:source)
    {
      SFunction sfunc=new SFunction();
      sfunc.setUuid(func.getUuid());
      sfunc.setName(func.getName());
      sfunc.setCheck(func.isCheck());
      sfunc.setUpperFunctionUuid(func.getUpperFunctionUuid());
      sfunc.setChild(convertListFunction(func.getChild()));
      sfunclist.add(sfunc);
    }

    return sfunclist;
  }
  


}// ---end--class