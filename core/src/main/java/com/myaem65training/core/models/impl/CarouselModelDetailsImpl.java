package com.myaem65training.core.models.impl;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;

@Model(adaptables=Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CarouselModelDetailsImpl{

	@Inject
	public String internallink;
	
	@Inject
	public String externallink;
	
	@Inject
	public String title;
	
	@Inject
	public String carouseldescription;
	
	@Inject
	public String linkType;

	public String getInternalLink() {
		return internallink;
	}
	
	public String getExternalLink() {
		return externallink;
	}

	public String getTitle() {
		return title;
	}

	public String getCarouselDescription() {
		return carouseldescription;
	}

	public String getLinkType() {
		return linkType;
	}

}
