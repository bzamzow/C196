package ed.wgu.zamzow.scheduler.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;

import ed.wgu.zamzow.scheduler.helpers.DateHelper;
import ed.wgu.zamzow.scheduler.objects.Term;

public class DBReader {
    private SchedulerDB schedulerDB;
    private SQLiteDatabase db;
    private ArrayList<Term> terms;

    public DBReader(Context context) {
        schedulerDB = new SchedulerDB(context);
        db = schedulerDB.getReadableDatabase();
    }



    public ArrayList<Term> getTerms() {
        terms = new ArrayList<>();
        String[] params = {"*"};
        Cursor cursor = db.query("terms",params,null, null, null, null, null);
        while (cursor.moveToNext()) {
            System.out.println(cursor.getString(cursor.getColumnIndexOrThrow("start")));
            Term term = new Term();
            term.setId(cursor.getColumnIndexOrThrow("ID"));
            term.setTitle(cursor.getString(cursor.getColumnIndexOrThrow("title")));
            term.setStart(DateHelper.getDateFromDB(cursor.getString(cursor.getColumnIndexOrThrow("start"))));
            term.setEnd(DateHelper.getDateFromDB(cursor.getString(cursor.getColumnIndexOrThrow("endDate"))));
            terms.add(term);
        }
        return terms;
    }
}