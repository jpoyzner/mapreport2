package mapreport.db;

import java.io.UnsupportedEncodingException;

import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; 
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import mapreport.filter.DBFilter;
import mapreport.filter.Filter;
import mapreport.filter.NameFilter;
import mapreport.filter.loc.LocationByCoords;
import mapreport.filter.time.Latest;
import mapreport.filter.time.OfficialTimeFilter;
import mapreport.filter.time.TimeFilter;
import mapreport.front.option.Options;
import mapreport.front.page.FilterNode;
import mapreport.front.page.PageMetaData;
import mapreport.front.page.PageMetaDataSerialiser;
import mapreport.front.page.PagePresentation;
import mapreport.front.page.PagePresentationSerialiser;
import mapreport.front.param.RequestParameter;
import mapreport.front.url.PageURL;
import mapreport.nav.NavigationNode;
import mapreport.nav.NavigationNodeSerializer;
import mapreport.nav.NavigationPath;
import mapreport.nav.NavigationPathSerializer;
import mapreport.news.News;
import mapreport.util.JSONHandler;
import mapreport.util.JsonError;
import mapreport.util.Log;
import mapreport.view.map.Rectangle;

public class DBQueryBuilder {	
  FilterNode filterNode = new FilterNode();   
  int limit = 2;
  
  static final String SELECT_EXTERNAL_COORD_FILTER = "select  f.priority as filterPriority, nl.dateTime, f.name as fName" + 
	 ", nf.priority as nfPriority" + 
	 ", \n nl.topCoord , nl.bottomCoord , nl.leftCoord , nl.rightCoord, nl.isOfficial, nl.newsId, nl.label, nl.priority as nPriority" + 
	 ", \n abs(nl.topCoord - nl.bottomCoord) * abs(nl.leftCoord - nl.rightCoord) / 1000000000 as span, f.isLocation \n " + 
	 ", nl.url as url, nl.video as video, nl.image as image, nl.addressText as addressText, nl.shortLabel as shortLabel, nl.description as description, nl.newsText as newsText ";

  static final String SELECT_EXTERNAL = "select  f.priority as filterPriority, n.dateTime, f.name as fName, fp.name as pName, nf.priority as nfPriority, " + 
			 " \n n.addressX , n.addressY, n.newsId, n.label, n.priority as nPriority, " + 
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
	  selectSQL.append(filter.getSelectSQL());
	  fromSQL.append(filter.getFromSQL());
	  whereSQL.append(filter.getWhereSQL());
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
		sql.append(fromSQL);
		sql.append(selectSQL);
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
	   // json = buildJson(new Rectangle(-65.0, -15.0, 3.0, 10.0), null, 20);
	    Set<NameFilter> nameFilters = new HashSet<NameFilter>(3);
	    nameFilters.add(new DBFilter("Crime"));
	  //  nameFilters.add(new DBFilter("San Jose"));
	    
	 //   OfficialTimeFilter timeFilter = parseDateStr(partPath); 
	    nameFilters.add(OfficialTimeFilter.parseDateStr("2011"));
	   //  nameFilters.add(OfficialTimeFilter.parseDateStr("2011-12-03"));
	  //  nameFilters.add(OfficialTimeFilter.parseDateStr("2011-12"));
	    json = buildJson(null, nameFilters, 100);

