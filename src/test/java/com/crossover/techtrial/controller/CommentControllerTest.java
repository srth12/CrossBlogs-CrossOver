package com.crossover.techtrial.controller;

import com.crossover.techtrial.model.Article;
import com.crossover.techtrial.model.Comment;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentControllerTest {

    @Autowired
    private TestRestTemplate template;

    @Before
    public void setUp() throws Exception {
    }
    private HttpEntity<Object> getHttpEntity(Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<Object>(body, headers);
    }

    @Test
    public void testCreateComment() {

        HttpEntity<Object> article =  getHttpEntity("{\"email\": \"user1@gmail.com\", \"title\": \"Hello Comments\" }");
        ResponseEntity<Article> result = template.postForEntity("/articles", article , Article.class);

        Comment comment = new Comment();
        comment.setEmail("srth12@gmail.com");
        comment.setMessage("trial comment");

        ResponseEntity<Comment> resultAsset = template.postForEntity("/articles/" + result.getBody().getId()+ "/comments/", comment, Comment.class);
        Assert.assertNotNull(resultAsset);
        Assert.assertEquals(HttpStatus.CREATED, resultAsset.getStatusCode());

    }

    @Test
    public void testGetComments() {
        HttpEntity<Object> article =  getHttpEntity("{\"email\": \"user1@gmail.com\", \"title\": \"Hello Comments\" }");
        ResponseEntity<Article> result = template.postForEntity("/articles", article , Article.class);

        Comment comment = new Comment();
        comment.setEmail("srth12@gmail.com");
        comment.setMessage("trial comment");
        ResponseEntity<Comment> resultAsset = template.postForEntity("/articles/" + result.getBody().getId()+ "/comments/", comment, Comment.class);
        Assert.assertEquals(comment.getMessage(), resultAsset.getBody().getMessage());

    }
}