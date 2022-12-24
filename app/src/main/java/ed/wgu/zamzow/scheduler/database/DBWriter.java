package ed.wgu.zamzow.scheduler.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ed.wgu.zamzow.scheduler.objects.Term;

public class DBWriter {

    SchedulerDB schedulerDB;
    SQLiteDatabase db;

    public DBWriter(Context context) {
        schedulerDB = new SchedulerDB(context);
        db = schedulerDB.getWritableDatabase();
    }

    public void CreateTerm(Term term) {
        ContentValues values = new ContentValues();
        values.put("title", term.getTitle());
        values.put("start",term.getStart().toString());
        values.put("endDate",term.getEnd().toString());
        db.insert("terms",null, values);
        db.close();
        schedulerDB.close();
    }
}
