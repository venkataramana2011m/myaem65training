package com.myaem65training.core.cqcomponents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.wcm.commons.WCMUtils;
import com.myaem65training.core.bean.FetchDamListingBean;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;

import com.day.cq.dam.api.Asset;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;

public class FetchDamListingComponent extends WCMUsePojo {

	private static final Logger log = LoggerFactory.getLogger(FetchDamListingComponent.class);

	public static final String RESOURCE_PATH = "/content/dam";

	private Session session;

	private ResourceResolver resourceResolver;

	//private List<FetchDamListingBean> damAssets;
	private List<String> damAssets;
	
	//private Map<String, Map<String, String>> myMap;

	private Resource resource;

	@Override
	public void activate() throws Exception {
		// TODO Auto-generated method stub
		try {

			resourceResolver = getResourceResolver();
			damAssets = new ArrayList<>();
			if (resourceResolver != null) {
				System.out.println("Resource resolver is not null ....");
				resource = resourceResolver.getResource(RESOURCE_PATH);
				if (resource != null) {
					System.out.println("Resource is not null ....");
					// FetchDamListingBean fdlb = new FetchDamListingBean(); damAssets.add(fdlb);
					QueryBuilder builder = getResourceResolver().adaptTo(QueryBuilder.class);
					Session session = resourceResolver.adaptTo(Session.class);
					Map<String, String> map = new HashMap<>();
					// Type and mime type
					map.put("property", "jcr:content/metadata/dam:MIMEtype");
					map.put("path", "/content/dam");
					map.put("type", "dam:Asset");
					map.put("p.limit", "-1");
					map.put("p.hits", "full");
					map.put("p.guesstotal", "true");
					map.put("p.nodedepth", "20");
					PredicateGroup predicateGroup = PredicateGroup.create(map);
					Query query = builder.createQuery(predicateGroup, session);

					SearchResult result = query.getResult();
					List<Hit> hits = result.getHits();

					for (Hit hit : hits) {
						Resource myResource = hit.getResource();
						String path = hit.getPath();
						damAssets.add(path);
					}

				} else {
					System.out.println("Resource is null .....");
				}

			} else {
				System.out.println("Resource resolver is null .....");
			}

		} catch (RepositoryException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		}
	}

	public List<String> getDamAssets() {
		return damAssets;
	}

}
