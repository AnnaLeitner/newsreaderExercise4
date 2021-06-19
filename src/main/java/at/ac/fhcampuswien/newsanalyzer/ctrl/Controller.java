package at.ac.fhcampuswien.newsanalyzer.ctrl;

import at.ac.fhcampuswien.newsapi.NewsApi;
import at.ac.fhcampuswien.newsapi.NewsApiBuilder;
import at.ac.fhcampuswien.newsapi.beans.Article;
import at.ac.fhcampuswien.newsapi.beans.NewsResponse;
import at.ac.fhcampuswien.newsapi.enums.Endpoint;


import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Controller {

	public static final String APIKEY = "48fab5bfdd0746208746311d65815d08";  //TODO add your api key Finished

	public void process(NewsApi newsApi) {
		System.out.println("Start process");
		//TODO load the news based on the parameters
		//TODO implement Error handling

		NewsResponse newsResponse = null;
		try {
			newsResponse = newsApi.getNews();
		}catch (Exception e){
			System.err.println(e.getMessage());
		}

		if(newsResponse != null){
			List<Article> articles = newsResponse.getArticles();

			System.out.println("Anzahl der Artikel: " + articles.size());

			String prov = articles.stream()
					.collect(Collectors.groupingBy(article -> article.getSource().getName(),Collectors.counting()))
					.entrySet().stream().max(Comparator.comparingInt(entry -> Math.toIntExact(entry.getValue()))).get().getKey();
			if(prov != null){
				System.out.println("Most published Provider: " +prov);
			}

			String author = articles.stream().filter(article -> Objects.nonNull(article.getAuthor()))
					.min(Comparator.comparingInt(article -> article.getAuthor().length())).get().getAuthor();

			if(author != null){
				System.out.println("Shortest Name: " +author);
			}

			List<Article> articles2 = articles.stream()
					.sorted(Comparator.comparingInt(article -> article.getTitle().length()))
					.sorted(Comparator.comparing(Article::getTitle)) //methode getTitle
					.collect(Collectors.toList());

			if(articles2 != null){
				System.out.println(articles2.get(0));
			}

			for (Article a:articles) {
				try{
					URL url = new URL(a.getUrl());
					InputStream input = url.openStream();
					BufferedReader bf = new BufferedReader(new InputStreamReader(input));
					BufferedWriter bw = new BufferedWriter(new FileWriter(a.getTitle().substring(0,10)+".html"));
					String line;
					while((line = bf.readLine()) != null){
						bw.write(line);
					}
					bf.close();
					bw.close();
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}

			}

		}else{
			System.err.println("Couldn't find news");
		}

		//TODO implement methods for analysis


		System.out.println("End process");
	}


	public Object getData() {

		return null;
	}
}
