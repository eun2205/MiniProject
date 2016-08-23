package example.tacademy.miniproject.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import example.tacademy.miniproject.ChatActivity;
import example.tacademy.miniproject.R;
import example.tacademy.miniproject.data.NetworkResult;
import example.tacademy.miniproject.data.User;
import example.tacademy.miniproject.manager.NetworkManager;
import example.tacademy.miniproject.manager.NetworkRequest;
import example.tacademy.miniproject.request.FriendListRequest;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class OneFragment extends Fragment {


    public OneFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.listView)
    ListView listView;

    ArrayAdapter<User> mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new ArrayAdapter<User>(getContext(), android.R.layout.simple_list_item_1);
        FriendListRequest request = new FriendListRequest(getContext());
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<List<User>>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<List<User>>> request, NetworkResult<List<User>> result) {
                List<User> users = result.getResult();
                mAdapter.addAll(users);
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<List<User>>> request, int errorCode, String errorMessage, Throwable e) {

            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        ButterKnife.bind(this, view);

        listView.setAdapter(mAdapter);
        return view;
    }
    @OnItemClick(R.id.listView)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(), ChatActivity.class);
        User user = (User)listView.getItemAtPosition(position);
        intent.putExtra(ChatActivity.EXTRA_USER, user);
        startActivity(intent);
    }
}
