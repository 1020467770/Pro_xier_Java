/*
   建表理由：
   
   1.国家名字可以由iso完全依赖，单独拿出建iso_country表，实现第二范式
   
   2.地区表中的所有内容需要iso和province作为属性组就能实现完全依赖，所以把它们和其他数据分开，与这两个数据放在一起，消除非主属性对主码的部分函数依赖，实现第二范式
      
   3.国家表中的数据依赖于国家名，国家名又依赖于iso，所以把国家表中的数据单独拿出建表可以消除对iso的传递依赖，实现第三范式
   
*/
CREATE TABLE iso_country(
	iso INT PRIMARY KEY,-- iso作为主键
	country VARCHAR(20) -- 国家名字
);

DROP TABLE iso_country;

DELETE FROM iso_country WHERE iso=392;

SELECT * FROM iso_country;


CREATE TABLE district_concrete_info( -- 国家/地区具体信息
	iso_dis INT, -- 地区表对应iso
	province VARCHAR(30),-- 对应地区
	recovered INT, -- 地区恢复人数
	confirmed INT, -- 地区确诊人数
	updated VARCHAR(60), -- 数据更新日期
	latitude DOUBLE, -- 维度
	longitude DOUBLE, -- 经度
	deaths INT,-- 死亡人数
	CONSTRAINT dis_iso_fk FOREIGN KEY (iso_dis) REFERENCES iso_country(iso) ON UPDATE CASCADE -- 地区表对应iso与iso_country表的外键
);

TRUNCATE TABLE district_concrete_info;

SELECT * FROM district_concrete_info;

CREATE TABLE country_general( -- 国家的大概信息
	iso_gen INT, -- 国家表对应iso
	continent VARCHAR(20), -- 国家所处大洲
	capital_city VARCHAR(20), -- 国家搜狐度
	life_expectancy DOUBLE, -- 国家预期寿命
	abbreviation VARCHAR(10), -- 国家名称缩写
	confirmed INT, -- 全国确诊人数
	population INT,-- 全国总人口
	sq_km_area INT, -- 国家领土大小
	recovered INT, -- 全国康复人数
	elevation_in_meters VARCHAR(10), -- 国家海拔高度
	location VARCHAR(20), -- 国家地理位置
	deaths INT, -- 全国死亡人数
	CONSTRAINT gen_iso_fk FOREIGN KEY (iso_gen) REFERENCES iso_country(iso) ON UPDATE CASCADE -- 国家表对应iso与iso_country表的外键
);

SELECT * FROM country_general;

DROP TABLE country_general;
















