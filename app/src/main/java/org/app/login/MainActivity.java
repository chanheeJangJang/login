package org.app.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textID, textPW, text4;
    EditText idEditText, pwEditText;
    Button btnLogin, btnJoin;

    int version = 1;
    DatabaseOpenHelper helper;
    SQLiteDatabase database;

    String sql;
    Cursor cursor;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textID = (TextView) findViewById(R.id.textView);
        textPW = (TextView) findViewById(R.id.textView2);
        text4 = (TextView) findViewById(R.id.textView4);

        idEditText = (EditText) findViewById(R.id.editTextTextPersonName11);
        pwEditText = (EditText) findViewById(R.id.editTextTextPersonName22);

        btnLogin = (Button) findViewById(R.id.buttonj);
        btnJoin = (Button) findViewById(R.id.button2);

        helper = new DatabaseOpenHelper(MainActivity.this, DatabaseOpenHelper.tableName, null, version);
        database = helper.getWritableDatabase();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = idEditText.getText().toString();
                String pw = pwEditText.getText().toString();
                Integer g1 = 0, g2 = 0, g3 = 0, g4 = 0, total = 0;////****************

                if (id.length() == 0 || pw.length() == 0) {
                    //아이디와 비밀번호는 필수 입력사항입니다.
                    text4.setText("아이디를 입력하세요");
                    Toast.makeText(MainActivity.this, "아이디와 비밀번호는 필수 입력사항입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                sql = "SELECT id FROM " + DatabaseOpenHelper.tableName + " WHERE id = '" + id + "'";
                cursor = database.rawQuery(sql, null);

                if (cursor.getCount() != 1) {
                    //아이디가 틀렸습니다.
                    Toast toast = Toast.makeText(MainActivity.this, "존재하지 않는 아이디입니다.", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                sql = "SELECT pw FROM " + DatabaseOpenHelper.tableName + " WHERE id = '" + id + "'";
                cursor = database.rawQuery(sql, null);

                cursor.moveToNext();
                if (!pw.equals(cursor.getString(0))) {
                    //비밀번호가 틀렸습니다.
                    Toast toast = Toast.makeText(MainActivity.this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    //로그인성공
                    Toast toast = Toast.makeText(MainActivity.this, "로그인성공", Toast.LENGTH_SHORT);
                    toast.show();
                    //인텐트 생성 및 호출
                    Intent intent = new Intent(getApplicationContext(), end.class);
                    intent.putExtra("userID",id);///////////
                    intent.putExtra("userPW",pw);////////////
                    intent.putExtra("g1s",g1);////////////
                    intent.putExtra("g2s",g2);////////////
                    intent.putExtra("g3s",g3);////////////
                    intent.putExtra("g4s",g4);////////////
                    intent.putExtra("ts",total);////////////

                    startActivity(intent);
                    finish();
                }
                cursor.close();
            }
        });

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원가입 버튼 클릭
                Toast toast = Toast.makeText(MainActivity.this, "회원가입 화면으로 이동", Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(getApplicationContext(), join.class);
                startActivity(intent);
                //finish();
            }
        });

    }

    public static class DatabaseOpenHelper extends SQLiteOpenHelper {

        public static final String tableName = "Users";

        public DatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i("tag", "db 생성_db가 없을때만 최초로 실행함");
            createTable(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        }

        public void createTable(SQLiteDatabase db) {
            String sql = "CREATE TABLE " + tableName + "(id text, pw text, g1 INTEGER, g2 INTEGER, g3 INTEGER, g4 INTEGER, total INTEGER)";
            try {///////////////////********************************
                db.execSQL(sql);
            } catch (SQLException e) {
            }
        }

        public void insertUser(SQLiteDatabase db, String id, String pw) {
            Log.i("tag", "회원가입을 했을때 실행함");
            db.beginTransaction();
            try {
               String sql = "INSERT INTO " + tableName + "(id, pw)" + "values('" + id + "', '" + pw + "')";
                //String sql = "INSERT INTO BAKINGGAME VALUES( '" + id  + "','" + pw  + "','" + g1  + "','" + g2  + "','" + g3  + "','" + g4  + "','" + total  + "')";

                db.execSQL(sql);///////////////////**/*******************************
                db.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.endTransaction();
            }
        }
    }
}
