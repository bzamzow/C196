package ed.wgu.zamzow.scheduler.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Term implements Serializable {

    private String title;
    private Date start;
    private Date end;
    private ArrayList<Class> classes;
    private int id;

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Class> getClasses() {
        return classes;
    }

    public int getId() {
        return id;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setClasses(ArrayList<Class> classes) {
        this.classes = classes;
    }

    public void setId(int id) {
        this.id = id;
    }
}
