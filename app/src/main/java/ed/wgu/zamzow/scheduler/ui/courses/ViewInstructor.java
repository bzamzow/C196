package ed.wgu.zamzow.scheduler.ui.courses;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import ed.wgu.zamzow.scheduler.R;
import ed.wgu.zamzow.scheduler.objects.Class;
import ed.wgu.zamzow.scheduler.objects.Instructor;

public class ViewInstructor extends AppCompatActivity {

    private EditText editName, editPhone, editEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_instructor);

        Instructor selectedInstructor = (Instructor) getIntent().getSerializableExtra("selectedInstructor");

        editEmail = findViewById(R.id.editEmail);
        editPhone = findViewById(R.id.editPhone);
        editName = findViewById(R.id.editName);

        editName.setText(selectedInstructor.getName());
        editPhone.setText(selectedInstructor.getPhone());
        editEmail.setText(selectedInstructor.getEmail());
    }
}