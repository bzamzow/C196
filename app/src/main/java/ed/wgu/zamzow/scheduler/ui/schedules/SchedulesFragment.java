package ed.wgu.zamzow.scheduler.ui.schedules;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ed.wgu.zamzow.scheduler.databinding.FragmentSchedulesBinding;

public class SchedulesFragment extends Fragment {

    private FragmentSchedulesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SchedulesViewModel schedulesViewModel =
                new ViewModelProvider(this).get(SchedulesViewModel.class);

        binding = FragmentSchedulesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        schedulesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}