package ed.wgu.zamzow.scheduler.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;

import ed.wgu.zamzow.scheduler.helpers.DateHelper;
import ed.wgu.zamzow.scheduler.objects.Assessment;
import ed.wgu.zamzow.scheduler.objects.Class;
import ed.wgu.zamzow.scheduler.objects.Instructor;
import ed.wgu.zamzow.scheduler.objects.Note;
import ed.wgu.zamzow.scheduler.objects.Term;

public class DBReader {
    private SchedulerDB schedulerDB;
    private SQLiteDatabase db;
    private ArrayList<Term> terms;
    private ArrayList<Class> courses;
    private ArrayList<Instructor> instructors;
    private ArrayList<Assessment> assessments;
    private ArrayList<Note> notes;

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
            term.setId(cursor.getInt(cursor.getColumnIndexOrThrow("ID")));
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
        String whereClause = "termid = ?";
        String[] whereArgs = {String.valueOf(TermID)};
        Cursor cursor = db.query("courses",params,whereClause, whereArgs, null, null, null);
        while (cursor.moveToNext()) {
            Class course = new Class();
            course.setId(cursor.getInt(cursor.getColumnIndexOrThrow("ID")));
            course.setTitle(cursor.getString(cursor.getColumnIndexOrThrow("title")));
            course.setDesc(cursor.getString(cursor.getColumnIndexOrThrow("description")));
            course.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow("status")));
            course.setStart(DateHelper.getDateFromDB(cursor.getString(cursor.getColumnIndexOrThrow("start"))));
            course.setEnd(DateHelper.getDateFromDB(cursor.getString(cursor.getColumnIndexOrThrow("endDate"))));
            course.setInstructorID(cursor.getInt(cursor.getColumnIndexOrThrow("instructorID")));
            course.setTermid(cursor.getInt(cursor.getColumnIndexOrThrow("termid")));
            courses.add(course);
        }
        cursor.close();
        return courses;
    }

    public ArrayList<Assessment> getAssessments(int CourseID) {
        assessments = new ArrayList<>();
        String[] params = {"*"};
        String whereClause = "classID = ?";
        String[] whereArgs = {String.valueOf(CourseID)};
        Cursor cursor = db.query("assessments",params,whereClause, whereArgs, null, null, null);
        while (cursor.moveToNext()) {
            Assessment assessment = new Assessment();
            assessment.setID(cursor.getInt(cursor.getColumnIndexOrThrow("ID")));
            assessment.setTitle(cursor.getString(cursor.getColumnIndexOrThrow("title")));
            assessment.setType(cursor.getInt(cursor.getColumnIndexOrThrow("type")));
            assessment.setEnd(DateHelper.getDateFromDB(cursor.getString(cursor.getColumnIndexOrThrow("endDate"))));
            assessment.setClassID(cursor.getInt(cursor.getColumnIndexOrThrow("classID")));
            assessments.add(assessment);
        }
        cursor.close();
        return assessments;
    }

    public ArrayList<Note> getNotes(int CourseID) {
        notes = new ArrayList<>();
        String[] params = {"*"};
        String whereClause = "classID = ?";
        String[] whereArgs = {String.valueOf(CourseID)};
        Cursor cursor = db.query("notes",params,whereClause, whereArgs, null, null, null);
        while (cursor.moveToNext()) {
            Note note = new Note();
            note.setId(cursor.getInt(cursor.getColumnIndexOrThrow("ID")));
            note.setTitle(cursor.getString(cursor.getColumnIndexOrThrow("title")));
            note.setNote(cursor.getString(cursor.getColumnIndexOrThrow("note")));
            note.setClassID(cursor.getInt(cursor.getColumnIndexOrThrow("classID")));
            notes.add(note);
        }
        cursor.close();
        return notes;
    }

    public ArrayList<Instructor> getInstructors() {
        instructors = new ArrayList<>();
        String[] params = {"*"};
        Cursor cursor = db.query("instructors",params,null, null, null, null, null);
        while (cursor.moveToNext()) {
            Instructor instructor = new Instructor();
            instructor.setID(cursor.getInt(cursor.getColumnIndexOrThrow("ID")));
            instructor.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
            instructor.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
            instructor.setPhone(cursor.getString(cursor.getColumnIndexOrThrow("phone")));
            instructors.add(instructor);
        }
        cursor.close();
        return instructors;
    }
}
