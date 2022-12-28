package ed.wgu.zamzow.scheduler.ui.courses;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import ed.wgu.zamzow.scheduler.R;
import ed.wgu.zamzow.scheduler.adapters.CoursesAdapter;
import ed.wgu.zamzow.scheduler.database.DBReader;
import ed.wgu.zamzow.scheduler.helpers.DateHelper;
import ed.wgu.zamzow.scheduler.objects.Assessment;
import ed.wgu.zamzow.scheduler.objects.Class;
import ed.wgu.zamzow.scheduler.objects.Instructor;
import ed.wgu.zamzow.scheduler.objects.Note;

public class ClassViewActivity extends AppCompatActivity {

    private EditText txtTitle, txtStart, txtEnd;
    private Class selectedClass;
    private FloatingActionButton fabEdit;
    private AppCompatSpinner spinnerInstructor, spinnerStatus;
    private RecyclerView recyclerNotes, recyclerAssessments;
    private ArrayList<Instructor> instructors = new ArrayList<>();
    private ArrayList<Assessment> assessments;
    private ArrayList<Note> notes;
    private DBReader dbReader;
    private final Calendar myCalendar= Calendar.getInstance();
    private boolean isEditMode = false;
    private EditText editThis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_view);

        SetupInterface();
    }

    private void SetupDateFields() {
        DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH,month);
            myCalendar.set(Calendar.DAY_OF_MONTH,day);
            setdate();
        };

        if (isEditMode) {
            txtStart.setOnFocusChangeListener((view, b) -> {
                if (view.hasFocus()) {
                    editThis = txtStart;
                    new DatePickerDialog(ClassViewActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
            txtEnd.setOnFocusChangeListener((view, b) -> {
                if (view.hasFocus()) {
                    editThis = txtEnd;
                    new DatePickerDialog(ClassViewActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
        }
    }

    private void SaveChanges() {

    }

    private void SetupInterface() {

        if ((selectedClass = (Class) getIntent().getSerializableExtra("selectedClass")) != null) {
            txtTitle = findViewById(R.id.txtTitle);
            txtStart = findViewById(R.id.editStart);
            txtEnd = findViewById(R.id.editEnd);
            fabEdit = findViewById(R.id.fabEdit);

            txtTitle.setText(selectedClass.getTitle());


            fabEdit.setOnClickListener(view -> {
                if (!isEditMode) {
                    isEditMode = true;
                    fabEdit.setImageResource(android.R.drawable.ic_menu_save);
                } else {
                    isEditMode = false;
                    fabEdit.setImageResource(android.R.drawable.ic_menu_edit);
                }

                txtTitle.setEnabled(isEditMode);
                txtEnd.setEnabled(isEditMode);
                txtStart.setEnabled(isEditMode);
                spinnerInstructor.setEnabled(isEditMode);
                spinnerStatus.setEnabled(isEditMode);
                SetupDateFields();
            });


            System.out.println(DateHelper.showDate(selectedClass.getStart()));
            System.out.println(DateHelper.showDate(selectedClass.getEnd()));

            txtStart.setText(DateHelper.showDate(selectedClass.getStart()));
            txtEnd.setText(DateHelper.showDate(selectedClass.getEnd()));
        }

        dbReader = new DBReader(this);
        instructors = dbReader.getInstructors();
        spinnerInstructor = findViewById(R.id.spinnerInstructor);
        spinnerStatus = findViewById(R.id.spinnerStatus);

        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this, R.array.status_array, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setEnabled(false);
        spinnerStatus.setAdapter(statusAdapter);

        ArrayAdapter<Instructor> instructorArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, instructors);
        instructorArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerInstructor.setEnabled(false);
        spinnerInstructor.setAdapter(instructorArrayAdapter);

        int i = 0;
        for (Instructor I : instructors) {
            if (I.getID() == selectedClass.getInstructorID()) {
                spinnerInstructor.setSelection(i);
            }
            i++;
        }

        spinnerStatus.setSelection(selectedClass.getStatus());

        SetupAssessments();
        SetupNotes();
    }

    private void SetupAssessments() {
        ArrayList<Class> courses = dbReader.getCourses(selectedClass.getId());
        recyclerAssessments = findViewById(R.id.recyclerNotes);
        recyclerAssessments.setLayoutManager(new LinearLayoutManager(this));
        CoursesAdapter coursesAdapter = new CoursesAdapter(this, courses);
        coursesAdapter.setClickListener((view, position) -> {
            Class selectedClass = courses.get(position);
            Intent termView = new Intent(this, ClassViewActivity.class);
            termView.putExtra("selectedClass", selectedClass);
            startActivity(termView);
        });
        recyclerAssessments.setAdapter(coursesAdapter);
    }

    private void SetupNotes() {
        ArrayList<Class> courses = dbReader.getCourses(selectedClass.getId());
        recyclerNotes = findViewById(R.id.recyclerNotes);
        recyclerNotes.setLayoutManager(new LinearLayoutManager(this));
        CoursesAdapter coursesAdapter = new CoursesAdapter(this, courses);
        coursesAdapter.setClickListener((view, position) -> {
            Class selectedClass = courses.get(position);
            Intent termView = new Intent(this, ClassViewActivity.class);
            termView.putExtra("selectedClass", selectedClass);
            startActivity(termView);
        });
        recyclerNotes.setAdapter(coursesAdapter);
    }

    public void setdate() {
        String dateFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        editThis.setText(sdf.format(myCalendar.getTime()));
    }
}