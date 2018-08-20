package com.skcodestack.fastec.ec.database;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/12
 * Version  1.0
 * Description:数据库管理
 */

public class DatabaseManager {

    private DaoSession mDaoSession = null;
    private UserProfileDao mDao = null;

    private static final class Holder{
        private static final DatabaseManager DATABASE_MANAGER = new DatabaseManager();
    }

    private DatabaseManager(){

    }

    public DatabaseManager init(Context context){
        initDao(context);
        return this;
    }

    public static DatabaseManager getInstance(){
        return Holder.DATABASE_MANAGER;
    }

    private void initDao(Context context) {
        final ReleaseOpenHelper openHelper = new ReleaseOpenHelper(context, "fast_ec.db");
        Database db = openHelper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
        mDao = mDaoSession.getUserProfileDao();
    }

    public final UserProfileDao getDao(){
        return mDao;
    }
}
