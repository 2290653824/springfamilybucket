package com.zj.datesourcetest.second;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SecondMapper {

    String getNameById(@Param("id") int id);
}
