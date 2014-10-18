package mapreport.db;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mapreport.filter.DBFilter;
import mapreport.filter.Filter;
import mapreport.filter.NameFilter;
import mapreport.filter.loc.LocationByCoords;
import mapreport.filter.loc.LocationByName;
import mapreport.filter.time.Latest;
import mapreport.filter.time.TimeFilter;
import mapreport.filter.topic.Topic;
import mapreport.front.option.Options;
import mapreport.front.page.FilterNode;
import mapreport.front.page.PagePresentation;
import mapreport.front.url.PageURL;
import mapreport.news.News;
import mapreport.util.JSONHandler;
import mapreport.util.JsonError;
import mapreport.util.Log;
import mapreport.view.map.Rectangle;

public class DBQueryBuilder {	
  FilterNode filterNode = new FilterNode();   
  int limit = 2;
  
  static final String SELECT_EXTERNAL_COORD_FILTER = "select  f.priority as filterPriority, nl.dateTime, f.name as fName" + 
	 ", nf.priority as nfPriority" +  ",	 nf.isPrimary as isPrimary, n.addressText as addressText," +
	 ", \n nl.topCoord as bottomCoord, nl.bottomCoord as topCoord, nl.leftCoord , nl.rightCoord, nl.isOfficial, nl.newsId, nl.label, nl.priority as nPriority" + 
	 ", \n abs(nl.topCoord - nl.bottomCoord) * abs(nl.leftCoord - nl.rightCoord) / 1000000000 as span, f.isLocation \n " + 
	 ", nl.url as url, nl.video as video, nl.image as image, nl.addressText as addressText, nl.shortLabel as shortLabel, nl.description as description, nl.newsText as newsText ";

  static final String SELECT_EXTERNAL = "select  f.priority as filterPriority, n.dateTime, f.name as fName, fp.name as pName, fp.isLocation as isPLocation, ff.level as pLevel, nf.priority as nfPriority, " + 
		  "	 nf.isPrimary as isPrimary, n.addressText as addressText," +
			 " \n (n.addressX / 1000000) as addressX, (n.addressY / 1000000) as addressY,  n.newsId, n.label, n.priority as nPriority, " + 
			 " \n f.isLocation, n.url as url, n.video as video, n.image as image, n.addressText as addressText," + 
			 " \n n.shortLabel as shortLabel, n.description as description, n.newsText as newsText ";

  static final String FROM_EXTERNAL_COORD_FILTER = "\n from   filter f, newsfilter nf, \n ( ";
  static final String FROM_EXTERNAL = "\n from  filter f, filter fp, newsfilter nf, filterfilter ff, news n \n ";
  static final String FROM_EXTERNAL_END_COORD_FILTER = "\n ) nl ";
  static final String FROM_EXTERNAL_END = "";
  static final String WHERE_EXTERNAL_COORD_FILTER = "\n where  f.filterid = nf.filterid  and nl.newsid = nf.newsid   and f.filterid = nf.filterid";
  static final String WHERE_EXTERNAL = "\n where  f.filterid = nf.filterid  and nf.newsid = n.newsid and f.filterid = ff.childFilterId  and fp.filterid = ff.parentFilterId ";
  
  void addFilter(Filter filter) {
	  	Log.log("DBQueryBuilder addFilter filter=" + filter);
	  filterNode.add(filter);
	  // just for logging
	     if (filter instanceof DBFilter) {
	    	 DBFilter dBFilter = (DBFilter) filter;
	    	    Log.log("DBQueryBuilder addFilter dBFilter=" + dBFilter + " getName=" + dBFilter.getName()+ " getDbFilterCntr=" + dBFilter.getDbFilterCntr());
	     }
	  selectSQL.append(filter.getSelectSQL());
	  fromSQL.append(filter.getFromSQL());
	                Log.log("DBQueryBuilder addFilter fromSQL=" + fromSQL);
	  whereSQL.append(filter.getWhereSQL());
                    Log.log("DBQueryBuilder addFilter filter.getWhereSQL()=" + filter.getWhereSQL());
	  orderBySQL.append(filter.getOrderBySQL());
  }
	
  private static PreparedStatement pst = null;
  private static ResultSet resultSet = null;  
	  
