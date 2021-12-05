package ua.nure.lab4.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ua.nure.lab4.BuildConfig;
import ua.nure.lab4.R;
import ua.nure.lab4.api.IRequestAPI;
import ua.nure.lab4.api.response.GroupList;
import ua.nure.lab4.ui.recycler.GroupAdapter;

public class TransitActivity extends AppCompatActivity {
    SharedPreferences sPref;
    private IRequestAPI iRequestAPI;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sPref = this.getSharedPreferences(BuildConfig.PERSISTANT_STORAGE_NAME, MODE_PRIVATE);
        setContentView(R.layout.ac_transition);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        iRequestAPI = retrofit.create(IRequestAPI.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.btn_transition).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // String newProvider = ((EditText)findViewById(R.id.et_new_provider)).getText().toString();
                // changeProvide(newProvider);
            }
        });
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
                intent = new Intent(this, LecturerActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_new_server:
                recreate();
                break;
        }
        return true;
    }

    private void changeProvider(String newProvider) {
        Call<Object> call = iRequestAPI.changeProvider(sPref.getString(BuildConfig.TOKEN, null), newProvider);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(TransitActivity.this, "Provider was successfully changed!", Toast.LENGTH_LONG)
                            .show();
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(TransitActivity.this, t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
