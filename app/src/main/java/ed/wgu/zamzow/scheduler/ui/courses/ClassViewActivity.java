package ed.wgu.zamzow.scheduler.ui.courses;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import ed.wgu.zamzow.scheduler.adapters.AssessmentAdapter;
import ed.wgu.zamzow.scheduler.adapters.CoursesAdapter;
import ed.wgu.zamzow.scheduler.adapters.NotesAdapter;
import ed.wgu.zamzow.scheduler.database.DBReader;
import ed.wgu.zamzow.scheduler.helpers.DateHelper;
import ed.wgu.zamzow.scheduler.objects.Assessment;
import ed.wgu.zamzow.scheduler.objects.Class;
import ed.wgu.zamzow.scheduler.objects.Instructor;
import ed.wgu.zamzow.scheduler.objects.Note;

public class ClassViewActivity extends AppCompatActivity {

    private EditText txtTitle, txtStart, txtEnd, txtDesc;
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
    private final int ADD_NOTE = 114;
    private final int ADD_ASSESSMENT = 115;


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

    private void AddAssessment() {
        Intent AddAssessmentIntent = new Intent(this, AddAssessmentActivity.class);
        AddAssessmentIntent.putExtra("selectedClass", selectedClass);
        startActivityForResult(AddAssessmentIntent, ADD_ASSESSMENT);
    }

    private void AddNote() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.classes_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_assessment:
                AddAssessment();
                return true;
            case R.id.add_note:
                AddNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SetupInterface();
    }

    private void SetupInterface() {

        if ((selectedClass = (Class) getIntent().getSerializableExtra("selectedClass")) != null) {
            txtTitle = findViewById(R.id.txtTitle);
            txtStart = findViewById(R.id.editStart);
            txtEnd = findViewById(R.id.editEnd);
            txtDesc = findViewById(R.id.txtDesc);
            fabEdit = findViewById(R.id.fabEdit);

            txtTitle.setText(selectedClass.getTitle());
            txtDesc.setText(selectedClass.getDesc());


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
                txtDesc.setEnabled(isEditMode);
                SetupDateFields();
            });

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
        ArrayList<Assessment> assessments = dbReader.getAssessments(selectedClass.getId());
        recyclerAssessments = findViewById(R.id.recyclerAssessments);
        recyclerAssessments.setLayoutManager(new LinearLayoutManager(this));
        AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this, assessments);
        assessmentAdapter.setClickListener((view, position) -> {
            Assessment assessment = assessments.get(position);
            Intent termView = new Intent(this, ClassViewActivity.class);
            termView.putExtra("selectedClass", selectedClass);
            startActivity(termView);
        });
        recyclerAssessments.setAdapter(assessmentAdapter);
    }

    private void SetupNotes() {
        ArrayList<Note> notes = dbReader.getNotes(selectedClass.getId());
        recyclerNotes = findViewById(R.id.recyclerNotes);
        recyclerNotes.setLayoutManager(new LinearLayoutManager(this));
        NotesAdapter coursesAdapter = new NotesAdapter(this, notes);
        coursesAdapter.setClickListener((view, position) -> {
            Note selectedNote = notes.get(position);
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