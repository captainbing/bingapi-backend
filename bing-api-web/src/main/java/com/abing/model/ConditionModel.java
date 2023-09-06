package com.abing.model;

/**
 * @Author abing
 * @Date 2023/7/25 10:56
 * @Description
 */
public class ConditionModel {
    private String field;
    /**
     * = , <> , <= , >= , != , in , not in
     */
    private String condition;
    private String target;
    /**
     * and , or
     */
    private String nextCondition;
}
