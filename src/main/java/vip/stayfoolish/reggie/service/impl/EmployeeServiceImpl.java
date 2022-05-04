package vip.stayfoolish.reggie.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.stayfoolish.reggie.entity.Employee;
import vip.stayfoolish.reggie.mapper.EmployeeMapper;
import vip.stayfoolish.reggie.service.EmployeeService;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
