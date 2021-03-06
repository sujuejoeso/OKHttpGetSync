package com.joeso.okhttptest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    Handler mHandler=new Handler();
    TextView tvCode,tvDetails,tvContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button=findViewById(R.id.button);
        tvCode=findViewById(R.id.tvCode);
        tvDetails=findViewById(R.id.tvDetails);
        tvContent=findViewById(R.id.tvContent);
        tvContent.setMovementMethod(new ScrollingMovementMethod());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    String code,details,content;
                    @Override
                    public void run(){
                        OkHttpClient okHttpClient=new OkHttpClient();
                        Request request=new Request.Builder().url("http://dummy.restapiexample.com/api/v1/employees").build();
                        Call call=okHttpClient.newCall(request);
                        try {
                            Response response=call.execute();
                            if(response!=null) {
                                code=String.valueOf(response.code());
                                details=response.message();
                                content=response.body().string();
                            }
                            else {
                                code="N/A";
                                details="N/A";
                                content="N/A";
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                               tvCode.setText(code);
                               tvDetails.setText(details);
                               tvContent.setText(content);
                            }
                        });
                    }
                }.start();
            }
        });




    }
}
