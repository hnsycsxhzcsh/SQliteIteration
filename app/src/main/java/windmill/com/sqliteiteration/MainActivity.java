package windmill.com.sqliteiteration;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTvMessage;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvMessage = (TextView) findViewById(R.id.tv_message);

        findViewById(R.id.bt_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteConnectionDao instance = SQLiteConnectionDao.getInstance();
                SQLiteDatabase sqLiteDatabase = instance.openDatabase();
                Cursor cursor = sqLiteDatabase.rawQuery("select * from " +
                        MySQLiteOpenHelper.TABLE_NAME, null);
                String message = "";
                while (cursor.moveToNext()) {
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String body = cursor.getString(cursor.getColumnIndex("body"));
                    if (MySQLiteOpenHelper.DATABASE_VERSION == 2) {
                        String pic = cursor.getString(cursor.getColumnIndex("pic"));
                        message = message + ":" + title + "," + body + "," + pic;
                    } else if (MySQLiteOpenHelper.DATABASE_VERSION == 1) {
                        message = message + ":" + title + "," + body;
                    }
                }
                instance.closeDatabase();
                mTvMessage.setText(message);
            }
        });

        findViewById(R.id.bt_insert1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteConnectionDao instance = SQLiteConnectionDao.getInstance();
                SQLiteDatabase sqLiteDatabase = instance.openDatabase();
                ContentValues cv = new ContentValues();
                cv.put("title", "title" + count);
                cv.put("body", "body" + count);
                count++;
                sqLiteDatabase.insert(MySQLiteOpenHelper.TABLE_NAME, null, cv);
            }
        });

        findViewById(R.id.bt_insert2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteConnectionDao instance = SQLiteConnectionDao.getInstance();
                SQLiteDatabase sqLiteDatabase = instance.openDatabase();
                ContentValues cv = new ContentValues();
                cv.put("title", "title" + count);
                cv.put("body", "body" + count);
                cv.put("pic", "pic" + count);
                count++;
                sqLiteDatabase.insert(MySQLiteOpenHelper.TABLE_NAME, null, cv);
            }
        });

    }
}
