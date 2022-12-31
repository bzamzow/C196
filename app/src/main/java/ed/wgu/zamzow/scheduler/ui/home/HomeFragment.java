package ed.wgu.zamzow.scheduler.ui.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import ed.wgu.zamzow.scheduler.MainActivity;
import ed.wgu.zamzow.scheduler.R;
import ed.wgu.zamzow.scheduler.adapters.CoursesAdapter;
import ed.wgu.zamzow.scheduler.adapters.InstructorAdapter;
import ed.wgu.zamzow.scheduler.database.DBReader;
import ed.wgu.zamzow.scheduler.database.SchedulerDB;
import ed.wgu.zamzow.scheduler.databinding.FragmentHomeBinding;
import ed.wgu.zamzow.scheduler.helpers.Vars;
import ed.wgu.zamzow.scheduler.objects.Class;
import ed.wgu.zamzow.scheduler.objects.Instructor;
import ed.wgu.zamzow.scheduler.objects.Term;
import ed.wgu.zamzow.scheduler.ui.courses.ClassViewActivity;
import ed.wgu.zamzow.scheduler.ui.courses.ViewInstructor;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private SchedulerDB schedulerDB;
    private TextView txtCurrentTerm;
    private RecyclerView currentCourses, currentInstructors;
    private boolean foundTerm;
    private ArrayList<Class> courses;
    private ArrayList<Instructor> instructors;
    private final int VIEW_COURSE = 113;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        txtCurrentTerm = binding.txtCurrTerm;
        currentCourses = binding.currentCourses;
        currentInstructors = binding.currentInstructors;


        schedulerDB = new SchedulerDB(getContext());
        SQLiteDatabase sqLiteDatabase = schedulerDB.getWritableDatabase();
        schedulerDB.onCreate(sqLiteDatabase);

        ActivityResultLauncher<String[]> notificationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean postNotification = result.getOrDefault(
                                    Manifest.permission.POST_NOTIFICATIONS, false);

                            if (postNotification != null) {
                                System.out.println("Access Granted");
                            } else {
                                System.out.println("No notifications granted");
                            }
                        }
                );

        notificationPermissionRequest.launch(new String[] {Manifest.permission.POST_NOTIFICATIONS});

        SetupInterface();

        return root;

    }

    private void SetupInterface() {
        DBReader dbReader = new DBReader(getContext());
        ArrayList<Term> terms = dbReader.getTerms();
        instructors = new ArrayList<>();
        foundTerm = false;
        terms.forEach((t) -> {
            Calendar now = Calendar.getInstance();
            Calendar start = Calendar.getInstance();
            start.setTime(t.getStart());
            start.add(Calendar.DATE,-1);
            Calendar end = Calendar.getInstance();
            end.setTime(t.getEnd());
            end.add(Calendar.DATE,1);
            if (now.after(start) && now.before(end) ) {
                foundTerm = true;
                txtCurrentTerm.setText(t.getTitle());
                courses = dbReader.getCurrentCourses(t.getId());
                currentCourses.setLayoutManager(new LinearLayoutManager(getContext()));
                CoursesAdapter coursesAdapter = new CoursesAdapter(getContext(), courses);
                coursesAdapter.setClickListener((view, position) -> {
                    Class selectedClass = courses.get(position);
                    Intent classView = new Intent(getContext(), ClassViewActivity.class);
                    classView.putExtra("selectedClass", selectedClass);
                    startActivityForResult(classView,VIEW_COURSE);
                });
                currentCourses.setAdapter(coursesAdapter);
                courses.forEach((c) -> {
                    instructors.add(dbReader.getInstructor(c.getInstructorID()));
                });

                currentInstructors.setLayoutManager(new LinearLayoutManager(getContext()));
                InstructorAdapter instructorAdapter = new InstructorAdapter(getContext(), instructors);
                instructorAdapter.setClickListener(((view, position) -> {
                    Instructor selectedInstructor = instructors.get(position);
                    Intent instructorView = new Intent(getContext(), ViewInstructor.class);
                    instructorView.putExtra("selectedInstructor", selectedInstructor);
                    startActivityForResult(instructorView, 116);
                }));
                currentInstructors.setAdapter(instructorAdapter);
            }
        });
        if (!foundTerm) {
            txtCurrentTerm.setText("N/A");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}