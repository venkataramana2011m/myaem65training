package com.myaem65training.core.service;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

@Component(service = GsonProvider.class)
public class GsonProvider {
    private Gson gson;
    public Gson getGson() {
        return gson;
    }

    @Activate
    protected void start(){
        this.gson = new Gson();
    }
    @Deactivate
    protected void stop(){
        this.gson = null;
    }

}
