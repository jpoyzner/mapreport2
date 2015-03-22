package mapreport.db;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mapreport.filter.DBFilter;
import mapreport.filter.Filter;
import mapreport.filter.NameFilter;
import mapreport.front.page.FilterNode;
import mapreport.resp.ResponseBuilder;
import mapreport.util.Log;

public class DBQueryBuilder extends DBBase{	
  FilterNode filterNode = new FilterNode();   
  public FilterNode getFilterNode() {
		return filterNode;
  }
	
	public void setFilterNode(FilterNode filterNode) {
		this.filterNode = filterNode;
  }

   static final String SELECT_EXTERNAL_COORD_FILTER = "select  f.priority as filterPriority, nl.dateTime, f.name as fName" + 
	 ", nf.priority as nfPriority" +  ",	 nf.isPrimary as isPrimary, n.addressText as addressText," +
	 ", \n nl.topCoord as bottomCoord, nl.bottomCoord as topCoord, nl.leftCoord , nl.rightCoord, nl.isOfficial, nl.newsId, nl.label, nl.priority as nPriority" + 
	 ", \n abs(nl.topCoord - nl.bottomCoord) * abs(nl.leftCoord - nl.rightCoord) / 1000000000 as span, f.isLocation \n " + 
	 ", nl.url as url, nl.video as video, nl.image as image, nl.addressText as addressText, nl.shortLabel as shortLabel, nl.description as description, nl.newsText as newsText ";

  static final String SELECT_EXTERNAL = "select  f.priority as filterPriority, n.dateTime, f.name as fName, fp.name as pName, fp.isLocation as isPLocation, ff.level as pLevel, nf.priority as nfPriority, " + 
		  "	 nf.isPrimary as isPrimary, n.addressText as addressText," +
			 " \n (n.addressX / 1000000) as addressX, (n.addressY / 1000000) as addressY,  n.newsId, n.label, n.priority as nPriority, nf.priority as nfPriority, " + 
			 " \n f.isLocation, n.url as url, n.video as video, n.image as image, n.addressText as addressText," + 
			 " \n n.shortLabel as shortLabel, n.description as description, n.newsText as newsText ";

  static final String FROM_EXTERNAL_COORD_FILTER = "\n from   filter f, newsfilter nf, \n ( ";
  static final String FROM_EXTERNAL = "\n from  filter f, filter fp, newsfilter nf, filterfilter ff, news n \n ";
  static final String FROM_EXTERNAL_END_COORD_FILTER = "\n ) nl ";
  static final String FROM_EXTERNAL_END = "";
  static final String WHERE_EXTERNAL_COORD_FILTER = "\n where  f.filterId = nf.filterId  and nl.newsId = nf.newsId   and f.filterId = nf.filterId";
  static final String WHERE_EXTERNAL = "\n where  f.filterId = nf.filterId  and nf.newsId = n.newsId and f.filterId = ff.childFilterId  and fp.filterId = ff.parentFilterId ";
  
  public void addFilter(Filter filter) {
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
	
	public DBQueryBuilder(int limit) {
		this.limit = limit;

		selectSQL = new StringBuilder("");
		
		fromSQL = new StringBuilder("\n from  filter f, filter fp, newsfilter nf, filterfilter ff, news n \n ");
		whereSQL = new StringBuilder(" f.filterId = l.filterId " + 
	       " and n.newsId = nf.newsId " + 
	       "  and f.filterId = nf.filterId " + 
	       "  and nf.isPrimary = 1");
		
		orderBySQL = new StringBuilder("");
		
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

	       Log.log("buildSql selectSQL=" + selectSQL);
	       Log.log("buildSql fromSQL=" + fromSQL);
	       Log.log("buildSql whereSQL=" + whereSQL);
	       Log.log("buildSql orderBySQL=" + orderBySQL);
	       Log.info("\n\n buildSql() sql=" + sql);
		return this.sql;
	}

    public static void main (String args[]) {
    	Log.info("start main");
    
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
	    nameFilters.add(new DBFilter("Fire"));
	//    nameFilters.add(new DBFilter("San Jose"));
	    
	 //   OfficialTimeFilter timeFilter = parseDateStr(partPath); 
		//    nameFilters.add(OfficialTimeFilter.parseDateStr("2011"));
	//	    nameFilters.add(OfficialTimeFilter.parseDateStr("2010s"));
	   // nameFilters.add(OfficialTimeFilter.parseDateStr(AllTime.ALL_TIME_NAME));
	   //  nameFilters.add(OfficialTimeFilter.parseDateStr("2011-12-03"));
	//    nameFilters.add(OfficialTimeFilter.parseDateStr("2011-04"));
	    json = ResponseBuilder.buildJson(null, nameFilters, 200);
	 //   json = buildJson(new Rectangle(-65.0, -15.0, 17.0, 10.0), nameFilters, 20);
        	Log.log("end main");
	}

	
	void bindFilters() throws SQLException {
		filterNode.bindFilters(pst);
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
		  row.setNewsPriority(Integer.parseInt(nPriority));
		  
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
		  
		  Log.log("DBQueryBuilder processResultSet label=" + label +  " filterPriority=" + filterPriority +  " date=" + date 
			  +  " fName=" + fName +  " pName=" + pName +  " pLevel=" + pLevel  +  " newsId=" + newsId  +  " isLocation=" + isLocation  +  " isParentLocation=" + isParentLocation 
			  +  " nPriority=" + nPriority  +  " isPrimary=" + isPrimary  +  " addressText=" + addressText  );
		  return row;
	}
	  public List<NewsFilterRow> runQuery(int nameFiltersNo, boolean isCoordFilter) throws SQLException {
			begin(nameFiltersNo, isCoordFilter);
			Log.log("start startBindQuery");
			startBindQuery();	
			Log.log("start bindFilters");	
			bindFilters();
			Log.log("start executeQuery");
			Log.info("DBQueryBuilder runQuery() pst=\n" + pst.toString());
			ResultSet resultSet = pst.executeQuery();
			Log.log("start processResultSet");		
		    List<NewsFilterRow> rows = processResultSet(resultSet);
		    return rows;
	  }

}
