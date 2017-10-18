package com.hwangjjung.channel;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.zoyi.channel.plugin.android.ChannelView;

/**
 * Created by itmnext13 on 2017. 10. 18..
 */

public class RNChannelViewManager extends SimpleViewManager<ChannelView> {
    public static final String REACT_CLASS = "ChannelView";

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected ChannelView createViewInstance(ThemedReactContext reactContext) {
        ChannelView channelView = new ChannelView(reactContext);
        return channelView;
    }
}
