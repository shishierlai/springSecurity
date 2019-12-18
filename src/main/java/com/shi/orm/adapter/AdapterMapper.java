package com.shi.orm.adapter;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface AdapterMapper {


    @Select({"$sql"})
    List executeQuery(Map var);

    @Update({"${sql}"})
    int executeUpdate(Map var);


}
