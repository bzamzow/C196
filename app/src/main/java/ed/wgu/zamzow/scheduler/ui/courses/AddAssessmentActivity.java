package ed.wgu.zamzow.scheduler.ui.courses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ed.wgu.zamzow.scheduler.R;
import ed.wgu.zamzow.scheduler.database.DBWriter;
import ed.wgu.zamzow.scheduler.helpers.DateHelper;
import ed.wgu.zamzow.scheduler.objects.Assessment;
import ed.wgu.zamzow.scheduler.objects.Class;
import ed.wgu.zamzow.scheduler.objects.Term;

public class AddAssessmentActivity extends AppCompatActivity {


    private EditText editTitle, editEnd;
    private Button btnCancel, btnSave;
    private AppCompatSpinner spinnerType;
    private EditText editThis;
    private final Calendar myCalendar= Calendar.getInstance();
    private Class selectedClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);

        DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH,month);
            myCalendar.set(Calendar.DAY_OF_MONTH,day);
            setdate();
        };

        selectedClass = (Class) getIntent().getSerializableExtra("selectedClass");


        editTitle = findViewById(R.id.editAssessmentName);
        editEnd = findViewById(R.id.editEndDate);
        spinnerType = findViewById(R.id.spinnerType);

        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);

        btnCancel.setOnClickListener(view -> finish());

        btnSave.setOnClickListener((view -> {
            Assessment assessment = new Assessment();
            assessment.setTitle(editTitle.getText().toString());
            assessment.setEnd(DateHelper.getDate(editEnd.getText().toString()));
            assessment.setType(spinnerType.getSelectedItemPosition());
            assessment.setClassID(selectedClass.getId());

            DBWriter dbWriter = new DBWriter(this);
            dbWriter.CreateAssessment(assessment);
            finish();

        }));

        editEnd.setOnFocusChangeListener((view, b) -> {
            if (view.hasFocus()) {
                editThis = editEnd;
                new DatePickerDialog(AddAssessmentActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this, R.array.assessment_type_array, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(statusAdapter);
    }



    public void setdate() {
        String dateFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        editThis.setText(sdf.format(myCalendar.getTime()));
    }
}