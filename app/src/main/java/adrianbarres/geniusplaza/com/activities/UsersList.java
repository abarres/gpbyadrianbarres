package adrianbarres.geniusplaza.com.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;

import adrianbarres.geniusplaza.com.R;
import adrianbarres.geniusplaza.com.adapters.CustomAdapter;
import adrianbarres.geniusplaza.com.controller.UserController;
import adrianbarres.geniusplaza.com.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;


public class UsersList extends AppCompatActivity {

    CustomAdapter adapter;

    private static String TAG_GET_USER = "GET USERS";

    private ListView userListView = null;

    private Button gotoAddUser = null;

    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        setTitle("GeniusPlaza - Adrian Barres Assessment.");
        initControls();
        loadUsers();


        gotoAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddUser.class);
                startActivity(intent);
                finish();
            }
        });

    }



    private void loadUsers() {
        showProgressDialog();

        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create();

        Call<User> call = UserController.getUserManagerService(gsonConverterFactory).getUserAllList();

        retrofit2.Callback<User> callback = new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                hideProgressDialog();
                try {
                    if (response.isSuccessful()) {

                        User userDtoList = response.body();

                        showUserInfoInListView(userDtoList);
                    } else {
                        String errorMessage = response.errorBody().string();
                        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                    }
                } catch (IOException ex) {
                    Log.e(TAG_GET_USER, ex.getMessage());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
        call.enqueue(callback);
    }

    private void showUserInfoInListView(User userList) {
        if (userList != null) {
            adapter = new CustomAdapter(this.getApplicationContext(), userList);
            userListView.setAdapter(adapter);
        }
    }

    private void initControls() {
        if (userListView == null) {
            userListView = (ListView) findViewById(R.id.retrofit_user_list_view);
        }
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(UsersList.this);
        }

        if(gotoAddUser == null)
        {
            gotoAddUser = (Button)findViewById(R.id.gotoAddUser);
        }
    }

    private void showProgressDialog() {
        progressDialog.setMessage("Loading Users...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void hideProgressDialog() {
        progressDialog.hide();
    }
}
