package vip.stayfoolish.reggie.common;

/*
 * @Author LiuLiu
 * @Date 2022/5/6 18:25
 * @Description 基于 ThreadLocal 封装工具类，用户保存和获取当前登录用户 id
 * @Since version-1.0
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /*
     * @Author LiuLiu
     * @Date 2022/5/6 18:27
     * @Description 设置值
     * @Param
     * @Return
     * @Since version-1.0
     */
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    /*
     * @Author LiuLiu
     * @Date 2022/5/6 18:28
     * @Description 获取值
     * @Param
     * @Return
     * @Since version-1.0
     */
    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
