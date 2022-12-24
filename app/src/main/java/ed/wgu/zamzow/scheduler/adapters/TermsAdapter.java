package ed.wgu.zamzow.scheduler.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ed.wgu.zamzow.scheduler.R;
import ed.wgu.zamzow.scheduler.helpers.DateHelper;
import ed.wgu.zamzow.scheduler.objects.Term;

public class TermsAdapter extends RecyclerView.Adapter<TermsAdapter.ViewHolder> {

    private final ArrayList<Term> terms;
    private final LayoutInflater inflater;
    public ItemClickListener clickListener;

    public TermsAdapter(Context context, ArrayList<Term> terms) {
        System.out.println("Launched adapter");
        inflater = LayoutInflater.from(context);
        this.terms = terms;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.term_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        System.out.println("Bound");
        holder.getTermName().setText(terms.get(position).getTitle());
        holder.getStartDate().setText(DateHelper.showDate(terms.get(position).getStart()));
        holder.getEndDate().setText(DateHelper.showDate(terms.get(position).getEnd()));

        System.out.println("Set " + terms.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return terms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView termName;
        private final TextView startDate;
        private final TextView endDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            termName = itemView.findViewById(R.id.termName);
            startDate = itemView.findViewById(R.id.startDate);
            endDate = itemView.findViewById(R.id.endDate);
            itemView.setOnClickListener(this);
        }

        public TextView getTermName() {
            return termName;
        }

        public TextView getStartDate() {
            return startDate;
        }

        public TextView getEndDate() {
            return endDate;
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
