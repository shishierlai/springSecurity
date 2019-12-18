package com.shi.orm.utils;



import com.shi.orm.core.stereotype.Column;
import com.shi.orm.core.stereotype.Table;
import com.shi.orm.core.stereotype.ValueObject;
import com.shi.orm.exception.BaseOrmException;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommonOrmUtils {

    public CommonOrmUtils() {
    }
    private static SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    //获取注解表名
    public static <T extends ValueObject> String getTableFromClz(Class<T> clz) {
        Table table = (Table)clz.getAnnotation(Table.class);
        if (table != null) {
            return table.name();
        } else {
            throw new BaseOrmException("该类没有实现Table注解" + clz.getName());
        }
    }


    public static <T extends ValueObject> List<String> getTableColumnFromClz(Class<T> clz) {
        List<String> columnList = new ArrayList();
        Field[] fields = clz.getDeclaredFields();
        Field[] var4 = fields;
        int var5 = fields.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Field f = var4[var6];
            Column col = (Column)f.getAnnotation(Column.class);
            String column = col.name().toLowerCase();
            if (!column.equals("ts")) {
                columnList.add(column);
            }
        }

        columnList.add("ts");
        return columnList;
    }


    /*
    * ===生成数据库语句====
    *  1.generateQuerySql 生成查询数据语句
    *  2.generateUpdateSql 生成更新数据语句
    *  3.generateDeleteSql 生成删除数据语句
    *  4.generateRemoveSql 生成移除数据语句
    *  5.generateInsertSql 生成插入数据语句
    *
    * */


    private static Object getBeanValueToDB(ValueObject vo, String field) throws BaseOrmException {
        try {
            Object value = null;
            PropertyDescriptor pd = new PropertyDescriptor(field, vo.getClass());
            Method getter = pd.getReadMethod();
            if (getter != null) {
                value = getter.invoke(vo);
            }

            return value;
        } catch (Exception var5) {
            throw new BaseOrmException("Bean 取值错误，runtime级别");
        }
    }
    private static String getTimeStamp() {
        return timeStampFormat.format(new Date());
    }

}
