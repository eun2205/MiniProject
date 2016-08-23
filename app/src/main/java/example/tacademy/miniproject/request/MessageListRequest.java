package example.tacademy.miniproject.request;

import android.os.Message;

import java.lang.reflect.Type;

import example.tacademy.miniproject.data.NetworkResult;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-08-23.
 */
public class MessageListRequest extends AbstractRequest<NetworkResult<Message>> {
    @Override
    protected Type getType() {
        return null;
    }

    @Override
    public Request getRequest() {
        return null;
    }
}
