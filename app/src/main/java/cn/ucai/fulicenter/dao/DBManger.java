package cn.ucai.fulicenter.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import cn.ucai.fulicenter.bean.UserBean;


/**
 * Created by mac-yk on 2016/10/24.
 */

public class DBManger  {
    private static DBManger dbManger=new DBManger();
    private DBOpenHelper dbHelper;
    void OnInit(Context context){
        dbHelper=new DBOpenHelper(context);
    }
    public static synchronized DBManger getInstance(){
        return dbManger;
    }
    public synchronized void closeDB(){
        if(dbHelper!=null){
            dbHelper.close();
        }
    }
    public synchronized boolean saveUser(UserBean userBean){
        SQLiteDatabase database=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(UserDao.USER_COLUMN_NAME,userBean.getMuserName());
        values.put(UserDao.USER_COLUMN_NICK,userBean.getMuserNick());
        values.put(UserDao.USER_COLUMN_AVATAR_ID,userBean.getMavatarId());
        values.put(UserDao.USER_COLUMN_AVATAR_TYPE,userBean.getMavatarType());
        values.put(UserDao.USER_COLUMN_AVATAR_PATH,userBean.getMavatarPath());
        values.put(UserDao.USER_COLUMN_AVATAR_SUFFIX,userBean.getMavatarSuffix());
        values.put(UserDao.USER_COLUMN_AVATAR_LASTUPDATE_TIME,userBean.getMavatarLastUpdateTime());
        if(database.isOpen()){
            return database.replace(UserDao.USER_TABLE_NAME,null,values)!=-1;
        }
        return false;
    }

    public synchronized boolean updateUser(UserBean user) {
        int result = -1;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = UserDao.USER_COLUMN_NAME + "=?";
        ContentValues values = new ContentValues();
        values.put(UserDao.USER_COLUMN_NICK, user.getMuserNick());
        if (db.isOpen()) {
            result = db.update(UserDao.USER_TABLE_NAME, values,sql,new String[]{user.getMuserName()});
        }
        return result > 0;
    }

    public synchronized UserBean getUser(String userName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + UserDao.USER_TABLE_NAME + " WHERE " + UserDao.USER_COLUMN_NAME + " =?";
        UserBean user = null;
        Cursor cursor = db.rawQuery(sql, new String[]{userName});
        if (cursor.moveToNext()) {
            user = new UserBean();
            user.setMuserName(userName);
            user.setMuserNick(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_NICK)));
            user.setMavatarId(cursor.getInt(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_ID)));
            user.setMavatarType(cursor.getInt(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_TYPE)));
            user.setMavatarPath(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_PATH)));
            user.setMavatarSuffix(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_SUFFIX)));
            user.setMavatarLastUpdateTime(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_LASTUPDATE_TIME)));
            return user;
        }
        return user;
    }


}
