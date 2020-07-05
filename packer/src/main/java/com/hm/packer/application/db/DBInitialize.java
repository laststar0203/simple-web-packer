package com.hm.packer.application.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@Configuration("DBInitialize")
public class DBInitialize {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void schemaInit(){
        try{
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(
                    "select count(*) from sqlite_master Where Name = 'PACKAGE'");
            rs.next();
            if(rs.getInt("count(*)") == 1) {
                rs.close();
                statement.close();
                connection.close();
                return;
            }

            boolean result = statement.execute(
                    "CREATE TABLE PACKAGE(" +
                            "    number INTEGER PRIMARY KEY," +
                            "    name VARCHAR," +
                            "    version VARCHAR," +
                            "    installed INTEGER" +
                            ")"
            );
            System.out.println(result);
            result = statement.execute(
                   "CREATE TABLE LICENSE_KEY(" +
                           "    license_key VARCHAR," +
                           "    engineer_key VARCHAR" +
                           ")"
            );
            System.out.println(result);

            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
