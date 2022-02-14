package top.pcat.study.Utils;

import android.content.Context;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import java.io.File;

public class DatabaseHelper extends SQLiteOpenHelper {

    //类没有实例化,是不能用作父类构造器的参数,必须声明为静态

    private static final String name = "study"; //数据库名称

    private static final int version = 1; //数据库版本

    public DatabaseHelper(Context context) {

        //第三个参数CursorFactory指定在执行查询时获得一个游标实例的工厂类,设置为null,代表使用系统默认的工厂类

        super(context, name, null, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(new StringBuilder()
                .append("DROP TABLE IF EXISTS `t_user_info`;\n")
                .append(" CREATE TABLE `t_user_info`  (")
                .append("  `id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '用户id',\n").append("  `name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',\n")
                .append("  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',\n")
                .append("  `sex` int UNSIGNED NULL DEFAULT 0 COMMENT '性别',\n")
                .append("  `birthday` datetime(0) NULL DEFAULT NULL COMMENT '生日',\n")
                .append("  `city` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '城市',\n")
                .append("  `school` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学校',\n")
                .append("  `college` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学院',\n")
                .append("  `major` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '专业',\n")
                .append("  `grade` int NULL DEFAULT 0 COMMENT '年级',\n")
                .append("  `position` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '学生' COMMENT '职位',\n").append("  `del_flag` int NULL DEFAULT 0 COMMENT '删除',\n")
                .append("  `pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '头像',\n")
                .append("  `registration_time` datetime(0) NULL DEFAULT NULL COMMENT '注册时间',\n")
                .append("  `text` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL\n")
                .append(") ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息' ROW_FORMAT = Dynamic;\n").toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("ALTER TABLE person ADD phone VARCHAR(12)");

    }
}
