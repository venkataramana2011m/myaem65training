package com.myaem65training.core.models.impl;
import java.util.List;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables=Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CarouselModelImpl {

    @ValueMapValue
    @Default(values = "image")
    private String mediatype;
    
    @Inject
    private List<CarouselModelDetailsImpl> mediaCarouselDetailsWithMap;

	public String getMediatype() {
		return mediatype;
	}

	public List<CarouselModelDetailsImpl> getMediaCarouselDetailsWithMap() {
		return mediaCarouselDetailsWithMap;
	}
    
}
