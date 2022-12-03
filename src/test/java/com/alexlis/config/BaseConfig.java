package com.alexlis.config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:config/config.properties")
public interface BaseConfig extends Config {

    String hostname();
}
