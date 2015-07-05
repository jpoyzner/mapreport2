package mapreport.front.option;

import java.util.HashMap;
import java.util.Map;

import mapreport.util.Log;

public class Options {
	/*
	 * 	sortDirection time, time desc, importance, 
	isHighlightImportant
	newsNumberLevel 
	isShowImages
 	rowDimension
 	colDimension
	x
	y
	zoom
	locations
	display columns: topic, location, date
 	viewType
	isShowFuture
	isShowInternationalCountry  
	moreTime
	moreLocation
	moreTopic
    moreNews
	pageNum
	 */
	final String SORT_OPTION = "sort";
	final String IS_HIGHLIGHT_IMPORTANT_OPTION = "isHighlight";
	final String NEWS_NUMBER_LEVEL = "newsNumberLevel";
	final String IS_SHOW_IMAGES_OPTION = "isShowImages";
	final String ROW_DIMENSION_OPTION = "rowDimension";
	final String COL_DIMENSION_OPTION = "colDimension";
	
	final String X_OPTION = "lat";
	final String Y_OPTION = "long";
	final String X_SPAN = "latspan";
	final String Y_SPAN = "longspan";
	
	final String LEFT_OPTION = "left";
	final String RIGHT_OPTION = "right";
	final String TOP_OPTION = "top";
	final String BOTTOM_OPTION = "bottom";
	
	final String ZOOM_OPTION = "zoom";
	final String VIEW_TYPE_OPTION = "view";
	final String IS_SHOW_FUTURE_OPTION = "isShowFuture";
	final String MORE_TIME_OPTION = "moreTime";
	final String MORE_LOCATION_OPTION = "moreLocation";
	final String MORE_TOPIC_OPTION = "moreTopic";
	final String MORE_NEWS_OPTION = "moreNews";
	final String PAGE_NUM_OPTION = "pageNum";	

	NumberOption left;
	NumberOption right;
	NumberOption top;
	NumberOption bottom;
	
	NumberOption zoom;

	BooleanOption isDisplayLocColumn;
	BooleanOption isDisplayTimeColumn;
	BooleanOption isDisplayTopicColumn;

	BooleanOption isMoreLocations;
	BooleanOption isMoreTime;
	BooleanOption isMoreTopics;
	BooleanOption isMoreNews;
	
	Option viewType;
	BooleanOption isShowFuture;
	BooleanOption isShowInternationalCountry;
	NumberOption pageNum;	

	Option sortDirection;
	BooleanOption isHighlightImportant;
	NumberOption newsNumberLevel;
	BooleanOption isShowImages;
	
	Option rowDimension;
	Option colDimension;
	
	NumberOption xCoord;
	NumberOption yCoord;
	NumberOption xSpan;
	NumberOption ySpan;
	
	Map<String, String> paramMap = new HashMap<String, String>(15);	

	public void addParam(String name, String value) {
		paramMap.put(name, value);
		
		switch (name) {
			case SORT_OPTION: sortDirection = new Option(name, value);	break;
			case IS_HIGHLIGHT_IMPORTANT_OPTION: isHighlightImportant = new BooleanOption(name, value);	break;
			case NEWS_NUMBER_LEVEL: newsNumberLevel = new NumberOption(name, value);	break;
			case IS_SHOW_IMAGES_OPTION: isShowImages = new BooleanOption(name, value);	break;
			case ROW_DIMENSION_OPTION: rowDimension = new Option(name, value);	break;
			case COL_DIMENSION_OPTION: colDimension = new Option(name, value);	break;
			
			case X_OPTION: xCoord = new NumberOption(name, value);	break;
			case Y_OPTION: yCoord = new NumberOption(name, value);	break;
			case X_SPAN: xSpan = new NumberOption(name, value);	break;
			case Y_SPAN: ySpan = new NumberOption(name, value);	break;
			
			case TOP_OPTION: top = new NumberOption(name, value);	break;
			case BOTTOM_OPTION: bottom = new NumberOption(name, value);	break;			
			case LEFT_OPTION: left = new NumberOption(name, value);	break;
			case RIGHT_OPTION: right = new NumberOption(name, value);	break;		
			
			case ZOOM_OPTION: zoom = new NumberOption(name, value);	break;
			case VIEW_TYPE_OPTION: viewType = new DBWhereOption(name, value);	break;
			case IS_SHOW_FUTURE_OPTION: isShowFuture = new BooleanOption(name, value);	break;
			case MORE_TIME_OPTION: isMoreTime = new BooleanOption(name, value);	break;
			case MORE_LOCATION_OPTION: isMoreLocations = new BooleanOption(name, value);	break;
			case MORE_TOPIC_OPTION: isMoreTopics = new BooleanOption(name, value);	break;
			case MORE_NEWS_OPTION: isMoreNews = new BooleanOption(name, value);	break;
			case PAGE_NUM_OPTION: pageNum = new NumberOption(name, value);	break;
			default: 
				Log.info("Options addParam UNKNOWN! name:" + name + " value:" + value);
				break;
		}
	}
	
