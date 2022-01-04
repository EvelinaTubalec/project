package com.leverx.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan({"com.leverx"})
@EnableWebMvc
@Import(value = DataSourceConfig.class)
public class SpringConfig implements WebMvcConfigurer {

}
