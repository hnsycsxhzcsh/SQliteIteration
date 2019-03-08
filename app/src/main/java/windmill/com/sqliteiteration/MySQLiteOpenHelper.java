package windmill.com.sqliteiteration;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by HARRY on 2017/4/1 0001.
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sqlite_iteration.db";
    public static final String TABLE_NAME = "tabledemo";
    public static final int DATABASE_VERSION = 2;

    public MySQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * * 这个方法
     * * 1、在第一次打开数据库的时候才会走
     * * 2、在清除数据之后再次运行-->打开数据库，这个方法会走
     * * 3、没有清除数据，不会走这个方法
     * * 4、数据库升级的时候这个方法不会走
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String searchKeyTable = "create table " + TABLE_NAME + " (" +
                "id integer PRIMARY KEY AUTOINCREMENT," +
                "title varchar ," +
                "body varchar " +
                ")";
        db.execSQL(searchKeyTable);

        // 若不是第一个版本安装，直接执行数据库升级
        // 不要修改FIRST_DATABASE_VERSION的值，其为第一个数据库版本大小
        final int FIRST_DATABASE_VERSION = 1;
        onUpgrade(db, FIRST_DATABASE_VERSION, DATABASE_VERSION);
    }

    //    1、第一次创建数据库的时候，这个方法不会走
    //    * 2、清除数据后再次运行(相当于第一次创建)这个方法不会走
    //    * 3、数据库已经存在，而且版本升高的时候，这个方法才会调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 使用for实现跨版本升级数据库
        for (int i = oldVersion; i < newVersion; i++) {
            switch (i) {
                case 1:
                    upgrade1ToVersion2(db);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /**
         * 执行数据库的降级操作
         * 1、只有新版本比旧版本低的时候才会执行
         * 2、如果不执行降级操作，会抛出异常
         */
        super.onDowngrade(db, oldVersion, newVersion);
    }

    private void upgrade1ToVersion2(SQLiteDatabase db) {
        String pushOrderStateChangeTable = "alter table " + TABLE_NAME + " add pic varchar";
        db.execSQL(pushOrderStateChangeTable);
    }

}
