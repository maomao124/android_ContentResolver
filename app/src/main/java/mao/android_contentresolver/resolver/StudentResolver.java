package mao.android_contentresolver.resolver;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mao.android_contentresolver.dao.StudentContent;
import mao.android_contentresolver.entity.Student;

/**
 * Project name(项目名称)：android_ContentResolver
 * Package(包名): mao.android_contentresolver.resolver
 * Class(类名): StudentResolver
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/9/30
 * Time(创建时间)： 21:20
 * Version(版本): 1.0
 * Description(描述)： 无
 */

public class StudentResolver
{
    /**
     * 上下文
     */
    private final Context context;


    /**
     * 构造方法
     *
     * @param context 上下文
     */
    public StudentResolver(Context context)
    {
        this.context = context;
    }

    /**
     * 插入
     *
     * @param student Student对象
     */
    public void insert(Student student)
    {
        ContentValues contentValues = new ContentValues();
        setContentValues(student, contentValues);
        context.getContentResolver().insert(StudentContent.CONTENT_URI, contentValues);
    }

    /**
     * 更新
     *
     * @param student Student对象
     * @return boolean
     */
    public boolean update(Student student)
    {
        ContentValues contentValues = new ContentValues();
        setContentValues(student, contentValues);
        int update = context.getContentResolver().update(StudentContent.CONTENT_URI,
                contentValues, "id=?", new String[]{String.valueOf(student.getId())});
        return update > 0;
    }

    /**
     * 删除
     *
     * @param id id
     * @return boolean
     */
    public boolean delete(Serializable id)
    {
        int delete = context.getContentResolver()
                .delete(StudentContent.CONTENT_URI, "id=?",
                        new String[]{String.valueOf(id)});
        return delete > 0;
    }

    /**
     * 查询所有
     *
     * @return {@link List}<{@link Student}>
     */
    public List<Student> queryAll()
    {

        Cursor cursor = context.getContentResolver().query(StudentContent.CONTENT_URI,
                null, "1=1", null, null);
        List<Student> list = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Student student = setStudent(cursor, new Student());
            list.add(student);
        }
        return list;
    }

    /**
     * 查询
     *
     * @param id id
     * @return {@link Student}
     */
    public Student query(Serializable id)
    {
        Cursor cursor = context.getContentResolver().query(StudentContent.CONTENT_URI,
                null, "id=?", new String[]{String.valueOf(id)}, null);
        if (cursor.moveToNext())
        {
            return setStudent(cursor, new Student());
        }
        return null;
    }


    /**
     * 填充ContentValues
     *
     * @param student       Student
     * @param contentValues ContentValues
     */
    private void setContentValues(Student student, ContentValues contentValues)
    {
        contentValues.put("id", student.getId());
        contentValues.put("name", student.getName());
        contentValues.put("age", student.getAge());
        contentValues.put("weight", student.getWeight());
    }

    /**
     * 填充Student
     *
     * @param cursor  游标
     * @param student Student对象
     */
    private Student setStudent(Cursor cursor, Student student)
    {
        student.setId(cursor.getLong(0));
        student.setName(cursor.getString(1));
        student.setAge(cursor.getInt(2));
        student.setWeight(cursor.getFloat(3));

        return student;
    }
}
