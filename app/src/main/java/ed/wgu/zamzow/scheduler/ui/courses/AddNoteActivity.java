package ed.wgu.zamzow.scheduler.ui.courses;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import ed.wgu.zamzow.scheduler.R;
import ed.wgu.zamzow.scheduler.database.DBWriter;
import ed.wgu.zamzow.scheduler.objects.Class;
import ed.wgu.zamzow.scheduler.objects.Note;

public class AddNoteActivity extends AppCompatActivity {

    private Class selectedClass;
    private EditText editTitle, editNote;
    private Button btnCancel, btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        selectedClass = (Class) getIntent().getSerializableExtra("selectedClass");
        editTitle = findViewById(R.id.editTitle);
        editNote = findViewById(R.id.editNote);
        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);

        btnCancel.setOnClickListener(view -> finish());
        btnSave.setOnClickListener(view -> {
            Note note = new Note();
            note.setTitle(editTitle.getText().toString());
            note.setNote(editNote.getText().toString());
            note.setClassID(selectedClass.getId());
            DBWriter dbWriter = new DBWriter(this);
            dbWriter.CreateNote(note);
            finish();
        });
    }
}