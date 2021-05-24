package com.myaem65training.core.models;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myaem65training.core.service.RestAPIConfigService;

@Model(adaptables = Resource.class)
public class FetchCountriesListModel {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Inject
	@Named("sling:resourceType")
	@Default(values = "No resourceType")
	protected String resourceType;

	@Inject
	RestAPIConfigService rfapics;

	private String message;

	private List<String> countriesList;

	
	@PostConstruct
	protected void init() {
		LOG.info("***** IN INIT");
		message = "\tHello World!\n";
		message += "\tResource type is: " + resourceType + "\n";
		System.out.println(PropertiesUtil.toString(rfapics.getRestfulAPIPathConfig(), null));
		System.out.println(PropertiesUtil.toString(rfapics.getRestfulAPIHostConfig(), null));
		System.out.println(PropertiesUtil.toString(rfapics.getRestfulAPIKeyConfig(), null));
	}

	public String getMessage() {
		return message;
	}
	
	public List<String> getCountriesList() throws IOException, InterruptedException, JSONException  {
		/*
		 * HttpRequest request =
		 * HttpRequest.newBuilder().uri(URI.create(rfapics.getRestfulAPIPathConfig()))
		 * .header("x-rapidapi-key", rfapics.getRestfulAPIKeyConfig())
		 * .header("x-rapidapi-host", rfapics.getRestfulAPIHostConfig()) .method("GET",
		 * HttpRequest.BodyPublishers.noBody()).build(); HttpResponse<String> response =
		 * HttpClient.newHttpClient().send(request,
		 * HttpResponse.BodyHandlers.ofString()); System.out.println(response.body());
		 * System.out.println(response.body().length());
		 */
		return countriesList;
	}


	
}
