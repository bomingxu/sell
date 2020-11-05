package com.xbm.product.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.SpringfoxWebMvcConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * ****************************************************************************
 * swagger配置
 *
 * @author(作者)：xuboming
 * @date(创建日期)：2020年10月30日
 ******************************************************************************
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
@ConditionalOnClass(SpringfoxWebMvcConfiguration.class)
public class SwaggerConfig implements WebMvcConfigurer {
	

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	@Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.xbm.product.controller"))
                .paths(PathSelectors.any())
                .build();
    }
	
    /**
     * 构建 api文档的详细信息函数,注意这里的注解引用的是哪个
     *	
     * @return
     * @since           1.0
     */
	private ApiInfo apiInfo() {
        Contact contact = new Contact("销售系统", "", "");
        return new ApiInfoBuilder()
                .title("销售系统API接口")//标题
                .description("提供销售系统前后端交互API接口描述")//文档接口的描述
                .contact(contact)
                .version("1.0.0")//版本号
                .build();
    }

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		
		
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		
		
	}

	@Override
	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
		
		
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		
		
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		
		
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		
		
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		
		
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		
		
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		
		
	}

	@Override
	public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
		
		
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		
	}

	FastJsonHttpMessageConverter fastJsonHttpMessageConverter(){
       FastJsonHttpMessageConverter fastJsonHttpMessageConverter=new FastJsonHttpMessageConverter();

       FastJsonConfig fastJsonConfig=new FastJsonConfig();
       fastJsonConfig.setSerializerFeatures(
               SerializerFeature.QuoteFieldNames,
               SerializerFeature.WriteMapNullValue,
               SerializerFeature.DisableCircularReferenceDetect,
               SerializerFeature.WriteDateUseDateFormat,
               SerializerFeature.WriteNullStringAsEmpty);

       List<MediaType> mediaTypeList=new ArrayList<>();
       mediaTypeList.add(MediaType.APPLICATION_JSON_UTF8);
       mediaTypeList.add(MediaType.APPLICATION_JSON);
       fastJsonHttpMessageConverter.setSupportedMediaTypes(mediaTypeList);
       fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
       return fastJsonHttpMessageConverter;
    }

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		
		converters.clear();
        StringHttpMessageConverter converter = new StringHttpMessageConverter(
                Charset.forName("UTF-8"));
        converters.add(converter);        
        //converters.add(fastJsonHttpMessageConverter());
	}

	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		
		
	}

	@Override
	public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		
		
	}

	@Override
	public Validator getValidator() {
		
		return null;
	}

	@Override
	public MessageCodesResolver getMessageCodesResolver() {
		
		return null;
	}
}
