package com.myaem65training.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(
		name = "Restful API Configuration", 
		description = "This configuration reads the values to make an HTTP call to a JSON webservice")
public @interface RestAPIConfig {
	
	@AttributeDefinition(
			name = "Third party api url config", 
			description = "This property indicates the third party api url configuration", 
			type = AttributeType.STRING)
	public String restfulAPIPathConfig();
	
	@AttributeDefinition(
			name = "Third party api key config", 
			description = "This property indicates the third party api key configuration", 
			type = AttributeType.STRING)
	public String restfulAPIKeyConfig();
	
	@AttributeDefinition(
			name = "Third party api host config", 
			description = "This property indicates the third party api host configuration", 
			type = AttributeType.STRING)
	public String restfulAPIHostConfig();
	
	
}
