package com.abing.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author CaptainBing
 * @Date 2023/12/5 16:11
 * @Description
 */
public class ExcelUtils {

    private ExcelUtils(){}

    /**
     *
     * @return
     */
    public static String excelToCsv(MultipartFile multipartFile){

//        File file = null;
//        try {
//            file = ResourceUtils.getFile("classpath:data.xlsx");
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }

        List<Map<Integer,String>> list = null;
        try {
            list = EasyExcel.read(multipartFile.getInputStream())
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet()
                    .headRowNumber(0)
                    .doReadSync();
        } catch (IOException e) {
            return "";
        }

        StringBuilder dataBuilder = new StringBuilder();
        LinkedHashMap<Integer, String> headMap = (LinkedHashMap)list.get(0);
        List<String> headList = headMap.values().stream().filter(StringUtils::isNotEmpty).collect(Collectors.toList());

        String headCsv = StringUtils.join(headList, ",");
        dataBuilder.append(headCsv).append("\n");

        for (int i = 1; i < list.size(); i++) {
            LinkedHashMap<Integer,String> dataMap = (LinkedHashMap) list.get(i);
            List<String> dataList = dataMap.values().stream().filter(StringUtils::isNotEmpty).collect(Collectors.toList());
            dataBuilder.append(StringUtils.join(dataList,",")).append("\n");
        }

        return dataBuilder.toString();
    }

    public static void main(String[] args) {

        String answer = "【【【【【\n" +
                "{\n" +
                "    xAxis: {\n" +
                "        type: 'category',\n" +
                "        data: ['1号', '2号', '3号']\n" +
                "    },\n" +
                "    yAxis: {\n" +
                "        type: 'value'\n" +
                "    },\n" +
                "    series: [{\n" +
                "        data: [10, 20, 30],\n" +
                "        type: 'line'\n" +
                "    }]\n" +
                "}\n" +
                "【【【【【\n" +
                "根据提供的原始数据可以看出，用户的增长情况如下：\n" +
                "- 1号：10个用户\n" +
                "- 2号：20个用户\n" +
                "- 3号：30个用户\n" +
                "\n" +
                "从数据的变化趋势来看，用户数量呈现了逐渐增长的态势。用户数量从1号到2号增加了10个，从2号到3号增加了10个。因此，可以认为用户数量每天增加10个。";
        String[] split = answer.split("【【【【【");
        System.out.println(split);

//        excelToCsv(null);
    }

}
