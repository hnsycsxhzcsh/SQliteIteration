package windmill.com.sqliteiteration;

import android.app.Application;
import android.content.Context;

/**
 * Created by HARRY on 2019/3/8 0008.
 */

public class Token extends Application {
    private static Context mTokenContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mTokenContext = getApplicationContext();
    }

    public static Context getContext() {
        return mTokenContext;
    }
}
