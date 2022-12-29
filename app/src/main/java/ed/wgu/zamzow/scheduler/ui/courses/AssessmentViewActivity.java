package ed.wgu.zamzow.scheduler.ui.courses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ed.wgu.zamzow.scheduler.R;
import ed.wgu.zamzow.scheduler.database.DBWriter;
import ed.wgu.zamzow.scheduler.helpers.DateHelper;
import ed.wgu.zamzow.scheduler.objects.Assessment;
import ed.wgu.zamzow.scheduler.objects.Class;

public class AssessmentViewActivity extends AppCompatActivity {

    private Assessment selectedAssessment;
    private EditText editTitle, editEnd;
    private AppCompatSpinner spinnerType;
    private EditText editThis;
    private final Calendar myCalendar= Calendar.getInstance();
    private FloatingActionButton fabEdit;
    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_view);

        DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH,month);
            myCalendar.set(Calendar.DAY_OF_MONTH,day);
            setdate();
        };

        selectedAssessment = (Assessment) getIntent().getSerializableExtra("selectedAssessment");

        fabEdit = findViewById(R.id.fabEdit);

        editTitle = findViewById(R.id.editAssessmentName);
        editEnd = findViewById(R.id.editEndDate);
        spinnerType = findViewById(R.id.spinnerType);


        editEnd.setOnFocusChangeListener((view, b) -> {
            if (view.hasFocus()) {
                editThis = editEnd;
                new DatePickerDialog(AssessmentViewActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this, R.array.assessment_type_array, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setEnabled(false);
        spinnerType.setAdapter(statusAdapter);

        spinnerType.setSelection(selectedAssessment.getType());

        fabEdit.setOnClickListener(view -> {
            if (!isEditMode) {
                isEditMode = true;
                fabEdit.setImageResource(android.R.drawable.ic_menu_save);
            } else {
                SaveChanges();
                isEditMode = false;
                fabEdit.setImageResource(android.R.drawable.ic_menu_edit);
            }

            editTitle.setEnabled(isEditMode);
            editEnd.setEnabled(isEditMode);
            spinnerType.setEnabled(isEditMode);
        });

        editTitle.setText(selectedAssessment.getTitle());
        editEnd.setText(DateHelper.showAltDate(selectedAssessment.getEnd()));

    }

    private void SaveChanges() {
        Assessment assessment = new Assessment();
        assessment.setID(selectedAssessment.getID());
        assessment.setTitle(editTitle.getText().toString());
        assessment.setEnd(DateHelper.getDate(editEnd.getText().toString()));
        assessment.setType(spinnerType.getSelectedItemPosition());
        assessment.setClassID(selectedAssessment.getClassID());

        DBWriter dbWriter = new DBWriter(this);
        dbWriter.UpdateAssessment(assessment);
        finish();
    }

    public void setdate() {
        String dateFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        editThis.setText(sdf.format(myCalendar.getTime()));
    }
}