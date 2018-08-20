package com.skcodestack.fastec.ec.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "user_profile".
*/
public class UserProfileDao extends AbstractDao<UserProfile, Long> {

    public static final String TABLENAME = "user_profile";

    /**
     * Properties of entity UserProfile.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property UserId = new Property(0, long.class, "userId", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Avatar = new Property(2, String.class, "avatar", false, "AVATAR");
        public final static Property Gender = new Property(3, String.class, "gender", false, "GENDER");
        public final static Property Address = new Property(4, String.class, "address", false, "ADDRESS");
    };


    public UserProfileDao(DaoConfig config) {
        super(config);
    }
    
    public UserProfileDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"user_profile\" (" + //
                "\"_id\" INTEGER PRIMARY KEY NOT NULL ," + // 0: userId
                "\"NAME\" TEXT," + // 1: name
                "\"AVATAR\" TEXT," + // 2: avatar
                "\"GENDER\" TEXT," + // 3: gender
                "\"ADDRESS\" TEXT);"); // 4: address
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"user_profile\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, UserProfile entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getUserId());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String avatar = entity.getAvatar();
        if (avatar != null) {
            stmt.bindString(3, avatar);
        }
 
        String gender = entity.getGender();
        if (gender != null) {
            stmt.bindString(4, gender);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(5, address);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, UserProfile entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getUserId());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String avatar = entity.getAvatar();
        if (avatar != null) {
            stmt.bindString(3, avatar);
        }
 
        String gender = entity.getGender();
        if (gender != null) {
            stmt.bindString(4, gender);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(5, address);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public UserProfile readEntity(Cursor cursor, int offset) {
        UserProfile entity = new UserProfile( //
            cursor.getLong(offset + 0), // userId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // avatar
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // gender
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // address
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, UserProfile entity, int offset) {
        entity.setUserId(cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setAvatar(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setGender(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setAddress(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(UserProfile entity, long rowId) {
        entity.setUserId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(UserProfile entity) {
        if(entity != null) {
            return entity.getUserId();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
