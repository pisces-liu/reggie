package vip.stayfoolish.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.stayfoolish.reggie.dto.DishDto;
import vip.stayfoolish.reggie.entity.Dish;

public interface DishService extends IService<Dish> {

    // 新增菜品。同时操作两张表。同时操作 Dish 和 DishFlavor
    public void saveWithFlavor(DishDto dishDto);

    // 根据 id 查询菜品信息和对应的口味信息
    public DishDto getByIdWithFlavor(Long id);

    // 更新菜品信息，同时更新对应的口味信息
    public void updateWithFlavor(DishDto dishDto);
}
