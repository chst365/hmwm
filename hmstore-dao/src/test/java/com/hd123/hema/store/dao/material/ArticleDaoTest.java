/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-dao
 * 文件名：	ArticleDaoTest.java
 * 模块说明：
 * 修改历史：
 * 2016-8-8 - xiepingping - 创建。
 */
package com.hd123.hema.store.dao.material;

import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.hd123.hema.store.bean.material.Article;
import com.hd123.hema.store.dao.AbstractDataAccessTests;
import com.hd123.wms.antman.common.query.PageQueryDefinition;
import com.hd123.wms.antman.common.query.QueryParam;

/**
 * @author xiepingping
 * 
 */
@DatabaseSetup({
  "article/InitData.xml" })
public class ArticleDaoTest extends AbstractDataAccessTests {

  @Autowired
  private ArticleDao articleDao;

  @Test
  public void getByUuid() {
    Article article = articleDao.getByUuid("1001");

    assertNotNull(article);
    Assert.assertEquals("1001", article.getUuid());
    Assert.assertEquals("A001", article.getCode());
    Assert.assertEquals("摩西赤豆米仁羹", article.getName());
    Assert.assertEquals("morning", article.getCategoryCode());
    Assert.assertEquals("早餐", article.getCategoryName());
  }

  @Test
  public void getByCode() {
    Article article = articleDao.getByCode("A001");

    assertNotNull(article);
    Assert.assertEquals("1001", article.getUuid());
    Assert.assertEquals("A001", article.getCode());
    Assert.assertEquals("摩西赤豆米仁羹", article.getName());
    Assert.assertEquals("morning", article.getCategoryCode());
    Assert.assertEquals("早餐", article.getCategoryName());
  }

  @Test
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
      value = "article/InsertArticle.xml")
  public void insert() throws ParseException {
    Article article = new Article();
    article.setUuid("1004");
    article.setCode("A004");
    article.setName("山药炒肉片");
    article.setCategoryCode("noon");
    article.setCategoryName("午餐");

    articleDao.insert(article);
  }

  @Test
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
      value = "article/InsertBatchArticle.xml")
  public void insertBatch() throws ParseException {
    List<Article> articles = new ArrayList<Article>();
    Article article1 = new Article();
    article1.setUuid("1004");
    article1.setCode("A004");
    article1.setName("山药炒肉片");
    article1.setCategoryCode("noon");
    article1.setCategoryName("午餐");
    articles.add(article1);

    Article article2 = new Article();
    article2.setUuid("1005");
    article2.setCode("A005");
    article2.setName("五香素鸡");
    article2.setCategoryCode("noon");
    article2.setCategoryName("午餐");
    articles.add(article2);
    articleDao.insertBatch(articles);
  }

  @Test
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
      value = "article/UpdateArticle.xml")
  public void update() throws ParseException {
    Article article = articleDao.getByCode("A001");
    assertNotNull(article);

    article.setCategoryName("早餐2");
    articleDao.update(article);
  }

  @Test
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
      value = "article/DeleteArticle.xml")
  public void delete() {
    articleDao.delete("1001");
  }

  @Test
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
      value = "article/QueryListArticle.xml")
  public void querylist() {
    QueryParam param = new QueryParam();
    articleDao.queryByList(param);
  }

  @Test
  public void querypage() {
    PageQueryDefinition filter = new PageQueryDefinition();
    int page = 1;
    int pageSize = 2;
    filter.setPage(page);
    filter.setPageSize(pageSize);

    List<Article> articles = articleDao.queryByPage(filter);
    Assert.assertEquals(2, articles.size());
  }

}
