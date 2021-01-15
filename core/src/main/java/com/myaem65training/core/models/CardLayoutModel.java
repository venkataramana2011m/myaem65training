package com.myaem65training.core.models;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = Resource.class)
public class CardLayoutModel {
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());
     // Inject the products node under the current node
    @Inject
    @Optional
    public Resource cards;

}
