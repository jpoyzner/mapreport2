package mapreport.view.list;

import java.util.ArrayList;
import java.util.List;

import mapreport.front.page.FilterNode;
import mapreport.news.News;
import mapreport.news.NewsPresentation;

public class NewsList {
	List<NewsPresentation> newses;
	
	public NewsList(List<News> newses, FilterNode filters) {
		this.newses = new ArrayList<NewsPresentation>(newses.size());
		for (News news : newses) {
			this.newses.add(new NewsPresentation(news, filters));
		}
	}

	public List<NewsPresentation> getNewses() {
		return newses;
	}

	public void setNewses(List<NewsPresentation> newses) {
		this.newses = newses;
	}
}
