package com.myaem65training.core.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.jcr.AccessDeniedException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.UnsupportedRepositoryOperationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.jackrabbit.api.JackrabbitSession;
import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import com.myaem65training.core.service.SearchService;

public class SearchServiceImpl implements SearchService {

	public Map<String, String> createPageSearchPredicateMap(String queryString, String queryPagesLocation) {
		// TODO Auto-generated method stub

		Map<String, String> pagePredicate = new HashMap<>();

		/**
		 * Configuring the Map for the predicate
		 */
		pagePredicate.put("path", queryPagesLocation);
		pagePredicate.put("type", "cq:page");
		pagePredicate.put("group.p.or", "true");
		pagePredicate.put("group.1_fulltext", queryString);
		pagePredicate.put("group.1_fulltext.relPath", "jcr:content");
		pagePredicate.put("p.offset", "0"); // same as query.setStart(0) below
		pagePredicate.put("p.limit", "20"); // same as query.setHitsPerPage(20)

		return pagePredicate;
	}

	public Map<String, String> createPageSearchPredicateMap(String queryString) {
		// TODO Auto-generated method stub

		Map<String, String> pagePredicate = new HashMap<>();

		/**
		 * Configuring the Map for the predicate
		 */
		pagePredicate.put("path", "/content/we-retail");
		pagePredicate.put("type", "cq:page");
		pagePredicate.put("group.p.or", "true");
		pagePredicate.put("group.1_fulltext", queryString);
		pagePredicate.put("group.1_fulltext.relPath", "jcr:content");

		return pagePredicate;
	}

	public String fetchUserDetailsPredicateMap(SlingHttpServletRequest request, Session session) {
		String user_group_Name = StringUtils.EMPTY;
		try {
			ResourceResolver resourceResolver = request.getResourceResolver();
			Resource resource = resourceResolver.getResource("/home");
			session = (resourceResolver != null ? resourceResolver.adaptTo(Session.class) : null);
			String param = request.getParameter("param");
			UserManager userManager = ((JackrabbitSession) session).getUserManager();
			Iterator<Authorizable> userIterator = userManager.findAuthorizables("jcr:primaryType", "rep:User");
			Iterator<Authorizable> groupIterator = userManager.findAuthorizables("jcr:primaryType", "rep:Group");
			while (userIterator.hasNext()) {
				Authorizable user = userIterator.next();
				if (!user.isGroup() && user.getID().contains(param)) {
					user_group_Name = user.getID();
				}
			}

			while (groupIterator.hasNext()) {
				Authorizable group = groupIterator.next();
				if (group.isGroup() && param.equalsIgnoreCase(group.getID())) {
					user_group_Name = group.getID();
				}
			}
			System.out.println("Test User Group Name" + user_group_Name);
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

		return user_group_Name;
	}

}
