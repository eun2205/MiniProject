package example.tacademy.miniproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import example.tacademy.miniproject.data.NetworkResult;
import example.tacademy.miniproject.data.User;
import example.tacademy.miniproject.login.SimpleLoginActivity;
import example.tacademy.miniproject.manager.NetworkManager;
import example.tacademy.miniproject.manager.NetworkRequest;
import example.tacademy.miniproject.manager.PropertyManager;
import example.tacademy.miniproject.request.LoginRequest;
import example.tacademy.miniproject.request.ProfileRequest;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ProfileRequest request = new ProfileRequest(this);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<User>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<User>> request, NetworkResult<User> result) {
                moveMainActivity();
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<User>> request, int errorCode, String errorMessage, Throwable e) {
                if (errorCode == -1) {
                    if (errorMessage.equals("not login")) {
                        loginSharedPreference();
                        return;
                    }
                }
                moveLoginActivity();
            }
        });
    }

    private void loginSharedPreference() {
        if (isAutoLogin()) {
            processAutoLogin();
        }else {
            moveLoginActivity();
        }
    }

    private void processAutoLogin() {
        String email = PropertyManager.getInstance().getEmail();
        if (!TextUtils.isEmpty(email)) {
            String password = PropertyManager.getInstance().getPassword();
            String regid = PropertyManager.getInstance().getRegistrationId();
            LoginRequest request = new LoginRequest(this, email, password, regid);
            NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<User>>() {

                @Override
                public void onSuccess(NetworkRequest<NetworkResult<User>> request, NetworkResult<User> result) {
                    moveMainActivity();
                }

                @Override
                public void onFail(NetworkRequest<NetworkResult<User>> request, int errorCode, String errorMessage, Throwable e) {
                    moveLoginActivity();
                }
            });
        }
    }

    private void moveLoginActivity() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, SimpleLoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }

    private void moveMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    Handler mHandler = new Handler(Looper.getMainLooper());

    private boolean isAutoLogin() {
        String email = PropertyManager.getInstance().getEmail();
        return !TextUtils.isEmpty(email);
    }
}
