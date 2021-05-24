package com.myaem65training.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class)
public class ResponseBodyApplicationTranslations {
	private String langCode;
	private String langValue;

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	public String getLangValue() {
		return langValue;
	}

	public void setLangValue(String langValue) {
		this.langValue = langValue;
	}

}
