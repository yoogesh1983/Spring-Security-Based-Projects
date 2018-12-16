package com.yoogesh.service.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.yoogesh.common.RestHelper.MyJacksonMessageConverter;

@Configuration
@EnableAsync
@ComponentScan({ "com.yoogesh.service" })
@PropertySource({ "classpath:service-${envTarget:local}.properties" })
public class ServiceConfig extends WebMvcConfigurerAdapter
{
    public ServiceConfig() {
        super();
    }
    
    @Autowired private Environment env;
    
    /*******************************************NOTE**************************************************************************************
     * - Message converters come outofthe box by spring. However it needs to be enable since it is disable by default
     * 
     * - To enable Message converters, we need to use @EnableWebMvc annotation. we have used it on our WebConfig.java
     * 
     * - To use XML-Message converters, we don't need any jar into our classpath, it works directly However, to use Json, a jackson jar 
     *   must be into our classpath. otherwise it will not work
     * 
     * - if we think our outof the box message converters is not sufficient, then we can create our own message converter. Here, we have 
     *   created our own messageConverter i.e. MyJacksonMessageConverter.java and to add that convertor with the other outofbox converters,
     *   we need to override extendMessageConverters() method of WebMvcConfigurer interface which we have done here 
     ****************************************************************************************************************************************/
    
    
    /*******************************************Content Negotiation For Rest******************************************************************
     * - For content Negotiation, it first check what we are passing from URL i.e. Are we passing .xml or .json? i.e. {url}.json
     * - This takes highest precedence. However we don't like this and are disabling this rule by overriding configureContentNegotiation()
     *   below by setting "favorPathExtension(false)"
     *   
     * - If .json or .xml is not provided, then it checks for whether the query param is provided on url? However this is disable by default
     *   and we need to enable it. Actually we want to use that and for this we have overridden configureContentNegotiation() method below.
     *   This means we can now pass {url}?mediaType=json 
     *   
     * - If the first and second schenario is not passed,then it checks what is comming from Request header at accept header? i.e. the
     *   value of request.setHeader("Accept", "application/json") which eventuall becomes headers = {"Accept=application/json"}. In Rest 
     *   template, we we have set incoming request's header on createHeaders() methods of AbstractHttpAdapter.java.This is the lowest
     *   precedence. we can disable it by setting  this by setting "ignoreAcceptHeader(true)".However we are not disabling it here.
     *    
     * - Make sure, this has nothing to do with @produces and @Consumes or @headers = {"Accept=application/xml"} annotation at RequestMapping
     *   of the rest controller. These you can put to define that what you can accept and what you can produce. however it is optional.But
     *   if you let's say put here @consumes = {MediaType.APPLICATION_XML_VALUE} and if you pass {url}?mediaType=json, then it will not work
     *   since we can only support xml. so it not good to define @produce and @consumes at rest controller. however for security reason or 
     *   if you are more concert about what you are producing and consuming, then you can also maintain that there. but make sure these have
     *   no concern with the above's content Negotiation manager.
     ********************************************************************************************************************************************/
    
    @Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) 
    {
		configurer.favorPathExtension(false)
				  		.favorParameter(true).parameterName(env.getProperty("rest.url.query.mediaType.name")).mediaType(" + env.getProperty('rest.url.query.mediaType.xml') + ", MediaType.APPLICATION_XML).mediaType(" + env.getProperty('rest.url.query.mediaType.json') + ", MediaType.APPLICATION_JSON)
				  			.ignoreAcceptHeader(false);
	}

    
    @Bean
    public MyJacksonMessageConverter myJacksonMessageConverter(){
    	return new MyJacksonMessageConverter();
    }

    
    
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		for(HttpMessageConverter<?> converter : converters){
			if(converter instanceof AbstractJackson2HttpMessageConverter){
				((AbstractJackson2HttpMessageConverter) converter).setObjectMapper(myJacksonMessageConverter());
			}
		}
		super.extendMessageConverters(converters);
	}  


}
