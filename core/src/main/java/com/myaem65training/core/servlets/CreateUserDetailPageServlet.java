package com.myaem65training.core.servlets;


import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.day.cq.wcm.api.PageManager;
import com.myaem65training.core.constants.AppConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
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
import javax.jcr.Node;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

@SuppressWarnings("ALL")
@Component(service= Servlet.class,
        property={
                Constants.SERVICE_DESCRIPTION + "=Create User Detail Page servlet",
                ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
                ServletResolverConstants.SLING_SERVLET_PATHS + "=/bin/myaem65training/createuserdetailpage",
                ServletResolverConstants.SLING_SERVLET_EXTENSIONS + "=json" })
public class CreateUserDetailPageServlet extends SlingAllMethodsServlet {
    private static final Logger log = LoggerFactory.getLogger(CreateUserDetailPageServlet.class);
    private String PAGE_TEMPLATE = AppConstants.PAGE_TEMPLATE;
    private String RENDERER = AppConstants.PAGE_COMPONENT_PATH;
    @Reference
    private ResourceResolverFactory resolverFactory;
    private ResourceResolver resourceResolver;
    private String pageName = StringUtils.EMPTY;
    private String pageTitle = StringUtils.EMPTY;
    private String path = AppConstants.LEADERSHIP_PAGE_PATH;
    private Page prodPage = null;
    private Session session;
    @Reference
    private QueryBuilder builder;
    private PageManager pageManager;
    private JSONObject NavigationJson;
    private JSONArray NavigationJsonArray;
    private HashSet<String> uniquePageList;
    @Override
    protected void doGet(@Nonnull SlingHttpServletRequest request, @Nonnull SlingHttpServletResponse response) throws ServletException, IOException {
        try{
            resourceResolver = request.getResourceResolver();
            session = resourceResolver.adaptTo(Session.class);
            if(request.getParameter(AppConstants.USER_ID).toString() != null){
                String userId = request.getParameter(AppConstants.USER_ID).toString();
                pageName = userId.toLowerCase().replace(AppConstants.DOT,AppConstants.HYPHEN);
                pageTitle = userId.toUpperCase().replace(AppConstants.DOT,AppConstants.SPACE);
                if(checkPageExists(pageName, pageName, path, resourceResolver, request) == false) {
                    createPages(userId,pageName, pageTitle, resourceResolver);
                }
            }
            session.save();
            session.refresh(true);
        } catch (Exception e) { log.error(e.getMessage(), e); }
        finally {
            if (session != null) { session.logout(); }
        }
    }

    public void createPages(String userID, String pageName, String pageTitle, ResourceResolver resourceResolver) {
        try {
            PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
            prodPage = pageManager.create(path, pageName, PAGE_TEMPLATE, pageTitle, true);
            Resource resource = resourceResolver.getResource(prodPage.getPath());
            Node pageNode = resource.adaptTo(Node.class);
            Node jcrNode = null;
            if (prodPage.hasContent()) {jcrNode = prodPage.getContentResource().adaptTo(Node.class);}
            else {jcrNode = pageNode.addNode(AppConstants.JCR_CONTENT, AppConstants.CQ_PAGE_CONTENT);}
            jcrNode.setProperty(AppConstants.SLING_RESOURCE_TYPE, RENDERER);
            jcrNode.setProperty("data-user-id", userID);
            Node root = session.getNode(prodPage.getPath().toString() + AppConstants.FORWARD_SLASH + AppConstants.JCR_CONTENT + AppConstants.FORWARD_SLASH + AppConstants.ROOT + AppConstants.FORWARD_SLASH + AppConstants.CONTAINER + AppConstants.FORWARD_SLASH + AppConstants.CONTAINER);
            Node day = root.addNode(AppConstants.BIOGRAPHY_COMPONENT, AppConstants.NT_UNSTRUCTURED);
            day.setProperty(AppConstants.SLING_RESOURCE_TYPE, AppConstants.BIOGRAPHY_COMPONENT_PATH);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public boolean checkPageExists(String pageName, String pageTitle, String path, ResourceResolver resourceResolver, SlingHttpServletRequest request){
        boolean pageExists = false;
       try {
           resourceResolver = request.getResourceResolver();
           PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
           Page rootPage = pageManager.getPage(path);
           Iterator<Page> rootPageIterator = rootPage.listChildren(new PageFilter(), true);
           while(rootPageIterator.hasNext())
           {
               Page childPage =  rootPageIterator.next();
               String childPagePath = childPage.getPath();
               if(childPage.getName().equalsIgnoreCase(pageName)){
                   pageExists = true;
                   break;
               }
           }
       }catch (Exception e){
           log.error(e.getMessage(),e);
       }
        return pageExists;
    }

}
