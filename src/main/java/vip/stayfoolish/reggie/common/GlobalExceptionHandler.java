package vip.stayfoolish.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/*
 * @Author LiuLiu
 * @Date 2022/5/4 16:32
 * @Description 全局异常处理
 * @Since version-1.0
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /*
     * @Author LiuLiu
     * @Date 2022/5/4 16:31
     * @Description 异常处理方法
     * @Param
     * @Return
     * @Since version-1.0
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException exception) {
        log.error(exception.getMessage());

        if (exception.getMessage().contains("Duplicate entry")) {
            String[] split = exception.getMessage().split(" ");
            String msg = split[2] + "已存在";
            return R.error(msg);
        }
        return R.error("未知错误");
    }

    /*
     * @Author LiuLiu
     * @Date 2022/5/7 8:45
     * @Description 处理异常方法
     * @Param
     * @Return
     * @Since version-1.0
     */
    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHandler(CustomException exception) {
        log.error(exception.getMessage());

        return R.error(exception.getMessage());
    }
}
