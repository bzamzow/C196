package ed.wgu.zamzow.scheduler.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ed.wgu.zamzow.scheduler.objects.Class;
import ed.wgu.zamzow.scheduler.objects.Instructor;
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

    public void CreateCourse(Class course) {
        ContentValues values = new ContentValues();
        values.put("title", course.getTitle());
        values.put("start",course.getStart().toString());
        values.put("endDate",course.getEnd().toString());
        values.put("description",course.getDesc());
        values.put("status",course.getStatus());
        values.put("instructorID", course.getInstructorID());
        values.put("termid", course.getId());
        db.insert("courses",null, values);
        db.close();
        schedulerDB.close();
    }

    public void CreateInstructor(Instructor instructor) {
        ContentValues values = new ContentValues();
        values.put("name", instructor.getName());
        values.put("email",instructor.getEmail());
        values.put("phone",instructor.getPhone());
        db.insert("instructors",null, values);
        db.close();
        schedulerDB.close();
    }
}
