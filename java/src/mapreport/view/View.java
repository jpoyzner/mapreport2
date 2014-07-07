package mapreport.view;

import mapreport.view.list.NewsList;

public class View {
	NewsList newsList;
	
	public NewsList getNewsList() {
		return newsList;
	}

	public void setNewsList(NewsList newsList) {
		this.newsList = newsList;
	}

	public View(NewsList newsList2) {
		this.newsList = newsList2;
	}
	
}
