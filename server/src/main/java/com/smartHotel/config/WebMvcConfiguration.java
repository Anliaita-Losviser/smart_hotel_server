package com.smartHotel.config;

import com.smartHotel.interceptor.JwtTokenAdminInterceptor;
import com.smartHotel.json.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

/**
 * 配置类，注册web层相关组件
 */
@Configuration
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurationSupport {
    
    @Autowired
    private JwtTokenAdminInterceptor jwtTokenAdminInterceptor;
    
    /**
     * 注册自定义拦截器
     *
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册自定义拦截器...");
        registry.addInterceptor(jwtTokenAdminInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/employee/login");
    }
    
    /**
     * 通过knife4j生成接口文档
     *
     * @return
     */
    @Bean
    public Docket donket() {
        log.info("Swagger执行");
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("智慧酒店项目文档")
                .version("2.0")
                .description("智慧酒店项目文档")
                .build();
        Docket docket = new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.smartHotel.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
    
    
    /**
     * 设置静态资源映射
     *
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始设置静态资源映射");
        registry.addResourceHandler("/doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    
    /**
     * 扩展MVC消息转换器，处理日期数据和Long到字符串的转换
     *
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("扩展消息转换器");
        //创建消息转换器
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(new JacksonObjectMapper());
        //将消息转换器加入容器中
        converters.add(1, converter);
    }
    
    // 拦截器跨域配置
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 跨域路径
        CorsRegistration cors = registry.addMapping("/**");
        // 可访问的外部域
        cors.allowedOrigins("*");
        // 支持跨域用户凭证
        //cors.allowCredentials(true);
        //cors.allowedOriginPatterns("*");
        // 设置 header 能携带的信息
        cors.allowedHeaders("*");
        // 支持跨域的请求方法
        cors.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
        // 设置跨域过期时间，单位为秒
        cors.maxAge(3600);
    }
}
