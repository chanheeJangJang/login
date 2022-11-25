package org.app.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class join extends AppCompatActivity {

    int version = 1;
    MainActivity.DatabaseOpenHelper helper; ////////   여기부분 참고코드랑 좀 다름
    SQLiteDatabase database;

    EditText idEditText;
    EditText pwEditText;
    Button btnJoin2;

    String sql;
    Cursor cursor;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        idEditText = (EditText) findViewById(R.id.editTextTextPersonName11);
        pwEditText = (EditText) findViewById(R.id.editTextTextPersonName22);

        btnJoin2 = (Button) findViewById(R.id.buttonj);

        helper = new MainActivity.DatabaseOpenHelper(join.this, MainActivity.DatabaseOpenHelper.tableName, null, version);
        database = helper.getWritableDatabase();

        btnJoin2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                String id = idEditText.getText().toString();
                String pw = pwEditText.getText().toString();
                Integer g1 = 0, g2 = 0, g3 = 0, g4 = 0, total = 0;////****************

                if(id.length() == 0 || pw.length() == 0) {
                    //아이디와 비밀번호는 필수 입력사항입니다.
                    Toast toast = Toast.makeText(join.this, "아이디와 비밀번호는 필수 입력사항입니다.", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                sql = "SELECT id FROM "+ helper.tableName + " WHERE id = '" + id + "'";
                cursor = database.rawQuery(sql, null);

                if(cursor.getCount() != 0){
                    //존재하는 아이디입니다.
                    Toast toast = Toast.makeText(join.this, "존재하는 아이디입니다.", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    helper.insertUser(database,id,pw);
                    //database.execSQL("UPDATE BAKINGGAME SET g1 = 0 WHERE id = '" + idEditText.getText().toString() + "'" );
                    //database.close();

                    Toast toast = Toast.makeText(join.this, "가입이 완료되었습니다. 로그인을 해주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });


    }
}