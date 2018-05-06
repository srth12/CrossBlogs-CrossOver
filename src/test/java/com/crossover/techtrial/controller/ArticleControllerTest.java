package com.crossover.techtrial.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.crossover.techtrial.model.Article;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ArticleControllerTest {

	@Autowired
	private TestRestTemplate template;

	private static String content="";
	private Article article;
	@Before
	public void setup() throws Exception {

		article = new Article();
		article.setId(-1l);
		article.setTitle("New title" + Math.random());
		article.setContent("New Content "+ Math.random());
		article.setDate(LocalDateTime.now());
		article.setEmail("srth12@gmail.com");
		article.setPublished(true);
		content = IntStream.range(0,Article.MAX_CONTENT_SIZE + 5).mapToObj(x -> x +" ").collect(Collectors.joining());
	}

	@Test
	public void testArticleShouldBeCreated() throws Exception {
		HttpEntity<Object> article = getHttpEntity("{\"email\": \"user1@gmail.com\", \"title\": \"hello\" }");
		ResponseEntity<Article> resultAsset = template.postForEntity("/articles", article, Article.class);
		Assert.assertNotNull(resultAsset.getBody().getId());
	}

	private HttpEntity<Object> getHttpEntity(Object body) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<Object>(body, headers);
	}

	@Test
	public void testArticleShouldNotBeCreatedLongTitle() throws Exception {
		HttpEntity<Object> article = getHttpEntity("{\"email\": \"user1@gmail.com\", \"title\": \"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\" }");
		ResponseEntity<Article> resultAsset = template.postForEntity("/articles", article, Article.class);
		Assert.assertEquals(HttpStatus.BAD_REQUEST, resultAsset.getStatusCode());
	}

	@Test
	public void testArticleShouldNotCreatedLongContent() throws Exception {
		HttpEntity<Object> article = getHttpEntity("{\"email\": \"user1@gmail.com\", \"title\": \"hello\" , \"content\": \""+content+"\"}");
		ResponseEntity<Article> resultAsset = template.postForEntity("/articles", article, Article.class);
		Assert.assertEquals(HttpStatus.BAD_REQUEST, resultAsset.getStatusCode());
	}

	@Test
	public void testGetArticleByIdWithInvalidId(){
		ResponseEntity<Article> article = template.getForEntity("/articles/-1", Article.class);
		Assert.assertEquals(HttpStatus.NOT_FOUND, article.getStatusCode());
	}

	@Test
	public void testGetArticleByIdWithValidId(){
		ResponseEntity<Article> article = template.getForEntity("/articles/1", Article.class);
		Assert.assertEquals(HttpStatus.OK, article.getStatusCode());
	}

	@Test
	public void testUpdateArticleShouldUpdateArticle(){
		Article article = new Article();
		article.setId(1l);
		article.setTitle("New title" + Math.random());
		article.setContent("New Content "+ Math.random());
		article.setDate(LocalDateTime.now());
		article.setEmail("srth12@gmail.com");
		article.setPublished(true);
		template.put("/articles/"+article.getId(), article);
		ResponseEntity<Article> returnedArticle = template.getForEntity("/articles/"+article.getId(), Article.class);
		Assert.assertEquals(article, returnedArticle.getBody());
	}

	@Test
	public void testUpdateArticleShouldNotUpdateArticleForInvalidId(){

		Article article = new Article();
		article.setId(-1l);
		article.setTitle("New title" + Math.random());
		article.setContent("New Content "+ Math.random());
		article.setDate(LocalDateTime.now());
		article.setEmail("srth12@gmail.com");
		article.setPublished(true);

		template.put("/articles/"+article.getId(), article);
		ResponseEntity<Article> returnedArticle = template.getForEntity("/articles/"+article.getId(), Article.class);
		Assert.assertEquals(HttpStatus.NOT_FOUND, returnedArticle.getStatusCode());
	}

	@Test
	public void testDeleteArticleByIdShouldNotDelete(){

		HttpEntity<Object> article = getHttpEntity("{\"email\": \"user1@gmail.com\", \"title\": \"hello\" }");
		ResponseEntity<Article> result = template.postForEntity("/articles/-2",article , Article.class);

		ResponseEntity<Article> response = template.exchange("/articles/" + result.getBody().getId(), HttpMethod.DELETE, result, Article.class);
		Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}

	@Test
	public void testDeleteArticleByIdShouldDelete(){

		HttpEntity<Object> article = getHttpEntity("{\"email\": \"user1@gmail.com\", \"title\": \"hello\" }");

		ResponseEntity<Article> result = template.postForEntity("/articles",article , Article.class);

		ResponseEntity<Article> response = template.exchange("/articles/" + result.getBody().getId(), HttpMethod.DELETE, result, Article.class);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	@Test
	public void testSearchArticles(){
		HttpEntity<Object> article = getHttpEntity("{\"email\": \"user1@gmail.com\", \"title\": \"hello\" }");

		ResponseEntity<Article> result = template.postForEntity("/articles/",article , Article.class);
		ResponseEntity<List<Article>> responseEntity = template.exchange("/articles/search?text=" + result.getBody().getTitle(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Article>>(){});
		Assert.assertNotNull(responseEntity);

	}
}
