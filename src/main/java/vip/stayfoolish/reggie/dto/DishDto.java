package vip.stayfoolish.reggie.dto;

import lombok.Data;
import vip.stayfoolish.reggie.entity.Dish;
import vip.stayfoolish.reggie.entity.DishFlavor;

import java.util.ArrayList;
import java.util.List;

/*
 * @Author LiuLiu
 * @Date 2022/5/23 8:49
 * @Description DishDto 继承于 Dish
 * @Since version-1.0
 */
@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
