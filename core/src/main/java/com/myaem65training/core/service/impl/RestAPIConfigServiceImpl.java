package com.myaem65training.core.service.impl;

import org.apache.sling.commons.osgi.PropertiesUtil;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myaem65training.core.config.DomainConfig;
import com.myaem65training.core.config.RestAPIConfig;
import com.myaem65training.core.service.DomainConfigService;
import com.myaem65training.core.service.RestAPIConfigService;

@Component(service = RestAPIConfigService.class, immediate = true)
@Designate(ocd = RestAPIConfig.class)
public class RestAPIConfigServiceImpl implements RestAPIConfigService{
	
	private static final Logger log = LoggerFactory.getLogger(RestAPIConfigServiceImpl.class);
	
	private RestAPIConfig restAPIConfig;
	
	@Activate
	@Modified
	protected void activate(RestAPIConfig configuration) {
		this.restAPIConfig = configuration;
	}
	
	@Override
	public String getRestfulAPIPathConfig() {
		System.out.println(PropertiesUtil.toString(restAPIConfig.restfulAPIPathConfig(),null));
		return restAPIConfig.restfulAPIPathConfig();
		
	}

	@Override
	public String getRestfulAPIKeyConfig() {
		// TODO Auto-generated method stub
		System.out.println(PropertiesUtil.toString(restAPIConfig.restfulAPIKeyConfig(),null));
		return restAPIConfig.restfulAPIKeyConfig();
	}

	@Override
	public String getRestfulAPIHostConfig() {
		// TODO Auto-generated method stub
		System.out.println(PropertiesUtil.toString(restAPIConfig.restfulAPIHostConfig(),null));
		return restAPIConfig.restfulAPIHostConfig();
	}

}
