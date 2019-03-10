package windmill.com.sqliteiteration;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView mTvMessage;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvMessage = (TextView) findViewById(R.id.tv_message);
        //也可使用query方法，我这里使用了rawQuery
//        query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit)  查询语句
//        table为表名
//        columns想要显示的字段
//        selection条件
//        selectionArgs 中为selection的值
//        groupBy 分组
//        having  分组条件
//        orderBy 排序
//        limit 分页
        //TODO 因为数据库操作是耗时操作，以下的这些操作需放在异步中进行操作，我这里为了简便没有放异步中

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

//        insert(String table, String nullColumnHack, ContentValues values) 插入语句，
//        table表名
//        values为ContentValues对象！该对象存储方式为Key-value，Key表示表中的字段名称，value表示该字段对应的值
//        nullColumnHack表示强行向表中插入null，前提是第三个参数（values）为空或者不包含任何Key-value时生效
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
                instance.closeDatabase();
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
                long insert = sqLiteDatabase.insert(MySQLiteOpenHelper.TABLE_NAME, null, cv);
                instance.closeDatabase();
            }
        });

//        update(String table, ContentValues values, String whereClause, String[] whereArgs)  更新语句
//        table为表名
//        values为ContentValues对象！该对象存储方式为Key-value，Key表示表中的字段名称
//        value表示该字段对应的值，第三个参数为条件例如：_ID = ? ,第四个参数为第三个参数的值！
        findViewById(R.id.bt_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteConnectionDao instance = SQLiteConnectionDao.getInstance();
                SQLiteDatabase sqLiteDatabase = instance.openDatabase();
                ContentValues values = new ContentValues();
                values.put("body","body_change");
                int update = sqLiteDatabase.update(MySQLiteOpenHelper.TABLE_NAME, values, "title=?", new String[]{"title1"});
                Toast.makeText(Token.getContext(), "更新了"+update, Toast.LENGTH_SHORT).show();
                instance.closeDatabase();
            }
        });

//        delete(String table, String whereClause, String[] whereArgs)  删除数据
//        table表名
//        whereClause为条件例如：_ID = ?
//        whereArgs为whereClause的值！
        findViewById(R.id.bt_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteConnectionDao instance = SQLiteConnectionDao.getInstance();
                SQLiteDatabase sqLiteDatabase = instance.openDatabase();
                int delete = sqLiteDatabase.delete(MySQLiteOpenHelper.TABLE_NAME, "title=?", new String[]{"title1"});
                Toast.makeText(Token.getContext(), "删除了"+delete, Toast.LENGTH_SHORT).show();
                instance.closeDatabase();
            }
        });

    }
}
