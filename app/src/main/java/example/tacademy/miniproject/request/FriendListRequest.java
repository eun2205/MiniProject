package example.tacademy.miniproject.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import example.tacademy.miniproject.data.NetworkResult;
import example.tacademy.miniproject.data.User;
import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-08-10.
 */
public class FriendListRequest extends AbstractRequest<NetworkResult<List<User>>> {
    Request request;

    public FriendListRequest(Context context) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("friendlist")
                .build();

        request = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
    }

    @Override
    protected Type getType() {
        return new TypeToken<NetworkResult<List<User>>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
