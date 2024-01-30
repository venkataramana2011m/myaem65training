package com.myaem65training.core.service;

import org.apache.sling.api.SlingHttpServletRequest;

import javax.jcr.Session;
import java.util.Map;

public interface AutoCompleteSearchService {

    public Map<String, String> createAutoCompleteSearchPredicateMap(String queryString, String queryPagesLocation);
    public Map<String, String> createFetchPageListPredicateMap(String path);

}
