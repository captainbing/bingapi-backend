package com.abing;

import com.abing.model.domain.Quote;
import com.abing.service.QuoteService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;

/**
 * @Author abing
 * @Date 2023/7/25 15:31
 * @Description
 */
@SpringBootTest
public class QuoteTest {

    @Resource
    private QuoteService quoteService;

    @Test
    public void testSaveQuote() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("quote.txt"));
        ArrayList<Quote> quoteList = new ArrayList<>();
        while (bufferedReader.ready()){
            String content = bufferedReader.readLine();
            quoteList.add(new Quote(content));
            System.out.println(content);
        }
        quoteService.saveBatch(quoteList);
    }

}
