package vip.stayfoolish.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.stayfoolish.reggie.entity.DishFlavor;
import vip.stayfoolish.reggie.mapper.DishFlavorMapper;
import vip.stayfoolish.reggie.service.DishFlavorService;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
