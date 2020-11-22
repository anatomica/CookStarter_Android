package ru.anatomica.cookstarter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.textfield.TextInputEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import ru.anatomica.cookstarter.data.LoginDataSource;
import ru.anatomica.cookstarter.entity.Token;
import ru.anatomica.cookstarter.ui.login.LoginActivity;

public class RegisterActivity extends AppCompatActivity {

    private Button buttonReg;
    private TextInputEditText editPhone, editPassword, editConfirmPass, editFirstName, editLastName, editEmail;
    public static List<Token> tokenList;
    public static String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editPhone = findViewById(R.id.editPhone);
        editPassword = findViewById(R.id.editPassword);
        editConfirmPass = findViewById(R.id.editConfirmPass);
        editFirstName = findViewById(R.id.editFirstName);
        editLastName = findViewById(R.id.editLastName);
        editEmail = findViewById(R.id.editEmail);
        buttonReg = findViewById(R.id.register);

        buttonReg.setOnClickListener(view -> {
            String phone = editPhone.getText().toString();
            String password = editPassword.getText().toString();
            String firstName = editFirstName.getText().toString();
            String lastName = editLastName.getText().toString();
            String email = editEmail.getText().toString();
            String confirmPassword = editConfirmPass.getText().toString();

            getToken(phone, password, confirmPassword, firstName, lastName, email);
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void getToken(String phone, String password, String confirmPass, String firstName, String lastName, String email) {
        String auth = "https://marketcook.herokuapp.com/market/register";

        JSONObject json = new JSONObject();
        try {
            json.put("phone", phone);
            json.put("password", password);
            json.put("matchingPassword", confirmPass);
            json.put("firstName", firstName);
            json.put("lastName", lastName);
            json.put("email", email);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringEntity entity = new StringEntity(json.toString(), "UTF-8");

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(getApplicationContext(), auth, entity, "application/json", new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                System.out.println("called before request is started");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                try {
                    String json = new String(response, "UTF-8");
                    ObjectMapper mapper = new ObjectMapper();
                    System.out.println(json);

                    tokenList = Arrays.asList(mapper.readValue(json, Token.class));
                    token = tokenList.get(0).getToken();

                    LoginDataSource.success = 1;
                    // loginViewModel.login(usernameEditText.getText().toString(), passwordEditText.getText().toString());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                try {
                    System.out.println(new String(errorResponse, "UTF-8") + " " + statusCode);
                    // loginViewModel.login(usernameEditText.getText().toString(), passwordEditText.getText().toString());
                } catch (UnsupportedEncodingException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onRetry(int retryNo) {
                System.out.println("called when request is retried");
            }
        });
    }
}
