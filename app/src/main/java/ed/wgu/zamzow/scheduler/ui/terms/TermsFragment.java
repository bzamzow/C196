package ed.wgu.zamzow.scheduler.ui.terms;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import ed.wgu.zamzow.scheduler.R;
import ed.wgu.zamzow.scheduler.adapters.TermsAdapter;
import ed.wgu.zamzow.scheduler.database.DBReader;
import ed.wgu.zamzow.scheduler.databinding.FragmentTermsBinding;
import ed.wgu.zamzow.scheduler.helpers.Vars;
import ed.wgu.zamzow.scheduler.objects.Term;

public class TermsFragment extends Fragment {

    private FragmentTermsBinding binding;
    private final int ADD_TERM = 111;
    private View root;
    private ArrayList<Term> terms;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TermsViewModel termsViewModel =
                new ViewModelProvider(this).get(TermsViewModel.class);

        binding = FragmentTermsBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        FloatingActionButton btnAddTerm = root.findViewById(R.id.fabAddTerm);
        btnAddTerm.setOnClickListener(view -> {
            Intent addTerm = new Intent(getActivity(), AddTermActivity.class);
            startActivityForResult(addTerm, ADD_TERM);
        });


        SetupInterface();


        return root;
    }

    private void SetupInterface() {

        DBReader dbReader = new DBReader(getContext());
        terms = dbReader.getTerms();
        RecyclerView recyclerTerms = root.findViewById(R.id.recyclerClasses);
        recyclerTerms.setLayoutManager(new LinearLayoutManager(getActivity()));
        TermsAdapter termsAdapter = new TermsAdapter(getActivity(), terms);
        termsAdapter.setClickListener((view, position) -> {
            Term selectedTerm = terms.get(position);
            Intent termView = new Intent(getActivity(), TermViewActivity.class);
            termView.putExtra("selectedTerm", selectedTerm);
            startActivity(termView);
        });
        recyclerTerms.setAdapter(termsAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_TERM) {
            SetupInterface();
        }
    }
}