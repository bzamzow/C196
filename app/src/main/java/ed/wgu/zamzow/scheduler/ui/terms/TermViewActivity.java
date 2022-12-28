package ed.wgu.zamzow.scheduler.ui.terms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import ed.wgu.zamzow.scheduler.R;
import ed.wgu.zamzow.scheduler.adapters.CoursesAdapter;
import ed.wgu.zamzow.scheduler.adapters.TermsAdapter;
import ed.wgu.zamzow.scheduler.database.DBReader;
import ed.wgu.zamzow.scheduler.helpers.DateHelper;
import ed.wgu.zamzow.scheduler.helpers.Vars;
import ed.wgu.zamzow.scheduler.objects.Class;
import ed.wgu.zamzow.scheduler.objects.Term;
import ed.wgu.zamzow.scheduler.ui.courses.AddClassActivity;

public class TermViewActivity extends AppCompatActivity {

    private TextView txtTitle, txtStart, txtEnd;
    private Term selectedTerm;
    private FloatingActionButton fabAddCourse;
    private RecyclerView recyclerCourses;

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

            SetupInterface();

            fabAddCourse.setOnClickListener(view -> {
                Intent addCourseActivity = new Intent(this, AddClassActivity.class);
                addCourseActivity.putExtra("termID",selectedTerm.getId());
                startActivity(addCourseActivity);
            });
        }
    }

    private void SetupInterface() {
        DBReader dbReader = new DBReader(this);
        ArrayList<Class> courses = dbReader.getCourses(selectedTerm.getId());
        recyclerCourses = findViewById(R.id.recyclerClasses);
        recyclerCourses.setLayoutManager(new LinearLayoutManager(this));
        CoursesAdapter coursesAdapter = new CoursesAdapter(this, courses);
        coursesAdapter.setClickListener((view, position) -> {
            Class selectedClass = courses.get(position);
            Intent termView = new Intent(this, TermViewActivity.class);
            termView.putExtra("selectedClass", selectedClass);
            startActivity(termView);
        });
        recyclerCourses.setAdapter(coursesAdapter);
    }
}