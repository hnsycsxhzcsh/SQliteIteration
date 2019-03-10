package windmill.com.sqliteiteration;

import android.database.sqlite.SQLiteDatabase;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by HARRY on 2017/12/11 0011.
 */

public class SQLiteConnectionDao {

    private static MySQLiteOpenHelper helper;
    private static SQLiteConnectionDao instance;
    private SQLiteDatabase mDatabase;
    private AtomicInteger mOpenCounter = new AtomicInteger();//自增长类

    public static SQLiteConnectionDao getInstance() {
        if (instance == null) {
            synchronized (SQLiteConnectionDao.class) {
                if (instance == null) {
                    helper = new MySQLiteOpenHelper(Token.getContext());
                    instance = new SQLiteConnectionDao();
                }
            }
        }
        return instance;
    }

    //打开数据库方法
    public synchronized SQLiteDatabase openDatabase() {
        if (mOpenCounter.incrementAndGet() == 1) {//incrementAndGet会让mOpenCounter自动增长1
            // Opening new database
            try {
                mDatabase = helper.getWritableDatabase();
            } catch (Exception e) {
                mDatabase = helper.getReadableDatabase();
            }
        }
        return mDatabase;
    }

    //关闭数据库方法
    public synchronized void closeDatabase() {
        if (mOpenCounter.decrementAndGet() == 0) {//decrementAndGet会让mOpenCounter自动减1
            // Closing database
            mDatabase.close();
        }
    }
}
