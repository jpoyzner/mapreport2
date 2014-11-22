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
		for (int cntr = 0; cntr < newses.size(); cntr++) {
			News news = newses.get(cntr);
			this.newses.add(new NewsPresentation(news, filters, Integer.toString(cntr + 1)));
		}
	}

	public List<NewsPresentation> getNewses() {
		return newses;
	}

	public void setNewses(List<NewsPresentation> newses) {
		this.newses = newses;
	}
}
