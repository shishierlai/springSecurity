package com.shi.orm.utils;



import com.shi.orm.core.condition.CompareBeanImpl;
import com.shi.orm.core.condition.Condition;
import com.shi.orm.core.condition.ICompareBean;
import com.shi.orm.core.stereotype.Column;
import com.shi.orm.core.stereotype.Table;
import com.shi.orm.core.stereotype.ValueObject;
import com.shi.orm.exception.BaseOrmException;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

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

    //获取带注解的属性名
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
    *  1.generateInsertSql 生成插入数据语句
    *  2.generateUpdateSql 生成更新数据语句
    *  3.generateRemoveSql 生成移除数据语句
    *  4.generateDeleteSql 生成删除数据语句
    *
    *  5.generateQuerySql 生成查询数据语句
    *
    * */
    //5.generateQuerySql 生成查询数据语句

    //1.generateInsertSql 生成插入数据语句
    public static <T extends ValueObject> void generateInsertSql(StringBuilder sql, Map<String, Object> values, T vo) {

        String tableName=getTableFromClz(vo.getClass());

        List<String> fieldList=getTableColumnFromClz(vo.getClass());

        String fieldStr="";
        String paramStr="";
        Integer currParamIdx=Integer.valueOf(1);
        for(int i = 0; i < fieldList.size(); ++i) {
            String field = fieldList.get(i);
            if (!field.equals("ts")) {
                if (fieldStr.length() > 0) {
                    fieldStr = fieldStr + ",";
                }

                fieldStr = fieldStr + field;
                if (paramStr.length() > 0) {
                    paramStr = paramStr + ",";
                }

                StringBuilder var10000 = (new StringBuilder()).append("p");
                Integer var11 = currParamIdx;
                currParamIdx = currParamIdx.intValue() + 1;
                String param = var10000.append(var11).toString();
                paramStr = paramStr + "#{" + param + "}";
                Object value = getBeanValueToDB(vo, field);
                values.put(param, value);
            }
        }

        String ts = getTimeStamp();
        vo.setTs(ts);
        vo.setCreate_ts(ts);
        fieldStr = fieldStr + ",ts,create_ts";
        paramStr = paramStr + ",#{ts},#{ts}";
        values.put("ts", ts);
        sql.append(" insert into ").append(tableName);
        sql.append(" (").append(fieldStr).append(")");
        sql.append(" values ");
        sql.append(" (").append(paramStr).append(")");


    }

    //2.generateUpdateSql 生成更新数据语句
    public static <T extends ValueObject> void generateUpdateSql(StringBuilder sql, Map<String, Object> values, T vo) {
        String tableName = getTableFromClz(vo.getClass());
        List<String> fieldList = getTableColumnFromClz(vo.getClass());
        String fieldStr = "";
        Integer currPrarmIdx = Integer.valueOf(1);

        for(int i = 0; i < fieldList.size(); ++i) {
            String field = (String)fieldList.get(i);
            if (!field.toLowerCase().equals("ts")) {
                if (fieldStr.length() > 0) {
                    fieldStr = fieldStr + ",";
                }

                StringBuilder var10000 = (new StringBuilder()).append("p");
                Integer var11 = currPrarmIdx;
                currPrarmIdx = currPrarmIdx.intValue() + 1;
                String param = var10000.append(var11).toString();
                fieldStr = fieldStr + "" + field + "=#{" + param + "}";
                Object value = getBeanValueToDB(vo, field);
                values.put(param, value);
            }
        }
        // update User a  set id=#{p1},name=#{p2},node_id=#{p3},parent_id=#{p4},ts=#{ts} where 1 = 1
       //  update fps_staff a  set name=#{p1},id=#{p2},code=#{p3},phone=#{p4},email=#{p5},ts=#{ts} where 1 = 1  and a.ID= #{p7}
        fieldStr = fieldStr + ",ts=#{ts}";
        String ts = getTimeStamp();
        values.put("ts", ts);
        vo.setTs(ts);
        sql.append(" update ").append(tableName).append(" a ");
        sql.append(" set ").append(fieldStr);
        //直接获取更新的ID属于 危险行为 后期将
        //更新此行为
        StringBuilder whereStr = new StringBuilder();
        whereStr.append(" and a.ID=\'").append(vo.getId()+"\'");
        //values.put()

        sql.append(" where 1 = 1").append(whereStr);

    }


    //3.generateRemoveSql 生成移除数据语句
    public static <T extends ValueObject> void generateRemoveSql(StringBuilder sql, Map<String, Object> values,  Class<T> clz,String id) {
        String tableName = getTableFromClz(clz);
        String fieldStr = "dr=1,ts=#{ts}";
        values.put("ts", getTimeStamp());
        sql.append(" update ").append(tableName).append(" a ");
        sql.append(" set ").append(fieldStr);

        StringBuilder whereStr = new StringBuilder();
        whereStr.append(" and a.ID=\'").append(id+"\'");
        sql.append(" where 1 = 1 ").append(whereStr.toString());


    }


    //4.generateDeleteSql 生成删除数据语句
    public static <T extends ValueObject> void generateDeleteSql(StringBuilder sql, Map<String, Object> values, String id, Class<T> clz) {
        String tableName = getTableFromClz(clz);
        sql.append(" delete from ").append(tableName).append(" a ");
        StringBuilder whereStr = new StringBuilder();

        whereStr.append(" and a.ID=\'").append(id+"\'");
        sql.append(" where 1 = 1 ").append(whereStr.toString());

    }
    //5.generateQuerySql 生成查询数据语句
    public static <T extends ValueObject> void generateQuerySql(StringBuilder sql, Map<String, Object> values, List<String> fields, String table, String tableAlias, Condition cond) {
        sql.append(" select ");
        for(int i = 0; i < fields.size(); ++i) {
            if (i > 0) {
                sql.append(",");
            }
            sql.append(tableAlias + "." + (String)fields.get(i));
        }

        sql.append(" from ").append(table).append(" ").append(tableAlias);
        Iterator var10;
        sql.append(" where 1=1 ");
        //如果有Condition条件查找
        if (cond.getCompares() != null) {
            var10 = cond.getCompares().iterator();

            while(var10.hasNext()) {
                ICompareBean item = (ICompareBean)var10.next();
                processCondition(tableAlias, item, sql, values);
            }

            sql.append(" and coalesce(" + tableAlias + ".dr,0)=0");
        }



    }
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


    private static void processCondition(String tableAlias, ICompareBean item, StringBuilder sb, Map<String, Object> values) {
        Integer paramIdx = values.size() + 1;
        if (item instanceof CompareBeanImpl) {
            CompareBeanImpl bean = (CompareBeanImpl)item;
            String field = tableAlias + "." + bean.getField();
            switch(bean.getType()) {
                case EQ:
                    sb.append(" and ").append(field).append("= #{p" + paramIdx + "}");
                    values.put("p" + paramIdx, bean.getValue());
                    break;
                default:
                    throw new BaseOrmException("错误的ConditionCompareBean类型");
            }
        }
    }
}
