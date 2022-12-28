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
import ed.wgu.zamzow.scheduler.objects.Assessment;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.ViewHolder> {

    private final ArrayList<Assessment> assessments;
    private final LayoutInflater inflater;
    public ItemClickListener clickListener;

    public AssessmentAdapter(Context context, ArrayList<Assessment> assessments) {
        inflater = LayoutInflater.from(context);
        this.assessments = assessments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.assessment_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTitle().setText(assessments.get(position).getTitle());
        String type = "Not Set";
        System.out.println("Assessment type: " + assessments.get(position).getType());
        if (assessments.get(position).getType() == 0) {
            type = "Objective";
        } else if (assessments.get(position).getType() == 1) {
            type = "Performance";
        }
        holder.getType().setText(type);
        holder.getEndDate().setText(DateHelper.showDate(assessments.get(position).getEnd()));
    }

    @Override
    public int getItemCount() {
        return assessments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView title;
        private final TextView type;
        private final TextView endDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            type = itemView.findViewById(R.id.startDate);
            endDate = itemView.findViewById(R.id.endDate);
            itemView.setOnClickListener(this);
        }

        public TextView getTitle() {
            return title;
        }

        public TextView getType() {
            return type;
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
