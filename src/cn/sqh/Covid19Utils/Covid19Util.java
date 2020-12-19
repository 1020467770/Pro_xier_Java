package cn.sqh.Covid19Utils;

import cn.sqh.JDBCutils.JDBCutils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class Covid19Util {

    static private String load(String url, String query) throws Exception {

        URL restURL = new URL(url);

        HttpURLConnection conn = (HttpURLConnection) restURL.openConnection();

        conn.setRequestMethod("GET");

        conn.setDoOutput(true);

        conn.setAllowUserInteraction(false);

        BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String line, resultStr = "";

        while (null != (line = bReader.readLine())) {
            resultStr += line;
        }

        bReader.close();

        return resultStr;

    }

    static public void pushCountrytIntodb(String country) {

        String url = "https://covid-api.mmediagroup.fr/v1/cases?country=" + country;

        try {

            String resultString = load(url, "");

            JSONObject jsonObject = JSON.parseObject(resultString);

            JdbcTemplate template = new JdbcTemplate(JDBCutils.getDataSource());

            String[] gennerl = new String[15];
            String[] concrete = new String[8];

            boolean isFirst = false;

            int iso = 0;

            for (Map.Entry<String, Object> stringObjectEntry : jsonObject.entrySet()) {

                String key = stringObjectEntry.getKey();

                JSONObject value = (JSONObject) stringObjectEntry.getValue();

                if (key == "All") {
                    isFirst = true;
                }

                JSONObject insideJson = value;

                int i = 0;

                for (Map.Entry<String, Object> insideEntry : insideJson.entrySet()) {

                    Object insideValue = insideEntry.getValue();

                    if (isFirst) {

                        gennerl[i++] = insideValue.toString();
                    } else {

                        concrete[i++] = insideValue.toString();
                    }

                }

                if (isFirst) {
                    //genneral=Asia China 156 Peking 71.4 CN 94821 1409517397 9572900 88538 1,840 Eastern Asia 4763
                    iso = Integer.parseInt(gennerl[2]);

                    String sql = "insert into iso_country(iso,country) values(?,?)";

                    template.update(sql, iso, gennerl[1]);

                    sql = "insert into country_general(iso_gen,continent,capital_city,life_expectancy,abbreviation,confirmed,population,sq_km_area,recovered,elevation_in_meters,location,deaths) " +
                            "values(?,?,?,?,?,?,?,?,?,?,?,?)";

                    template.update(sql,
                            iso,//iso
                            gennerl[0],//大洲
                            gennerl[3],//首都
                            Double.parseDouble(gennerl[4]),//预期寿命
                            gennerl[5],//缩写
                            Integer.parseInt(gennerl[6]),//确诊数
                            Integer.parseInt(gennerl[7]),//人口
                            Integer.parseInt(gennerl[8]),//领土
                            Integer.parseInt(gennerl[9]),//恢复数
                            gennerl[10],//1,840用的是String存的 海拔
                            gennerl[11],//位置
                            Integer.parseInt(gennerl[12])//死亡数
                    );
                    isFirst = false;
                } else {

                    //key=Gansu    concrete=180 182 2020/12/19 10:27:16+00 35.7518 104.2861 2

                    String sql = "insert into district_concrete_info(iso_dis,province,recovered,confirmed,updated,latitude,longitude,deaths) " +
                            "values(?,?,?,?,?,?,?,?)";

                    template.update(sql,
                            iso,//iso_dis
                            key,//地区名——省份
                            Integer.parseInt(concrete[0]),//恢复数
                            Integer.parseInt(concrete[1]),//确诊数
                            concrete[2],//数据更新的日期
                            "".equals(concrete[3])? null : Double.parseDouble(concrete[3]),//维度
                            "".equals(concrete[4])? null : Double.parseDouble(concrete[4]),//经度
                            Integer.parseInt(concrete[5])//死亡人数
                    );

                }

            }

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

}
