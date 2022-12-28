package ed.wgu.zamzow.scheduler.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;

import ed.wgu.zamzow.scheduler.helpers.DateHelper;
import ed.wgu.zamzow.scheduler.objects.Class;
import ed.wgu.zamzow.scheduler.objects.Instructor;
import ed.wgu.zamzow.scheduler.objects.Term;

public class DBReader {
    private SchedulerDB schedulerDB;
    private SQLiteDatabase db;
    private ArrayList<Term> terms;
    private ArrayList<Class> courses;
    private ArrayList<Instructor> instructors;

    public DBReader(Context context) {
        schedulerDB = new SchedulerDB(context);
        db = schedulerDB.getReadableDatabase();
    }



    public ArrayList<Term> getTerms() {
        terms = new ArrayList<>();
        String[] params = {"*"};
        Cursor cursor = db.query("terms",params,null, null, null, null, null);
        while (cursor.moveToNext()) {
            Term term = new Term();
            term.setId(cursor.getColumnIndexOrThrow("ID"));
            term.setTitle(cursor.getString(cursor.getColumnIndexOrThrow("title")));
            term.setStart(DateHelper.getDateFromDB(cursor.getString(cursor.getColumnIndexOrThrow("start"))));
            term.setEnd(DateHelper.getDateFromDB(cursor.getString(cursor.getColumnIndexOrThrow("endDate"))));
            terms.add(term);
        }
        cursor.close();
        return terms;
    }

    public ArrayList<Class> getCourses(int TermID) {
        courses = new ArrayList<>();
        String[] params = {"*"};
        String whereClause = "termID = ?";
        String[] whereArgs = {String.valueOf(TermID)};
        Cursor cursor = db.query("courses",params,whereClause, whereArgs, null, null, null);
        while (cursor.moveToNext()) {
            Class course = new Class();
            course.setId(cursor.getColumnIndexOrThrow("ID"));
            course.setTitle(cursor.getString(cursor.getColumnIndexOrThrow("title")));
            course.setDesc(cursor.getString(cursor.getColumnIndexOrThrow("description")));
            course.setStatus(cursor.getColumnIndexOrThrow("status"));
            course.setStart(DateHelper.getDateFromDB(cursor.getString(cursor.getColumnIndexOrThrow("start"))));
            course.setEnd(DateHelper.getDateFromDB(cursor.getString(cursor.getColumnIndexOrThrow("endDate"))));
            course.setInstructorID(cursor.getColumnIndexOrThrow("instructorID"));
            course.setTermid(cursor.getColumnIndexOrThrow("termid"));
            courses.add(course);
        }
        cursor.close();
        return courses;
    }

    public ArrayList<Instructor> getInstructors() {
        instructors = new ArrayList<>();
        String[] params = {"*"};
        Cursor cursor = db.query("instructors",params,null, null, null, null, null);
        while (cursor.moveToNext()) {
            Instructor instructor = new Instructor();
            instructor.setID(cursor.getColumnIndexOrThrow("ID"));
            instructor.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
            instructor.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
            instructor.setPhone(cursor.getString(cursor.getColumnIndexOrThrow("phone")));
            instructors.add(instructor);
        }
        cursor.close();
        return instructors;
    }
}
