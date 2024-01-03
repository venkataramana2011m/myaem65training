package com.myaem65training.core.utils;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.myaem65training.core.service.AutoCompleteSearchService;
import com.myaem65training.core.servlets.ServiceCouponsServlet;
import org.apache.commons.lang3.StringUtils;
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
}
