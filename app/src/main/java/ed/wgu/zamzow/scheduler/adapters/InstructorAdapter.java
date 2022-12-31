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
import ed.wgu.zamzow.scheduler.objects.Instructor;

public class InstructorAdapter extends RecyclerView.Adapter<InstructorAdapter.ViewHolder> {

    private final ArrayList<Instructor> instructors;
    private final LayoutInflater inflater;
    public ItemClickListener clickListener;

    public InstructorAdapter(Context context, ArrayList<Instructor> instructors) {
        inflater = LayoutInflater.from(context);
        this.instructors = instructors;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.instructor_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getName().setText(instructors.get(position).getName());
        holder.getEmail().setText(instructors.get(position).getEmail());
        holder.getPhone().setText(instructors.get(position).getPhone());
        System.out.println(instructors.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return instructors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView name;
        private final TextView email;
        private final TextView phone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.txtEmail);
            phone = itemView.findViewById(R.id.txtPhone);
            itemView.setOnClickListener(this);
        }

        public TextView getName() {
            return name;
        }

        public TextView getEmail() {
            return email;
        }

        public TextView getPhone() {
            return phone;
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
