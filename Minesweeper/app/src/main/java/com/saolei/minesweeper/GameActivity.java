package com.saolei.minesweeper;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.saolei.minesweeper.adapter.BoomAdapter;
import com.saolei.minesweeper.entity.GridEntity;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {
    //定时器实例
    private Timer timer=new Timer();
    //设置显示时间的文本区域
    private TextView showTime;
    //开始游戏按钮
    private Button startGame;
    //解决多线程并发问题 区别并发和并行
    private Handler handler;
    //定义开始时间为零
    private int gameTime=0;
//    创建适配器类实例
    private BoomAdapter adapter;
    //显示多行多列的控件
    private GridView gv;
    //定义变量level
    private int level=10;
    //标志变量，是否游戏中，默认false：否
    private boolean isGaming=false;
    /*接受数据level*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //intent接收level数据
        Intent intent=getIntent();
        level=Integer.parseInt(intent.getStringExtra("level"));
        gv=(GridView)findViewById(R.id.gv);
        //创建有参构造实例
        adapter=new BoomAdapter(level,gv,this);
//        设置列数
        gv.setNumColumns(level);
        //加载适配器
        gv.setAdapter(adapter);
        //初始化
        inint();
        //设置监听
        addListener();
    }


/*初始化方法*/
    public void inint(){
        startGame=(Button)findViewById(R.id.startGame);
        showTime = (TextView) findViewById(R.id.timeView);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //msg.what根据message源码what是其定义的成员变量根据变量作出响应的行为
                if (msg.what == 1 ) {
                    showTime.setText("已用时间：" +gameTime/60+"分"+ gameTime%60 + "秒");
                }
            }
        };
    }
    /*开始游戏*/
    public void startGame(){
        adapter=new BoomAdapter(level,gv,this);
        gv.setNumColumns(level);
        gv.setAdapter(adapter);
        //正在游戏
        isGaming=true;
        //开始时间为0
        gameTime=0;
        //注销定时器
        timer.cancel();
//        定义定时器实例
        timer = new Timer();
//        开启定时器（task，延迟时间，1s执行一次）
        timer.schedule(
                new TimerTask() {
            @Override
            public void run() {
                gameTime++;
                handler.sendEmptyMessage(1);
            }
        }, 0, 1000);
    }

    /*方法：结束游戏*/
    public void stopGame(){
        isGaming=false;
        timer.cancel();
    }
    /*开始初始化*/
    public void addListener(){
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
                startGame.setText("重新开始");
            }
        });
        //执行与单击相关的所有正常操作
        gv.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
              /*parent发生点击动作的AdapterView。
              view点击的item布局
              *position：点击的item的position
              * id：点击的item的id
              * */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!isGaming){
                    return;
                }
//                如果游戏开始，通过position获取格子对象
                GridEntity grid=adapter.getItem(position);
                if(!grid.isShow()){
                    if(grid.isBoom()){
                        grid.setIsShow(true);
                        stopGame();
                        adapter.showAllBooms();
                        Toast.makeText(getApplicationContext(),"游戏失败，请重新开始！",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(grid.getBoomsCount()==0&&!grid.isBoom()){
//                        如果格子不是雷格且周围地雷数为0，则展现无雷区域
                        adapter.showNotBoomsArea(position);
                    }
                    grid.setIsShow(true);
                    if(adapter.isWin()){
                        stopGame();
                        Toast.makeText(getApplicationContext(),"恭喜您！闯关成功,您的用时为"+showTime.getText(),Toast.LENGTH_LONG).show();
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}
