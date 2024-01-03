package com.myaem65training.core.servlets;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.myaem65training.core.service.AutoCompleteSearchService;
import com.myaem65training.core.utils.AutoSearchUtil;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class,
        property = {
                "sling.servlet.methods=" + HttpConstants.METHOD_GET,
                "sling.servlet.paths=" + "/bin/servlet/autoCompleteSearch",
                "sling.servlet.extensions=" + "json"
        })
@ServiceDescription("AutoCompleteSearchServlet")
public class AutoCompleteSearchServlet extends SlingSafeMethodsServlet {


    //Optional fields
    @Reference
    protected ResourceResolverFactory resolverFactory;

    //@Reference
    //private ExampleService exampleService;
    private static final Logger log = LoggerFactory.getLogger(AutoCompleteSearchServlet.class);

    /**
     * Injecting the QueryBuilder dependency
     */
    @Reference
    private QueryBuilder builder;
    private Session session;

    private AutoSearchUtil autoSearchUtil;

    @Override
    protected void doGet(final SlingHttpServletRequest req,
                         final SlingHttpServletResponse resp) throws ServletException, IOException {
        JSONObject contentSearchJson = new JSONObject();
        autoSearchUtil= new AutoSearchUtil();
        ResourceResolver resourceResolver = req.getResourceResolver();
        session = resourceResolver.adaptTo(Session.class);
        autoSearchUtil.searchPage(req.getParameter("query"), req.getParameter("trgtSearchPath"), session);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        // Set JSON in String
        resp.getWriter().write(autoSearchUtil.searchPage(req.getParameter("query"), req.getParameter("searchLocation"), session));

    }

}