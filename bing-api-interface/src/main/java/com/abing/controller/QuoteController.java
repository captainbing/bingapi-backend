package com.abing.controller;

import com.abing.common.BaseResponse;
import com.abing.common.DeleteRequest;
import com.abing.common.ResultUtils;
import com.abing.model.domain.Quote;
import com.abing.dubbo.service.QuoteService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author abing
 * @Date 2023/7/24 18:59
 * @Description
 */
@RestController
@RequestMapping("/quote")
public class QuoteController {

    @Reference
    private QuoteService quoteService;

    @GetMapping("/say")
    public String say(){
        return quoteService.play();
    }
    @GetMapping("/hello")
    public String hello(){
        return "Hello Dubbo !!!";
    }
    @GetMapping("/get/{id}")
    public BaseResponse<Quote> getQuote(@PathVariable Integer id){
        return ResultUtils.success(quoteService.getById(id));
    }
    @GetMapping("/get")
    public BaseResponse<Quote> getQuoteById(Integer id){
        return ResultUtils.success(quoteService.getById(id));
    }

    @GetMapping("/list")
    public BaseResponse<List<Quote>> getQuote(){
        return ResultUtils.success(quoteService.listQuote());
    }

    @PostMapping("/save")
    public BaseResponse<Boolean> saveQuote(@RequestBody Quote quote){
        return ResultUtils.success(quoteService.save(quote));
    }

    @PostMapping("/update")
    public BaseResponse<Boolean> updateQuote(@RequestBody Quote quote){
        return ResultUtils.success(quoteService.updateById(quote));
    }
    @GetMapping("/delete/")
    public BaseResponse<Boolean> deleteQuote(DeleteRequest deleteRequest){
        return ResultUtils.success(quoteService.removeById(deleteRequest.getId()));
    }
}
