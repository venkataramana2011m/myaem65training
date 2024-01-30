package com.myaem65training.core.service.impl;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.myaem65training.core.constants.AppConstants;
import com.myaem65training.core.service.AutoCompleteSearchService;
import org.apache.commons.lang3.StringUtils;
import org.apache.jackrabbit.api.JackrabbitSession;
import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.jcr.AccessDeniedException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.UnsupportedRepositoryOperationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AutoCompleteSearchServiceImpl implements AutoCompleteSearchService {
    @Override
    public Map<String, String> createAutoCompleteSearchPredicateMap(String queryString, String queryPagesLocation) {

        Map<String, String> autoCompleteSearchPredicate = new HashMap<>();

        /**
         * Configuring the Map for the predicate
         */
        autoCompleteSearchPredicate.put("path", queryPagesLocation);
        autoCompleteSearchPredicate.put("type", "cq:page");
        autoCompleteSearchPredicate.put("group.p.or", "true");
        autoCompleteSearchPredicate.put("group.1_fulltext", queryString);
        autoCompleteSearchPredicate.put("group.1_fulltext.relPath", "jcr:content");
        autoCompleteSearchPredicate.put("p.offset", "0"); // same as query.setStart(0) below
        autoCompleteSearchPredicate.put("p.limit", "20"); // same as query.setHitsPerPage(20)

        return autoCompleteSearchPredicate;
    }

    @Override
    public Map<String, String> createFetchPageListPredicateMap(String path) {
        Map<String, String> autoCompleteSearchPredicate = new HashMap<>();

        /**
         * Configuring the Map for the predicate
         */
        autoCompleteSearchPredicate.put(AppConstants.PREDICATE_PATH, path);
        autoCompleteSearchPredicate.put(AppConstants.PROPERTY_1, AppConstants.JCR_PRIMARYTYPE);
        autoCompleteSearchPredicate.put(AppConstants.PROPERTY_1_VALUE, AppConstants.CQ_PAGE);
        return autoCompleteSearchPredicate;
    }


}
