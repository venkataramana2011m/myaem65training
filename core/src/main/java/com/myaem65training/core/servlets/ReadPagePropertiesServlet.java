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
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.*;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ALL")
@Component(service= Servlet.class,
        property={
                Constants.SERVICE_DESCRIPTION + "=Fetch Page Properties servlet",
                ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
                ServletResolverConstants.SLING_SERVLET_PATHS + "=/bin/myaem65training/pageproperties",
                ServletResolverConstants.SLING_SERVLET_EXTENSIONS + "=json" })
public class ReadPagePropertiesServlet extends SlingSafeMethodsServlet {
    private static final Logger log = LoggerFactory.getLogger(ReadPagePropertiesServlet.class);
    @Reference
    private QueryBuilder builder;
    private Session session;

    @Override
    protected void doGet( SlingHttpServletRequest request,  SlingHttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = null;
        try{
            out = response.getWriter();
            String title = "Fetch Page Properties";
            out.println("<html>");
            out.println("<head>");
            out.println("<title>" + title + "</title><style>@import url('https://fonts.googleapis.com/css2?family=DM+Mono:wght@300;400;500&display=swap');.search-body {\r\n"
                    + "    padding: 0;\r\n"
                    + "    margin: 0;\r\n"
                    + "    background-color: #17141d;\r\n"
                    + "    color: white;"
                    + "    font-family: 'DM Mono', monospace;\r\n"
                    + "}</style>");
            out.println("</head>");
            out.println("<body style='font-family: 'DM Mono', monospace;'>");
            out.println("<center>");
            out.println("<table border=1 cellpadding=0 cellspacing=0 width=100%>");
            ResourceResolver resourceResolver = request.getResourceResolver();
            session = resourceResolver.adaptTo(Session.class);
            Map<String, String> pagePredicate = new HashMap<>();
            pagePredicate.put("path", "/content/autoexpo/us/en/add-to-cart/cart");
            pagePredicate.put("type", "cq:page");
            pagePredicate.put("group.p.or", "true");
            Query query = builder.createQuery(PredicateGroup.create(pagePredicate), session);
            query.setStart(0);
            query.setHitsPerPage(20);
            SearchResult searchResult = query.getResult();
            out.print("<tr>\r\n"
                    + "    <th>Page Title</th>\r\n"
                    + "    <th>Page Path</th>\r\n"
                    + "  </tr>");

            for(Hit hit : searchResult.getHits()) {
                String str = hit.getPath().toString();
                String parts[] = str.split("/");
                for(String part: parts) {
                    System.out.println(part);
                    if(part.equalsIgnoreCase("rep:policy") && !part.equalsIgnoreCase("jcr:content")){
                        if(hit.getTitle().contains("allow")){
                            ResourceResolver resolver = request.getResourceResolver();
                            Node pageNode = resolver.getResource(hit.getPath().toString()).adaptTo(Node.class);
                            String pageTitle= pageNode.getProperty("jcr:primaryType").getString();
                            String prinicipleName= pageNode.getProperty("rep:principalName").getString();
                            out.print("<tr>");
                            out.print("<td style='word-wrap: break-word;'><a href='"+ hit.getPath() +".html" +"'  target='_blank'>"+ hit.getTitle().toString() +"</a></td><td style='word-wrap: break-word;'>"+hit.getPath()+"::-->"+ pageTitle +" :: --> " + prinicipleName +"</td>");
                            out.print("</tr>");
                        }
                    }
                }
            }
            out.print("</table>");
        }catch (Exception e){
            log.error(e.getMessage());
        }
        out.println("</body>");
        out.println("</html>");
    }
}
