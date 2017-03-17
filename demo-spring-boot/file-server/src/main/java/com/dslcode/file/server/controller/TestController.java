package com.dslcode.file.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dongsilin on 2017/3/1.
 */
@RestController
@RequestMapping("test")
public class TestController {

//    public static void main(String[] args){
//        for(int i=60;i>1;i--){
//            System.out.print("DROP PROCEDURE IF EXISTS pro_face_daily_count ;\n" +
//                    "CREATE PROCEDURE pro_face_daily_count() BEGIN\n" +
//                    "  DECLARE is_over BOOLEAN DEFAULT 0;-- 声明局部参数：是否查询完成，结束标志\n" +
//                    "  DECLARE c_id INT DEFAULT NULL;-- 声明局部参数：渠道id\n" +
//                    "  DECLARE daily_count INT DEFAULT NULL;-- 声明局部参数：当天访问量\n" +
//                    "  DECLARE age0_10_count INT DEFAULT NULL;\n" +
//                    "\tDECLARE age10_20_count INT DEFAULT NULL;\n" +
//                    "\tDECLARE age20_30_count INT DEFAULT NULL;\n" +
//                    "\tDECLARE age30_40_count INT DEFAULT NULL;\n" +
//                    "\tDECLARE age40_50_count INT DEFAULT NULL;\n" +
//                    "\tDECLARE gender_male_count INT DEFAULT NULL;\n" +
//                    "\tDECLARE gender_female_count INT DEFAULT NULL;\n" +
//                    "\n" +
//                    "  DECLARE today_str VARCHAR(10) DEFAULT DATE_FORMAT(NOW(), '%Y-%m-%d');\n" +
//                    "  DECLARE begin_time INT DEFAULT UNIX_TIMESTAMP(today_str) - 86400*"+i+";\n" +
//                    "  DECLARE end_time INT DEFAULT begin_time + 86399;\n" +
//                    "\n" +
//                    "  DECLARE channel_ids CURSOR FOR-- 声明游标：来自sql\n" +
//                    "    SELECT DISTINCT t0.channel_id FROM t_face t0 WHERE t0.create_time BETWEEN begin_time AND end_time;\n" +
//                    "  DECLARE CONTINUE HANDLER FOR NOT FOUND SET is_over = 1;-- 持续操作直到没有发现更多结果设置完成标志\n" +
//                    "\n" +
//                    "  OPEN channel_ids;-- 开启游标\n" +
//                    "  REPEAT-- 开始遍历\n" +
//                    "    FETCH channel_ids INTO c_id;-- 将游标指针移动到的数据交给channel_id\n" +
//                    "    IF NOT is_over AND c_id IS NOT NULL THEN-- 如果结束标志为0则操作\n" +
//                    "        SELECT COUNT(t0.face_id) FROM t_face t0 WHERE t0.channel_id=c_id AND t0.create_time BETWEEN begin_time AND end_time INTO daily_count;\n" +
//                    "        INSERT INTO t_face_day_count(channel_id, create_time, day_total) VALUES (c_id, FROM_UNIXTIME(end_time - 3600, '%Y-%m-%d %H:%i:%S'), daily_count);\n" +
//                    "\n" +
//                    "        IF (SELECT t0.channel_id FROM t_face_analysis t0 WHERE t0.channel_id=c_id) IS NULL THEN\n" +
//                    "          INSERT INTO t_face_analysis(channel_id) VALUES (c_id);\n" +
//                    "        END IF;\n" +
//                    "\n" +
//                    "        SELECT COUNT(t0.face_id) FROM t_face t0 WHERE t0.channel_id=c_id AND t0.create_time BETWEEN begin_time AND end_time AND t0.face_age<=10 INTO age0_10_count;\n" +
//                    "        SELECT COUNT(t0.face_id) FROM t_face t0 WHERE t0.channel_id=c_id AND t0.create_time BETWEEN begin_time AND end_time AND t0.face_age<=20 AND t0.face_age>10 INTO age10_20_count;\n" +
//                    "        SELECT COUNT(t0.face_id) FROM t_face t0 WHERE t0.channel_id=c_id AND t0.create_time BETWEEN begin_time AND end_time AND t0.face_age<=30 AND t0.face_age>20 INTO age20_30_count;\n" +
//                    "        SELECT COUNT(t0.face_id) FROM t_face t0 WHERE t0.channel_id=c_id AND t0.create_time BETWEEN begin_time AND end_time AND t0.face_age<=40 AND t0.face_age>30 INTO age30_40_count;\n" +
//                    "        SELECT COUNT(t0.face_id) FROM t_face t0 WHERE t0.channel_id=c_id AND t0.create_time BETWEEN begin_time AND end_time AND t0.face_age<=50 AND t0.face_age>40 INTO age40_50_count;\n" +
//                    "        SELECT COUNT(t0.face_id) FROM t_face t0 WHERE t0.channel_id=c_id AND t0.create_time BETWEEN begin_time AND end_time AND t0.face_gender='男' INTO gender_male_count;\n" +
//                    "        SELECT COUNT(t0.face_id) FROM t_face t0 WHERE t0.channel_id=c_id AND t0.create_time BETWEEN begin_time AND end_time AND t0.face_gender='女' INTO gender_female_count;\n" +
//                    "\n" +
//                    "        SET age0_10_count = age0_10_count + (SELECT t0.age0_10_total  FROM t_face_analysis t0 WHERE t0.channel_id=c_id);\n" +
//                    "        SET age10_20_count = age10_20_count + (SELECT t0.age10_20_total FROM t_face_analysis t0 WHERE t0.channel_id=c_id);\n" +
//                    "        SET age20_30_count = age20_30_count + (SELECT t0.age20_30_total FROM t_face_analysis t0 WHERE t0.channel_id=c_id);\n" +
//                    "        SET age30_40_count = age30_40_count + (SELECT t0.age30_40_total FROM t_face_analysis t0 WHERE t0.channel_id=c_id);\n" +
//                    "        SET age40_50_count = age40_50_count + (SELECT t0.age40_50_total FROM t_face_analysis t0 WHERE t0.channel_id=c_id);\n" +
//                    "        SET gender_male_count = gender_male_count + (SELECT t0.gender_male_total FROM t_face_analysis t0 WHERE t0.channel_id=c_id);\n" +
//                    "        SET gender_female_count = gender_female_count + (SELECT t0.gender_female_total FROM t_face_analysis t0 WHERE t0.channel_id=c_id);\n" +
//                    "        SET daily_count = daily_count + (SELECT t0.total FROM t_face_analysis t0 WHERE t0.channel_id=c_id);\n" +
//                    "\n" +
//                    "        UPDATE t_face_analysis SET\n" +
//                    "          age0_10_total =age0_10_count,\n" +
//                    "          age10_20_total=age10_20_count,\n" +
//                    "          age20_30_total=age20_30_count,\n" +
//                    "          age30_40_total=age30_40_count,\n" +
//                    "          age40_50_total=age40_50_count,\n" +
//                    "          gender_male_total=gender_male_count,\n" +
//                    "          gender_female_total=gender_female_count,\n" +
//                    "          total =daily_count\n" +
//                    "        WHERE channel_id=c_id;\n" +
//                    "      SET c_id = NULL ;\n" +
//                    "    END IF;\n" +
//                    "  UNTIL is_over END REPEAT;-- 结束标志为1退出遍历\n" +
//                    "  CLOSE channel_ids;-- 用完必须关闭游标\n" +
//                    "  END;\n" +
//                    "\n" +
//                            (i==60? "DELETE FROM t_face_analysis;DELETE FROM t_face_day_count;\n" : "" )+
//                    "CALL pro_face_daily_count();"+
//                    "\n" +
//                    "\n" +
//                    "\n" );
//        }
//
//    }

}
