package vip.stayfoolish.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import vip.stayfoolish.reggie.entity.Employee;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
