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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.day.cq.search.QueryBuilder;
import com.myaem65training.core.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=Fetch User Details Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/fetchUserDetails",
		"sling.servlet.extensions=" + "json" })
public class FetchUserDetailsServlet extends SlingSafeMethodsServlet {
	private static final long serialVersionUID = 2057483906330608157L;
	private static final Logger log = LoggerFactory.getLogger(FetchUserDetailsServlet.class);
	@Reference
	private QueryBuilder builder;
	private Session session;
	private SearchService searchService;
	private JSONObject UserDetailJson;

	private JSONArray userDetailJsonArray;

	@SuppressWarnings({ "null", "unused" })
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		UserDetailJson = new JSONObject();
		userDetailJsonArray = new JSONArray();
		try {
			ResourceResolver resourceResolver = request.getResourceResolver();
			Resource resource = resourceResolver.getResource("/home");
			session = (resourceResolver != null ? resourceResolver.adaptTo(Session.class) : null);
			String user_group_givenName= StringUtils.EMPTY;
			String user_group_familyName= StringUtils.EMPTY;
			String user_group_email= StringUtils.EMPTY;
			if(request.getParameter("userID") != null){
				String param = request.getParameter("userID");
				log.info("Requested Parameter is not null {} ", param);
				UserManager userManager = ((JackrabbitSession) session).getUserManager();
				Iterator<Authorizable> userIterator = userManager.findAuthorizables("jcr:primaryType", "rep:User");
				while (userIterator.hasNext()) {
					Authorizable user = userIterator.next();
					JSONObject userDetailList = new JSONObject();
					if (!user.isGroup()) {
						if (user.getID().equalsIgnoreCase(request.getParameter("userID").toString())) {
							user_group_givenName = user.getProperty("./profile/givenName") != null
									? user.getProperty("./profile/givenName")[0].getString()
									: user.getPrincipal().getName().toString();
							user_group_familyName = user.getProperty("./profile/familyName") != null
									? user.getProperty("./profile/familyName")[0].getString()
									: user.getPrincipal().getName().toString();

							user_group_email = user.getProperty("./profile/email") != null
									? user.getProperty("./profile/email")[0].getString()
									: null;
							userDetailList.put("userID", user.getID());
							userDetailList.put("user_path", user.getPath());
							userDetailList.put("user_givenname", user_group_givenName);
							userDetailList.put("user_group_name", user_group_familyName);
							userDetailList.put("user_email", user_group_email);
							userDetailList.put("user_profile_picture", user.getPath()+"/profile.profile.image");
							userDetailJsonArray.put(userDetailList);
							break;
						}
					}

				}
				UserDetailJson.put("userDetails", userDetailJsonArray);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(UserDetailJson.toString());
			}
		} catch (AccessDeniedException e) {
			e.printStackTrace();
		} catch (UnsupportedRepositoryOperationException e) {
			e.printStackTrace();
		} catch (RepositoryException e) {
			e.printStackTrace();
		} catch (JSONException e) {
            throw new RuntimeException(e);
        } finally {
			if (session != null) {
				session.logout();
			}
		}


	}

}
