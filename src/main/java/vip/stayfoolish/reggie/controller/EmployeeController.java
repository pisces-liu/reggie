package vip.stayfoolish.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import vip.stayfoolish.reggie.common.R;
import vip.stayfoolish.reggie.entity.Employee;
import vip.stayfoolish.reggie.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /*
     * @Author LiuLiu
     * @Date 2022/5/3 19:52
     * @Description 登陆
     * // @RequestBody 用于接受 json 格式的请求数据
     * request 用于保存员工登陆成功之后的信息，将登陆信息保存到 session 中
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        // 1. 将页面提交的密码 password 进行 md5 加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes()); // 将密码以 md5 的格式进行存储

        // 2. 根据页面提交的用户名 username 查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper); // 调用 getOne 方法是因为 username 字段在数据库中已经被唯一约束

        // 3. 如果没有查询到则返回登陆失败结果
        if (emp == null) {
            return R.error("登陆失败！");
        }

        // 4. 密码比对，如果不一致则返回登陆失败结果
        if (!emp.getPassword().equals(password)) { // 对判断条件进行取反
            return R.error("登陆失败！");
        }

        // 5. 查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if (emp.getStatus() == 0) {
            return R.error("账号被禁用！");
        }

        // 6、登陆成功，将员工 id 存入 session 并返回登陆成功结果
        request.getSession().setAttribute("employee", emp.getId());

        return R.success(emp);
    }

    /*
     * @Author LiuLiu
     * @Date 2022/5/3 20:57
     * @Description 员工退出
     * @Param
     * @Return
     * @Since version-1.0
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        // 清理 Session 中保存的当前登陆员工的 id
        request.getSession().removeAttribute("employee");
        return R.success("退出成功！");
    }

    /*
     * @Author LiuLiu
     * @Date 2022/5/4 16:24
     * @Description 新增员工
     * @Param
     * @Return R 中泛型的使用 String 是因为这个返回结果没有用到其他更加复杂的类型
     * @Since version-1.0
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("新增员工，员工信息：{}", employee.toString());

        // 设置初始密码 123456，需要进行 md5 加密处理
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        // employee.setCreateTime(LocalDateTime.now());
        // employee.setUpdateTime(LocalDateTime.now());

        // 获取当前登录用户的 id
        // Long empId = (Long) request.getSession().getAttribute("employee");

        // employee.setCreateUser(empId);
        // employee.setUpdateUser(empId);

        employeeService.save(employee); // 使用 myBatisPlus 中提供的 save 方法

        return R.success("新增员工成功！");
    }

    /*
     * @Author LiuLiu
     * @Date 2022/5/4 18:54
     * @Description 员工信息分页查询，这里泛型传入 Page 类型的原因是页面需要什么数据，我们就传入什么类型的数据
     * @Param page 当前展示的页数 pageSize 每一页展示内容的数量 name 查询姓名变量
     * @Return
     * @Since version-1.0
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        log.info("page = {}, pageSize = {}, name = {}", page, pageSize, name);

        // 构造分页构造器
        Page pageInfo = new Page(page, pageSize);

        // 构造条件查询器
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 添加过滤条件
        if (name != null && !name.equals("")) { // 当姓名不为空时才进行模糊查询
            lambdaQueryWrapper.like(Employee::getName, name);
        }
        // 添加排序条件
        lambdaQueryWrapper.orderByDesc(Employee::getUpdateTime);

        // 执行查询
        employeeService.page(pageInfo, lambdaQueryWrapper);

        return R.success(pageInfo);
    }

    /*
     * @Author LiuLiu
     * @Date 2022/5/4 21:50
     * @Description 根据 id 修改员工信息
     * 在修改的时候，由于 js 会有精度问题，所以需要将 id 由 number 类型转为 string 类型，在这里就使用到了 JacksonObjectMapper
     * 然后在 WebMvcConfig 类中设置 扩展消息转换器
     * @Param
     * @Return
     * @Since version-1.0
     */
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        log.info(employee.toString());

        /*
         * 已经设置了公共字段自动插入
         * */
        // 由于 mybatis plus 生成 id 使用的是雪花算法，所以需要使用 long 强制转换类型
        // Long empId = (Long) request.getSession().getAttribute("employee");
        //employee.setUpdateTime(LocalDateTime.now());
        //employee.setUpdateUser(empId);
        //employeeService.updateById(employee);

        return R.success("员工信息修改成功！");

    }

    /*
     * @Author LiuLiu
     * @Date 2022/5/4 22:08
     * @Description 用于修改员工信息的时候回显员工信息
     * /{id} 用于接收动态路径，@PathVariable：接收请求路径中占位符的值
     * @Param
     * @Return
     * @Since version-1.0
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        log.info("根据 id 查询员工信息...");
        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return R.success(employee); // 返回员工信息
        }
        return R.error("没有查询到对应员工信息");
    }


}
