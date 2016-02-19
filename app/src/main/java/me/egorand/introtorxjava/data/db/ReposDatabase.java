package me.egorand.introtorxjava.data.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * @author Egor
 */
@Database(name = ReposDatabase.DATABASE_NAME, version = ReposDatabase.DATABASE_VERSION)
public class ReposDatabase {

    public static final String DATABASE_NAME = "Repos";
    public static final int DATABASE_VERSION = 1;
}
