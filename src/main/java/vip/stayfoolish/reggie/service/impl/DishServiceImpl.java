package vip.stayfoolish.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.stayfoolish.reggie.entity.Dish;
import vip.stayfoolish.reggie.mapper.DishMapper;
import vip.stayfoolish.reggie.service.DishService;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
}
