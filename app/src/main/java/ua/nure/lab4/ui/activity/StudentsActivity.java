package ua.nure.lab4.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ua.nure.lab4.BuildConfig;
import ua.nure.lab4.R;
import ua.nure.lab4.api.IRequestAPI;
import ua.nure.lab4.api.model.Group;
import ua.nure.lab4.api.response.GroupList;
import ua.nure.lab4.ui.recycler.GroupAdapter;

public class StudentsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    SharedPreferences sPref;
    IRequestAPI iRequestAPI;

    RecyclerView rvGroups;

    List<Group> groups;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sPref = this.getSharedPreferences(BuildConfig.PERSISTANT_STORAGE_NAME, MODE_PRIVATE);
        setContentView(R.layout.ac_students);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        iRequestAPI = retrofit.create(IRequestAPI.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ((SearchView)findViewById(R.id.sv_students)).setOnQueryTextListener(this);
/*        ((SearchView)findViewById(R.id.sv_students)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllStudents();
            }
        });*/
        rvGroups = findViewById(R.id.rv_groups);
        getGroupData();
    }

    private void getAllStudents() {
        rvGroups = findViewById(R.id.rv_groups);
        GroupAdapter groupAdapter = (GroupAdapter) rvGroups.getAdapter();
        groups = groupAdapter.groups;

        Toast.makeText(StudentsActivity.this, "Ok!", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_logout:
                intent = new Intent(this, AuthActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.menu_students:
                recreate();
                break;
            case R.id.menu_lecturers:
                intent = new Intent(this, LecturerActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_new_server:
                intent = new Intent(this, TransitActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    private void getGroupData() {
        Call<GroupList> call = iRequestAPI.getGroups(sPref.getString(BuildConfig.TOKEN, null));

        call.enqueue(new Callback<GroupList>() {
            @Override
            public void onResponse(Call<GroupList> call, Response<GroupList> response) {
                if (response.isSuccessful()) {
                    groups = response.body().getGroups();
                    rvGroups.setAdapter(new GroupAdapter(StudentsActivity.this, groups));
                } else {  // For testing
                    groups = new ArrayList<>(); //= response.body().getGroups();

                    ArrayList<String> students1 = new ArrayList<>();
                    students1.add("Тарасов Роман Олегович");
                    students1.add("Semenova Nataliia");
                    students1.add("Yana Leiba");
                    Group group1 = new Group("PZPI-18-5", students1);
                    groups.add(group1);


                    ArrayList<String> students2 = new ArrayList<>();
                    students2.add("Тарасов Роман Олегович");
                    students2.add("Yana Leiba");
                    Group group2 = new Group("PZPI-18-2", students2);
                    groups.add(group2);

                    rvGroups.setAdapter(new GroupAdapter(StudentsActivity.this, groups));
                }
            }

            @Override
            public void onFailure(Call<GroupList> call, Throwable t) {
                Toast.makeText(StudentsActivity.this, t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchContent(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        searchContent(newText);
        return false;
    }

    private void searchContent(String text) {
        if(text.length() == 0) {
            rvGroups.setAdapter(new GroupAdapter(StudentsActivity.this, groups));
        } else {
            text = text.toLowerCase();
            List<Group> groupList = new ArrayList<>();
            for (int i = 0; i < groups.size(); i++) {
                String currentGroup = groups.get(i).getGroup().toLowerCase();
                if (currentGroup.contains(text)) {
                    groupList.add(groups.get(i));
                } else {
                    ArrayList<String> students = groups.get(i).getStudents();
                    for (int j = 0; j < students.size(); j++) {
                        String currentStudent = students.get(j).toLowerCase();
                        if (currentStudent.contains(text)) {
                            groupList.add(groups.get(i));
                            break;
                        }
                    }
                }
            }
            rvGroups.setAdapter(new GroupAdapter(StudentsActivity.this, groupList));
        }
    }
}
