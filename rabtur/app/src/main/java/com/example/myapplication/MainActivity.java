package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private  int rabpr =0,turpr=0;
    private SeekBar seekBar,seekBar2;
    private Button btn_start;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar=findViewById(R.id.seekBar);
        seekBar2=findViewById(R.id.seekBar2);
        btn_start=findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void  onClick(View v){
                btn_start.setEnabled(false);
                rabpr=0;
                turpr=0;
                seekBar.setProgress(0);
                seekBar2.setProgress(0);
                runThread();
                runAsyncTask();
            }
        });
    }
    private void runThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(rabpr<=100&&turpr<=100){
                    try{
                        Thread.sleep(100);
                        rabpr+=(int)(Math.random()*3);
                        Message msg = new Message();
                        msg.what=1;
                        mHandler.sendMessage(msg);
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    seekBar.setProgress(rabpr);
                    break;
            }
            if(rabpr>=100&&turpr<100){
                Toast.makeText(MainActivity.this,"兔子勝利",Toast.LENGTH_SHORT).show();
                btn_start.setEnabled(true);
            }
            return false;
        }
    });
    @SuppressLint("StaticFieldLeak")
    private void runAsyncTask(){
        new AsyncTask<Void,Integer,Boolean>(){
            @Override
            protected Boolean doInBackground(Void...voids){
                while(turpr<=100&&rabpr<100){
                    try{
                        Thread.sleep(100);
                        turpr+=(int)(Math.random()*3);
                        publishProgress(turpr);
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
                return  true;
            }
            @Override
            protected  void onProgressUpdate(Integer... values){
                super.onProgressUpdate(values);
                seekBar2.setProgress(values[0]);
            }
            @Override
            protected void onPostExecute(Boolean aBoolean){
                super.onPostExecute(aBoolean);
                if (turpr>=100&&rabpr<100){
                    Toast.makeText(MainActivity.this,"烏龜勝利",Toast.LENGTH_SHORT).show();
                    btn_start.setEnabled(true);
                }
            }
        }.execute();
    }

}
