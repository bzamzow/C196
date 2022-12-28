package ed.wgu.zamzow.scheduler.ui.courses;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import ed.wgu.zamzow.scheduler.R;
import ed.wgu.zamzow.scheduler.database.DBReader;
import ed.wgu.zamzow.scheduler.database.DBWriter;
import ed.wgu.zamzow.scheduler.helpers.DateHelper;
import ed.wgu.zamzow.scheduler.objects.Class;
import ed.wgu.zamzow.scheduler.objects.Instructor;
import ed.wgu.zamzow.scheduler.objects.Term;
import ed.wgu.zamzow.scheduler.ui.instructors.AddInstructorActivity;

public class AddClassActivity extends AppCompatActivity {

    private EditText editTitle, editDesc, editStart, editEnd;
    private AppCompatSpinner spinnerInstructor, spinnerStatus;
    private Term selectedTerm;
    private ArrayList<Instructor> instructors = new ArrayList<>();
    private Button btnCancel, btnSave;
    private EditText editThis;
    private final Calendar myCalendar= Calendar.getInstance();
    private final int ADD_INSTRUCTOR = 110;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        selectedTerm = (Term) getIntent().getSerializableExtra("selectedTerm");

        SetupInterface();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_class_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_instructor:
               addInstructor();
               return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addInstructor() {
        Intent addInstructorActivity = new Intent(getApplication(), AddInstructorActivity.class);
        startActivityForResult(addInstructorActivity, ADD_INSTRUCTOR);
    }

    private void SetupInterface() {

        DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH,month);
            myCalendar.set(Calendar.DAY_OF_MONTH,day);
            setdate();
        };

        DBReader dbReader = new DBReader(this);
        instructors = dbReader.getInstructors();
        editDesc = findViewById(R.id.editDesc);
        editEnd = findViewById(R.id.editEndDate);
        editStart = findViewById(R.id.editStartDate);
        editTitle = findViewById(R.id.editClassName);
        spinnerInstructor = findViewById(R.id.spinnerInstructor);
        spinnerStatus = findViewById(R.id.spinnerStatus);
        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);

        editStart.setOnFocusChangeListener((view, b) -> {
            if (view.hasFocus()) {
                editThis = editStart;
                new DatePickerDialog(AddClassActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        editEnd.setOnFocusChangeListener((view, b) -> {
            if (view.hasFocus()) {
                editThis = editEnd;
                new DatePickerDialog(AddClassActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this, R.array.status_array, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(statusAdapter);

        ArrayAdapter<Instructor> instructorArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, instructors);
        instructorArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerInstructor.setAdapter(instructorArrayAdapter);

        if (instructors.size() == 0 || instructors == null) {

            MaterialAlertDialogBuilder alertDialog = new MaterialAlertDialogBuilder(this)
                    .setTitle("No Instructors")
                    .setMessage("There are no instructors available. Would you like to add one now?")
                    .setPositiveButton("Yes", (dialogInterface, i) -> {
                        addInstructor();
                    })
                    .setNegativeButton("No", ((dialogInterface, i) -> finish()));
            alertDialog.show();

        }

        btnCancel.setOnClickListener(view -> finish());

        btnSave.setOnClickListener(view -> {
            Class course = new Class();
            course.setTitle(editTitle.getText().toString());
            course.setStatus(spinnerStatus.getSelectedItemPosition());
            course.setStart(DateHelper.getDate(editStart.getText().toString()));
            course.setEnd(DateHelper.getDate(editEnd.getText().toString()));
            course.setDesc(editDesc.getText().toString());
            course.setInstructorID(((Instructor)spinnerInstructor.getSelectedItem()).getID());
            course.setTermid(selectedTerm.getId());
            DBWriter dbWriter = new DBWriter(this);
            dbWriter.CreateCourse(course);
            finish();
        });
    }

    public void setdate() {
        String dateFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        editThis.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_INSTRUCTOR) {
            SetupInterface();
        }
    }
}