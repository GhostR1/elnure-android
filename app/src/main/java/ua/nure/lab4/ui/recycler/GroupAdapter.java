package ua.nure.lab4.ui.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ua.nure.lab4.R;
import ua.nure.lab4.api.model.Group;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    public final List<Group> groups;
    private final Context context;

    public GroupAdapter(Context context, List<Group> groups) {
        this.groups = groups;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public GroupAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_group, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroupAdapter.ViewHolder holder, int position) {
        Group group = groups.get(position);
        holder.tvGroupName.setText(group.getGroup());

        holder.rvStudents.setAdapter(new StudentAdapter(holder.rvStudents.getContext(), group.getStudents()));
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvGroupName;
        final RecyclerView rvStudents;
        ViewHolder(View view){
            super(view);
            tvGroupName = view.findViewById(R.id.tv_recycle_group_name);
            rvStudents = view.findViewById(R.id.rv_students);
        }
    }
}
