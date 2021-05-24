/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.myaem65training.core.models;

import static org.apache.sling.api.resource.ResourceResolver.PROPERTY_RESOURCE_TYPE;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.ExporterOption;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.settings.SlingSettingsService;
import com.adobe.cq.export.json.ComponentExporter;

import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.myaem65training.core.service.DomainConfigService;

@Model(adaptables = Resource.class, resourceType = {
		com.myaem65training.core.models.HelloWorldModel.RESOURCE_TYPE }, adapters = {
				com.myaem65training.core.models.HelloWorldModel.class,
				ComponentExporter.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION, options = {
		@ExporterOption(name = "SerializationFeature.WRITE_DATES_AS_TIMESTAMPS", value = "true") })
public class HelloWorldModel implements ComponentExporter {

	protected static final String RESOURCE_TYPE = "myaem65training/components/helloworld";

	@ValueMapValue(name = PROPERTY_RESOURCE_TYPE, injectionStrategy = InjectionStrategy.OPTIONAL)
	@Default(values = "No resourceType")
	protected String resourceType;

	@Inject
	DomainConfigService ms;

	@OSGiService
	private SlingSettingsService settings;
	@SlingObject
	private Resource currentResource;
	@SlingObject
	private ResourceResolver resourceResolver;

	private String message;
	@Inject
	@Named("linkURL")
	private String linkURL;

	@PostConstruct
	protected void init() {
		PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
		String currentPagePath = Optional.ofNullable(pageManager).map(pm -> pm.getContainingPage(currentResource))
				.map(Page::getPath).orElse("");

		message = "Hello World!\n" + "Resource type is: " + resourceType + "\n" + "Current page is:  " + currentPagePath
				+ "\n" + "This is instance: " + settings.getSlingId() + "\n";
	}

	public String getMessage() {
		return message;
	}

	public String getLinkURL() {
		String tmpLinkURL = linkURL;
		linkURL = null;

		return linkURL = tmpLinkURL.startsWith("http") ? tmpLinkURL : ms.getDomainPathConfig() + tmpLinkURL;
	}

	@Override
	public String getExportedType() {
		// TODO Auto-generated method stub
		return RESOURCE_TYPE;
	}

}
