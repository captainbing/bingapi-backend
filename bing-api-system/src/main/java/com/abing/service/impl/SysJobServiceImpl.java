package com.abing.service.impl;

import com.abing.mapper.SysJobMapper;
import com.abing.model.domain.SysJob;
import com.abing.service.SysJobService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author 阿炳亿点点帅
* @description 针对表【sys_job(定时任务调度表)】的数据库操作Service实现
* @createDate 2023-09-23 12:53:46
*/
@Service
public class SysJobServiceImpl extends ServiceImpl<SysJobMapper, SysJob>
    implements SysJobService{

}




