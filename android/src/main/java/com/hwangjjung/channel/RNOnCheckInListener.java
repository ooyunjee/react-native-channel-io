package com.hwangjjung.channel;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.WritableMap;
import com.zoyi.channel.plugin.android.ChannelException;
import com.zoyi.channel.plugin.android.OnCheckInListener;

/**
 * Created by itmnext13 on 2017. 10. 18..
 */

public class RNOnCheckInListener implements OnCheckInListener {

    private final Promise promise;

    private static final String RN_CHANNEL_CHECK_IN_ERROR = "RN_CHANNEL_CHECK_IN_ERROR";

    public RNOnCheckInListener(Promise promise) {
        this.promise = promise;
    }

    @Override
    public void onSuccessed() {
        WritableMap map = Arguments.createMap();
        map.putBoolean("success", true);
        this.promise.resolve(map);
    }

    @Override
    public void onFailed(ChannelException ex) {
        this.promise.reject(RN_CHANNEL_CHECK_IN_ERROR, new RNChannelException(ex.getStatusCode()));
    }
}