  static String url = "jdbc:mysql://localhost:3306/new_schema2"; //testdb";
  static String user = "root";
  static String password = "hadera";
  static Connection con;
  
  static {
      try {      	
      	 System.out.println("DBQueryBuilder starts");      	 
		 Class.forName("com.mysql.jdbc.Driver");
		 
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		} 
  }
	
	private String sql = null;

	private StringBuilder selectSQL = new StringBuilder("");
	
 //   from filter f, location l, news n, newsfilter nf
	// "\n from  filter f, filter fp, newsfilter nf, filterfilter ff, news n \n ";
	private StringBuilder fromSQL = new StringBuilder("\n from  filter f, filter fp, newsfilter nf, filterfilter ff, news n \n ");
	private StringBuilder whereSQL = new StringBuilder(" f.filterid = l.filterid " + 
       " and n.newsid = nf.newsid " + 
       "  and f.filterid = nf.filterid " + 
       "  and nf.isPrimary = 1");
	
	private StringBuilder orderBySQL = new StringBuilder("");
	
	public DBQueryBuilder(int limit) {
		this.limit = limit;
	}
	
	public String buildSql() {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_EXTERNAL);
		sql.append(selectSQL);
		sql.append(fromSQL);
		sql.append("\r\n");
		
		/*   for coord filter
		sql.append("from ");
		sql.append(fromSQL);
		sql.append("\r\n\r\n");
		sql.append("where ");
		sql.append(whereSQL);
		sql.append("\n");
		sql.append(" limit ");
		sql.append(limit);
		sql.append(" \r\n\r\n");
		sql.append(FROM_EXTERNAL_END);
		sql.append("\r\n\r\n");
		*/
		
		sql.append(WHERE_EXTERNAL);
		sql.append("\n");
		sql.append(whereSQL);
		sql.append("\r\n\r\n");
		sql.append(orderBySQL);
		sql.append(" limit ");
		sql.append(limit);
		
		this.sql = sql.toString();

