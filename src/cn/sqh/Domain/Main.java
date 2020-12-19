package cn.sqh.Domain;

import cn.sqh.Covid19Utils.Covid19Util;
import cn.sqh.JDBCutils.JDBCutils;
import cn.sqh.Test.Test测试;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        Covid19Util.pushCountrytIntodb("China");
        Covid19Util.pushCountrytIntodb("US");
        Covid19Util.pushCountrytIntodb("United%20Kingdom");
        Covid19Util.pushCountrytIntodb("Japan");


        Scanner sc = new Scanner(System.in);

        JdbcTemplate template = new JdbcTemplate(JDBCutils.getDataSource());
        int choose = sc.nextInt();
        Test测试 test = new Test测试();//不想改类名了，凑合看吧
        switch (choose) {
            case 1://如果先输入1   就根据国家iso查询在数据库中的数据
                int iso = sc.nextInt();
                test.test3(iso);
                break;
            case 2://如果先输入2   就输出国家表的所有数据
                test.test2();
                break;
            case 3://如果先输入3   就根据输入的具体地区查询在数据库中的数据
                String area = sc.next();
                test.test1(area);
                break;
            case 4: //如果输入4   就往数据库加入新的国家
                String countryAdd = sc.next();
                Covid19Util.pushCountrytIntodb(countryAdd);
                break;
        }

    }
}
