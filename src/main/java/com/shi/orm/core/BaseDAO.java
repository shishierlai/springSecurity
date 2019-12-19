package com.shi.orm.core;


import com.shi.orm.adapter.ExcuteAdapter;
import com.shi.orm.core.condition.Condition;
import com.shi.orm.core.stereotype.ValueObject;
import com.shi.orm.exception.BaseOrmException;
import com.shi.orm.utils.CommonOrmUtils;
import com.shi.orm.utils.GenerateID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BaseDAO {

    @Autowired
    private ExcuteAdapter excuteAdapter;

    public BaseDAO() {
    }

    /*
    *  新增操作
    * */
    public String insertOrUpdateValueObject(ValueObject vo) throws BaseOrmException {

        if(vo.getState()==0){
            vo.setState(1);
        }
        StringBuilder sql;
        switch (vo.getState()){
            case 1://新增数据Id是否为空
                if(vo.getId()==null || vo.getId().trim().length()==0){
                    vo.setId(GenerateID.getInstance().generateID());
                }
                sql=new StringBuilder();
                Map<String,Object> values=new HashMap<>();

                CommonOrmUtils.generateInsertSql(sql,values,vo);
                int updates =1; //this.excuteAdapter.executeUpdate(sql.toString(), values);
                if (updates > 0) {
                    vo.setState(2);
                }
                break;
            case 2://state=2表示  已经存在这个数据，此操作应该是更新数据
                sql=new StringBuilder();
                Map<String, Object> values1 = new HashMap();
                CommonOrmUtils.generateUpdateSql(sql, values1,vo);
                int updates1 = this.excuteAdapter.executeUpdate(sql.toString(), values1);
                if (updates1 != 1) {
                    throw new BaseOrmException("更新失败：可能是该数据已过期！");
                }
                break;
            case 3:
                this.removeByID(vo.getClass(),vo.getId());
        }

        return vo.getId();
    }

    /*
    *  新增操作
    * */
    public String updateValueObject(ValueObject vo) throws BaseOrmException {

        return "";
    }

    /*
    *  根据Id移除信息，
    *  但在数据库保留信息，状态改变
    * */
    public <T extends ValueObject> int removeByID(Class<T> clz,String id) throws BaseOrmException{
        StringBuilder sql = new StringBuilder();

        Map<String, Object> values = new HashMap();
        CommonOrmUtils.generateRemoveSql(sql, values, clz,id);
        int updates = this.excuteAdapter.executeUpdate(sql.toString(), values);
        if (updates != 1) {
            throw new BaseOrmException("更新失败：可能是该数据已过期！");
        } else {
            return updates;
        }
    }

    /*
    *  根据Id删除，彻底从数据库删除
    * */
    public <T extends ValueObject> int deleteByID(Class<T> clz,String id) throws BaseOrmException{
        StringBuilder sql = new StringBuilder();
        Map<String, Object> values = new HashMap();
        CommonOrmUtils.generateDeleteSql(sql, values, id, clz);
        int updates = this.excuteAdapter.executeUpdate(sql.toString(), values);
        return updates;
    }

    /*
    *  根据Id查询信息
    * */
    public <T extends ValueObject> T queryValueByID(Class<T> clz,String id)throws BaseOrmException {

        Condition cond = new Condition();
        cond.eq("ID", id);
        List<T> list = this.queryValueByCond(clz, cond);
        if (list != null && list.size() > 1) {
            throw new BaseOrmException("预期返回条数不大于1，实际返回条数" + list.size());
        } else {
            return list != null && list.size() != 0 ? list.get(0) : null;
        }
    }

    /*
    *  根据条件查询
    * */
    public <T extends ValueObject> List<T> queryValueByCond(Class<T> clz,Condition cond)throws BaseOrmException{
        String tableName = CommonOrmUtils.getTableFromClz(clz);
        List<String> fieldList = CommonOrmUtils.getTableColumnFromClz(clz);
        StringBuilder sql = new StringBuilder();
        Map<String, Object> values = new HashMap();
        CommonOrmUtils.generateQuerySql(sql, values, fieldList, tableName, "a", cond);
        List<T> list = this.excuteAdapter.executeQuery(sql.toString(), values, clz);

        return list;
    }

}
