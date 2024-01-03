package com.myaem65training.core.service;

import org.osgi.service.component.annotations.Component;

public interface SearchHelperService {
    MyNYTimesApiConfigurationFactory getConfigFactory(String path);
}
