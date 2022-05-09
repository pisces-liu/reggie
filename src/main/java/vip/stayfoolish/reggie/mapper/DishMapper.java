package vip.stayfoolish.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import vip.stayfoolish.reggie.entity.Dish;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
