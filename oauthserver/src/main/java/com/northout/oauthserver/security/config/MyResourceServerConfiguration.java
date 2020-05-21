package com.northout.oauthserver.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableResourceServer
public class MyResourceServerConfiguration {

}
