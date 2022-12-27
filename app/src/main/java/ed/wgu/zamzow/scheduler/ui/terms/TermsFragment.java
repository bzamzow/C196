package ed.wgu.zamzow.scheduler.ui.terms;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ed.wgu.zamzow.scheduler.R;
import ed.wgu.zamzow.scheduler.adapters.TermsAdapter;
import ed.wgu.zamzow.scheduler.databinding.FragmentTermsBinding;
import ed.wgu.zamzow.scheduler.helpers.Vars;
import ed.wgu.zamzow.scheduler.objects.Term;

public class TermsFragment extends Fragment {

    private FragmentTermsBinding binding;
    private FloatingActionButton btnAddTerm;
    private RecyclerView recyclerTerms;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TermsViewModel termsViewModel =
                new ViewModelProvider(this).get(TermsViewModel.class);

        binding = FragmentTermsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btnAddTerm = root.findViewById(R.id.fabAddTerm);
        btnAddTerm.setOnClickListener(view -> {
            Intent addTerm = new Intent(getActivity(), AddTermActivity.class);
            startActivity(addTerm);
        });

        System.out.println("Setting up recycler");
        recyclerTerms = root.findViewById(R.id.recyclerTerms);
        recyclerTerms.setLayoutManager(new LinearLayoutManager(getActivity()));
        TermsAdapter termsAdapter = new TermsAdapter(getActivity(), Vars.terms);
        termsAdapter.setClickListener((view, position) -> {
            Term selectedTerm = Vars.terms.get(position);
            Intent termView = new Intent(getActivity(), TermViewActivity.class);
            termView.putExtra("selectedTerm", selectedTerm);
            startActivity(termView);
        });
        recyclerTerms.setAdapter(termsAdapter);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}