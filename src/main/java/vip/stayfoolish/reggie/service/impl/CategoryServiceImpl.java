package vip.stayfoolish.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vip.stayfoolish.reggie.common.CustomException;
import vip.stayfoolish.reggie.entity.Category;
import vip.stayfoolish.reggie.entity.Dish;
import vip.stayfoolish.reggie.entity.Setmeal;
import vip.stayfoolish.reggie.mapper.CategoryMapper;
import vip.stayfoolish.reggie.service.CategoryService;
import vip.stayfoolish.reggie.service.DishService;
import vip.stayfoolish.reggie.service.SetmealService;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /*
     * @Author LiuLiu
     * @Date 2022/5/7 8:35
     * @Description 根据 id 删除分类，删除之前需要进行判断
     * @Param
     * @Return
     * @Since version-1.0
     */
    @Override
    public void remove(Long id) {

        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 添加查询条件，根据分类 id 进行查询
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count1 = dishService.count(dishLambdaQueryWrapper);

        // 查询当前分类是否关联了菜品，如果已经关联，抛出一个业务异常
        if (count1 > 0) {
            // 已经关联菜品，则抛出异常
            throw new CustomException("当前分类下关联了菜品，不能删除！");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getId, id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);

        if (count2 > 0) {
            throw new CustomException("当前分类下关联了套餐，不能删除！");
        }

        // 正常删除分类
        super.removeById(id);

    }
}
