package com.myaem65training.core.models;

import com.myaem65training.core.config.MyNYTimesApiConfiguration;
import com.myaem65training.core.service.MyNYTimesAPIConfigService;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Model(adaptables = Resource.class)
public class MyNYTimesAPIModel {
    @Inject
    MyNYTimesAPIConfigService myNYTimesApiConfiguration;

    private String nytendPoint;
    private String nytapiId;
    private String nytaPIKey;
    private String nytsecretKey;
    private String nytbooksByCategoryEndPoint;
    private String nytbooksListEndPoint;
    @PostConstruct
    private void init(){
        nytendPoint =  PropertiesUtil.toString(myNYTimesApiConfiguration.getNYTimesAPIConfigEndPoint(), null);
        nytapiId = PropertiesUtil.toString(myNYTimesApiConfiguration.getNYTimesAPIConfigApiID(), null);
        nytaPIKey = PropertiesUtil.toString(myNYTimesApiConfiguration.getNYTimesAPIConfigApiKey(), null);
        nytsecretKey = PropertiesUtil.toString(myNYTimesApiConfiguration.getNYTimesAPIConfigSecretKey(), null);
        nytbooksByCategoryEndPoint=PropertiesUtil.toString(myNYTimesApiConfiguration.getNYTimesAPIConfigBooksListEndPoint(), null);
        nytbooksListEndPoint=PropertiesUtil.toString(myNYTimesApiConfiguration.getNYTimesAPIConfigBooksListByCategoryEndPoint(), null);
    }


    public String getNytendPoint() {
        return nytendPoint;
    }

    public String getNytapiId() {
        return nytapiId;
    }

    public String getNytaPIKey() {
        return nytaPIKey;
    }

    public String getNytsecretKey() {
        return nytsecretKey;
    }

    public String getNytbooksByCategoryEndPoint() {
        return nytbooksByCategoryEndPoint;
    }

    public String getNytbooksListEndPoint() {
        return nytbooksListEndPoint;
    }
}
