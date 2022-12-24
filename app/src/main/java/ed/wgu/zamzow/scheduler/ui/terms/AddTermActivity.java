package ed.wgu.zamzow.scheduler.ui.terms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ed.wgu.zamzow.scheduler.R;
import ed.wgu.zamzow.scheduler.database.DBWriter;
import ed.wgu.zamzow.scheduler.helpers.DateHelper;
import ed.wgu.zamzow.scheduler.helpers.Vars;
import ed.wgu.zamzow.scheduler.objects.Term;

public class AddTermActivity extends AppCompatActivity {

    final Calendar myCalendar= Calendar.getInstance();
    private EditText editStart;
    private EditText editEnd;
    private EditText editThis;
    private EditText editTitle;
    private Button btnSave;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);

        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                setdate();
            }
        };

        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        editTitle = findViewById(R.id.editTermName);
        editStart = findViewById(R.id.editStartDate);
        editEnd = findViewById(R.id.editEndDate);
        editStart.setOnFocusChangeListener((view, b) -> {
            if (view.hasFocus()) {
                editThis = editStart;
                new DatePickerDialog(AddTermActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        editEnd.setOnFocusChangeListener((view, b) -> {
            if (view.hasFocus()) {
                editThis = editEnd;
                new DatePickerDialog(AddTermActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnSave.setOnClickListener(view -> {
            Term term = new Term();
            term.setTitle(editTitle.getText().toString());
            term.setStart(DateHelper.getDate(editStart.getText().toString()));
            term.setEnd(DateHelper.getDate(editEnd.getText().toString()));
            DBWriter dbWriter = new DBWriter(this);
            dbWriter.CreateTerm(term);
            Vars.terms.add(term);
            finish();
        });

        btnCancel.setOnClickListener(view -> {
            finish();
        });
    }

    private void setdate() {
        String dateFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        editThis.setText(sdf.format(myCalendar.getTime()));
    }
}