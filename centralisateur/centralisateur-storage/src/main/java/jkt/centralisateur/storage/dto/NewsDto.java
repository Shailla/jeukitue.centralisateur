package jkt.centralisateur.storage.dto;

import java.util.Date;

public class NewsDto {
	private Long newsId;
	private Date date;
	private String text;
	
	public Long getNewsId() {
		return newsId;
	}
	
	public void setNewsId(Long newsId) {
		this.newsId = newsId;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
}
