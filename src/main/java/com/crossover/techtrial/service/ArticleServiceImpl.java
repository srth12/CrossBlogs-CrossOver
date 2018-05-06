package com.crossover.techtrial.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.crossover.techtrial.model.Article;
import com.crossover.techtrial.repository.ArticleRepository;
import org.springframework.web.bind.annotation.ResponseStatus;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	ArticleRepository articleRepository;

	public Article save(Article article) {
		return articleRepository.save(article);
	}

	public Article findById(Long id) {
		return articleRepository.findById(id).orElse(null);
	}

	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such article found")
	public void delete(Long id) {
		articleRepository.deleteById(id);
	}

	public List<Article> search(String search) {
		return articleRepository.findTop10ByTitleContainingIgnoreCase(search);
	}

}