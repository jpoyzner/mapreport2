package mapreport.filter.topic;

import mapreport.util.Log;

public class SecondTopicFilter extends Topic {
//	final static String START_WHERE_SQL = 
//			"\n and  f3.filterId = nf3.filterId  and nf3.newsId = n.newsId and f3.filterId = ff3.childFilterId  and fp3.filterId = ff3.parentFilterId and f3.legacyType <> 'KeywordTimeLineFile' and (fp3.name IN ('";
	
//	final static String MID_WHERE_SQL = "' ) or  f3.name IN ( '";
//	final static String END_WHERE_SQL = "'   ))   \n";
	
//	final static String FROM_SQL = "\n, filter f3, filter fp3, newsfilter nf3, filterfilter ff3 \n";
	
	public SecondTopicFilter(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	/*
	 * select n.newsId, n.label  
 from  filter f, filter fp,
 
  filter f3, filter fp3, newsfilter nf3, filterfilter ff3, 
  
  newsfilter nf, filterfilter ff, news n 
  
 , 
 (select nf.isPrimary as isPrimary2, f.isLocation as isLocation2,  n.newsId, f.priority as filterPriority,  f.name as fName, fp.name as pName, ff.level as pLevel, nf.priority as nfPriority,	 nf.isPrimary as isPrimary, n.addressText as addressText 
 from news n, filter f, filter fp, newsfilter nf , filterfilter ff 
 where n.newsId = nf.newsId 
 and nf.filterId = f.filterId 
 and nf.filterId = ff.childfilterId 
 and fp.filterId = ff.parentfilterId 
 // and f.legacyType <> 'KeywordTimeLineFile' 
 and (fp.name = 'Sports' or f.name = 'Sports')) f2  
 where  f.filterId = nf.filterId  and nf.newsId = n.newsId and f.filterId = ff.childFilterId  and fp.filterId = ff.parentFilterId // and f.legacyType <> 'KeywordTimeLineFile' 
  and n.newsId = f2.newsId  and (fp.name IN ('Germany'  ) or  f.name IN ( 'Germany'   ))  

and  f3.filterId = nf3.filterId  and nf3.newsId = n.newsId and f3.filterId = ff3.childFilterId  and fp3.filterId = ff3.parentFilterId // and f3.legacyType <> 'KeywordTimeLineFile' 
 and (fp3.name IN ('France' ) or  f3.name IN ( 'France'   ))   
 
 -- and n.dateTime < ? 
 order by n.dateTime desc, n.priority  limit 500
	 */
/*
	@Override
	public StringBuilder getWhereSQL() {
	//	whereSQL = super.getWhereSQL();
		whereSQL = new StringBuilder("");
		         Log.info("SecondTopicFilter getWhereSQL getName()=" + getName() + " whereSQL=" + whereSQL.toString());
		whereSQL.append(START_WHERE_SQL);
		whereSQL.append(getName());
		whereSQL.append(MID_WHERE_SQL);
		whereSQL.append(getName());
		whereSQL.append(END_WHERE_SQL);
        Log.info("SecondTopicFilter getWhereSQL aft getName()=" + getName() + " whereSQL=" + whereSQL.toString());
		return whereSQL;
	}

	@Override
	public StringBuilder getFromSQL() {
	//	fromSQL = super.getFromSQL();
		fromSQL = new StringBuilder(FROM_SQL);
		return fromSQL;
	}

	@Override
	public StringBuilder getSelectSQL() {
		selectSQL = new StringBuilder("");
		return selectSQL;
	}
	*/
}
