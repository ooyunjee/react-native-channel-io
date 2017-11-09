package com.hwangjjung.channel;

import android.util.Log;
import android.view.View;

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
    protected ChannelView createViewInstance(ThemedReactContext themedReactContext) {
        ChannelView channelView = new ChannelView(themedReactContext);
        channelView.setVisibility(View.VISIBLE);
        Log.d("RNChannel", "createViewInstance");
        return channelView;
    }

    @Override
    public void updateExtraData(ChannelView root, Object extraData) {
        Log.d("RNChannel:updateExtraData",String.valueOf(extraData));
        root.setButtonGravity((int)extraData);
    }
}
