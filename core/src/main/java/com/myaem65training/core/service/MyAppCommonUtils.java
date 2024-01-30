package com.myaem65training.core.service;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;

public interface MyAppCommonUtils {
    public String fetchPageListing(String path, ResourceResolver resourceResolver, SlingHttpServletRequest request);
}
