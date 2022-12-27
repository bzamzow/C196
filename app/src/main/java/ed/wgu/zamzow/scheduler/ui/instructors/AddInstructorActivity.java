package ed.wgu.zamzow.scheduler.ui.instructors;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import ed.wgu.zamzow.scheduler.R;
import ed.wgu.zamzow.scheduler.database.DBWriter;
import ed.wgu.zamzow.scheduler.objects.Instructor;

public class AddInstructorActivity extends AppCompatActivity {

    private EditText editName, editEmail, editPhone;
    private Button btnCancel, btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instructor);

        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPhone = findViewById(R.id.editPhone);

        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);

        btnCancel.setOnClickListener(view -> finish());

        btnSave.setOnClickListener(view -> {
            Instructor instructor = new Instructor();
            instructor.setName(editName.getText().toString());
            instructor.setEmail(editEmail.getText().toString());
            instructor.setPhone(editPhone.getText().toString());
            DBWriter dbWriter = new DBWriter(this);
            dbWriter.CreateInstructor(instructor);
            finish();
        });
    }
}