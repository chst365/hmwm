/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-web
 * 文件名：	StoreController.java
 * 模块说明：
 * 修改历史：
 * 2016-6-23 - xiepingping - 创建。
 */
package com.hd123.hema.store.web.material;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.multipart.MultipartFile;

import com.hd123.hema.store.bean.material.Store;
import com.hd123.hema.store.service.material.StoreService;
import com.hd123.hema.store.web.common.response.RespBean;
import com.hd123.hema.store.web.common.utils.ExcelHelper;
import com.hd123.wms.antman.common.query.PageQueryDefinition;
import com.hd123.wms.antman.common.query.PageQueryResult;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * @author xiepingping
 * 
 */
@Controller
@RequestMapping("/store")
public class StoreController {
  private static final Logger logger = LoggerFactory.getLogger(StoreController.class);

  @Autowired
  private StoreService storeService;

  @ApiOperation(value = "分页查询门店")
  @RequestMapping(value = "/list/{page}/{pageSize}", method = RequestMethod.GET)
  public @ResponseBody
  RespBean getStores(@ApiParam(required = true, name = "page", value = "页码")
  @PathVariable("page")
  Integer page, @ApiParam(required = true, name = "pageSize", value = "页大小")
  @PathVariable("pageSize")
  Integer pageSize, @RequestParam(required = false)
  String code) {
    RespBean resp = new RespBean();
    PageQueryDefinition filter = new PageQueryDefinition();
    filter.setPage(page);
    filter.setPageSize(pageSize);
    filter.put("code", code);

    try {
      PageQueryResult<Store> list = storeService.queryByPage(filter);
      resp.setObj(list);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "新增门店")
  @RequestMapping(method = RequestMethod.POST)
  public @ResponseBody
  RespBean addStore(@RequestBody
  Store store) {
    RespBean resp = new RespBean();
    try {
      String uuid = storeService.insert(store);
      resp.setObj(uuid);

    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "修改门店")
  @RequestMapping(method = RequestMethod.PUT)
  public @ResponseBody
  RespBean editStore(@RequestBody
  Store store) {
    RespBean resp = new RespBean();
    try {
      storeService.update(store);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "根据UUID获取门店")
  @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
  public @ResponseBody
  RespBean getStore(@PathVariable("uuid")
  String uuid) {
    RespBean resp = new RespBean();
    try {
      Store store = storeService.getByUuid(uuid);
      resp.setObj(store);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "删除门店")
  @RequestMapping(value = "/{uuid}", method = RequestMethod.DELETE)
  public @ResponseBody
  RespBean deleteStore(@PathVariable("uuid")
  String uuid) {
    RespBean resp = new RespBean();
    try {
      storeService.delete(uuid);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "上传门店")
  @RequestMapping(value = "/upload", method = RequestMethod.POST)
  public @ResponseBody
  RespBean upload(MultipartFile file, HttpServletRequest request) {
    RespBean resp = new RespBean();
    try {
      List<String> storesStr = ExcelHelper.exportListFromExcel(file);

      List<Store> stores = convertStores(storesStr);
      storeService.insertBatch(stores);
      resp.setObj(stores.size());
    } catch (Exception e) {
      logger.error(e.getMessage());
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  private List<Store> convertStores(List<String> storesStr) {
    List<Store> result = new ArrayList<Store>();
    for (String source : storesStr) {
      String[] parts = ExcelHelper.substring(source);
      Store target = new Store();
      target.setCode(parts[0]);
      target.setName(parts[1]);
      target.setAddress(parts[2]);
      target.setRemark(parts[3]);

      result.add(target);
    }
    return result;
  }

}