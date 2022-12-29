package ed.wgu.zamzow.scheduler.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ed.wgu.zamzow.scheduler.objects.Assessment;
import ed.wgu.zamzow.scheduler.objects.Class;
import ed.wgu.zamzow.scheduler.objects.Instructor;
import ed.wgu.zamzow.scheduler.objects.Note;
import ed.wgu.zamzow.scheduler.objects.Term;

public class DBWriter {

    private SQLiteDatabase db;
    private SchedulerDB schedulerDB;


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
    }

    public void CreateCourse(Class course) {
        ContentValues values = new ContentValues();
        values.put("title", course.getTitle());
        values.put("start",course.getStart().toString());
        values.put("endDate",course.getEnd().toString());
        values.put("description",course.getDesc());
        values.put("status",course.getStatus());
        values.put("instructorID", course.getInstructorID());
        values.put("termid", course.getTermid());
        db.insert("courses",null, values);
        db.close();
    }

    public void UpdateCourse(Class course) {
        String whereClause = "ID = ?";
        String[] whereArgs = {String.valueOf(course.getId())};
        ContentValues values = new ContentValues();
        values.put("title", course.getTitle());
        System.out.println(course.getStart().toString());
        values.put("start",course.getStart().toString());
        values.put("endDate",course.getEnd().toString());
        values.put("description",course.getDesc());
        values.put("status",course.getStatus());
        values.put("instructorID", course.getInstructorID());
        values.put("termid", course.getTermid());
        db.update("courses",values, whereClause, whereArgs);
        db.close();
    }

    public void CreateInstructor(Instructor instructor) {
        ContentValues values = new ContentValues();
        values.put("name", instructor.getName());
        values.put("email",instructor.getEmail());
        values.put("phone",instructor.getPhone());
        db.insert("instructors",null, values);
        db.close();
    }

    public void CreateAssessment(Assessment assessment) {
        ContentValues values = new ContentValues();
        values.put("title", assessment.getTitle());
        values.put("type",assessment.getType());
        values.put("endDate",assessment.getEnd().toString());
        values.put("classID",assessment.getClassID());
        db.insert("assessments",null, values);
        db.close();
    }

    public void UpdateAssessment(Assessment assessment) {
        String whereClause = "ID = ?";
        String[] whereArgs = {String.valueOf(assessment.getID())};
        ContentValues values = new ContentValues();
        values.put("title", assessment.getTitle());
        values.put("type",assessment.getType());
        values.put("endDate",assessment.getEnd().toString());
        values.put("classID",assessment.getClassID());
        db.update("assessments",values, whereClause, whereArgs);
        db.close();
    }

    public void CreateNote(Note note) {
        ContentValues values = new ContentValues();
        values.put("title", note.getTitle());
        values.put("note",note.getNote());
        values.put("classID",note.getClassID());
        db.insert("notes",null, values);
        db.close();
    }
}
