package org.app.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class end extends AppCompatActivity {

    int version = 1;
    MainActivity.DatabaseOpenHelper helper; ////////   여기부분 참고코드랑 좀 다름
    SQLiteDatabase database;

    TextView t_id, t_pw,t_1,t_2,t_3,t_4,t_t;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        t_id =(TextView) findViewById(R.id.textView3);
        t_pw =(TextView) findViewById(R.id.textView5);
        t_1 =(TextView) findViewById(R.id.textView6);
        t_2 =(TextView) findViewById(R.id.textView7);
        t_3 =(TextView) findViewById(R.id.textView8);
        t_4 =(TextView) findViewById(R.id.textView9);
        t_t =(TextView) findViewById(R.id.textView10);

        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        String userPW = intent.getStringExtra("userPW");
        Integer g1s = intent.getIntExtra("g1s",0);
        Integer g2s = intent.getIntExtra("g2s",0);
        Integer g3s = intent.getIntExtra("g3s",0);
        Integer g4s = intent.getIntExtra("g4s",0);
        Integer ts = intent.getIntExtra("ts",0);


        t_id.setText(userID);
        t_pw.setText(userPW);
        t_1.setText(String.valueOf(g1s));
        t_2.setText(String.valueOf(g2s));
        t_3.setText(String.valueOf(g3s));
        t_4.setText(String.valueOf(g4s));
        t_t.setText(String.valueOf(ts));

    }
}