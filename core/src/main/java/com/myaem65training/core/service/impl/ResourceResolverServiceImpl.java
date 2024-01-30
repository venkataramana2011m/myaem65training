package com.myaem65training.core.service.impl;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.framework.Constants;
import com.myaem65training.core.service.ResourceResolverService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import com.myaem65training.core.service.ResourceResolverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.LoginException;

import static com.myaem65training.core.constants.AppConstants.SUB_SERVICE;
@Component(
        service = ResourceResolverService.class,
        property = {
                Constants.SERVICE_ID + "= AEM Tutorial Resource Resolver Service",
                Constants.SERVICE_DESCRIPTION + "= This service is responsible for returning an instance of ResourceResolver"
        })
public class ResourceResolverServiceImpl implements ResourceResolverService{
    private static final String TAG = ResourceResolverServiceImpl.class.getSimpleName();
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceResolverServiceImpl.class);

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    private ResourceResolver resourceResolver;
    @Activate
    protected void activate() throws LoginException {
        // Service User map
        Map<String, Object> serviceUserMap = new HashMap<>();
        // Putting sub-service name in the map
        serviceUserMap.put(ResourceResolverFactory.SUBSERVICE, SUB_SERVICE);
        // Get the instance of Service Resource Resolver
        try {
            resourceResolver = resourceResolverFactory.getServiceResourceResolver(serviceUserMap);
        } catch (org.apache.sling.api.resource.LoginException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public ResourceResolver getResourceResolver() {
        return resourceResolver;
    }
    
}
