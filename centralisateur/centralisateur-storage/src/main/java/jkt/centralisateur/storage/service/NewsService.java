package jkt.centralisateur.storage.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import jkt.centralisateur.storage.buisiness.NewsBuisiness;
import jkt.centralisateur.storage.dto.NewsDto;
import jkt.centralisateur.storage.model.News;

public class NewsService {
	private NewsBuisiness newsBuisiness;
	
	/**
	 * Return all user names in alphabetical order
	 * 
	 * @param firstUser database row of the first user
	 * @param nbrUsers number of users get by the request
	 * @return list of user names in alphabetical order
	 */
	@Transactional(readOnly=true)
	public List<NewsDto> loadNews(final int firstNews, final int nbrNews) {
		List<News> news = newsBuisiness.getNews(firstNews, nbrNews);
		
		List<NewsDto> newsDtoList = new ArrayList<NewsDto>();
		for(News aNews : news) {
			NewsDto newsDto = new NewsDto();	
			newsDto.setNewsId(aNews.getId());
			newsDto.setDate(aNews.getHorodatage());
			newsDto.setText(aNews.getText());
			
			newsDtoList.add(newsDto);
		}
		
		return newsDtoList;
	}
	
	/**
	 * Create a new news
	 * 
	 * @param newsDto news description
	 */
	@Transactional(readOnly=false)
	public void createNews(final NewsDto newsDto) {
		Date date = newsDto.getDate();
		String text = newsDto.getText();
		
		newsBuisiness.createNews(date, text);	
	}

	public void setNewsBuisiness(NewsBuisiness newsBuisiness) {
		this.newsBuisiness = newsBuisiness;
	}
}
