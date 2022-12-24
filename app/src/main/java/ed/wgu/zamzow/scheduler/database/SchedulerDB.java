package ed.wgu.zamzow.scheduler.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SchedulerDB extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "scheduler.sqlite";

    private static final String CREATE_CLASS =
            "CREATE TABLE courses (ID INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT," +
            "description TEXT, status INTEGER, start DATETIME, endDate DATETIME, termid INTEGER, instructorID INTEGER)";
    private static final String CREATE_TERM =
            "CREATE TABLE terms (ID INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT," +
            "start DATETIME, endDate DATETIME)";
    private static final String CREATE_INSTRUCTOR =
            "CREATE TABLE instructors (ID INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT," +
            "email TEXT, phone TEXT)";
    private static final String CREATE_NOTE =
            "CREATE TABLE notes (ID INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, note TEXT," +
            "classID INTEGER)";
    private static final String CREATE_ASSESSMENT =
            "CREATE TABLE assessments (ID INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT," +
            "type INTEGER, endDate DATETIME, classID INTEGER)";

    public SchedulerDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SchedulerDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(CREATE_ASSESSMENT);
            sqLiteDatabase.execSQL(CREATE_CLASS);
            sqLiteDatabase.execSQL(CREATE_INSTRUCTOR);
            sqLiteDatabase.execSQL(CREATE_NOTE);
            sqLiteDatabase.execSQL(CREATE_TERM);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
