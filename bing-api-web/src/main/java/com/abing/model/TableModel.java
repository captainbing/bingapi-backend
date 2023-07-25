package com.abing.model;

import java.util.HashMap;
import java.util.List;

/**
 * @Author abing
 * @Date 2023/7/25 10:27
 * @Description
 */
public class TableModel {

    /**
     * select name,age from a join b on a.id=b.id where a.id = ? and b.id = ?
     * group by a.age having ? order by ?,? asc|desc limit 0 1;
     */
    private List<String> select;
    /**
     * ("from","{table1}"),("inner join","{table2 on a.id=b.id}"),("left join","{table3 on}")
     * {
     *     "from":{
     *         "tableName":"a",
     *         "on":null
     *     },
     *     "inner join":{
     *         "tableName":"b",
     *         "on":{
     *             "field":"a.id",
     *             "condition":"=",
     *             "target":"b.id"
     *         }
     *     }
     * }
     */
    private HashMap<String,List<JoinModel>> fromJoinMap;
    private List<ConditionModel> where;
    private List<String> group;
    private List<ConditionModel> having;
    private List<OrderModel> order;
    private String limit;
}
