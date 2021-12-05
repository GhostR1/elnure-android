package ua.nure.lab4.ui.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ua.nure.lab4.R;
import ua.nure.lab4.api.model.Group;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private final List<String> students;

    public StudentAdapter(Context context, List<String> students) {
        this.students = students;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_student, parent, false);
        return new StudentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StudentAdapter.ViewHolder holder, int position) {
        String student = students.get(position);
        holder.tvStudentName.setText(student);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvStudentName;
        ViewHolder(View view){
            super(view);
            tvStudentName = view.findViewById(R.id.tv_recycle_student_name);
        }
    }
}