	       System.out.println("buildSql selectSQL=" + selectSQL);
	       System.out.println("buildSql fromSQL=" + fromSQL);
	       System.out.println("buildSql whereSQL=" + whereSQL);
	       System.out.println("buildSql orderBySQL=" + orderBySQL);
	       System.out.println("\n\n buildSql sql=" + sql);
		return this.sql;
	}

    public static void main (String args[]) {
    	System.out.println("start main");
    
    	@SuppressWarnings("unused")
		String json = null;
    /*  
     * Json by URL
     * 
     * 
    	try {
    		json = buildJson("http://mapreport.com/news?long=-15.000000&lat=-65.000000&longspan=3.0000000&latspan=10.0000000&size=20");
			// buildJson("http://mapreport.com/Angola/news?size=20");
			// buildJson("http://mapreport.com/2011/news?size=20");
    					// not working yet buildJson("http://mapreport.com/May-2011/news?size=20");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}
		*/     		
	    	// Json by URL by Java objects
	    Set<NameFilter> nameFilters = new HashSet<NameFilter>(3);  
	 //   nameFilters.add(new DBFilter("Fire"));
	    nameFilters.add(new DBFilter("San Jose"));
	    
	 //   OfficialTimeFilter timeFilter = parseDateStr(partPath); 
		 //   nameFilters.add(OfficialTimeFilter.parseDateStr("2011"));
	//	    nameFilters.add(OfficialTimeFilter.parseDateStr("2010s"));
	   // nameFilters.add(OfficialTimeFilter.parseDateStr(AllTime.ALL_TIME_NAME));
	   //  nameFilters.add(OfficialTimeFilter.parseDateStr("2011-12-03"));
	  //  nameFilters.add(OfficialTimeFilter.parseDateStr("2011-12"));
	    json = buildJson(null, nameFilters, 100);
	 //   json = buildJson(new Rectangle(-65.0, -15.0, 17.0, 10.0), nameFilters, 20);
        	Log.log("end main");
	}

	public static String buildJson(String url) throws MalformedURLException, UnsupportedEncodingException {
		         Log.log("buildJson url=" + url);
		PageURL pageURL = new PageURL(url);
		pageURL.parseUrlParameters(url);
		pageURL.parseParams();
		Options options = pageURL.getOptions();

		Set<NameFilter> nameFilters = pageURL.getFilters();

		Rectangle rect = Rectangle.getRectangle(options);
		int size = 100;
		
		if (options.getParam("size") != null) {
			size = Integer.parseInt(options.getParam("size"));
		}
    	String json = buildJson(rect, nameFilters, size);
		return json;
	}

	public static String buildJson(Rectangle rect, Set<NameFilter> nameFilters, int size) {  
		String json = null;
		
		try {
			// LocationByCoords coordFilter;
			DBQueryBuilder queryBuilder = new DBQueryBuilder(size);

				Log.log("buildJson rect=" + rect + " nameFilters=" + nameFilters + " size=" + size);

			List<NewsFilterRow> parents = buildParents(rect, nameFilters,	queryBuilder);
			
				Log.log("queryBuilder.filterNode.getFilterList().size()=" + queryBuilder.filterNode.getFilterList().size());
				
			queryBuilder.setWhereSQL(queryBuilder.filterNode.getWhereSQL());
			queryBuilder.setOrderBySQL(new StringBuilder(queryBuilder.filterNode.getOrderSQL())); 
			  
			List<NewsFilterRow> rows = queryBuilder.runQuery();
	
			List<NewsFilterRow> newsFilters = NewsFilterRow.buildNewsFilterPriority(rows);
			List<News> newsList = NewsFilterRow.buildNews(rows);
			List<NewsFilterRow> filters = NewsFilterRow.buildFilters(newsFilters);
			
			Map<String, NameFilter> childFilters = NameFilter.buildChildFilters(filters, parents);
				
			PagePresentation page = new PagePresentation (queryBuilder.filterNode, newsFilters, newsList, filters, childFilters, parents) ;
			   Log.log("buildJson page.getView()=" + page.getView());
			   Log.log("buildJson page.getView().getNewsList()=" + page.getView().getNewsList());
			   Log.log("buildJson page.getView().getNewsList().getNewses()=" + page.getView().getNewsList().getNewses());
			int newsListsize = page.getView().getNewsList().getNewses().size();
			   Log.log("end main newsListsize=" + newsListsize);
			   Log.log ("page.getNavigationPath=" + page.getNavigationPath());     
		
			   // just for test
			  // if (true) throw new Exception("test exception"); 
			   
			json = JSONHandler.gson.toJson(page); //"data1":100,"data2":"hello","list":["String 1","String 2","String 3"]
		    Log.log("buildJson end json=" + json);
		} catch (Exception e) {		
			   Log.log ("catch (Exception e) e.getMessage()" + e.getMessage());	
			e.printStackTrace();
			   Log.log ("printStackTrace");	
			json = new JsonError(e.getMessage(), e).getText();
			   Log.log ("buildJson ends");	
		}
		return json;
	}

	public static List<NewsFilterRow> buildParents(Rectangle rect,
			Set<NameFilter> nameFilters, DBQueryBuilder queryBuilder)
			throws SQLException {
		LocationByCoords coordFilter;
		if (rect != null && rect.getxSpan() > 0) {
			          Log.log("queryBuilder buildJson rect.getxSpan() > 0");
			coordFilter = new LocationByCoords (rect);
			queryBuilder.addFilter(coordFilter);
		}		
		
		List<NewsFilterRow> parents = new ArrayList<NewsFilterRow>();
		
		if (nameFilters != null) {
			List<String> filterIds = new ArrayList<String>(nameFilters.size());
			for (NameFilter filter: nameFilters) {
				filterIds.add(filter.getName());
			}
				
			FilterDBQueryBuilder filterDBQueryBuilder = new FilterDBQueryBuilder();
			
			if (filterIds.size() > 0) {
				parents = filterDBQueryBuilder.runQuery(filterIds);
			}
			
			for (NameFilter filter: nameFilters) {
				Log.log("\n before queryBuilder.addFilter(filter) filter=" + filter + " filter.getName()=" + filter.getName());
				
				if (filter instanceof TimeFilter) {
					queryBuilder.addFilter(filter);  
				} else {
					boolean isLocation = true;
					for (NewsFilterRow parentNewsFilterRow : parents) {
						       Log.log("before queryBuilder.addFilter(filter) parentNewsFilterRow.getFilterName=" + parentNewsFilterRow.getFilterName() + " filter.getName()=" + filter.getName());
						if (parentNewsFilterRow.filterName.equals(filter.getName())) {
							isLocation = parentNewsFilterRow.isFilterLocation;
						       Log.log("before queryBuilder.addFilter(filter) parentNewsFilterRow.filterId.equals(filter.getName()) isLocation=" + isLocation);
							break;
						}
					}
					
					NameFilter newFilter = isLocation ? new LocationByName(filter.getName()) :  new Topic(filter.getName());
			    	queryBuilder.addFilter(newFilter);   //  adding named filters
				}
			}
		}
		
		if (queryBuilder.filterNode.getFilterList().size() == 0 || queryBuilder.filterNode.getTimeFilter() == null) {   // no filters added, so just global latest
			     Log.log("queryBuilder buildJson ading Latest");
			queryBuilder.addFilter(new Latest());  
		}
		return parents;
	}
	
	public List<NewsFilterRow> runQuery() throws SQLException {
		begin();
		Log.log("start startBindQuery");
		startBindQuery();	
		Log.log("start bindFilters");	
		bindFilters();
		Log.log("start executeQuery");
		ResultSet resultSet = pst.executeQuery();
		Log.log("start processResultSet");		
	    List<NewsFilterRow> rows = processResultSet(resultSet);
	    return rows;
	}
	
	void startBindQuery() throws SQLException {

	}
	
	void bindFilters() throws SQLException {
		filterNode.bindFilters(pst);
	}
		
	public PreparedStatement begin(){
        buildConnection();        

		buildSql();
		pst = prepareStmt();
		
        // select  f.priority as filterPriority, 
        // f.name as fName, n.newsId, n.label, n.priority as nPriority, nf.priority as nfPriority, 
        
		Log.log("DBQueryBuilder con=" + con);
        return pst;
	}

	public static void buildConnection() {
		try {
			con = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	
	public PreparedStatement prepareStmt() {
		try {
			pst = con.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pst;
	}
	
	public static boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
	    ResultSetMetaData rsmd = rs.getMetaData();
	    int columns = rsmd.getColumnCount();
	    for (int x = 1; x <= columns; x++) {
	        if (columnName.equals(rsmd.getColumnName(x))) {
	            return true;
	        }
	    }
	    return false;
	}
	
	public List<NewsFilterRow> processResultSet(ResultSet res) throws SQLException{ 
		List<NewsFilterRow> rows = new ArrayList<NewsFilterRow>(100);
		while (res.next()) {
			//	select  f.priority as filterPriority, 
			//     f.name as fName, n.newsId, n.label, n.priority as nPriority, nf.priority as nfPriority, 
			  String fName = res.getString("fName");
			  int filterPriority = res.getInt("filterPriority");
			  String pName = res.getString("pName");
			  int pLevel = res.getInt("pLevel");
			  int nfPriority = res.getInt("nfPriority");
			  NewsFilterRow row = createNewsFilterRow(res, filterPriority, fName, pName, pLevel, nfPriority);	
			  rows.add(row);	

			  //  , f2.filterPriority as filterPriority2, f2.fName as fName2, f2.pName as pName2, f2.nfPriority as nfPriority2 		  
			  if (hasColumn(res, "fName2")) {	  			  
				  String fName2 = res.getString("fName2");
				  int filterPriority2 = res.getInt("filterPriority2");
				  String pName2 = res.getString("pName2");
				  int pLevel2 = res.getInt("pLevel2");
				  int nfPriority2 = res.getInt("nfPriority2");
				  NewsFilterRow row2 = createNewsFilterRow(res, filterPriority2, fName2, pName2, pLevel2, nfPriority2);	
				  rows.add(row2);	
			  }
		}
		
		return rows;
	}

	private NewsFilterRow createNewsFilterRow(ResultSet res,
			int filterPriority, String fName, String pName, int pLevel, int nfPriority)
			throws SQLException {
		  NewsFilterRow row = new NewsFilterRow();
		  
		  String newsId = res.getString("newsId");
		  String label = res.getString("label");
		  String nPriority = res.getString("nPriority");
		  Date date = res.getDate("dateTime");

		  boolean isLocation = res.getBoolean("isLocation");
		  boolean isParentLocation = res.getBoolean("isPLocation");
		  // 			 "n.url as url, n.video as video, n.image as image, n.addressText as addressText, n.shortLabel as shortLabel, n.description as description,
		  // n.newsText as newsText \n "
		  String url = res.getString("url");
		  String video = res.getString("video");
		  String image = res.getString("image");
		//  String addressText = res.getString("addressText");
		  String shortLabel = res.getString("shortLabel");
		  String description = res.getString("description");
		  
		  double x = res.getDouble("addressX");
		  double y = res.getDouble("addressY");
		  
		  //  isPrimary, n.addressText as addressText
		  boolean isPrimary = res.getBoolean("isPrimary");
		  String addressText = res.getString("addressText");

		  row.isPrimary = isPrimary;
		  row.setAddressText(addressText);
		  
		  row.setX(x);
		  row.setY(y);
		  row.setDate(date);
		  row.setFilterPriority(filterPriority);
		  row.setName(label);
		  row.setNewsFilterPriority(nfPriority);
		  row.setNewsId(Integer.parseInt(newsId));
		  row.setFilterId(fName);
		  row.setPriority(Integer.parseInt(nPriority));
		  
		  //for coord filter
		 // row.setOfficial(isOfficial);			  
				  
		  row.setLocation(isLocation);
		  row.setUrl(url);
		  row.setVideo(video);
		  row.setImage(image);
		  row.setAddressText(addressText);
		  row.setShortLabel(shortLabel);
		  row.setDescription(description);
		  row.setParentId(pName);
		  row.setParentLevel(pLevel);
		  row.setParentLocation(isParentLocation);
		  
		  System.out.println("processResultSet label=" + label +  " filterPriority=" + filterPriority +  " date=" + date 
			  +  " fName=" + fName +  " pName=" + pName +  " pLevel=" + pLevel  +  " newsId=" + newsId  +  " isLocation=" + isLocation  +  " isParentLocation=" + isParentLocation 
			  +  " nPriority=" + nPriority  +  " isPrimary=" + isPrimary  +  " addressText=" + addressText  );
		  return row;
	}
	
	public void end(){
    	try{
            pst.executeUpdate();
            
    	    } catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} finally {
    	
    	        try {
    	            if (pst != null) {
    	                pst.close();
    	            }
    	            if (con != null) {
    	                con.close();
    	            }
    	
    	        } catch (SQLException ex) {
    	          //  Logger lgr = Logger.getLogger(Test.class.getName());
    	           // lgr.log(Level.SEVERE, ex.getMessage(), ex);
    	            System.out.println(ex.getMessage());
    	            ex.printStackTrace(System.out);
    	        }
    	    }	}
	
	public static StringBuilder addComaSQL(StringBuilder sql, String toAddSQL) {
		if (sql.length() > 0) {
			sql.append(", ");			
		}
		sql.append(toAddSQL);
		return sql;
	}
	
	public StringBuilder addWhereSQL(String toAddSQL) {
		if (whereSQL.length() > 0) {
			whereSQL.append(" and ");			
		}
		whereSQL.append(toAddSQL);
		return whereSQL;
	}
	
	public ResultSet executeQuery() {
		try {
			resultSet = pst.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultSet;
	}
	
	public StringBuilder addOrderSQL(String toAddSQL) {
		return addComaSQL(orderBySQL, toAddSQL);
	}
	
	public StringBuilder addFromSQL(String toAddSQL) {
		return addComaSQL(fromSQL, toAddSQL);
	}
	
	public StringBuilder addSelectSQL(String toAddSQL) {
		return addComaSQL(selectSQL, toAddSQL);
	}
	
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
	
	public StringBuilder getSelectSQL() {
		return selectSQL;
	}
	public void setSelectSQL(StringBuilder selectSQL) {
		this.selectSQL = selectSQL;
	}

	public StringBuilder getFromSQL() {
		return fromSQL;
	}
	public void setFromSQL(StringBuilder fromSQL) {
		this.fromSQL = fromSQL;
	}
	public StringBuilder getWhereSQL() {
		return whereSQL;
	}
	public void setWhereSQL(StringBuilder whereSQL) {
		this.whereSQL = whereSQL;
	}
	public StringBuilder getOrderBySQL() {
		return orderBySQL;
	}
	public void setOrderBySQL(StringBuilder orderBySQL) {
		this.orderBySQL = orderBySQL;
	}

}
