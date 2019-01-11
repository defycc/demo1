package com.saolei.minesweeper;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.app.AlertDialog;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
//    设置两个按钮，开始游戏和设置难度
    private Button toPlay;
    private Button setDifficulty;
    //默认雷的个数和方格数
    public String level="15";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //布置ToPlayGame按钮事件
        toPlay=(Button)findViewById(R.id.toPlay);
        toPlay.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              Activity跳转
                //创建intent对象
                Intent intent=new Intent();
                //使用intent传递数据
                intent.putExtra("level",level);
//                跳转到GameActivity
                intent.setClass(MainActivity.this,GameActivity.class);
//                启动新的activity
                startActivity(intent);
            }
        });
        setDifficulty=(Button)findViewById(R.id.setDifficulty);
        setDifficulty.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置键盘输入数字难度
                final EditText editText = new EditText(v.getContext());
//                创建对话框，通过内部静态类来构造
                new AlertDialog.Builder(v.getContext())
                        .setTitle("请输入游戏难度（2-20）：")//设置对话框的标题
                        .setView(editText)//设置输入框
                        //点击确认获取值，退出对话框
                        .setPositiveButton("确定",
//                        获取输入值
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //trim移除字符串两端的空白字符
                                level= editText.getText().toString().trim();
                            }
                        })
                        //点击取消直接退出对话框
                        .setNegativeButton("取消", null)
                        .show();
            }
        });
}


}
