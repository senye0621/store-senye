package com.senye.storesenye;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
public class StoreSenyeApplicationTests {
    @Autowired
    private DataSource dataSource;

    @Test
    public void getConnection() throws Exception {
        System.out.println(dataSource.getConnection());
    }



}
