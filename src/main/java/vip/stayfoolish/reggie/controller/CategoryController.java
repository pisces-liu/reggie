package vip.stayfoolish.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.stayfoolish.reggie.common.R;
import vip.stayfoolish.reggie.entity.Category;
import vip.stayfoolish.reggie.service.CategoryService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    /*
     * @Author LiuLiu
     * @Date 2022/5/6 19:18
     * @Description 新建分类
     * @Param
     * @Return
     * @Since version-1.0
     */
    @PostMapping
    public R<String> save(@RequestBody Category category) {

        log.info("category:{}", category);
        categoryService.save(category);

        return R.success("新增分类成功！");
    }

    /*
     * @Author LiuLiu
     * @Date 2022/5/7 8:13
     * @Description 分页查询分类信息
     * @Param
     * @Return
     * @Since version-1.0
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize) {
        log.info("page = {}, pageSize = {}", page, pageSize);

        Page<Category> pageInfo = new Page<Category>(page, pageSize);

        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);

        categoryService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

    /*
     * @Author LiuLiu
     * @Date 2022/5/7 8:23
     * @Description 根据 id 删除分类
     * @Param
     * @Return
     * @Since version-1.0
     */
    @DeleteMapping
    public R<String> delete(Long id) {
        log.info("删除分类，id 为：{}", id);

        categoryService.remove(id);

        return R.success("分类信息删除成功");

    }

    /*
     * @Author LiuLiu
     * @Date 2022/5/9 18:04
     * @Description 实现更新分类
     * @Param
     * @Return
     * @Since version-1.0
     */
    @PutMapping
    public R<String> update(@RequestBody Category category) {
        log.info("修改分类信息：{}", category);

        categoryService.updateById(category);

        return R.success("修改分类信息成功！");
    }


    /*
     * @Author LiuLiu
     * @Date 2022/5/18 15:11
     * @Description 当进入菜品管理页面，点击新增菜品信息的时候，回显分类列表
     * @Param
     * @Return
     * @Since version-1.0
     */
    @GetMapping("/list")
    public R<List<Category>> list(Category category) {
        // 条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        // 添加条件，1.类型不能为空
        queryWrapper.eq(category.getType() != null, Category::getType, category.getType());
        // 添加排序条件 通过分类类型升序排序，通过更新时间降序排序
        queryWrapper.orderByAsc(Category::getType).orderByDesc(Category::getUpdateTime);

        List<Category> list = categoryService.list(queryWrapper);

        return R.success(list);
    }


}
