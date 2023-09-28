package com.abing.service.impl;

import com.abing.mapper.SysJobLogMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.abing.model.domain.SysJobLog;
import com.abing.service.SysJobLogService;
import org.springframework.stereotype.Service;

/**
* @author 阿炳亿点点帅
* @description 针对表【sys_job_log(定时任务调度日志表)】的数据库操作Service实现
* @createDate 2023-09-23 12:54:07
*/
@Service
public class SysJobLogServiceImpl extends ServiceImpl<SysJobLogMapper, SysJobLog>
    implements SysJobLogService{

}




