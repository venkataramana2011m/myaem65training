package com.myaem65training.core.models;

import java.util.Calendar;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

import com.adobe.cq.dam.cfm.ContentElement;
import com.adobe.cq.dam.cfm.ContentFragment;
import com.adobe.cq.dam.cfm.FragmentData;
import com.adobe.cq.dam.cfm.FragmentTemplate;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SampleContentFragment {
	public static final String MODEL_TITLE = "AEM Book";
	public static final String CF_PATH = "/content/dam/myinnovationpoc/mycountrylist";

	@Inject
	@Self
	private Resource resource;
	@Inject
	ResourceResolver resourceResolver;
	private Optional<ContentFragment> contentFragment;

	@PostConstruct
	public void init() {
		Resource fragmentResource = resourceResolver.getResource(CF_PATH);
		contentFragment = Optional.ofNullable(fragmentResource.adaptTo(ContentFragment.class));
	}

	public String getTitle() {
		return contentFragment.map(cf -> cf.getElement("title")).map(ContentElement::getContent)
				.orElse(StringUtils.EMPTY);
	}

	public String getDescription() {
		return contentFragment.map(cf -> cf.getElement("description")).map(ContentElement::getContent)
				.orElse(StringUtils.EMPTY);
	}

	public Calendar getReleaseDate() {
		return ((Calendar) contentFragment.map(cf -> cf.getElement("releaseDate")).map(ContentElement::getValue)
				.map(FragmentData::getValue).orElse(StringUtils.EMPTY));
	}

	public String getImage() {
		return contentFragment.map(cf -> cf.getElement("image")).map(ContentElement::getContent)
				.orElse(StringUtils.EMPTY);
	}
}
