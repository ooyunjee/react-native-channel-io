package com.hwangjjung.channel;

import com.zoyi.channel.plugin.android.ChannelException;

/**
 * Created by itmnext13 on 2017. 10. 18..
 */

public class RNChannelException extends Exception {
    public RNChannelException(ChannelException.StatusCode statusCode) {
      super(statusCode.toString());
    }

    public RNChannelException(String message) {
        super(message);
    }
}
