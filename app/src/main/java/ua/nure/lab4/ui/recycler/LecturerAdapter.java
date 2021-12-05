package ua.nure.lab4.ui.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ua.nure.lab4.R;
import ua.nure.lab4.api.model.Group;
import ua.nure.lab4.api.model.Lecturer;

public class LecturerAdapter extends RecyclerView.Adapter<LecturerAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    public final List<Lecturer> lecturers;

    public LecturerAdapter(Context context, List<Lecturer> lecturers) {
        this.lecturers = lecturers;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public LecturerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_lecturer, parent, false);
        return new LecturerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LecturerAdapter.ViewHolder holder, int position) {
        Lecturer lecturer = lecturers.get(position);
        String text = lecturer.getLecturer() + ": " + lecturer.getCourses();
        holder.tvLecturer.setText(text);
    }

    @Override
    public int getItemCount() {
        return lecturers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvLecturer;
        ViewHolder(View view){
            super(view);
            tvLecturer = view.findViewById(R.id.tv_recycle_lecturer);
        }
    }
}