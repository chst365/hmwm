package com.hd123.hema.store.dao.facility.pickscheme;


import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.hd123.hema.store.bean.facility.pickscheme.BinArticle;
import com.hd123.hema.store.dao.AbstractDataAccessTests;
import com.hd123.rumba.commons.biz.entity.UCN;
import com.hd123.wms.antman.common.query.PageQueryDefinition;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DatabaseSetup({"./InitPickschemeBinArticle.xml" })
public class BinArticleDaoTest extends AbstractDataAccessTests {
    @Autowired
    private BinArticleDao binArticleDao;

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./InsertPickschemeBinArticle.xml")
    public void insert() {
        BinArticle binArticle = new BinArticle();
        binArticle.setUuid("0eba248a9d2f4d6da6b919e712d78dfe");
        binArticle.setBinCode("ckjAA1");
        UCN article = new UCN();
        article.setUuid("ba47a36292b04ac6aaf9199e3326dc05");
        article.setCode("A001");
        article.setName("程序员的自我修养");
        binArticle.setArticle(article);
        binArticle.setPickSchemeUuid("b68ff957f95d443d8b88ea5a2a75bdb2");
        binArticleDao.insert(binArticle);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./UpdatePickschemeBinArticle.xml")
    public void update() {
        BinArticle binArticle = binArticleDao.getByUuid("02bdbeff95f24fcdba6348dfa8bbc460");
        binArticle.setBinCode("ckjAA1");
        UCN article = binArticle.getArticle();
        article.setUuid("ba47a36292b04ac6aaf9199e3326dc05");
        article.setCode("A001");
        article.setName("程序员的自我修养");
        binArticle.setArticle(article);
        binArticleDao.update(binArticle);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./DeletePickschemeBinArticle.xml")
    public void delete() {
        binArticleDao.delete("02bdbeff95f24fcdba6348dfa8bbc460");
    }

    @Test
    public void getByUuid() {
        BinArticle binArticle = binArticleDao.getByUuid("02ac2f3b4ba94c66815cb3cd00f7f676");
        Assert.assertEquals("02ac2f3b4ba94c66815cb3cd00f7f676", binArticle.getUuid());
        Assert.assertEquals("erger", binArticle.getBinCode());
        Assert.assertEquals("655f6b2bfc364aa991d52dc627743fde", binArticle.getArticle().getUuid());
        Assert.assertEquals("A002", binArticle.getArticle().getCode());
        Assert.assertEquals("Spring源码深度解析", binArticle.getArticle().getName());
        Assert.assertEquals("e9e8d111a89840cf822b8094591ab01f", binArticle.getPickSchemeUuid());
    }

    @Test
    public void queryByPage() {
        PageQueryDefinition page = new PageQueryDefinition();
        page.setPage(0);
        page.setPageSize(2);
        page.setPageCount(0);
        page.setRecordCount(1);
        List<BinArticle> list = binArticleDao.queryByPage(page);
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void queryByList() {
        List<BinArticle> list = binArticleDao.queryByList(null);
        Assert.assertEquals(2, list.size());
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
            value = "./InsertBatchPickschemeBinArticle.xml")
    public void insertBatch() {
        List<BinArticle> binArticles = new ArrayList<>();

        BinArticle binArticle1 = new BinArticle();
        binArticle1.setUuid("0eba248a9d2f4d6da6b919e712d78dfe");
        binArticle1.setBinCode("ckjAA1");
        UCN article1 = new UCN();
        article1.setUuid("ba47a36292b04ac6aaf9199e3326dc05");
        article1.setCode("A001");
        article1.setName("程序员的自我修养");
        binArticle1.setArticle(article1);
        binArticle1.setPickSchemeUuid("b68ff957f95d443d8b88ea5a2a75bdb2");

        BinArticle binArticle2 = new BinArticle();
        binArticle2.setUuid("4ee17253b1b34007a3ce5998b8f5e7f8");
        binArticle2.setBinCode("string");
        UCN article2 = new UCN();
        article2.setUuid("410891c3a5b24223a1aa8b6ade6aac65");
        article2.setCode("Z001");
        article2.setName("红豆薏仁奶茶");
        binArticle2.setArticle(article2);
        binArticle2.setPickSchemeUuid("65eb512555b14d41a7ca03b7b2ac847a");

        //hsqldb不支持批量插入
        binArticles.add(binArticle1);
        binArticles.add(binArticle2);
        binArticleDao.insertBatch(binArticles);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./DeletePickschemeBinArticle.xml")
    public void deleteByPickScheme() {
        binArticleDao.deleteByPickScheme("d9fbe3d283d64b10bb6985090b083628");
    }

    @Test
    public void getByArticleUuid() {
        List<BinArticle> list = binArticleDao.getByArticleUuid("655f6b2bfc364aa991d52dc627743fde");
        BinArticle binArticle = list.get(0);
        Assert.assertEquals("02ac2f3b4ba94c66815cb3cd00f7f676", binArticle.getUuid());
        Assert.assertEquals("erger", binArticle.getBinCode());
        Assert.assertEquals("655f6b2bfc364aa991d52dc627743fde", binArticle.getArticle().getUuid());
        Assert.assertEquals("A002", binArticle.getArticle().getCode());
        Assert.assertEquals("Spring源码深度解析", binArticle.getArticle().getName());
        Assert.assertEquals("e9e8d111a89840cf822b8094591ab01f", binArticle.getPickSchemeUuid());
    }

    @AfterClass
    public static void finishAll() {
        System.out.println("已全部完成。");
    }

}
