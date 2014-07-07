package mapreport.front.page;


public class PageMetaData {

	FilterNode filters;
	int newsNm = 0;
	String header;
	String description;
	String image;
	
	public FilterNode getFilterNode() {
		return filters;
	}

	public void setFilterNode(FilterNode filters) {
		this.filters = filters;
	}

	public int getNewsNm() {
		return newsNm;
	}

	public void setNewsNm(int newsNm) {
		this.newsNm = newsNm;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	
	public PageMetaData(FilterNode filters) {
		this.filters = filters;
		
	//	for (PageFilters filters)
	}
}
