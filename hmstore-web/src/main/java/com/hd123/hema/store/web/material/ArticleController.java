/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-web
 * 文件名：	ArticleController.java
 * 模块说明：
 * 修改历史：
 * 2016-6-17 - xiepingping - 创建。
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

import com.hd123.hema.store.bean.material.Article;
import com.hd123.hema.store.service.material.ArticleService;
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
@RequestMapping("/article")
public class ArticleController {
  private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

  @Autowired
  private ArticleService articleService;

  @ApiOperation(value = "分页查询商品")
  @RequestMapping(value = "/list/{page}/{pageSize}", method = RequestMethod.GET)
  public @ResponseBody
  RespBean getArticles(@ApiParam(required = true, name = "page", value = "页码")
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
      PageQueryResult<Article> list = articleService.queryByPage(filter);
      resp.setObj(list);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "新增商品")
  @RequestMapping(method = RequestMethod.POST)
  public @ResponseBody
  RespBean addArticle(@RequestBody
  Article article) {
    RespBean resp = new RespBean();
    try {
      String uuid = articleService.insert(article);
      resp.setObj(uuid);

    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "修改商品")
  @RequestMapping(method = RequestMethod.PUT)
  public @ResponseBody
  RespBean editArticle(@RequestBody
  Article article) {
    RespBean resp = new RespBean();

    try {
      articleService.update(article);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "根据UUID获取商品")
  @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
  public @ResponseBody
  RespBean getArticle(@PathVariable("uuid")
  String uuid) {
    RespBean resp = new RespBean();
    try {
      Article article = articleService.getByUuid(uuid);
      if (article != null) {
        resp.setObj(article);
      } else {
        resp.setSuccess(false);
        resp.setMsg("未找到指定商品");
      }

    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "删除商品")
  @RequestMapping(value = "/{uuid}", method = RequestMethod.DELETE)
  public @ResponseBody
  RespBean deleteArticle(@PathVariable("uuid")
  String uuid) {
    RespBean resp = new RespBean();
    try {

      articleService.delete(uuid);

    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "上传商品")
  @RequestMapping(value = "/upload", method = RequestMethod.POST)
  public @ResponseBody
  RespBean upload(MultipartFile file, HttpServletRequest request) {
    RespBean resp = new RespBean();
    try {
      List<String> articlesStr = ExcelHelper.exportListFromExcel(file);

      List<Article> articles = convertArticle(articlesStr);

      articleService.insertBatch(articles);
      resp.setObj(articles.size());
    } catch (Exception e) {
      logger.error(e.getMessage());
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  private List<Article> convertArticle(List<String> articlesStr) {
    List<Article> result = new ArrayList<Article>();
    for (String source : articlesStr) {
      String[] parts = ExcelHelper.substring(source);
      Article target = new Article();
      target.setCode(parts[0]);
      target.setName(parts[1]);
      target.setCategoryCode(parts[2]);
      target.setCategoryName(parts[3]);

      result.add(target);
    }
    return result;
  }
}
