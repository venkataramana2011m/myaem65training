package com.myaem65training.core.service;

import com.google.gson.JsonObject;
import org.osgi.service.component.annotations.Component;

public interface SearchHelperService {
    MyNYTimesApiConfigurationFactory getConfigFactory(String path);
    public String makeApiCall(String requestUrl, MyNYTimesApiConfigurationFactory config, JsonObject payload);
}
