package com.myaem65training.core.models;

import java.util.Date;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CardLayoutDetailsModel {
	
    @ValueMapValue
    private Date cardCreatedOn;
    
    @ValueMapValue
    private String cardTitle;
    
    @ValueMapValue
    private String cardDesc;
    
    @ValueMapValue
    private String cardLink;

	@ValueMapValue
    private String cardAuthorName;
    
    @ValueMapValue
    private String fileReference;

    public Date getCardCreatedOn(){
    	return cardCreatedOn;
	}

	public String getCardTitle() {
		return cardTitle;
	}

	public String getCardDesc() {
		return cardDesc;
	}

	public String getCardLink() {
		return cardLink;
	}

	public String getCardAuthorName() {
		return cardAuthorName;
	}

	public String getFileReference() {
		return fileReference;
	}
}
