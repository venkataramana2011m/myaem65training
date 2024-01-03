package com.myaem65training.core.utils;

import com.google.common.net.InternetDomainName;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.stream.Stream;

import static org.apache.sling.auth.core.AuthenticationSupport.REQUEST_ATTRIBUTE_RESOLVER;
public class RequestUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private RequestUtil(){

    }
    public static ResourceResolver getResolver(HttpServletRequest request){
       if(request instanceof SlingHttpServletRequest){
           return ((SlingHttpServletRequest)request).adaptTo(ResourceResolver.class);
       }
       return (ResourceResolver) request.getAttribute(REQUEST_ATTRIBUTE_RESOLVER);
    }
    public static boolean hasSelector(SlingHttpServletRequest request, String selector){
        return Stream.of(request.getRequestPathInfo().getSelectors()).anyMatch(s -> StringUtils.equals(s,selector));
    }
    public static boolean hasAnySelector(SlingHttpServletRequest request, String... selectors){
        return Stream.of(selectors).anyMatch(selector -> ArrayUtils.contains(request.getRequestPathInfo().getSelectors(),selector));
    }
    public static boolean hasAllSelector(SlingHttpServletRequest request, String... selectors){
        return Stream.of(selectors).anyMatch(selector -> ArrayUtils.contains(request.getRequestPathInfo().getSelectors(),selector));
    }

    public static String getDomain(HttpServletRequest request, boolean resolveTopPrivateDomain){
        String domain = request.getServerName();
        if(resolveTopPrivateDomain){
            try{
                domain = InternetDomainName.from(domain).topPrivateDomain().toString();
            }catch(IllegalStateException ex){
                LOGGER.error("IllegalStateException", ex);
            }
            LOGGER.debug("Resolved Domain : [{}]", domain);
            return domain;
        }
        return domain;
    }
}

