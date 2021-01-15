package com.myaem65training.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myaem65training.core.service.DomainConfigService;

@Model(adaptables = Resource.class)
public class ReadOSGIConfigModel {
	
	 protected final Logger log = LoggerFactory.getLogger(this.getClass());
	 
	    @Inject @Named("sling:resourceType") @Default(values="No resourceType")
	    protected String resourceType;
	     
	    @Inject 
	    DomainConfigService ms; 
	    
	    private String message;
	    
	    @PostConstruct
	    protected void init() {
	         
	        log.info("***** IN INIT") ; 
	         
	         
	        message = "\tHello World!\n";
	        message += "\tResource type is: " + resourceType + "\n";
	        message += "\tVALUE IS " + ms.getDomainPathConfig() + "\n";
	    }
	 
	    public String getMessage() {
	        return message;
	    }
}
