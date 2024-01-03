package com.myaem65training.core.servlets;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
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
                Constants.SERVICE_DESCRIPTION + "=Fetch DAM Asset List servlet",
                ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
                ServletResolverConstants.SLING_SERVLET_PATHS + "=/bin/myaem65training/damassetlist",
                ServletResolverConstants.SLING_SERVLET_EXTENSIONS + "=json" })
public class DamCarouselDynamicServlet extends SlingAllMethodsServlet {
    private static final Logger log = LoggerFactory.getLogger(DamCarouselDynamicServlet.class);
    @Reference
    private QueryBuilder builder;
    private Session session;

    @Override
    protected void doGet( SlingHttpServletRequest request,  SlingHttpServletResponse response) throws ServletException, IOException {
        String trgtGalleryPath = request.getParameter("trgtGalleryPath");
        JSONObject damAssetsJson = new JSONObject();
        try {
            JSONArray damAssetsJsonArray = new JSONArray();
            log.info("----------< Executing Query Builder Servlet >----------");
            ResourceResolver resourceResolver = request.getResourceResolver();
            session = resourceResolver.adaptTo(Session.class);
            Map<String, String> predicate = new HashMap<>();
            predicate.put("path", trgtGalleryPath);
            predicate.put("1_property", "jcr:primaryType");
            predicate.put("1_property.value", "dam:Asset");
            Query query = builder.createQuery(PredicateGroup.create(predicate), session);
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
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            // Set JSON in String
            response.getWriter().write(damAssetsJson.toString());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (session != null) {
                session.logout();
            }
        }
    }
}
