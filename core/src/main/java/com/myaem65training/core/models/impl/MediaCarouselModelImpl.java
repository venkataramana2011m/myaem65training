package com.myaem65training.core.models.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myaem65training.core.models.MediaCarouselModel;

@Model(adaptables = SlingHttpServletRequest.class, adapters = MediaCarouselModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MediaCarouselModelImpl implements MediaCarouselModel {
    private static final Logger LOG = LoggerFactory.getLogger(MediaCarouselModelImpl.class);
    @SlingObject
    Resource componentResource;
    @ValueMapValue
    @Default(values = "image")
    private String mediatype;

    @Override
    public List<Map<String, String>> getMediaCarouselDetailsWithMap() {
        List<Map<String, String>> mediaCarouselDetailsMap = new ArrayList<>();
        if (componentResource != null) {
            LOG.info("\n Book Details with Path {}  ", componentResource.getPath());
            Resource mediaCarouselDetail = componentResource.getChild("mediaCarouselDetailsWithMap");
            if (mediaCarouselDetail != null) {
                for (Resource carousel : mediaCarouselDetail.getChildren()) {
                    Map<String, String> carouselMap = new HashMap<>();
                    carouselMap.put("title", carousel.getValueMap().get("title", String.class));
                    carouselMap.put("carouseldescription",
                            carousel.getValueMap().get("carouseldescription", String.class));
                    carouselMap.put("linkType", carousel.getValueMap().get("linkType", String.class));
                    if (carousel.getValueMap().get("linkType", String.class).equals("internal")) {
                        carouselMap.put("carouselMedia", carousel.getValueMap().get("internallink", String.class));
                    } else if (carousel.getValueMap().get("linkType", String.class).equals("external")) {
                        carouselMap.put("carouselMedia", carousel.getValueMap().get("externalLink", String.class));
                    } else {
                        carouselMap.put("carouselMedia", carousel.getValueMap().get("carouselMedia", String.class));
                    }
                    mediaCarouselDetailsMap.add(carouselMap);
                }
            }
        }
        LOG.info("\n SIZE {} ", mediaCarouselDetailsMap.size());
        return mediaCarouselDetailsMap;
    }

    @Override
    public String getMediaType() {
        return mediatype;
    }

}
