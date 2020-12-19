package cn.sqh.Test;

import cn.sqh.JDBCutils.JDBCutils;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

public class Test测试 {

    private JdbcTemplate template = new JdbcTemplate(JDBCutils.getDataSource());//不在test里面但是会提前运行

    @Test
    public void test1(String area){
        String sql = "SELECT * FROM district_concrete_info WHERE province = ?;";
        for (Map<String, Object> stringObjectMap : template.queryForList(sql,area)) {
            System.out.println(stringObjectMap);
        }
    }

    @Test
    public void test2(){
        String sql = "SELECT * FROM country_general;";
        for (Map<String, Object> stringObjectMap : template.queryForList(sql)) {
            System.out.println(stringObjectMap);
        }
    }

    @Test
    public void test3(int iso){
        String sql = "SELECT * FROM country_general WHERE iso_gen = ?;";
        for (Map<String, Object> stringObjectMap : template.queryForList(sql,iso)) {
            System.out.println(stringObjectMap);
        }
    }

}
