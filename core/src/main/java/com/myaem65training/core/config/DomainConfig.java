package com.myaem65training.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(
		name = "Domain Path Configuration", 
		description = "This configuration reads the values to make an HTTP call to a JSON webservice")
public @interface DomainConfig {
	
	@AttributeDefinition(
			name = "Domain path config", 
			description = "This property indicates the domain path configuration", 
			type = AttributeType.STRING)
	public String domainPathConfig();
	
	@AttributeDefinition(
			name = "Enable config", 
			description = "This property indicates whether the configuration values will taken into account or not", 
			type = AttributeType.BOOLEAN)
	public boolean enableConfig();
	
	
}
