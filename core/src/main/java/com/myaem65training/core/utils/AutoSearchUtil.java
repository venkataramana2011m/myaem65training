package com.myaem65training.core.utils;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.myaem65training.core.constants.AppConstants;
import com.myaem65training.core.service.AutoCompleteSearchService;
import com.myaem65training.core.servlets.ServiceCouponsServlet;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.json.JSONArray;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.*;
import java.util.Map;

public class AutoSearchUtil {
    public AutoCompleteSearchService autoCompleteSearchService;
    @Reference
    private QueryBuilder builder;
    private static final Logger log = LoggerFactory.getLogger(AutoSearchUtil.class);
    public String searchPage(String queryString, String queryPagesLocation, Session session){
        String retSearchPageResults = StringUtils.EMPTY;
        Map<String, String> autoSearchPagePredicate = autoCompleteSearchService.createAutoCompleteSearchPredicateMap(queryString, queryPagesLocation);
        for (Map.Entry<String,String> entry : autoSearchPagePredicate.entrySet())
            log.debug("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        retSearchPageResults = this.executeQuery(autoSearchPagePredicate, session);
        return retSearchPageResults;
    }

    public String fetchUsersDetailPage(String queryPagesLocation, Session session){
        String retSearchPageResults = StringUtils.EMPTY;
        Map<String, String> autoSearchPagePredicate = autoCompleteSearchService.createFetchPageListPredicateMap(queryPagesLocation);
        for (Map.Entry<String,String> entry : autoSearchPagePredicate.entrySet())
            log.debug("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        retSearchPageResults = this.executeQuery(autoSearchPagePredicate, session);
        return retSearchPageResults;
    }
    public void searchUser(){}
    public void searchDam(){}
    public void searchConfig(){}
    public void searchUserWithACL(){}
    public String executeQuery(Map<String, String> autoSearchPagePredicate , Session session){
        String retSearchResult = StringUtils.EMPTY;
        JSONObject damAssetsJson = new JSONObject();
        try {
            JSONArray damAssetsJsonArray = new JSONArray();
            log.info("----------< Execute Query From AutoSearchUtil >----------");
            Query query = builder.createQuery(PredicateGroup.create(autoSearchPagePredicate), session);
            query.setStart(0);
            query.setHitsPerPage(500);

            SearchResult searchResult = query.getResult();
            for (Hit hit : searchResult.getHits()) {
                JSONObject damAsset = new JSONObject();
                damAsset.put("path", hit.getPath().toString());
                damAsset.put("title", hit.getTitle().toString());
                damAssetsJsonArray.put(damAsset);
            }
            damAssetsJson.put("DamAssetList", damAssetsJsonArray);
            retSearchResult = damAssetsJson.toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (session != null) {
                session.logout();
            }
        }
        return retSearchResult;
    }
    public static URL buildUrl(String endpoint, String path, Map<String, String> parameters)
            throws MalformedURLException {
        return buildUrl("", path, parameters);
    }

   public JSONObject fectUserDetPages(String path, ResourceResolver resourceResolver, SlingHttpServletRequest request){
       JSONObject userDetailPageListJsonObj = new JSONObject();
       JSONArray userDetailPageListJsonArray = new JSONArray();
       log.info("With in the Fetch Page Listing ...... !!!! ");
       try{
           //pageList = autoSearchUtil.fetchUsersDetailPage(request.getParameter("trgtSearchPath"), session);
           log.info("With in the Fetch Page Listing Try Method ...... !!!! ");
           resourceResolver = request.getResourceResolver();
           if(null != resourceResolver) {
               log.info("With in the Fetch Page Listing  Resource Resolver is not Null ...... !!!! ");
               Session session = resourceResolver.adaptTo(Session.class);
               Map<String, String> predicate = new HashMap<>();
               predicate.put(AppConstants.PREDICATE_PATH, path);
               predicate.put(AppConstants.PROPERTY_1, AppConstants.JCR_PRIMARYTYPE);
               predicate.put(AppConstants.PROPERTY_1_VALUE, AppConstants.CQ_PAGE);
               for (Map.Entry<String,String> entry : predicate.entrySet()){
                   log.debug("Key = " + entry.getKey() + ", Value = " + entry.getValue());
               }
               if(session != null){
                   log.info("With in the Fetch Page Listing Session is not Null ...... !!!! ");
                   Query query = builder.createQuery(PredicateGroup.create(predicate), session);
                   query.setStart(0);
                   query.setHitsPerPage(50);
                   PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
                   SearchResult searchResult = query.getResult();
                   log.info("With in the Fetch Page Listing Query Builder {}",builder.createQuery(PredicateGroup.create(predicate), session).toString());
                   for (final Hit hit : searchResult.getHits()) {
                       log.info("With the for each loop for the search results {}", hit.getPath());
                       if (pageManager != null) {
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
                   log.info("JSON Object  ...... {} ", userDetailPageListJsonObj.toString());
               } else{
                   log.info("With in the Fetch Page Listing Session is Null ...... !!!! ");
               }
           } else{
               log.info("With in the Fetch Page Listing Resource Resolver is Null ...... !!!! ");
           }
       }catch (Exception e){
           log.error(e.getMessage(),e);
       }
        return userDetailPageListJsonObj;
   }
}
