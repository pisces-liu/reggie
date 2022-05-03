package vip.stayfoolish.reggie.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

// 注解的作用：说明这是一个配置类
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    /*
     * @Author LiuLiu
     * @Date 2022/5/3 17:46
     * @Description 设置静态资源映射
     * @Param
     * @Return
     * @Since version-1.0
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 当浏览器的路径是 /backend/ 的时候，会映射到 resources 文件夹下的 backend 文件夹中的 index 文件
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
    }
}
