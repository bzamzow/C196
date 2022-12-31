package ed.wgu.zamzow.scheduler.ui.terms;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import ed.wgu.zamzow.scheduler.R;
import ed.wgu.zamzow.scheduler.adapters.CoursesAdapter;
import ed.wgu.zamzow.scheduler.database.DBReader;
import ed.wgu.zamzow.scheduler.database.DBWriter;
import ed.wgu.zamzow.scheduler.helpers.DateHelper;
import ed.wgu.zamzow.scheduler.objects.Class;
import ed.wgu.zamzow.scheduler.objects.Term;
import ed.wgu.zamzow.scheduler.ui.courses.AddClassActivity;
import ed.wgu.zamzow.scheduler.ui.courses.ClassViewActivity;

public class TermViewActivity extends AppCompatActivity {

    private TextView txtTitle, txtStart, txtEnd;
    private Term selectedTerm;
    private FloatingActionButton fabAddCourse;
    private RecyclerView recyclerCourses;
    private final int VIEW_COURSE = 113;
    private ArrayList<Class> courses;

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
                addCourseActivity.putExtra("selectedTerm",selectedTerm);
                startActivityForResult(addCourseActivity, 114);
            });
        }
    }

    private void DeleteTerm() {
        if (courses.size() > 0) {
            MaterialAlertDialogBuilder coursesError = new MaterialAlertDialogBuilder(this)
                    .setTitle("Cannot Delete")
                    .setMessage("There are " + courses.size() + " course(s) associated with this term. It cannot be deleted")
                    .setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss());
            coursesError.show();
        } else {

            MaterialAlertDialogBuilder deleteCourse = new MaterialAlertDialogBuilder(this)
                    .setTitle("Delete Term")
                    .setMessage("Are you sure you want to delete " + selectedTerm.getTitle() + "?")
                    .setPositiveButton("OK", (dialogInterface, i) -> {
                        DBWriter dbWriter = new DBWriter(this);
                        dbWriter.DeleteTerm(selectedTerm);
                        finish();
                    })
                    .setNegativeButton("Cancel", ((dialogInterface, i) -> dialogInterface.dismiss()));
            deleteCourse.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.terms_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_term:
                DeleteTerm();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void SetupInterface() {
        DBReader dbReader = new DBReader(this);
        courses = dbReader.getCourses(selectedTerm.getId());
        recyclerCourses = findViewById(R.id.recyclerClasses);
        recyclerCourses.setLayoutManager(new LinearLayoutManager(this));
        CoursesAdapter coursesAdapter = new CoursesAdapter(this, courses);
        coursesAdapter.setClickListener((view, position) -> {
            Class selectedClass = courses.get(position);
            Intent classView = new Intent(this, ClassViewActivity.class);
            classView.putExtra("selectedClass", selectedClass);
            startActivityForResult(classView,VIEW_COURSE);
        });
        recyclerCourses.setAdapter(coursesAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        SetupInterface();
        super.onActivityResult(requestCode, resultCode, data);
    }
}