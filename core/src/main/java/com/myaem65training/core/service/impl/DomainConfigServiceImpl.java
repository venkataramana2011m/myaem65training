package com.myaem65training.core.service.impl;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myaem65training.core.config.DomainConfig;
import com.myaem65training.core.service.DomainConfigService;

@Component(service = DomainConfigService.class, immediate = true)
@Designate(ocd = DomainConfig.class)
public class DomainConfigServiceImpl implements DomainConfigService {
	
	private static final Logger log = LoggerFactory.getLogger(DomainConfigServiceImpl.class);
	
	private DomainConfig domainConfig;
	
	@Activate
	@Modified
	protected void activate(DomainConfig configuration) {
		this.domainConfig = configuration;
	}

	@Override
	public String getDomainPathConfig() {
		
		return domainConfig.domainPathConfig();
	}
	
	@Override
	public boolean getEnableConfig() {
		
		return domainConfig.enableConfig();
		
	}
	
	
	

}
