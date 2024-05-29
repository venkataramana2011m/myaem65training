package com.myaem65training.core.service;

import com.google.gson.JsonObject;
import org.osgi.service.component.annotations.Component;

import java.io.IOException;

public interface SearchHelperService {
    MyNYTimesApiConfigurationFactory getConfigFactory(String path);
    public String makeApiCall(String requestUrl, MyNYTimesApiConfigurationFactory config, JsonObject payload);

    public String makeRecipeCategoryListApiCall() throws IOException;
}
