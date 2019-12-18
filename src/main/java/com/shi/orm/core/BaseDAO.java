package com.shi.orm.core;


import com.shi.orm.adapter.ExcuteAdapter;
import com.shi.orm.core.stereotype.ValueObject;
import com.shi.orm.exception.BaseOrmException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BaseDAO {

    @Autowired
    private ExcuteAdapter excuteAdapter;

    public BaseDAO() {
    }

    /*
    *  新增操作
    * */
    public String insertValueObject(ValueObject vo) throws BaseOrmException {

        return "";
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
        return 1;
    }

    /*
    *  根据Id删除，彻底从数据库删除
    * */
    public <T extends ValueObject> int deleteByID(Class<T> clz,String id) throws BaseOrmException{
        return 1;
    }

    /*
    *  根据Id查询信息
    * */
    public <T extends ValueObject> T queryValueByID(Class<T> clz,String id)throws BaseOrmException {
        List<T> result =new ArrayList<>();
        return result != null && result.size() > 0 ? result.get(0) : null;
    }

    /*
    *  根据条件删除
    * */
    public <T extends ValueObject> List<T> queryValueByCond(Class<T> clz,Condition cond)throws BaseOrmException{


        return  new ArrayList<>();
    }

}
