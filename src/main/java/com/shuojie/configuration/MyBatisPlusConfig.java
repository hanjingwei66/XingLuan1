package com.shuojie.configuration;
 
import com.baomidou.mybatisplus.core.parser.ISqlParser;

import com.baomidou.mybatisplus.extension.parsers.DynamicTableNameParser;
import com.baomidou.mybatisplus.extension.parsers.ITableNameHandler;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.*;
 
/**
 *
 * @Date: 2019/9/12 15:06
 * @Description: MybatisPlus配置类
 */
@Configuration
public class MyBatisPlusConfig {

 
    /**
     * 分页插件
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
 
        // 创建SQL解析器集合
        List<ISqlParser> sqlParserList = new ArrayList<>();
 
        // 动态表名SQL解析器
        DynamicTableNameParser dynamicTableNameParser = new DynamicTableNameParser();
//        Map<String,ITableNameHandler> tableNameHandlerMap = new HashMap<>();
//        // Map的key就是需要替换的原始表名
//        tableNameHandlerMap.put("sensor_title",new ITableNameHandler(){
//            @Override
//            public String dynamicTableName(MetaObject metaObject, String sql, String tableName) {
//                // 自定义表名规则，或者从配置文件、request上下文中读取
//
//                // 假设这里的用户表根据年份来进行分表操作
//                Date date = new Date();
//                SimpleDateFormat timeStamp = new SimpleDateFormat("yyyyMM");
//                String year = timeStamp.format(date);
//                // 返回最后需要操作的表名，sys_user_2019
//                return "sensor_" + year;
//            }
//        });
//        dynamicTableNameParser.setTableNameHandlerMap(tableNameHandlerMap);
//        sqlParserList.add(dynamicTableNameParser);
//        paginationInterceptor.setSqlParserList(sqlParserList);
 
 
        return paginationInterceptor;
    }
 
}