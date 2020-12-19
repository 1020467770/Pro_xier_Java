package cn.sqh.Test;

import cn.sqh.JDBCutils.JDBCutils;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

public class Test测试 {

    private JdbcTemplate template = new JdbcTemplate(JDBCutils.getDataSource());//不在test里面但是会提前运行

    @Test
    public void test1(){
        String sql = "SELECT * FROM district_concrete_info;";
        for (Map<String, Object> stringObjectMap : template.queryForList(sql)) {
            System.out.println(stringObjectMap);
        }
    }

    @Test
    public void test2(){
        String sql = "SELECT * FROM iso_country;";
        for (Map<String, Object> stringObjectMap : template.queryForList(sql)) {
            System.out.println(stringObjectMap);
        }
    }

    @Test
    public void test3(){
        String sql = "SELECT * FROM country_general;";
        for (Map<String, Object> stringObjectMap : template.queryForList(sql)) {
            System.out.println(stringObjectMap);
        }
    }

}
