package com.myaem65training.core.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.day.cq.tagging.TagManager;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ServiceCouponsModel {

	@SlingObject
	private Resource currentResource;

	@SlingObject
	private ResourceResolver resourceResolver;

	@Inject
	@Named("cq:tags")
	private String[] cqTags;

	@Inject
	@Default(values = "/content/dam")
	private String parentPath;

	private final List<ContentFragmentStateList> stateList = new ArrayList<>();

	private final List<ContentFragmentMetroList> metroList = new ArrayList<>();

	private String message;

	@PostConstruct
	protected void init() {
		final TagManager tagManager = resourceResolver.adaptTo(TagManager.class);

		if (cqTags != null) {
			tagManager.find(parentPath, cqTags, true).forEachRemaining(resource -> {
				// The cq:tags property of the default ContentFragment tags field places the
				// property on the
				// <content-fragment>/jcr:content/metadata node. And so to adapt the
				// <content-fragment> you have to go two nodes upwards.
				final ContentFragmentStateList cfStateList = resource.getParent().getParent()
						.adaptTo(ContentFragmentStateList.class);
				if (cfStateList != null && ContentFragmentStateList.MODEL_TITLE.equals(cfStateList.getStateName())) {
					stateList.add(cfStateList);

				}
				
			});
		}
	}

	public List<ContentFragmentStateList> getStateList() {
		return stateList;
	}
	

}
