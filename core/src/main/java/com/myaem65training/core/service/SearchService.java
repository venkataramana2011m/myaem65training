package com.myaem65training.core.service;

import java.util.Map;

import javax.jcr.Session;

import org.apache.sling.api.SlingHttpServletRequest;

public interface SearchService {

	public Map<String, String> createPageSearchPredicateMap(String queryString, String queryPagesLocation);

	public Map<String, String> createPageSearchPredicateMap(String queryString);

	public String fetchUserDetailsPredicateMap(SlingHttpServletRequest request, Session session);

}
