package com.myaem65training.core.servlets;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.Session;
import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.myaem65training.core.service.SearchService;

/**
 * @author Anirudh Sharma
 * 
 * This servlet uses the QueryBuilder API to fetch the results from the JCR
 */
@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=Query Builder servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/demo/querybuilder" })
public class QueryBuilderServlet extends SlingSafeMethodsServlet {

	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = 2610051404257637265L;
	
	/**
	 * Logger
	 */
	private static final Logger log = LoggerFactory.getLogger(QueryBuilderServlet.class);
	
	/**
	 * Injecting the QueryBuilder dependency
	 */
	@Reference
	private QueryBuilder builder;
	
	/**
	 * Session object
	 */
	private Session session;
	
	/**
	 * Search Service object
	 */
	private SearchService searchService;
	
	/**
	 * Overridden doGet() method which executes on HTTP GET request
	 */
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {
		response.setContentType("text/html");
        PrintWriter out = null;
		
		try {

			log.info("----------< Executing Query Builder Servlet >----------");
			out = response.getWriter();
	 
	        String title = "Multiplication Table";
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

			/**
			 * This parameter is passed in the HTTP call
			 */
			String param = "%"+request.getParameter("param") +"%";

			log.info("Search term is: {}", param);
			
			/**
			 * Get resource resolver instance
			 */
			ResourceResolver resourceResolver = request.getResourceResolver();
			
			/**
			 * Adapting the resource resolver to the session object
			 */
			session = resourceResolver.adaptTo(Session.class);
			
			/**
			 * Map for the predicates
			 */
			/* Map<String, String> predicate = new HashMap<>(); */

			/**
			 * Configuring the Map for the predicate
			 */
			/*
			 * predicate.put("path", "/content/we-retail"); predicate.put("type",
			 * "cq:page"); predicate.put("group.p.or", "true");
			 * predicate.put("group.1_fulltext", param);
			 * predicate.put("group.1_fulltext.relPath", "jcr:content");
			 */
			Map<String, String> pagePredicate = new HashMap<>();

			

			/**
			 * Configuring the Map for the predicate
			 */
			pagePredicate.put("path", "/content/");
			pagePredicate.put("type", "cq:page");
			pagePredicate.put("group.p.or", "true");
			pagePredicate.put("group.1_fulltext", param);
			pagePredicate.put("group.1_fulltext.relPath", "jcr:content");
			/**
			 * Creating the Query instance
			 */
			/*Query query = builder.createQuery(PredicateGroup.create(searchService.createPageSearchPredicateMap(param)), session);*/
			Query query = builder.createQuery(PredicateGroup.create(pagePredicate), session);
			
			query.setStart(0);
			query.setHitsPerPage(20);
			
			/**
			 * Getting the search results
			 */
			SearchResult searchResult = query.getResult();
			out.print("<tr>\r\n"
					+ "    <th>Page Title</th>\r\n"
					+ "    <th>Page Path</th>\r\n"
					+ "  </tr>");
			for(Hit hit : searchResult.getHits()) {
				
				out.print("<tr>");
				out.print("<td style='word-wrap: break-word;'><a href='"+ hit.getPath() +".html" +"'  target='_blank'>"+ hit.getTitle() +"</a></td>");
                out.print("</tr>");
			}
			out.print("</table>");
		} catch (Exception e) {

			log.error(e.getMessage(), e);
		} finally {
			
			if(session != null) {
				
				session.logout();
			}
		}
		out.println("</body>");
        out.println("</html>");
	}

}