	public String toString() {
		String ret = "";
		for (Map.Entry entry : paramMap.entrySet()) {
		    ret += ' ' + entry.toString();
		}
		return ret;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}
	
	public NumberOption getLeft() {
		return left;
	}

	public void setLeft(NumberOption left) {
		this.left = left;
	}

	public NumberOption getRight() {
		return right;
	}

	public void setRight(NumberOption right) {
		this.right = right;
	}

	public NumberOption getTop() {
		return top;
	}

	public void setTop(NumberOption top) {
		this.top = top;
	}

	public NumberOption getBottom() {
		return bottom;
	}

	public void setBottom(NumberOption bottom) {
		this.bottom = bottom;
	}

	public NumberOption getZoom() {
		return zoom;
	}

	public void setZoom(NumberOption zoom) {
		this.zoom = zoom;
	}

	public BooleanOption getIsDisplayLocColumn() {
		return isDisplayLocColumn;
	}

	public void setIsDisplayLocColumn(BooleanOption isDisplayLocColumn) {
		this.isDisplayLocColumn = isDisplayLocColumn;
	}

	public BooleanOption getIsDisplayTimeColumn() {
		return isDisplayTimeColumn;
	}

	public void setIsDisplayTimeColumn(BooleanOption isDisplayTimeColumn) {
		this.isDisplayTimeColumn = isDisplayTimeColumn;
	}

	public BooleanOption getIsDisplayTopicColumn() {
		return isDisplayTopicColumn;
	}

	public void setIsDisplayTopicColumn(BooleanOption isDisplayTopicColumn) {
		this.isDisplayTopicColumn = isDisplayTopicColumn;
	}

	public BooleanOption getIsMoreLocations() {
		return isMoreLocations;
	}

	public void setIsMoreLocations(BooleanOption isMoreLocations) {
		this.isMoreLocations = isMoreLocations;
	}

	public BooleanOption getIsMoreTime() {
		return isMoreTime;
	}

	public void setIsMoreTime(BooleanOption isMoreTime) {
		this.isMoreTime = isMoreTime;
	}

	public BooleanOption getIsMoreTopics() {
		return isMoreTopics;
	}

	public void setIsMoreTopics(BooleanOption isMoreTopics) {
		this.isMoreTopics = isMoreTopics;
	}

	public BooleanOption getIsMoreNews() {
		return isMoreNews;
	}

	public void setIsMoreNews(BooleanOption isMoreNews) {
		this.isMoreNews = isMoreNews;
	}

	public Option getViewType() {
		return viewType;
	}

	public void setViewType(Option viewType) {
		this.viewType = viewType;
	}

	public BooleanOption getIsShowFuture() {
		return isShowFuture;
	}

	public void setIsShowFuture(BooleanOption isShowFuture) {
		this.isShowFuture = isShowFuture;
	}

	public BooleanOption getIsShowInternationalCountry() {
		return isShowInternationalCountry;
	}

	public void setIsShowInternationalCountry(
			BooleanOption isShowInternationalCountry) {
		this.isShowInternationalCountry = isShowInternationalCountry;
	}

	public NumberOption getPageNum() {
		return pageNum;
	}

	public void setPageNum(NumberOption pageNum) {
		this.pageNum = pageNum;
	}

	public Option getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(Option sortDirection) {
		this.sortDirection = sortDirection;
	}

	public BooleanOption getIsHighlightImportant() {
		return isHighlightImportant;
	}

	public void setIsHighlightImportant(BooleanOption isHighlightImportant) {
		this.isHighlightImportant = isHighlightImportant;
	}

	public NumberOption getNewsNumberLevel() {
		return newsNumberLevel;
	}

	public void setNewsNumberLevel(NumberOption newsNumberLevel) {
		this.newsNumberLevel = newsNumberLevel;
	}

	public BooleanOption getIsShowImages() {
		return isShowImages;
	}

	public void setIsShowImages(BooleanOption isShowImages) {
		this.isShowImages = isShowImages;
	}

	public Option getRowDimension() {
		return rowDimension;
	}

	public void setRowDimension(Option rowDimension) {
		this.rowDimension = rowDimension;
	}

	public Option getColDimension() {
		return colDimension;
	}

	public void setColDimension(Option colDimension) {
		this.colDimension = colDimension;
	}

	public NumberOption getxCoord() {
		return xCoord;
	}

	public void setxCoord(NumberOption xCoord) {
		this.xCoord = xCoord;
	}

	public NumberOption getyCoord() {
		return yCoord;
	}

	public void setyCoord(NumberOption yCoord) {
		this.yCoord = yCoord;
	}

	public NumberOption getxSpan() {
		return xSpan;
	}

	public void setxSpan(NumberOption xSpan) {
		this.xSpan = xSpan;
	}

	public NumberOption getySpan() {
		return ySpan;
	}

	public void setySpan(NumberOption ySpan) {
		this.ySpan = ySpan;
	}

	public Map<String, String> getParamMap() {
		return paramMap;
	}
}
