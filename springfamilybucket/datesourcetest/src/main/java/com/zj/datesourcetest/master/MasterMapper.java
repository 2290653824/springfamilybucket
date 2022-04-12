package com.zj.datesourcetest.master;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MasterMapper {

    String getSerialById(int id);
}
