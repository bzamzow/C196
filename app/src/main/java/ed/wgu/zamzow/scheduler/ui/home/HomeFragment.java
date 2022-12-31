package ed.wgu.zamzow.scheduler.ui.home;

import android.Manifest;
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

import java.util.ArrayList;
import java.util.Random;

import ed.wgu.zamzow.scheduler.MainActivity;
import ed.wgu.zamzow.scheduler.database.DBReader;
import ed.wgu.zamzow.scheduler.database.SchedulerDB;
import ed.wgu.zamzow.scheduler.databinding.FragmentHomeBinding;
import ed.wgu.zamzow.scheduler.helpers.Vars;
import ed.wgu.zamzow.scheduler.objects.Term;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private SchedulerDB schedulerDB;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


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

        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}