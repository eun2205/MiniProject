package example.tacademy.miniproject.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import example.tacademy.miniproject.data.NetworkResult;
import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-08-10.
 */
public class LogoutRequest extends AbstractRequest<NetworkResult<String>> {
    Request request;

    public LogoutRequest(Context context) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("logout")
                .build();
        request = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
    }


    @Override
    public Request getRequest() {
        return request;
    }

    protected Type getType() {
        return new TypeToken<NetworkResult<String>>() {
        }.getType();
    }

}
