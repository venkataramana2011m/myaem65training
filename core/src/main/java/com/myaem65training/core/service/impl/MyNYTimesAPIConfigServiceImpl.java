package com.myaem65training.core.service.impl;

import com.myaem65training.core.config.MyNYTimesApiConfiguration;
import com.myaem65training.core.config.RestAPIConfig;
import com.myaem65training.core.service.MyNYTimesAPIConfigService;
import com.myaem65training.core.service.RestAPIConfigService;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = MyNYTimesAPIConfigService.class, immediate = true)
@Designate(ocd = MyNYTimesApiConfiguration.class)
public class MyNYTimesAPIConfigServiceImpl implements MyNYTimesAPIConfigService {
    private static final Logger log = LoggerFactory.getLogger(MyNYTimesAPIConfigServiceImpl.class);

    private MyNYTimesApiConfiguration restAPIConfig;

    @Activate
    @Modified
    protected void activate(MyNYTimesApiConfiguration configuration) {
        this.restAPIConfig = configuration;
    }

    @Override
    public String getNYTimesAPIConfigEndPoint() {

        return restAPIConfig.nytimesEndPoint();
    }

    @Override
    public String getNYTimesAPIConfigApiID() {

        return restAPIConfig.nytimesApiId();
    }

    @Override
    public String getNYTimesAPIConfigApiKey() {

        return restAPIConfig.nytimesAPIKey();
    }

    @Override
    public String getNYTimesAPIConfigSecretKey() {

        return restAPIConfig.nytimesSecretKey();
    }

    @Override
    public String getNYTimesAPIConfigBooksListEndPoint() {

        return restAPIConfig.nytimesBooksListEndPoint();
    }

    @Override
    public String getNYTimesAPIConfigBooksListByCategoryEndPoint() {

        return restAPIConfig.nytimesBooksListByCategoryEndPoint();
    }
}
