package mapreport.controller;

import java.util.HashSet;
import java.util.Set;

import mapreport.db.DBQueryBuilder;
import mapreport.filter.NameFilter;
import mapreport.filter.time.OfficialTimeFilter;

public class Endpoints {
	public static final String news() {
		Set<NameFilter> nameFilters = new HashSet<NameFilter>(3);
		nameFilters.add(OfficialTimeFilter.parseDateStr("2011"));
		return DBQueryBuilder.buildJson(null, nameFilters, 100).toString();
	}
}
