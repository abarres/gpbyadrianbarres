package adrianbarres.geniusplaza.com.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import adrianbarres.geniusplaza.com.R;
import adrianbarres.geniusplaza.com.controller.UserController;
import adrianbarres.geniusplaza.com.models.UserDataResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUser extends AppCompatActivity {

    private static String TAG_POST_USER = "POST USERS";

    private EditText userNameEditText = null;

    private EditText passwordEditText = null;

    private EditText emailEditText = null;

    private Button registerButton = null;

    private ListView userListView = null;

    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        setTitle("GeniusPlaza - Adrian Barres Assessment.");

        initControls();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showProgressDialog();

                String userNameValue = userNameEditText.getText().toString();

                String passwordValue = passwordEditText.getText().toString();

                String emailValue = emailEditText.getText().toString();

                Call<ResponseBody> call = UserController.getUserManagerService(null).addUser(userNameValue, passwordValue, emailValue);

                retrofit2.Callback<ResponseBody> callback = new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        hideProgressDialog();

                        StringBuffer messageBuffer = new StringBuffer();
                        int statusCode = response.code();
                        if (statusCode == 201) {
                            try {
                                String returnBodyText = response.body().string();

                                Gson gson = new Gson();

                                TypeToken<UserDataResponse> typeToken = new TypeToken<UserDataResponse>(){};

                                UserDataResponse userData = gson.fromJson(returnBodyText, typeToken.getType());

                                messageBuffer.append("Code " + statusCode +" User added successfully");
                                messageBuffer.append("\r\n");
                                messageBuffer.append("Name:      " + userData.getFirstName() );
                                messageBuffer.append("\r\n");
                                messageBuffer.append("Last Name: " + userData.getLastName() );
                                messageBuffer.append("\r\n");
                                messageBuffer.append("Avatar:    " + userData.getAvatar() );

                                Intent intent = new Intent(getApplicationContext(), UsersList.class);
                                startActivity(intent);
                                finish();

                            } catch (IOException ex) {
                                Log.e(TAG_POST_USER, ex.getMessage());
                            }
                        } else {
                            messageBuffer.append("Server return error code is ");
                            messageBuffer.append(statusCode);
                            messageBuffer.append("\r\n\r\n");
                            messageBuffer.append("Error message is ");
                            messageBuffer.append(response.message());
                        }

                        Toast.makeText(getApplicationContext(), messageBuffer.toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        hideProgressDialog();

                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                };

                call.enqueue(callback);
            }
        });
    }

    private void initControls()
    {
        if(userNameEditText==null)
        {
            userNameEditText = (EditText)findViewById(R.id.add_user_name);
        }

        if(passwordEditText==null)
        {
            passwordEditText = (EditText)findViewById(R.id.add_user_last_name);
        }

        if(emailEditText==null)
        {
            emailEditText = (EditText)findViewById(R.id.add_user_avatar);
        }

        if(registerButton == null)
        {
            registerButton = (Button)findViewById(R.id.retrofit_register_button);
        }

        if(userListView == null)
        {
            userListView = (ListView)findViewById(R.id.retrofit_user_list_view);
        }

        if(progressDialog == null) {
            progressDialog = new ProgressDialog(AddUser.this);
        }
    }

    private void showProgressDialog() {
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void hideProgressDialog(){
        progressDialog.hide();
    }
}
