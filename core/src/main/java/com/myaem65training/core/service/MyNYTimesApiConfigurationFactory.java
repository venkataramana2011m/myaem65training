package com.myaem65training.core.service;

import com.myaem65training.core.config.MyNYTimesApiConfiguration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;

@Component(service = MyNYTimesApiConfigurationFactory.class, configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true)
@Designate(ocd = MyNYTimesApiConfiguration.class, factory = true)
public class MyNYTimesApiConfigurationFactory {
    private String path;
    private String nytimesEndPoint;
    private String nytimesApiId;
    private String nytimesAPIKey;
    private String nytimesSecretKey;
    private String nytimesBooksListByCategoryEndPoint;
    private String nytimesBooksListEndPoint;
    public String getPath() {
        return path;
    }
    public String getNytimesEndPoint() {
        return nytimesEndPoint;
    }

    public String getNytimesApiId() {
        return nytimesApiId;
    }

    public String getNytimesAPIKey() {
        return nytimesAPIKey;
    }

    public String getNytimesSecretKey() {
        return nytimesSecretKey;
    }

    public String getNytimesBooksListByCategoryEndPoint() {
        return nytimesBooksListByCategoryEndPoint;
    }

    public String getNytimesBooksListEndPoint() {
        return nytimesBooksListEndPoint;
    }

    @Activate
    @Modified
    protected void activate(MyNYTimesApiConfiguration config){
        path = config.path();
        nytimesEndPoint = config.nytimesEndPoint();
        nytimesApiId = config.nytimesApiId();
        nytimesAPIKey = config.nytimesAPIKey();
        nytimesSecretKey = config.nytimesSecretKey();
        nytimesBooksListEndPoint = config.nytimesBooksListEndPoint();
        nytimesBooksListByCategoryEndPoint = config.nytimesBooksListByCategoryEndPoint();
    }


}
