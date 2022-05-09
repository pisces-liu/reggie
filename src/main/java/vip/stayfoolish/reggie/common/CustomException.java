package vip.stayfoolish.reggie.common;

/*
 * @Author LiuLiu
 * @Date 2022/5/7 8:42
 * @Description 自定义业务异常
 * @Since version-1.0
 */
public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}
