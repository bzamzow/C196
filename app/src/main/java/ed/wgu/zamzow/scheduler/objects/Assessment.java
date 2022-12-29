package ed.wgu.zamzow.scheduler.objects;

import java.io.Serializable;
import java.util.Date;

import kotlin.jvm.internal.PropertyReference0Impl;

public class Assessment implements Serializable {

    private final int OBJECTIVE = 1;
    private final int PERFORMANCE = 2;

    private int type;
    private String title;
    private String desc;
    private int ID;
    private int classID;
    private Date end;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public int getID() {
        return ID;
    }

    public int getClassID() {
        return classID;
    }

    public Date getEnd() {
        return end;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
