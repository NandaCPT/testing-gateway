/*
 *
 * Copyright (c) 2021 Aero Systems Indonesia, PT.
 * All rights reserved.
 *
 * AERO SYSTEMS INDONESIA PROPRIETARY/CONFIDENTIAL. Use is subject to
 * license terms.
 */
package com.muf.framework.mffgateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author hadi.nugraha
 * @version 1, (Created on Nov 13, 2021)
 * @since 1
 */
@ConfigurationProperties
public class HttpUriConfig {

    private String httpbin = "http://httpbin.org:80";

    public String getHttpbin() {
        return httpbin;
    }

    public void setHttpbin(String httpbin) {
        this.httpbin = httpbin;
    }

}
