package mao.android_contentresolver.dao;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Class(类名): StudentContent
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/9/30
 * Time(创建时间)： 21:18
 * Version(版本): 1.0
 * Description(描述)： 无
 */

public class StudentContent implements BaseColumns
{
    /*
       类需要继承BaseColumns
     */

    /**
     * 这里的名称必须与AndroidManifest.xml里的android:authorities保持一致
     */
    public static final String AUTHORITIES = "mao.android_ContentProvider.provider.StudentProvider";

    /**
     * 内容提供器的外部表名
     */
    public static final String TABLE_NAME = "student";


    /**
     * 路径
     */
    public static final String path = "/student";

    /**
     * 访问内容提供器的URI
     */
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITIES + path);


    //下面是该表的各个字段名称

    /**
     * 学生学号
     */
    public static final String STUDENT_ID = "id";
    /**
     * 学生名字
     */
    public static final String STUDENT_NAME = "name";
    /**
     * 学生年龄
     */
    public static final String STUDENT_AGE = "age";
    /**
     * 学生体重
     */
    public static final String STUDENT_WEIGHT = "weight";

}
