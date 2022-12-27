package ed.wgu.zamzow.scheduler.ui.terms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ed.wgu.zamzow.scheduler.R;
import ed.wgu.zamzow.scheduler.helpers.DateHelper;
import ed.wgu.zamzow.scheduler.objects.Term;
import ed.wgu.zamzow.scheduler.ui.courses.AddClassActivity;

public class TermViewActivity extends AppCompatActivity {

    private TextView txtTitle, txtStart, txtEnd;
    private Term selectedTerm;
    private FloatingActionButton fabAddCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_view);

        if ((selectedTerm = (Term) getIntent().getSerializableExtra("selectedTerm")) != null) {
            txtTitle = findViewById(R.id.txtTitle);
            txtStart = findViewById(R.id.txtStart);
            txtEnd = findViewById(R.id.txtEnd);
            fabAddCourse = findViewById(R.id.fabAddCourse);

            txtTitle.setText(selectedTerm.getTitle());
            txtStart.setText(DateHelper.showDate(selectedTerm.getStart()));
            txtEnd.setText(DateHelper.showDate(selectedTerm.getEnd()));

            fabAddCourse.setOnClickListener(view -> {
                Intent addCourseActivity = new Intent(this, AddClassActivity.class);
                addCourseActivity.putExtra("termID",selectedTerm.getId());
                startActivity(addCourseActivity);
            });
        }
    }
}