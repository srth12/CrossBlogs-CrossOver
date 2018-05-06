package com.crossover.techtrial.service;

import com.crossover.techtrial.model.Article;
import com.crossover.techtrial.model.Comment;
import org.junit.Assert;
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
public class CommentServiceImplTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticleService articleService;

    @Test
    public void findAll() {
        Article article = new Article();
        long l =  43;
        article.setId(l);
        article.setTitle("New title" + Math.random());
        article.setContent("New Content "+ Math.random());
        article.setDate(LocalDateTime.now());
        article.setEmail("srth12@gmail.com");
        article.setPublished(true);
        Article articleResponse = articleService.save(article);
        Comment comment = new Comment();
        comment.setEmail("srth12@gmail.com");
        comment.setMessage("trial comment");
        comment.setArticle(articleResponse);
        comment.setId(l);
        comment.setDate(LocalDateTime.now());
        Comment commentResponse = commentService.save(comment);

        List<Comment> response = commentService.findAll(l);
        Assert.assertNotNull(response);
        Assert.assertEquals(article.getId(), response.get(0).getArticle().getId());
    }

    @Test
    public void save() {
        Article article = new Article();
        article.setId(33l);
        article.setTitle("New title" + Math.random());
        article.setContent("New Content "+ Math.random());
        article.setDate(LocalDateTime.now());
        article.setEmail("srth12@gmail.com");
        article.setPublished(true);
        Comment comment = new Comment();
        comment.setEmail("srth12@gmail.com");
        comment.setMessage("trial comment");
        comment.setArticle(article);
        Comment response = commentService.save(comment);

        Assert.assertNotNull(response);
    }
}