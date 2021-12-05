package ua.nure.lab4.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ua.nure.lab4.BuildConfig;
import ua.nure.lab4.R;
import ua.nure.lab4.api.IRequestAPI;
import ua.nure.lab4.api.model.Lecturer;
import ua.nure.lab4.api.response.LecturerList;
import ua.nure.lab4.ui.recycler.LecturerAdapter;

public class LecturerActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    SharedPreferences sPref;
    private IRequestAPI iRequestAPI;

    RecyclerView rvLecturers;

    List<Lecturer> lecturers;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sPref = this.getSharedPreferences(BuildConfig.PERSISTANT_STORAGE_NAME, MODE_PRIVATE);
        setContentView(R.layout.ac_lecturers);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        iRequestAPI = retrofit.create(IRequestAPI.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ((SearchView)findViewById(R.id.sv_lecturers)).setOnQueryTextListener(this);
        rvLecturers = findViewById(R.id.rv_lecturers);
        testData();
        getAllLecturers();
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
                intent = new Intent(this, StudentsActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_lecturers:
                recreate();
                break;
            case R.id.menu_new_server:
                intent = new Intent(this, TransitActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    private void getLecturerData() {
        Call<LecturerList> call = iRequestAPI.getLecturers(sPref.getString(BuildConfig.TOKEN, null));

        call.enqueue(new Callback<LecturerList>() {
            @Override
            public void onResponse(Call<LecturerList> call, Response<LecturerList> response) {
                if (response.isSuccessful()) {
                    lecturers = response.body().getLecturers();
                    rvLecturers.setAdapter(new LecturerAdapter(LecturerActivity.this, lecturers));
                }
            }

            @Override
            public void onFailure(Call<LecturerList> call, Throwable t) {
                Toast.makeText(LecturerActivity.this, t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void testData() {
        lecturers = new ArrayList<>(); //= response.body().getGroups();

        Lecturer lecturer1 = new Lecturer("Широкопетлева М. С.", "КИС");
        Lecturer lecturer2 = new Lecturer("Сокорчук И. П.", "Айот, Юникс");
        Lecturer lecturer3 = new Lecturer("Колесников Д. О.", "Джава");
        Lecturer lecturer4 = new Lecturer("Кравец", "Облака");

        lecturers.add(lecturer1);
        lecturers.add(lecturer2);
        lecturers.add(lecturer3);
        lecturers.add(lecturer4);

        rvLecturers.setAdapter(new LecturerAdapter(LecturerActivity.this, lecturers));
    }


    private void getAllLecturers() {
        LecturerAdapter lecturerAdapter = (LecturerAdapter) rvLecturers.getAdapter();
        lecturers = lecturerAdapter.lecturers;
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
            rvLecturers.setAdapter(new LecturerAdapter(LecturerActivity.this, lecturers));
        } else {
            text = text.toLowerCase();
            List<Lecturer> lecturerList = new ArrayList<>();
            for (int i = 0; i < lecturers.size(); i++) {
                String currentLecturer = lecturers.get(i).getLecturer().toLowerCase();
                String currentCourses = lecturers.get(i).getCourses().toLowerCase();
                if (currentLecturer.contains(text) || currentCourses.contains(text)) {
                    lecturerList.add(lecturers.get(i));
                }
            }
            rvLecturers.setAdapter(new LecturerAdapter(LecturerActivity.this, lecturerList));
        }
    }
}
