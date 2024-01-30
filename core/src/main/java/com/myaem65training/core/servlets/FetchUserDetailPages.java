package com.myaem65training.core.servlets;

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
import com.myaem65training.core.utils.AutoSearchUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.JSONArray;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ALL")
@Component(service= Servlet.class,
        property={
                Constants.SERVICE_DESCRIPTION + "=Fetch User Detail Pages servlet",
                ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
                ServletResolverConstants.SLING_SERVLET_PATHS + "=/bin/myaem65training/fetchUserDetailPages",
                ServletResolverConstants.SLING_SERVLET_EXTENSIONS + "=json" })
public class FetchUserDetailPages extends SlingAllMethodsServlet {
    private static final Logger log = LoggerFactory.getLogger(FetchUserDetailPages.class);
    private ResourceResolver resourceResolver;
    private String path = AppConstants.LEADERSHIP_PAGE_PATH;
    private Session session;
    private AutoSearchUtil autoSearchUtil;
    private String pageName = StringUtils.EMPTY;
    private String pageTitle = StringUtils.EMPTY;
    private Page prodPage = null;
    @Reference
    private QueryBuilder builder;
    private PageManager pageManager;
    public AutoCompleteSearchService autoCompleteSearchService;
    public MyAppCommonUtils myAppCommonUtils;
    @Override
    protected void doGet(@Nonnull SlingHttpServletRequest request, @Nonnull SlingHttpServletResponse response) throws ServletException, IOException {
        String userDetailPageList = StringUtils.EMPTY;
        JSONObject userDetailPageListJsonObj = new JSONObject();
        JSONArray userDetailPageListJsonArray = new JSONArray();
        try{
            resourceResolver = request.getResourceResolver();
            Session session = resourceResolver.adaptTo(Session.class);
            Map<String, String> predicate = new HashMap<>();
            predicate.put(AppConstants.PREDICATE_PATH, path);
            predicate.put(AppConstants.PROPERTY_1, AppConstants.JCR_PRIMARYTYPE);
            predicate.put(AppConstants.PROPERTY_1_VALUE, AppConstants.CQ_PAGE);
            Query query = builder.createQuery(PredicateGroup.create(predicate), session);
            query.setStart(0);
            query.setHitsPerPage(50);
            PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
            SearchResult searchResult = query.getResult();
            for (Hit hit : searchResult.getHits()) {
                if(pageManager != null) {
                    Page currentPage = pageManager.getPage(hit.getPath());
                    JSONObject pageList = new JSONObject();
                    pageList.put(AppConstants.PAGE_TITLE, currentPage.getTitle());
                    pageList.put(AppConstants.PAGE_NAME, currentPage.getName());
                    pageList.put("myprop", currentPage.getProperties().get("data-user-id"));
                    pageList.put(AppConstants.PAGE_PATH, currentPage.getPath());
                    userDetailPageListJsonArray.put(pageList);
                }
            }
            userDetailPageListJsonObj.put(AppConstants.JSON_PAGE_LIST_KEY, userDetailPageListJsonArray);
            userDetailPageList = userDetailPageListJsonObj.toString();
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        response.setContentType(AppConstants.RESPONSE_CONTENT_TYPE);
        response.setCharacterEncoding(AppConstants.RESPONSE_ENCODING_TYPE);
        response.getWriter().write(userDetailPageList);
    }
}
