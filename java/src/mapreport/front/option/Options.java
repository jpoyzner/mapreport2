package mapreport.front.option;

import java.util.HashMap;
import java.util.Map;

public class Options {
	/*
	 * 	sortDirection time, time desc, importance, topic, location
	isHighlightImportant
	newsNumberLevel 
	isShowImages
 	rowDimension
 	colDimension
	x
	y
	zoom
	locations
	timeOption
	topics
	display columns: topic, location, date
 	viewType
	isShowFuture
	isShowInternationCountry  ??
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
	final String ZOOM_OPTION = "zoom";
	final String VIEW_TYPE_OPTION = "view";
	final String IS_SHOW_FUTURE_OPTION = "isShowFuture";
	final String MORE_TIME_OPTION = "moreTime";
	final String MORE_LOCATION_OPTION = "moreLocation";
	final String MORE_TOPIC_OPTION = "moreTopic";
	final String MORE_NEWS_OPTION = "moreNews";
	final String PAGE_NUM_OPTION = "pageNum";
	
	Map<String, String> paramMap = new HashMap<String, String>(15);	

	public void addParam(String name, String value) {
		paramMap.put(name, value);
		
		switch (name) {
			case SORT_OPTION: sortDirection = new DBWhereOption(value);	break;
			case IS_HIGHLIGHT_IMPORTANT_OPTION: isHighlightImportant = new BooleanDBWhereOption(value);	break;
			case NEWS_NUMBER_LEVEL: newsNumberLevel = new DBWhereOption(value);	break;
			case IS_SHOW_IMAGES_OPTION: isShowImages = new BooleanDBWhereOption(value);	break;
			case ROW_DIMENSION_OPTION: rowDimension = new DimensionOption(value);	break;
			case COL_DIMENSION_OPTION: colDimension = new DimensionOption(value);	break;
			case X_OPTION: xCoord = new DBWhereOption(value);	break;
			case Y_OPTION: yCoord = new DBWhereOption(value);	break;
			case X_SPAN: xSpan = new DBWhereOption(value);	break;
			case Y_SPAN: ySpan = new DBWhereOption(value);	break;
			case ZOOM_OPTION: zoom = new DBWhereOption(value);	break;
			case VIEW_TYPE_OPTION: viewType = new DBWhereOption(value);	break;
			case IS_SHOW_FUTURE_OPTION: isShowFuture = new BooleanDBWhereOption(value);	break;
			case MORE_TIME_OPTION: isMoreTime = new BooleanDBWhereOption(value);	break;
			case MORE_LOCATION_OPTION: isMoreLocations = new BooleanDBWhereOption(value);	break;
			case MORE_TOPIC_OPTION: isMoreTopics = new BooleanDBWhereOption(value);	break;
			case MORE_NEWS_OPTION: isMoreNews = new BooleanDBWhereOption(value);	break;
			case PAGE_NUM_OPTION: pageNum = new DBWhereOption(value);	break;
			default: 
				System.out.println("Options addParam UNKNOWN! buildUrlParameters name:" + name + " value:" + value);
				break;
		}
	}
	
	public DBWhereOption getxSpan() {
		return xSpan;
	}

	public void setxSpan(DBWhereOption xSpan) {
		this.xSpan = xSpan;
	}

	public DBWhereOption getySpan() {
		return ySpan;
	}

	public void setySpan(DBWhereOption ySpan) {
		this.ySpan = ySpan;
	}

	public void setLocation2Id(DBWhereFilterOption location2Id) {
		this.location2Id = location2Id;
	}

	DBWhereOption sortDirection;
	BooleanDBWhereOption isHighlightImportant;
	DBWhereOption newsNumberLevel;
	BooleanDBWhereOption isShowImages;
	
	Option rowDimension;
	Option colDimension;
	
	DBWhereOption xCoord;
	DBWhereOption yCoord;
	DBWhereOption xSpan;
	DBWhereOption ySpan;
	
	DBWhereOption zoom;
	
	DBWhereFilterOption locationId;
	DBWhereFilterOption location2Id;
	DBWhereFilterOption topicId;
	DBWhereFilterOption topic2Id;
	TimeOption time;

	BooleanDBWhereOption isDisplayLocColumn;
	BooleanDBWhereOption isDisplayTimeColumn;
	BooleanDBWhereOption isDisplayTopicColumn;

	BooleanDBWhereOption isMoreLocations;
	BooleanDBWhereOption isMoreTime;
	BooleanDBWhereOption isMoreTopics;
	BooleanDBWhereOption isMoreNews;
	
	Option viewType;
	BooleanDBWhereOption isShowFuture;
	BooleanDBWhereOption isShowInternationCountry;
	DBWhereOption pageNum;
	
	public String getParam(String name) {
		return paramMap.get(name);
	}
	
	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}
	
	
	public DBWhereOption getSortDirection() {
		return sortDirection;
	}
	public void setSortDirection(DBWhereOption sortDirection) {
		this.sortDirection = sortDirection;
	}
	public Option getIsHighlightImportant() {
		return isHighlightImportant;
	}
	public void setIsHighlightImportant(BooleanDBWhereOption isHighlightImportant) {
		this.isHighlightImportant = isHighlightImportant;
	}
	public DBWhereOption getNewsNumberLevel() {
		return newsNumberLevel;
	}
	public void setNewsNumberLevel(DBWhereOption newsNumberLevel) {
		this.newsNumberLevel = newsNumberLevel;
	}
	public BooleanDBWhereOption getIsShowImages() {
		return isShowImages;
	}
	public void setIsShowImages(BooleanDBWhereOption isShowImages) {
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
	public DBWhereOption getxCoord() {
		return xCoord;
	}
	public void setxCoord(DBWhereOption xCoord) {
		this.xCoord = xCoord;
	}
	public DBWhereOption getyCoord() {
		return yCoord;
	}
	public void setyCoord(DBWhereOption yCoord) {
		this.yCoord = yCoord;
	}
	public DBWhereOption getZoom() {
		return zoom;
	}
	public void setZoom(DBWhereOption zoom) {
		this.zoom = zoom;
	}
	public DBWhereOption getLocationId() {
		return locationId;
	}
	public void setLocationId(DBWhereFilterOption locationId) {
		this.locationId = locationId;
	}
	public DBWhereFilterOption getLocation2Id() {
		return location2Id;
	}
	public void DBWhereFilterOption(DBWhereFilterOption location2Id) {
		this.location2Id = location2Id;
	}
	public DBWhereFilterOption getTopicId() {
		return topicId;
	}
	public void setTopicId(DBWhereFilterOption topicId) {
		this.topicId = topicId;
	}
	public DBWhereFilterOption getTopic2Id() {
		return topic2Id;
	}
	public void setTopic2Id(DBWhereFilterOption topic2Id) {
		this.topic2Id = topic2Id;
	}
	public TimeOption getTime() {
		return time;
	}
	public void setTime(TimeOption time) {
		this.time = time;
	}
	public BooleanDBWhereOption getIsDisplayLocColumn() {
		return isDisplayLocColumn;
	}
	public void setIsDisplayLocColumn(BooleanDBWhereOption isDisplayLocColumn) {
		this.isDisplayLocColumn = isDisplayLocColumn;
	}
	public BooleanDBWhereOption getIsDisplayTimeColumn() {
		return isDisplayTimeColumn;
	}
	public void setIsDisplayTimeColumn(BooleanDBWhereOption isDisplayTimeColumn) {
		this.isDisplayTimeColumn = isDisplayTimeColumn;
	}
	public BooleanDBWhereOption getIsDisplayTopicColumn() {
		return isDisplayTopicColumn;
	}
	public void setIsDisplayTopicColumn(BooleanDBWhereOption isDisplayTopicColumn) {
		this.isDisplayTopicColumn = isDisplayTopicColumn;
	}
	public BooleanDBWhereOption getIsMoreLocations() {
		return isMoreLocations;
	}
	public void setIsMoreLocations(BooleanDBWhereOption isMoreLocations) {
		this.isMoreLocations = isMoreLocations;
	}
	public BooleanDBWhereOption getIsMoreTime() {
		return isMoreTime;
	}
	public void setIsMoreTime(BooleanDBWhereOption isMoreTime) {
		this.isMoreTime = isMoreTime;
	}
	public BooleanDBWhereOption getIsMoreTopics() {
		return isMoreTopics;
	}
	public void setIsMoreTopics(BooleanDBWhereOption isMoreTopics) {
		this.isMoreTopics = isMoreTopics;
	}
	public BooleanDBWhereOption getIsMoreNews() {
		return isMoreNews;
	}
	public void setIsMoreNews(BooleanDBWhereOption isMoreNews) {
		this.isMoreNews = isMoreNews;
	}
	public Option getViewType() {
		return viewType;
	}
	public void setViewType(Option viewType) {
		this.viewType = viewType;
	}
	public BooleanDBWhereOption getIsShowFuture() {
		return isShowFuture;
	}
	public void setIsShowFuture(BooleanDBWhereOption isShowFuture) {
		this.isShowFuture = isShowFuture;
	}
	public BooleanDBWhereOption getIsShowInternationCountry() {
		return isShowInternationCountry;
	}
	public void setIsShowInternationCountry(
			BooleanDBWhereOption isShowInternationCountry) {
		this.isShowInternationCountry = isShowInternationCountry;
	}
	public DBWhereOption getPageNum() {
		return pageNum;
	}
	public void setPageNum(DBWhereOption pageNum) {
		this.pageNum = pageNum;
	}


}
