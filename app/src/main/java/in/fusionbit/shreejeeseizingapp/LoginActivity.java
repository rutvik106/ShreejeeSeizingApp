package in.fusionbit.shreejeeseizingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.textfield.TextInputEditText;

import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.fusionbit.shreejeeseizingapp.api.Api;
import in.fusionbit.shreejeeseizingapp.apimodels.UserModel;
import in.fusionbit.shreejeeseizingapp.utils.SharedPreferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_username)
    TextInputEditText etUsername;
    @BindView(R.id.et_password)
    TextInputEditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.cb_rememberMe)
    CheckBox cbRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Login");
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (new SharedPreferences(this)
                .getSavedUsernameAndPassword() != null) {
            etUsername.setText(new SharedPreferences(this)
                    .getSavedUsernameAndPassword().get("username"));
            etPassword.setText(new SharedPreferences(this)
                    .getSavedUsernameAndPassword().get("password"));
            cbRememberMe.setChecked(true);
        }
    }

    @Override
    int getLayoutResourceId() {
        return R.layout.activity_login;
    }


    public static void start(final Context context) {
        final Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void tryLogin(final Context context, final String username, final String password) {

        showProgressDialog("Please Wait", "Logging in...");

        Api.User.tryLogin(username, password, new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()) {

                    if (response.body() != null) {
                        if (cbRememberMe.isChecked()) {
                            new SharedPreferences(context).saveUsernameAndPassword(username, password);
                        }
                        App.setCurrentUser(response.body());
                        HomeActivity.start(LoginActivity.this);
                    }

                } else {
                    Toast.makeText(context, "Something went wrong please try again later", Toast.LENGTH_SHORT).show();
                }
                hideProgressDialog();
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                hideProgressDialog();
            }
        });

    }


    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        if (etUsername.length() > 0 && etPassword.length() > 0) {
            //final String username = etUsername.getText().toString();
            //final String password = etPassword.getText().toString();

            tryLogin(this, etUsername.getText().toString(), etPassword.getText().toString());
        } else {
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
        }
    }
}
