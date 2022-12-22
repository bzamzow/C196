package ed.wgu.zamzow.scheduler.objects;

public class Note {

    private String title;
    private String note;
    private int id;
    private int classID;

    public String getNote() {
        return note;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public int getClassID() {
        return classID;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }
}
