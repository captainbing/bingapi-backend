package com.abing.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.abing.common.ErrorCode;
import com.abing.exception.BusinessException;
import com.abing.mapper.InvokeRecordMapper;
import com.abing.model.domain.InvokeRecord;
import com.abing.model.enums.ResourceTypeEnum;
import com.abing.model.request.InvokeRecordRequest;
import com.abing.model.request.RequestField;
import com.abing.model.vo.InvokeMenuVO;
import com.abing.model.vo.InvokeRecordVO;
import com.abing.service.InvokeRecordService;
import com.abing.utils.ThrowUtils;
import com.abing.utils.TokenUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author 阿炳亿点点帅
* @description 针对表【invoke_interface(接口调用表（仿postman）)】的数据库操作Service实现
* @createDate 2023-10-07 22:17:39
*/
@Service
public class InvokeInterfaceServiceImpl extends ServiceImpl<InvokeRecordMapper, InvokeRecord>
    implements InvokeRecordService {

    @Resource
    private InvokeRecordMapper invokeRecordMapper;



    @Override
    public Boolean deleteMenu(String id) {
        ThrowUtils.throwIf(id == null,ErrorCode.PARAMS_ERROR);
        InvokeRecord invokeRecord = this.getById(id);
        ThrowUtils.throwIf(invokeRecord == null,ErrorCode.OPERATION_ERROR,"删除项不存在");
        if (invokeRecord.getType().equals(ResourceTypeEnum.FILE.getCode())){
            return this.removeById(id);
        }
        List<InvokeRecord> allMenuByUserId = this.list(new QueryWrapper<InvokeRecord>()
                .lambda()
                .eq(InvokeRecord::getUserId, TokenUtils.getUserId()));
        List<InvokeRecord> pidList = this.list(new QueryWrapper<InvokeRecord>()
                .lambda()
                .eq(InvokeRecord::getUserId, TokenUtils.getUserId())
                .eq(InvokeRecord::getParentId, invokeRecord.getId()));
        List<String> res = new ArrayList<>();
        res.add(invokeRecord.getId());
        getSonId(allMenuByUserId,pidList,res);
        return this.removeBatchByIds(res);
    }

    private static final String ROOT_CODE = "0";

    @Override
    public List<InvokeMenuVO>  selectMenu(String id) {

        ThrowUtils.throwIf(id == null,ErrorCode.PARAMS_ERROR);
        List<InvokeRecord> source = this.list(new QueryWrapper<InvokeRecord>()
                .lambda()
                .eq(InvokeRecord::getUserId,id)
                .eq(InvokeRecord::getType,ResourceTypeEnum.DIRECTORY.getCode()));
        List<InvokeMenuVO> selectMenuVOList = new ArrayList<>();
        // 筛选出只是目录的菜单
        selectMenuVOList.addAll(source.stream().map(item -> {
                    InvokeMenuVO invokeMenuVO = new InvokeMenuVO();
                    invokeMenuVO.setKey(item.getId());
                    invokeMenuVO.setTitle(item.getTitle());
                    invokeMenuVO.setValue(item.getId());
                    invokeMenuVO.setParentId(item.getParentId());
                    invokeMenuVO.setIsLeaf(ResourceTypeEnum.FILE.getCode().equals(item.getType()));
                    return invokeMenuVO;
                })
                .collect(Collectors.toList()));
        // 生成树形结构
        Map<String, List<InvokeMenuVO>> parentIdMap = selectMenuVOList.stream()
                .collect(Collectors.groupingBy(InvokeMenuVO::getParentId));
        selectMenuVOList.forEach(menu->{
            menu.setChildren(parentIdMap.get(menu.getKey()));
        });
        selectMenuVOList = selectMenuVOList.stream()
                .filter(menu-> ROOT_CODE.equals(menu.getParentId()))
                .collect(Collectors.toList());
        InvokeMenuVO rootMenu = new InvokeMenuVO();
        rootMenu.setTitle("根目录");
        rootMenu.setValue(ROOT_CODE);
        rootMenu.setKey(ROOT_CODE);
        rootMenu.setParentId(ROOT_CODE);
        selectMenuVOList.add(rootMenu);
        return selectMenuVOList;
    }


    @Override
    public List<InvokeMenuVO>  getInvokeMenuTree() {

        List<InvokeRecord> source = this.list(new QueryWrapper<InvokeRecord>()
                .lambda()
                .eq(InvokeRecord::getUserId,TokenUtils.getUserId()));
        ThrowUtils.throwIf(source == null,ErrorCode.OPERATION_ERROR);
        List<InvokeMenuVO> invokeMenuVOList = source.stream().map(item -> {
            InvokeMenuVO invokeMenuVO = new InvokeMenuVO();
            invokeMenuVO.setTitle(item.getTitle());
            invokeMenuVO.setKey(item.getId());
            invokeMenuVO.setParentId(item.getParentId());
            invokeMenuVO.setIsLeaf(ResourceTypeEnum.FILE.getCode().equals(item.getType()));
            return invokeMenuVO;
        }).collect(Collectors.toList());

        Map<String, List<InvokeMenuVO>> parentIdMap = invokeMenuVOList.stream()
                .collect(Collectors.groupingBy(InvokeMenuVO::getParentId));
        invokeMenuVOList.forEach(menu->{
            menu.setChildren(parentIdMap.get(menu.getKey()));
        });
        invokeMenuVOList = invokeMenuVOList.stream()
                .filter(menu->ROOT_CODE.equals(menu.getParentId()))
                .collect(Collectors.toList());
        return invokeMenuVOList;
    }

    @Override
    public Boolean addMenu(InvokeRecord invokeRecord) {
        ThrowUtils.throwIf(StringUtils.isAnyEmpty(invokeRecord.getTitle(),invokeRecord.getId()),ErrorCode.PARAMS_ERROR);
        InvokeRecord target = new InvokeRecord();
        target.setUserId(TokenUtils.getUserId());
        target.setTitle(invokeRecord.getTitle());
        target.setParentId(invokeRecord.getId());
        return this.save(target);
    }

    /**
     * @param invokeRecord
     * @return
     */
    @Override
    public Boolean editMenu(InvokeRecord invokeRecord) {
        return this.updateById(invokeRecord);
    }

    @Override
    public Boolean saveInvokeRecord(InvokeRecordRequest invokeRecordRequest) {
        if (StringUtils.isEmpty(invokeRecordRequest.getRequestUrl())){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // todo 参数验证
        InvokeRecord invokeRecord = new InvokeRecord();
        BeanUtils.copyProperties(invokeRecordRequest,invokeRecord);
        String requestParam = JSONUtil.toJsonStr(invokeRecordRequest.getRequestParam());
        String requestHeader = JSONUtil.toJsonStr(invokeRecordRequest.getRequestHeader());
        String responseHeader = JSONUtil.toJsonStr(invokeRecordRequest.getResponseHeader());
        invokeRecord.setParentId(invokeRecordRequest.getParentId());
        invokeRecord.setUserId(TokenUtils.getUserId());
        invokeRecord.setType(ResourceTypeEnum.FILE.getCode());
        invokeRecord.setRequestParam(requestParam);
        invokeRecord.setRequestHeader(requestHeader);
        invokeRecord.setResponseHeader(responseHeader);
        invokeRecord.setCreateTime(new Date());
        invokeRecord.setCreateBy(TokenUtils.getUserName());
        invokeRecord.setUpdateBy(TokenUtils.getUserName());
        invokeRecord.setUpdateTime(new Date());
        invokeRecord.setTitle(invokeRecordRequest.getTitle());
        return this.save(invokeRecord);
    }


    /**
     * @param id
     * @return
     */
    @Override
    public InvokeRecordVO getInvokeRecordById(String id) {
        ThrowUtils.throwIf(StringUtils.isEmpty(id),ErrorCode.PARAMS_ERROR);
        InvokeRecord sourceInvokeRecord = this.getById(id);
        ThrowUtils.throwIf(sourceInvokeRecord == null,ErrorCode.OPERATION_ERROR,"数据不存在");
        InvokeRecordVO invokeRecordVO = new InvokeRecordVO();
        BeanUtils.copyProperties(sourceInvokeRecord,invokeRecordVO);

        List<RequestField> requestParam = JSONUtil.toList(sourceInvokeRecord.getRequestParam(), RequestField.class);
        List<RequestField> requestHeader = JSONUtil.toList(sourceInvokeRecord.getRequestHeader(), RequestField.class);
        List<RequestField> responseHeader = JSONUtil.toList(sourceInvokeRecord.getResponseHeader(), RequestField.class);
        invokeRecordVO.setRequestParam(requestParam);
        invokeRecordVO.setRequestHeader(requestHeader);
        invokeRecordVO.setResponseHeader(responseHeader);
        return invokeRecordVO;
    }

    /**
     * 获取当前目录的所有子id
     * @param allMenuByUserId
     * @param pidList
     * @param res
     */
    private void getSonId(List<InvokeRecord> allMenuByUserId,List<InvokeRecord> pidList,List<String> res){
        for (int i = 0; i < pidList.size(); i++) {
            InvokeRecord current = pidList.get(i);
            res.add(current.getId());
            List<InvokeRecord> sonList = allMenuByUserId.stream().filter(menu -> menu.getParentId().equals(current.getId()))
                    .collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(sonList)){
                getSonId(allMenuByUserId,sonList,res);
            }
        }
    }

}




