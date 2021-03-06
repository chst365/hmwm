/**
 * 业务公用实现。
 * 
 * 项目名： hmstore-plugins
 * 文件名： GoodsSendProcessor.java
 * 模块说明：
 * 修改历史：
 * 2016-7-21 - xiepingping - 创建。
 */
package com.hd123.hema.store.plugins.article.send;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hd123.hema.store.bean.material.Article;
import com.hd123.hema.store.plugins.article.send.response.PullArticleResponseBean;
import com.hd123.hema.store.service.material.ArticleService;
import com.hd123.wms.mm.common.entity.BaseStandardBean;
import com.hd123.wms.mm.common.entity.ResponseBean;
import com.hd123.wms.mm.process.exception.ProcessMMException;
import com.hd123.wms.mm.process.receive.ReceiveWithValidateProcessor;
import com.hd123.wms.mm.wdk.common.bean.WDKResponseBean;

/**
 * @author xiepingping
 * 
 */
public class ArticleSendProcessor extends ReceiveWithValidateProcessor<String> {
  private static final Logger logger = LoggerFactory.getLogger(ArticleSendProcessor.class);

  @Autowired
  private ArticleService articleService;

  @Override
  protected ResponseBean doProcess(BaseStandardBean<String> s) throws ProcessMMException {
    try {
      PullArticleResponseBean response = new PullArticleResponseBean(
          PullArticleResponseBean.STATE_CODE_SUCCESS, "成功");

      List<Article> list = articleService.queryByList();
      for (Article article : list) {
        response.getItems().add(article);
      }

      if (response.getItems().size() <= 0)
        response.setMsg("未找到可恢复商品");

      return response;
    } catch (Exception e) {
      logger.error("恢复商品资料出错", e);
      return WDKResponseBean.failure("接收失败，联系管理员");
    }
  }

}
