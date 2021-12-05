package ua.nure.lab4.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ua.nure.lab4.BuildConfig;
import ua.nure.lab4.R;
import ua.nure.lab4.api.IRequestAPI;
import ua.nure.lab4.api.model.User;

public class AuthActivity extends AppCompatActivity {
    Button btnLogin;
    EditText etEmail, etPassword;
    SharedPreferences sPref;

    IRequestAPI iRequestAPI;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sPref = this.getSharedPreferences(BuildConfig.PERSISTANT_STORAGE_NAME, MODE_PRIVATE);
        setContentView(R.layout.ac_auth);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        iRequestAPI = retrofit.create(IRequestAPI.class);

        btnLogin = findViewById(R.id.btn_sign_in);
        etEmail = findViewById(R.id.et_login);
        etPassword = findViewById(R.id.et_password);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fieldValidation()) {
                    handleLoginDialog(etEmail.getText().toString(), etPassword.getText().toString());
                }
            }
        });
    }

    private void handleLoginDialog(String email, String password) {
        Call<User> call = iRequestAPI.authenticate(email, password);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    etEmail.setText("");
                    etPassword.setText("");
                    sPref
                            .edit()
                            .putString(BuildConfig.TOKEN, response.body().getToken())
                            .apply();
                    Intent intent = new Intent(AuthActivity.this, StudentsActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(AuthActivity.this, t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean fieldValidation() {
        if (TextUtils.isEmpty(etEmail.getText().toString()) || TextUtils.isEmpty(etPassword.getText().toString())) {
            Toast.makeText(getApplicationContext(),
                    R.string.login_valid_empty, Toast.LENGTH_SHORT).show();
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
            Toast.makeText(getApplicationContext(),
                    R.string.login_valid_email, Toast.LENGTH_SHORT).show();
            return false;
        } else if (etPassword.getText().toString().length() < 5) {
            Toast.makeText(getApplicationContext(),
                    R.string.login_valid_password, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}