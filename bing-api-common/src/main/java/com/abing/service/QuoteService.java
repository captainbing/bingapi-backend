package com.abing.service;

import com.abing.model.domain.Quote;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 阿炳亿点点帅
* @description 针对表【quotes(毒鸡汤)】的数据库操作Service
* @createDate 2023-07-22 17:16:22
*/
public interface QuoteService extends IService<Quote> {

    String play();

    List<Quote> listQuote();

}
