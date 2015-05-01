package jkt.centralisateur.controller;

import java.util.Date;
import java.util.List;

import jkt.centralisateur.storage.dto.NewsDto;
import jkt.centralisateur.storage.service.NewsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NewsController {

    @Autowired
    @Qualifier("newsService")
    private NewsService newsService;

    /**
     * Affiche les news.
     */
    @RequestMapping("/secure/news.do")
    public ModelAndView listNews() throws Exception {
        final List<NewsDto> newsDto = newsService.loadNews(0, 100);

        return new ModelAndView("news", "news", newsDto);
    }

    /**
     * Affiche la page d'ajout d'une news.
     */
    @RequestMapping("/secure/admin/add_news.do")
    public ModelAndView addNews(final NewsDto newsDto) throws Exception {
        return new ModelAndView("addNews", "newsDto", newsDto);
    }
    
    /**
     * Enregistre une nouvelle news.
     */
    @RequestMapping("/secure/admin/save_news.do")
    public ModelAndView saveNews(final NewsDto newsDto) throws Exception {
        newsDto.setDate(new Date());
        newsService.createNews(newsDto);

        return new ModelAndView("redirect:/secure/news.do");
    }
}
