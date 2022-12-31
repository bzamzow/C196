package ed.wgu.zamzow.scheduler.ui.courses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import ed.wgu.zamzow.scheduler.R;
import ed.wgu.zamzow.scheduler.database.DBWriter;
import ed.wgu.zamzow.scheduler.helpers.DateHelper;
import ed.wgu.zamzow.scheduler.helpers.NotificationReceiver;
import ed.wgu.zamzow.scheduler.objects.Assessment;

public class AssessmentViewActivity extends AppCompatActivity {

    private Assessment selectedAssessment;
    private EditText editTitle, editEnd;
    private AppCompatSpinner spinnerType;
    private EditText editThis;
    private final Calendar myCalendar= Calendar.getInstance();
    private FloatingActionButton fabEdit;
    private boolean isEditMode = false;
    public static String NOTIFICATION_ID = "notification-id" ;
    public static String NOTIFICATION = "notification" ;

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

    private void CreateReminder() {
        Date dueDate = selectedAssessment.getEnd();
        Calendar dayBefore = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        today.setTime(dueDate);;
        today.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH),11,2);
        dayBefore.setTime(dueDate);;
        dayBefore.add(Calendar.DAY_OF_MONTH, -1);
        dayBefore.set(dayBefore.get(Calendar.YEAR), dayBefore.get(Calendar.MONTH), dayBefore.get(Calendar.DAY_OF_MONTH),11,2);


        scheduleNotification(getNotification(selectedAssessment.getTitle() + " is due today"),today.getTimeInMillis());
        if (Calendar.getInstance().before(dayBefore)) {
            scheduleNotification(getNotification(selectedAssessment.getTitle() + " is due tomorrow"), dayBefore.getTimeInMillis());
        }
    }

    private void scheduleNotification (Notification notification , long delay) {
        Intent notificationIntent = new Intent( this, NotificationReceiver.class ) ;
        notificationIntent.putExtra(NotificationReceiver.NOTIFICATION_ID , new Random().nextInt() ) ;
        notificationIntent.putExtra(NotificationReceiver.NOTIFICATION , notification) ;
        PendingIntent pendingIntent = PendingIntent.getBroadcast ( getApplicationContext(), new Random().nextInt(), notificationIntent , PendingIntent.FLAG_IMMUTABLE ) ;

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE ) ;
        assert alarmManager != null;
        alarmManager.set(AlarmManager.RTC_WAKEUP, delay, pendingIntent) ;
    }
    private Notification getNotification (String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder( this, "default" ) ;
        builder.setContentTitle( "Due Date" ) ;
        builder.setContentText(content) ;
        builder.setSmallIcon(android.R.drawable.ic_lock_idle_alarm ) ;
        builder.setAutoCancel( true ) ;
        builder.setChannelId( "47" ) ;
        return builder.build() ;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.assessment_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.assessment_notify:
                CreateReminder();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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