	    System.out.println("end buildJson json=" + json);
        	System.out.println("end main");
	}

	private static String buildJson(String url) throws MalformedURLException, UnsupportedEncodingException {
		PageURL pageURL = new PageURL(url);
		pageURL.parseUrlParameters(url);
		pageURL.parseParams();
	//	Map<String, RequestParameter> params = pageURL.getParamMap();
		Options options = pageURL.getOptions();

		Set<NameFilter> nameFilters = pageURL.getFilters();
	//	LocationByCoords coordFilter = null;	
	//	TimeFilter timeFilter = null;	

		Rectangle rect = new Rectangle(options);
		int size = Integer.parseInt(options.getParam("size"));
    	String json = buildJson(rect, nameFilters, size);
		return json;
	}

	public static String buildJson(Rectangle rect, Set<NameFilter> nameFilters, int size) {
		String json = null;
		
		try {
			LocationByCoords coordFilter;
			DBQueryBuilder queryBuilder = new DBQueryBuilder(size);

				Log.log("buildJson rect=" + rect + " nameFilters=" + nameFilters + " size=" + size);

			if (rect != null && rect.getxSpan() > 0) {
				          Log.log("queryBuilder buildJson rect.getxSpan() > 0");
				coordFilter = new LocationByCoords (rect);
				queryBuilder.addFilter(coordFilter);
			}		
			
			if (nameFilters != null) {
				for (NameFilter filter: nameFilters) {
					Log.log("before queryBuilder.addFilter(filter) filter=" + filter + " filter.getName()=" + filter.getName());
			    	queryBuilder.addFilter(filter);  
				}
			}
			
			if (queryBuilder.filterNode.getFilterList().size() == 0) {   // no filters added, so just global latest
				     Log.log("queryBuilder buildJson ading Latest");
				queryBuilder.addFilter(new Latest());  
			}
			
				Log.log("queryBuilder.filterNode.getFilterList().size()=" + queryBuilder.filterNode.getFilterList().size());
				
			queryBuilder.setWhereSQL(queryBuilder.filterNode.getWhereSQL());
			queryBuilder.setOrderBySQL(new StringBuilder(queryBuilder.filterNode.getOrderSQL())); 
			  
			List<NewsFilterRow> rows = queryBuilder.runQuery();
	
			List<NewsFilterRow> newsFilters = NewsFilterRow.buildNewsFilter(rows);
			List<News> newsList = NewsFilterRow.buildNews(rows);
			List<NewsFilterRow> filters = NewsFilterRow.buildFilters(newsFilters);		
			Map<String, NameFilter> nodes = NameFilter.buildIdFilters(filters);
				
			PagePresentation page = new PagePresentation (queryBuilder.filterNode, newsFilters, newsList, filters, nodes) ;
			   Log.log("buildJson page.getView()=" + page.getView());
			   Log.log("buildJson page.getView().getNewsList()=" + page.getView().getNewsList());
			   Log.log("buildJson page.getView().getNewsList().getNewses()=" + page.getView().getNewsList().getNewses());
			int newsListsize = page.getView().getNewsList().getNewses().size();
			Log.log("end main newsListsize=" + newsListsize);
			   Log.log ("page.getNavigationPath=" + page.getNavigationPath());     
		
			   // just for test
			  // if (true) throw new Exception("test exception"); 
			   
			json = JSONHandler.gson.toJson(page); //"data1":100,"data2":"hello","list":["String 1","String 2","String 3"]
		} catch (Exception e) {		
			   Log.log ("catch (Exception e) e.getMessage()" + e.getMessage());	
			e.printStackTrace();
			   Log.log ("printStackTrace");	
			json = new JsonError(e.getMessage(), e).getText();
			   Log.log ("buildJson ends");	
		}
		return json;
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
        try {
			con = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        

		buildSql();
		pst = prepareStmt();
		
        // select  f.priority as filterPriority, 
        // f.name as fName, n.newsId, n.label, n.priority as nPriority, nf.priority as nfPriority, 
        
		Log.log("DBQueryBuilder con=" + con);
        return pst;
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
	
	public List<NewsFilterRow> processResultSet(ResultSet res) throws SQLException{ 
		List<NewsFilterRow> rows = new ArrayList<NewsFilterRow>(100);
		while (res.next()) {
			NewsFilterRow row = new NewsFilterRow();
			//	select  f.priority as filterPriority, 
			//     f.name as fName, n.newsId, n.label, n.priority as nPriority, nf.priority as nfPriority, 
				  int filterPriority = res.getInt("filterPriority");
				  String fName = res.getString("fName");
				  String pName = res.getString("pName");
				  String newsId = res.getString("newsId");
				  String label = res.getString("label");
				  String nPriority = res.getString("nPriority");
				  int nfPriority = res.getInt("nfPriority");
				  Date date = res.getDate("dateTime");
				  
				  /* for coord filter
				  double topCoord = res.getDouble("topCoord");
				  double bottomCoord = res.getDouble("bottomCoord");
				  double leftCoord = res.getDouble("leftCoord");
				  double rightCoord = res.getDouble("rightCoord");
				  boolean isOfficial = res.getBoolean("isOfficial");
				  */
				  boolean isLocation = res.getBoolean("isLocation");
				  // 			 "n.url as url, n.video as video, n.image as image, n.addressText as addressText, n.shortLabel as shortLabel, n.description as description,
				  // n.newsText as newsText \n "
				  String url = res.getString("url");
				  String video = res.getString("video");
				  String image = res.getString("image");
				  String addressText = res.getString("addressText");
				  String shortLabel = res.getString("shortLabel");
				  String description = res.getString("description");
				  
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
				  
				  System.out.println("processResultSet label=" + label +  " filterPriority=" + filterPriority +  " date=" + date 
						  +  " fName=" + fName +  " pName=" + pName  +  " newsId=" + newsId  +  " isLocation=" + isLocation 
						  +  " nPriority=" + nPriority  );		
			rows.add(row);
		}
		
		return rows;
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
