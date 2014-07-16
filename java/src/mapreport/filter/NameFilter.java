package mapreport.filter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.annotations.Expose;

import mapreport.db.NewsFilterRow;
import mapreport.front.page.FilterNode;
import mapreport.util.Log;

public class NameFilter extends Filter implements ExclusionStrategy  {
	@Expose String name = null;
	String filterTable = null;
	String filterParentTable = null;
	String filterFilterTable = null;
	Map<String, NameFilter> parents = new HashMap<String, NameFilter>();
	List<NameFilter> parentList = new ArrayList<NameFilter>();
	
	public List<NameFilter> getParentList() {
		return parentList;
	}

	public void setParentList(List<NameFilter> parentList) {
		this.parentList = parentList;
	}

	public void limitFilter(FilterNode filterNode) {		
	}
	
	public void upFilter(FilterNode filterNode) {		
	}
	
	public Map<String, NameFilter> getParents() {
		return parents;
	}

	public void setParents(Map<String, NameFilter> parents) {
		this.parents = parents;
	}

	int level;

	public NameFilter(String name) {
		// this.name = name.replace(' ', '_').replace(',', '_').replace('\'', '_').replace('&', '_');
		this.name = name;
		
		// start=01-01-2012&end=01-31-2012
		//		/43rd-street-san-francisco
		// /San-Francisco/crime/2012/
		// /San-Francisco/crime/March-2012
		
		filterTable = name;
		filterParentTable = filterTable + "_" + "Parent";
		filterFilterTable = filterTable + "_" + "Filter";
		

	}
		
	public NameFilter(String id, int level) {
		this.name = id;
		this.level = level;
	}
	
	public boolean shouldSkipField(FieldAttributes f) {
		     boolean shouldSkipField = false;
		  //   shouldSkipField = excludedThisClass.equals(f.getDeclaredClass());
		     return shouldSkipField;
	}
	  
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addParent(NewsFilterRow parent) {
			Log.log("NameFilter  addParent  name=" + name + "parent.getParentId()=" + parent.getParentId() + "  parent.getFilterId()=" + parent.getFilterId());
		parents.put(parent.getParentId(), new NameFilter(parent.getParentId(), parent.getLevel()));
		parentList.add(new NameFilter(parent.getParentId(), parent.getLevel()));
	}
	
	public static Map<String, NameFilter> buildIdFilters(List<NewsFilterRow> list) { 
           System.out.println("buildFilterNodes list.size()=" +list.size());

		Map<String, NameFilter> filters = new HashMap<String, NameFilter>(list.size());
		
		for (NewsFilterRow newsFilter : list) {
			addNewsFilter(filters, newsFilter);
		}
		
		// for debug
		Set<String> keySet = filters.keySet();		
		for (String filterName : keySet) {
			NameFilter filter = filters.get(filterName);
			System.out.println("buildFilterNodes filter.getName()=" + filter.getName() + " filter.level=" + filter.level
					// + " filter.getDisplayParent=" + filter.
					 );
		}		
		
		return filters;
	}
	

	
	static void addNewsFilter(Map<String, NameFilter> filters, NewsFilterRow newsFilter) {
		        System.out.println("addNewsFilter filter.id=" + newsFilter.getFilterId()); 
		NameFilter filter = filters.get(newsFilter.getFilterId());
		
		if (filter == null) {
			//filter = new NameFilter(newsFilter.getFilterId(), newsFilter.getLevel());
			filter = newsFilter.getFilter();
		}
		filter.addParent(newsFilter);
		filters.put(newsFilter.getFilterId(), filter);
		         System.out.println("addNewsFilter filter=" + filter + " filter.name=" + filter.getName() 
		        		 + " newsFilter.name=" + newsFilter.getName()
		        		 + " newsFilter.FilterId=" + newsFilter.getFilterId()
		        		 + " newsFilter.priority=" + newsFilter.getPriority()); // + " filter.getPriority()=" + filter.);
	}
	
	public String getDisplayParent(String id) {
		String displayParent = null;

        NameFilter parent = parents.get(id);
        
        if (parent != null) {
			if (parent.level - level == 1) {
				displayParent = id;
			}
			else {
				for (Map.Entry<String, NameFilter> entry : parents.entrySet())
				{
					if (entry.getValue().level - level == 1) {
						displayParent = id;
					}
					break;
				}
			}
		}
		return displayParent;
	}
	
	@Override
	public String getLink() {
		// link = name.replace(' ', '-');	
	    link = name;	
	        Log.log("DBFilter getLink()  name=" + name + " link=" + link);
		return link;
	}
	
	@Override
	public void bindQuery(PreparedStatement pst) throws SQLException {
		//    int col = 1;
		//	pst.setString(++col, name);
	}
	
	public void setSelectSQL(StringBuilder toAddSQL) {
	 //    ft.priority as filterTopicPriority, fpt.priority as filterTopicParentPriority, 
     //    ft.name as ftName, nft.priority as nftPriority, 
     //    fpt.name topicParent, fpt.filterId, fft.level  as topicLevel  

	//	final StringBuilder selectStringBuilder = new StringBuilder("ft.priority as filterTopicPriority, fpt.priority as filterTopicParentPriority, " +  
   //  " ft.name as ftName, nft.priority as nftPriority, " +  
   //  "  fpt.name topicParent, fpt.filterId, fft.level  as topicLevel");   
         
         
		/*

      
     ft.priority as filterTopicPriority, fpt.priority as filterTopicParentPriority, -- for topic   
                 ft.name as ftName, nft.priority as nftPriority, -- for topic   
                 fpt.name topicParent, fpt.filterId, fft.level  as topicLevel  -- for topic        
                 
     l.topCoord , l.bottomCoord , l.leftCoord , l.rightCoord, l.isOfficial,
    abs(l.topCoord - l.bottomCoord) * abs(l.leftCoord - l.rightCoord) / 1000000000 as span,  
     fpl.priority as filterLocParentPriority, fl.priority as filterLocPriority,  -- for location 
                 fl.name as flName, nfl.priority as nflPriority, -- for location
                 fpl.name locParent, fpl.filterId, ffl.level as locLevel,  -- for location
		*/
		super.setSelectSQL(toAddSQL);
	}

	@Override
	public void processResultSet(ResultSet resultSet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean shouldSkipClass(Class<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

//	from        ,     filter fl, newsfilter nfl  -- for location
   // ,     filter fpl, filter fcl, filterfilter ffl   -- for location
	
  /*
	and n.newsid = nfl.newsid  -- for location
    and fl.filterid = nfl.filterid   -- for location
    and nfl.isLocation = true   -- for location
    
    and fcl.filterid = ffl.childFilterId   -- for location
    and fpl.filterid = ffl.parentFilterId    -- for location
    and fcl.filterId = nfl.filterid     -- for location
    
            and (fl.filterid in   -- location   if no coords
                (  -- location
                       select  fc.filterId
                      from filter fp, filter fc, filterfilter ff
                      where fc.filterid = ff.childFilterId
                       and fp.filterid = ff.parentFilterId
                       and fp.label in ('Northern Mexico')   -- fp.filterId in (...)
                )   or fl.label in ('Northern Mexico')   -- fp.filterId in (...) -- location
            )
    */

}
