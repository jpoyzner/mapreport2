package mapreport.view.list;

import java.util.List;

import mapreport.front.page.FilterNode;
import mapreport.nav.NavigationNode;
import mapreport.news.News;

public class Paragraph extends PageNewsList {
	public Paragraph(List<News> newses, FilterNode filters) {
		super(newses, filters);
		// TODO Auto-generated constructor stub
	}

	NavigationNode pageNode;
}
