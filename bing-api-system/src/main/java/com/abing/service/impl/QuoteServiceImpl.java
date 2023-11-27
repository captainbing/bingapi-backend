package com.abing.service.impl;

import com.abing.service.QuoteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.abing.model.domain.Quote;
import com.abing.mapper.QuotesMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 阿炳亿点点帅
* @description 针对表【quotes(毒鸡汤)】的数据库操作Service实现
* @createDate 2023-07-22 17:16:22
*/
@Service
public class QuoteServiceImpl extends ServiceImpl<QuotesMapper, Quote>
    implements QuoteService {
    /**
     * @return
     */
    @Override
    public String play() {
        System.out.println("=========================请求进来==============");
        return "Hello Dubbo Service !!!";
    }

    @Override
    public List<Quote> listQuote() {
        return list();
    }
}




