package vip.stayfoolish.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.stayfoolish.reggie.entity.Setmeal;
import vip.stayfoolish.reggie.mapper.SetmealMapper;
import vip.stayfoolish.reggie.service.SetmealService;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
}
