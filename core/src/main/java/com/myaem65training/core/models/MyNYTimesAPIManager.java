package com.myaem65training.core.models;

import com.myaem65training.core.config.MyNYTimesApiConfiguration;

import java.util.List;

public interface MyNYTimesAPIManager {
    List<MyNYTimesApiConfiguration> getConfigurationList();
}
