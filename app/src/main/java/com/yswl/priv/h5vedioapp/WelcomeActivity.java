package com.yswl.priv.h5vedioapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {
    static String  src = "http://116.31.98.133/videos/v0/20171225/bf/ae/49f8aa01537d7e95630c2660a5727248.mp4?key=0740cc26f0e316122d7c23683d06cae1d&dis_k=26941377324e3500dcb7254e164677237&dis_t=1514373791&dis_dz=CT-GuangDong&dis_st=44&src=iqiyi.com&uuid=a795aec-5a43829f-bf&m=v&qd_ip=db87e141&qd_p=db87e141&qd_k=88f45efc71b93935afbc44b1b803abbe&qd_src=02020031010000000000&ssl=&ip=&qd_vip=0&dis_src=vrs&qd_uid=1466207313&qdv=1&qd_tm=1514373791115";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ((TextView)findViewById(R.id.src)).setText(src);
        ((TextView)findViewById(R.id.src)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
//                String type = "video/* ";
                Uri uri = Uri.parse(src);
//                intent.setDataAndType(uri, type);
                intent.setData (uri);
                startActivity(intent);

            }
        });

    }
}
