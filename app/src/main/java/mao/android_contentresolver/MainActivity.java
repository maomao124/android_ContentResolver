package mao.android_contentresolver;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import mao.android_contentresolver.entity.Student;
import mao.android_contentresolver.resolver.StudentResolver;

public class MainActivity extends AppCompatActivity
{

    private StudentResolver studentResolver;
    private TextView textView;
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;

    /**
     * 标签
     */
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studentResolver = new StudentResolver(this);

        textView = findViewById(R.id.TextView1);

        editText1 = findViewById(R.id.EditText1);
        editText2 = findViewById(R.id.EditText2);
        editText3 = findViewById(R.id.EditText3);
        editText4 = findViewById(R.id.EditText4);


        findViewById(R.id.Button1).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                insert();
            }
        });

        findViewById(R.id.Button2).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                update();
            }
        });

        findViewById(R.id.Button3).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                delete();
            }
        });

        findViewById(R.id.Button4).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                query();
            }
        });

        findViewById(R.id.Button5).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                queryAll();
            }
        });

    }

    /**
     * 插入
     */
    private void insert()
    {
        try
        {
            Long id = Long.valueOf(editText1.getText().toString());
            String name = editText2.getText().toString();
            int age = Integer.parseInt(editText3.getText().toString());
            float weight = Float.parseFloat(editText4.getText().toString());

            Student student = new Student(id, name, age, weight);
            studentResolver.insert(student);
            toastShow("已尝试插入");
        }
        catch (Exception e)
        {
            Log.e(TAG, "insert: ", e);
            toastShow("异常：" + e.getMessage());
        }
    }


    /**
     * 更新
     */
    private void update()
    {
        try
        {
            Long id = Long.valueOf(editText1.getText().toString());
            String name = editText2.getText().toString();
            int age = Integer.parseInt(editText3.getText().toString());
            float weight = Float.parseFloat(editText4.getText().toString());

            Student student = studentResolver.query(id);
            if (student == null)
            {
                throw new Exception("未查询到学号为" + id + "的信息");
            }
            student.setName(name);
            student.setAge(age);
            student.setWeight(weight);
            boolean update = studentResolver.update(student);
            if (!update)
            {
                throw new Exception("更新失败");
            }
            toastShow("更新成功");
        }
        catch (Exception e)
        {
            Log.e(TAG, "update: ", e);
            toastShow("异常：" + e.getMessage());
        }
    }

    /**
     * 删除
     */
    private void delete()
    {
        try
        {
            if (editText1.getText().toString().equals(""))
            {
                toastShow("学号为空");
                return;
            }
            long id = Long.parseLong(editText1.getText().toString());

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("删除确认")
                    .setMessage("是否删除学号为" + id + "的信息？")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            boolean delete = studentResolver.delete(id);
                            if (!delete)
                            {
                                toastShow("删除失败");
                                return;
                            }
                            toastShow("删除成功");
                        }
                    })
                    .setNegativeButton("取消", null)
                    .create()
                    .show();

        }
        catch (Exception e)
        {
            Log.e(TAG, "delete: ", e);
            toastShow("异常：" + e.getMessage());
        }
    }

    /**
     * 查询
     */
    private void query()
    {
        try
        {
            if (editText1.getText().toString().equals(""))
            {
                toastShow("学号为空");
                return;
            }
            long id = Long.parseLong(editText1.getText().toString());

            Student student = studentResolver.query(id);
            if (student == null)
            {
                toastShow("查询不到学号为" + id + "的信息");
                editText1.setText("");
                editText2.setText("");
                editText3.setText("");
                editText4.setText("");
                return;
            }
            editText1.setText(String.valueOf(id));
            editText2.setText(student.getName());
            editText3.setText(String.valueOf(student.getAge()));
            editText4.setText(String.valueOf(student.getWeight()));
            toastShow("查询成功");
        }
        catch (Exception e)
        {
            Log.e(TAG, "query: ", e);
            toastShow("异常：" + e.getMessage());
        }
    }

    /**
     * 查询所有
     */
    @SuppressLint("SetTextI18n")
    private void queryAll()
    {
        try
        {
            textView.setText("");
            List<Student> studentList = studentResolver.queryAll();
            for (Student student : studentList)
            {
                textView.setText(textView.getText() + "\n\n" + student.toString());
            }
            toastShow("查询到" + studentList.size() + "条数据");
        }
        catch (Exception e)
        {
            Log.e(TAG, "queryAll: ", e);
            toastShow("异常：" + e.getMessage());
        }
    }

    /**
     * 显示消息
     *
     * @param message 消息
     */
    private void toastShow(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}