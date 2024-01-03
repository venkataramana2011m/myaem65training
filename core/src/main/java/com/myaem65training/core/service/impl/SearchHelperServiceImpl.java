package com.myaem65training.core.service.impl;

import com.myaem65training.core.config.MyNYTimesApiConfiguration;
import com.myaem65training.core.service.MyNYTimesApiConfigurationFactory;
import com.myaem65training.core.service.SearchHelperService;
import org.osgi.service.component.annotations.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component(service = SearchHelperService.class)
public class SearchHelperServiceImpl implements SearchHelperService {
    @Reference(service =  MyNYTimesApiConfigurationFactory.class, cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC, fieldOption = FieldOption.UPDATE, bind = "bindConfigFactory", unbind = "unbindConfigFactory")
    private List<MyNYTimesApiConfigurationFactory> configFactories;

    protected void bindConfigFactory(final MyNYTimesApiConfigurationFactory configFactory){
        if(configFactories == null){
            configFactories = new ArrayList<>();
        }
        configFactories.add(configFactory);
    }

    protected void unbindConfigFactory(final MyNYTimesApiConfigurationFactory configFactory){
        configFactories.remove(configFactory);
    }
    @Override
    public MyNYTimesApiConfigurationFactory getConfigFactory(String path) {
        //return configFactories.stream().filter(factory);
        return configFactories.stream().filter(factory -> path.startsWith(factory.getPath()))
                .max(Comparator.comparingInt(factory -> factory.getPath().length())).orElse(null);
    }
}
