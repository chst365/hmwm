/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-service
 * 文件名：	ArticleServiceImpl.java
 * 模块说明：
 * 修改历史：
 * 2016-6-17 - xiepingping - 创建。
 */
package com.hd123.hema.store.service.material.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hd123.hema.store.bean.facility.pickscheme.BinArticle;
import com.hd123.hema.store.bean.material.Article;
import com.hd123.hema.store.dao.facility.pickscheme.BinArticleDao;
import com.hd123.hema.store.dao.material.ArticleDao;
import com.hd123.hema.store.service.material.ArticleService;
import com.hd123.rumba.commons.lang.Assert;
import com.hd123.wms.antman.common.query.PageQueryDefinition;
import com.hd123.wms.antman.common.query.PageQueryResult;
import com.hd123.wms.antman.common.query.PageQueryUtil;
import com.hd123.wms.antman.common.query.QueryParam;
import com.hd123.wms.antman.common.utils.ListUtils;
import com.hd123.wms.antman.common.utils.UUIDGenerator;

/**
 * @author xiepingping
 * 
 */
@Service
public class ArticleServiceImpl implements ArticleService {

  @Autowired
  private ArticleDao articleDao;
  @Autowired
  private BinArticleDao binArticleDao;

  @Override
  public String insert(Article article) {
    Assert.assertArgumentNotNull(article, "article");
    article.validate();

    validByUniqueKey(article.getCode(), null);

    article.setUuid(UUIDGenerator.genUUID());
    articleDao.insert(article);

    return article.getUuid();
  }

  @Override
  public void insertBatch(List<Article> articles) {
    Assert.assertArgumentNotNull(articles, "articles");

    for (int i = 0; i < articles.size(); i++) {
      Article iArticle = articles.get(i);
      for (int j = i + 1; j < articles.size(); j++) {
        Article jArticle = articles.get(j);
        if (iArticle.getCode().equals(jArticle.getCode()))
          throw new IllegalArgumentException("商品第" + (i + 1) + "行与第" + (j + 1) + "行代码重复。");
      }
    }

    for (Article article : articles) {
      Assert.assertArgumentNotNull(article, "article");
      article.validate();

      validByUniqueKey(article.getCode(), null);

      article.setUuid(UUIDGenerator.genUUID());
    }

    for (List<Article> list : ListUtils.splitList(articles, 1000))
      articleDao.insertBatch(list);
  }

  @Override
  public void update(Article article) {
    Assert.assertArgumentNotNull(article, "article");
    article.validate();

    Article dbArticle = articleDao.getByUuid(article.getUuid());
    if (dbArticle == null)
      throw new IllegalArgumentException("商品不存在");

    validByUniqueKey(article.getCode(), article.getUuid());

    articleDao.update(article);
  }

  private void validByUniqueKey(String code, String uuid) {
    assert code != null;

    Article dbArticle = articleDao.getByCode(code);
    if (dbArticle != null && dbArticle.getUuid().equals(uuid) == false)
      throw new IllegalArgumentException("已存在代码为" + code + "的商品");
  }

  @Override
  public void delete(String uuid) {
    if (StringUtils.isEmpty(uuid))
      return;

    validByForeignKey(uuid);

    articleDao.delete(uuid);
  }

  private void validByForeignKey(String articleUuid) {
    List<BinArticle> dbbas = binArticleDao.getByArticleUuid(articleUuid);
    if (CollectionUtils.isEmpty(dbbas) == false)
      throw new IllegalArgumentException("当前商品正在被分拣方案/分拣方案模板使用，不能删除");
  }

  @Override
  public Article getByUuid(String uuid) {
    if (StringUtils.isEmpty(uuid))
      return null;

    return articleDao.getByUuid(uuid);
  }

  @Override
  public PageQueryResult<Article> queryByPage(PageQueryDefinition filter) {
    Assert.assertArgumentNotNull(filter, "filter");

    PageQueryResult<Article> pgr = new PageQueryResult<Article>();
    List<Article> list = articleDao.queryByPage(filter);
    PageQueryUtil.assignPageInfo(pgr, filter);
    pgr.setRecords(list);
    return pgr;
  }

  @Override
  public List<Article> queryByList() {
    QueryParam params = new QueryParam();
    return articleDao.queryByList(params);
  }

  @Override
  public Article getByCode(String code) {
    if (StringUtils.isEmpty(code))
      return null;

    return articleDao.getByCode(code);
  }

}
