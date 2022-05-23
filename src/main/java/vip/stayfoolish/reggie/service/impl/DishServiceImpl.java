package vip.stayfoolish.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.stayfoolish.reggie.dto.DishDto;
import vip.stayfoolish.reggie.entity.Dish;
import vip.stayfoolish.reggie.entity.DishFlavor;
import vip.stayfoolish.reggie.mapper.DishMapper;
import vip.stayfoolish.reggie.service.DishFlavorService;
import vip.stayfoolish.reggie.service.DishService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {

        /*
        * 页面传递的菜品口味信息，仅仅包含name 和 value属性，缺少一个非常重要的属性dishId， 所以
            在保存完菜品的基本信息后，我们需要获取到菜品ID，然后为菜品口味对象属性dishId赋值。
            具体逻辑如下：
            ①. 保存菜品基本信息 ;
            ②. 获取保存的菜品ID ;
            ③. 获取菜品口味列表，遍历列表，为菜品口味对象属性dishId赋值;
            ④. 批量保存菜品口味列表;
        * */
        // 保存菜品信息到 菜品表 dish
        this.save(dishDto);

        // 获取菜品 id
        Long dishId = dishDto.getId();

        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        // 保存菜品口味数据到菜品口味 dish_flavors
        dishFlavorService.saveBatch(flavors);

    }

    @Override
    public DishDto getByIdWithFlavor(Long id) {

        // 查询菜品基本信息，从 dish 查询
        Dish dish = this.getById(id);

        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);

        // 查询当前菜品对应的口味信息，从 dish_flavor 表查询
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavors);


        return dishDto;
    }

    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
        // 更新 dish 表基本信息
        this.updateById(dishDto);

        // 清理当前菜品对应口味数据--- dish_flavor 表的 delete 操作
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(queryWrapper);

        // 添加当前提交过来的口味数据-- dish_flavor 表的insert操作
        List<DishFlavor> flavors = dishDto.getFlavors();

        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);
    }
}


















