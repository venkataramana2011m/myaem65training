package com.myaem65training.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.jcr.AccessDeniedException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.lang3.StringUtils;
import org.apache.jackrabbit.api.JackrabbitSession;
import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.day.cq.search.QueryBuilder;
import com.myaem65training.core.service.SearchService;

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=Fetch User Details Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/fetchUserDetails",
		"sling.servlet.extensions=" + "json" })
public class FetchUserDetailsServlet extends SlingSafeMethodsServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2057483906330608157L;
	@Reference
	private QueryBuilder builder;
	private Session session;
	private SearchService searchService;

	@SuppressWarnings({ "null", "unused" })
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		String user_group_Name = StringUtils.EMPTY;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.println("<html>");
			out.println("<head>");
			out.println("</head>");
			out.println("<body style='font-family: 'DM Mono', monospace;'>");
			out.println("<center>");
			out.println("<table border=1 cellpadding=0 cellspacing=0 width=100%>");
			out.print("<tr>");
			out.println("<th style='word-wrap: break-word;'>User / Group Name</th>");
			out.println("<th style='word-wrap: break-word;'>User / Group Path</th>");
			out.println("<th style='word-wrap: break-word;'>User / Group Given Name</th>");
			out.println("<th style='word-wrap: break-word;'>User / Group Family Name</th>");
			out.println("<th style='word-wrap: break-word;'>User / Group Email Id</th>");
			out.print("</tr>");
			ResourceResolver resourceResolver = request.getResourceResolver();
			Resource resource = resourceResolver.getResource("/home");
			session = (resourceResolver != null ? resourceResolver.adaptTo(Session.class) : null);
			String param = request.getParameter("param");
			UserManager userManager = ((JackrabbitSession) session).getUserManager();
			Iterator<Authorizable> userIterator = userManager.findAuthorizables("jcr:primaryType", "rep:User");
			Iterator<Authorizable> groupIterator = userManager.findAuthorizables("jcr:primaryType", "rep:Group");
			while (userIterator.hasNext()) {
				Authorizable user = userIterator.next();

				if (!user.isGroup()) {
					if (user.getID().contains(param) && !param.equals("*")) {

						String user_group_givenName = user.getProperty("./profile/givenName") != null
								? user.getProperty("./profile/givenName")[0].getString()
								: user.getPrincipal().getName().toString();
						String user_group_familyName = user.getProperty("./profile/familyName") != null
								? user.getProperty("./profile/familyName")[0].getString()
								: user.getPrincipal().getName().toString();

						String user_group_email = user.getProperty("./profile/email") != null
								? user.getProperty("./profile/email")[0].getString()
								: null;
						out.print("<tr>");
						out.println("<td style='word-wrap: break-word;'> " + user.getID() + "</td>");
						out.println("<td style='word-wrap: break-word;'>" + user.getPath() + "</td>");
						out.println("<td style='word-wrap: break-word;'>" + user_group_givenName + "</td>");
						out.println("<td style='word-wrap: break-word;'>" + user_group_familyName + "</td>");
						out.println("<td style='word-wrap: break-word;'>" + user_group_email + "</td>");
						out.print("</tr>");
					} else {
						if (param.equals("*")) {
							String user_group_givenName = user.getProperty("./profile/givenName") != null
									? user.getProperty("./profile/givenName")[0].getString()
									: user.getPrincipal().getName().toString();
							String user_group_familyName = user.getProperty("./profile/familyName") != null
									? user.getProperty("./profile/familyName")[0].getString()
									: user.getPrincipal().getName().toString();

							String user_group_email = user.getProperty("./profile/email") != null
									? user.getProperty("./profile/email")[0].getString()
									: null;
							out.print("<tr>");
							out.println("<td style='word-wrap: break-word;'> " + user.getID() + "</td>");
							out.println("<td style='word-wrap: break-word;'>" + user.getPath() + "</td>");
							out.println("<td style='word-wrap: break-word;'>" + user_group_givenName + "</td>");
							out.println("<td style='word-wrap: break-word;'>" + user_group_familyName + "</td>");
							out.println("<td style='word-wrap: break-word;'>" + user_group_email + "</td>");
							out.print("</tr>");
						}

					}
				}
			}
			while (groupIterator.hasNext()) {
				Authorizable group = groupIterator.next();
				if (group.isGroup()) {
					if (group.getID().contains(param) && !param.equals("*")) {

						String user_group_givenName = group.getProperty("./profile/givenName") != null
								? group.getProperty("./profile/givenName")[0].getString()
								: group.getPrincipal().getName().toString();
						String user_group_familyName = group.getProperty("./profile/familyName") != null
								? group.getProperty("./profile/familyName")[0].getString()
								: group.getPrincipal().getName().toString();

						String user_group_email = group.getProperty("./profile/email") != null
								? group.getProperty("./profile/email")[0].getString()
								: null;
						out.print("<tr>");
						out.println("<td style='word-wrap: break-word;'> " + group.getID() + "</td>");
						out.println("<td style='word-wrap: break-word;'>" + group.getPath() + "</td>");
						out.println("<td style='word-wrap: break-word;'>" + user_group_givenName + "</td>");
						out.println("<td style='word-wrap: break-word;'>" + user_group_familyName + "</td>");
						out.println("<td style='word-wrap: break-word;'>" + user_group_email + "</td>");
						out.print("</tr>");
					} else {
						if (param.equals("*")) {
							String user_group_givenName = group.getProperty("./profile/givenName") != null
									? group.getProperty("./profile/givenName")[0].getString()
									: group.getPrincipal().getName().toString();
							String user_group_familyName = group.getProperty("./profile/familyName") != null
									? group.getProperty("./profile/familyName")[0].getString()
									: group.getPrincipal().getName().toString();

							String user_group_email = group.getProperty("./profile/email") != null
									? group.getProperty("./profile/email")[0].getString()
									: null;
							out.print("<tr>");
							out.println("<td style='word-wrap: break-word;'> " + group.getID() + "</td>");
							out.println("<td style='word-wrap: break-word;'>" + group.getPath() + "</td>");
							out.println("<td style='word-wrap: break-word;'>" + user_group_givenName + "</td>");
							out.println("<td style='word-wrap: break-word;'>" + user_group_familyName + "</td>");
							out.println("<td style='word-wrap: break-word;'>" + user_group_email + "</td>");
							out.print("</tr>");
						}

					}
				}
			}

			out.print("</table>");
		} catch (AccessDeniedException e) {
			e.printStackTrace();
		} catch (UnsupportedRepositoryOperationException e) {
			e.printStackTrace();
		} catch (RepositoryException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.logout();
			}
		}
		out.println("</body>");
		out.println("</html>");

	}

}
