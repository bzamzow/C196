package ed.wgu.zamzow.scheduler.objects;

import java.util.ArrayList;
import java.util.Date;

public class Class {

    private final int PLANTOTAKE = 1;
    private final int INPROGRESS = 2;
    private final int COMPLETED = 3;
    private final int DROPPED = 4;


    private String title;
    private String desc;
    private int status;
    private ArrayList<Assessment> assessments;
    private ArrayList<Note> notes;
    private Date start;
    private Date end;
    private int id;
    private int termid;
    private int instructorID;

    public int getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Assessment> getAssessments() {
        return assessments;
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public Date getEnd() {
        return end;
    }

    public Date getStart() {
        return start;
    }

    public int getStatus() {
        return status;
    }

    public int getTermid() {
        return termid;
    }

    public int getInstructorID() {
        return instructorID;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTermid(int termid) {
        this.termid = termid;
    }

    public void setInstructorID(int instructorID) {
        this.instructorID = instructorID;
    }

    public void setAssessments(ArrayList<Assessment> assessments) {
        this.assessments = assessments;
    }
}
