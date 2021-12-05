package ua.nure.lab4.api.model;

import android.util.Base64;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class User {
    private String email;
    private String password;
    private String access_token;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getToken() {
        return access_token;
    }
}
