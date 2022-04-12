package com.zj.datesourcetest;

import com.zj.datesourcetest.master.MasterMapper;
import com.zj.datesourcetest.second.SecondMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class DatesourcetestApplicationTests {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MasterMapper masterMapper;

    @Autowired
    private SecondMapper secondMapper;

    @Test
    void contextLoads() throws SQLException {

        System.out.println(dataSource);
        System.out.println(dataSource.getConnection());
    }

    @Test
    public void MasterTest(){
        String serialById = masterMapper.getSerialById(1);
        System.out.println(serialById);
    }

    @Test
    public void SecondTest(){
        String nameById = secondMapper.getNameById(23);
        System.out.println(nameById);
    }

}
