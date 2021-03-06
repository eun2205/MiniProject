package example.tacademy.miniproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.login.LoginManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.tacademy.miniproject.data.NetworkResult;
import example.tacademy.miniproject.login.ChatFragment;
import example.tacademy.miniproject.login.OneFragment;
import example.tacademy.miniproject.login.SimpleLoginActivity;
import example.tacademy.miniproject.manager.NetworkManager;
import example.tacademy.miniproject.manager.NetworkRequest;
import example.tacademy.miniproject.manager.PropertyManager;
import example.tacademy.miniproject.request.LogoutRequest;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tabhost)
    FragmentTabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        tabHost.addTab(tabHost.newTabSpec("main").setIndicator("Main"), OneFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("chat").setIndicator("Chat"), ChatFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("content").setIndicator("Content"), ContentFragment.class, null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_logout) {
            LogoutRequest request = new LogoutRequest(this);
            NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<String>>() {
                @Override
                public void onSuccess(NetworkRequest<NetworkResult<String>> request, NetworkResult<String> result) {
                    PropertyManager.getInstance().setEmail("");
                    PropertyManager.getInstance().setPassword("");
                    PropertyManager.getInstance().setFacebookId("");
                    LoginManager.getInstance().logOut();
                    Intent intent = new Intent(MainActivity.this, SimpleLoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onFail(NetworkRequest<NetworkResult<String>> request, int errorCode, String errorMessage, Throwable e) {

                }
            });
        }
        return super.onOptionsItemSelected(item);
    }
}
