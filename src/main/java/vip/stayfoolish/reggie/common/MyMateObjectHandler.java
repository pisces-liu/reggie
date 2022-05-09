package vip.stayfoolish.reggie.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class MyMateObjectHandler implements MetaObjectHandler {

    /*
     * @Author LiuLiu
     * @Date 2022/5/6 18:47
     * @Description 插入操作，自动填充
     * @Param
     * @Return
     * @Since version-1.0
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段自动填充[insert]...");
        log.info(metaObject.toString());
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("createUser", BaseContext.getCurrentId());
        metaObject.setValue("updateUser", BaseContext.getCurrentId());
    }

    /*
     * @Author LiuLiu
     * @Date 2022/5/6 18:50
     * @Description 更新操作 自动填充
     * @Param
     * @Return
     * @Since version-1.0
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段自动填充");
        log.info(metaObject.toString());

        Long id = Thread.currentThread().getId();
        log.info("线程 id 为：{}", id);


        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", BaseContext.getCurrentId());
    }
}
