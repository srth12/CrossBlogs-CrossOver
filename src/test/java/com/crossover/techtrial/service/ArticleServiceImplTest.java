package com.crossover.techtrial.service;

import com.crossover.techtrial.model.Article;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArticleServiceImplTest {

    @Autowired
    private ArticleService articleService;

    @Before
    public void setup(){
    }
    @Test
    public void save() {

        Article article = new Article();
        article.setTitle("New title" + Math.random());
        article.setContent("New Content "+ Math.random());
        article.setDate(LocalDateTime.now());
        article.setEmail("srth12@gmail.com");
        article.setPublished(true);
        Article response = articleService.save(article);
        Assert.assertEquals(article, response);

    }

    @Test
    public void findById() {
        Article article = new Article();
        article.setTitle("New title" + Math.random());
        article.setContent("New Content "+ Math.random());
        article.setDate(LocalDateTime.now());
        article.setEmail("srth12@gmail.com");
        article.setPublished(true);
        Article articleResponse = articleService.save(article);

        Article response = articleService.findById(articleResponse.getId());
        Assert.assertEquals(articleResponse, response);
    }

    @Test
    public void delete() {
        Article article = new Article();
        article.setId(1l);
        article.setTitle("New title" + Math.random());
        article.setContent("New Content "+ Math.random());
        article.setDate(LocalDateTime.now());
        article.setEmail("srth12@gmail.com");
        article.setPublished(true);
        Article articleResponse = articleService.save(article);

        articleService.delete(articleResponse.getId());
        Article response = articleService.findById(article.getId());
        Assert.assertNull(response);
    }

    @Test
    public void search() {
        Article article = new Article();
        article.setId(1l);
        article.setTitle("New title" + Math.random());
        article.setContent("New Content "+ Math.random());
        article.setDate(LocalDateTime.now());
        article.setEmail("srth12@gmail.com");
        article.setPublished(true);
        articleService.save(article);

        List<Article> response = articleService.search(article.getTitle());
        Assert.assertNotNull(response);
    }
}