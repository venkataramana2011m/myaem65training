package com.myaem65training.core.service.impl;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.myaem65training.core.constants.AppConstants;
import com.myaem65training.core.service.AutoCompleteSearchService;
import com.myaem65training.core.service.MyAppCommonUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.json.JSONArray;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import java.util.HashMap;
import java.util.Map;

public class MyAppCommonUtilsImpl implements MyAppCommonUtils {
    private static final Logger log = LoggerFactory.getLogger(MyAppCommonUtilsImpl.class);
    @Reference
    private QueryBuilder builder;
    public AutoCompleteSearchService autoCompleteSearchService;
   @Override
    public String fetchPageListing(String path, ResourceResolver resourceResolver, SlingHttpServletRequest request) {
       String userDetailPageList = StringUtils.EMPTY;
       JSONObject userDetailPageListJsonObj = new JSONObject();
       JSONArray userDetailPageListJsonArray = new JSONArray();
       log.info("With in the Fetch Page Listing ...... !!!! ");
       try{
           log.info("With in the Fetch Page Listing Try Method ...... !!!! ");
           resourceResolver = request.getResourceResolver();
           Session session = resourceResolver.adaptTo(Session.class);
           Query query = builder.createQuery(PredicateGroup.create(autoCompleteSearchService.createFetchPageListPredicateMap(path)), session);
           query.setStart(0);
           query.setHitsPerPage(50);
           PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
           SearchResult searchResult = query.getResult();
           for (Hit hit : searchResult.getHits()) {
               log.info("With the for each loop for the search results {}", hit.getPath());
               if(pageManager != null) {
                   log.info("Check If Page Manager object is not null .... ");
                   Page currentPage = pageManager.getPage(hit.getPath());
                   JSONObject pageList = new JSONObject();
                   log.info("Page Name  ...... {} ", currentPage.getTitle());
                   log.info("Page Title  ...... {} ", currentPage.getPath());
                   pageList.put(AppConstants.PAGE_TITLE, currentPage.getTitle());
                   pageList.put(AppConstants.PAGE_NAME, currentPage.getPath());
                   userDetailPageListJsonArray.put(pageList);
                   log.info("JSON Array  ...... {} ", userDetailPageListJsonArray);
               }
           }
           userDetailPageListJsonObj.put(AppConstants.JSON_PAGE_LIST_KEY, userDetailPageListJsonArray);
           userDetailPageList = userDetailPageListJsonObj.toString();
           log.info("JSON Object  ...... {} ", userDetailPageListJsonObj.toString());
       } catch (Exception e){
           log.error(e.getMessage(), e);
       }
       log.info("Returning String  ...... {} ", userDetailPageList);
       return userDetailPageList;
    }
}
