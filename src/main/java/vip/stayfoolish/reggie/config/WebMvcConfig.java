package vip.stayfoolish.reggie.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import vip.stayfoolish.reggie.common.JacksonObjectMapper;

import java.util.List;
import java.util.Map;

@Slf4j
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

    /*
     * @Author LiuLiu
     * @Date 2022/5/4 22:01
     * @Description 扩展 mvc 框架的消息转换器
     * @Param
     * @Return
     * @Since version-1.0
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("扩展消息转换器....");
        // 创建消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        // 设置对象转换器，底层使用 Jackson 将 Java 对象转为 Json
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        // 将上面的消息转换器对象追加到 mvc 框架的转换器中. 第一个参数代表权重，0 的优先级最高
        converters.add(0, messageConverter);
    }
}
