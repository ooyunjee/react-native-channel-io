
package com.hwangjjung.channel;


import android.content.Context;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;

import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.WritableMap;
import com.zoyi.channel.plugin.android.ChannelPlugin;
import com.zoyi.channel.plugin.android.CheckIn;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;
import com.zoyi.channel.plugin.android.global.PrefSupervisor;
import com.zoyi.channel.plugin.android.push.ChannelPushManager;

import java.util.HashMap;
import java.util.Map;


public class RNChannelModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  public RNChannelModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNChannel";
  }

  private String getStringFromReadableMap(ReadableMap map, String key) throws Exception {
    switch (map.getType(key)) {
      case String:
        return map.getString(key);
      case Number:
        try {
          return String.valueOf(map.getInt(key));
        } catch (Exception e) {
          return String.valueOf(map.getDouble(key));
        }
      case Boolean:
        return String.valueOf(map.getBoolean(key));
      case Null:
        return "NULL";
      default:
        throw new Exception("Unknown data type: " + map.getType(key).name() + " for metaData key " + key );
    }
  }

  private CheckIn withData(CheckIn checkIn, ReadableMap metaData) throws Exception {
    ReadableMapKeySetIterator iterator = metaData.keySetIterator();
      CheckIn checkInChain = checkIn;
      while (iterator.hasNextKey()) {
      String key = iterator.nextKey();
      String value = getStringFromReadableMap(metaData, key);
      switch(key) {
        case "userId": checkInChain = checkInChain.withUserId(value); break;
        case "userID": checkInChain = checkInChain.withUserId(value); break;
        case "userName": checkInChain = checkInChain.withName(value); break;
        case "name": checkInChain = checkInChain.withName(value); break;
        case "mobile": checkInChain = checkInChain.withMobileNumber(value); break;
        case "mobileNumber": checkInChain = checkInChain.withMobileNumber(value); break;
        case "avatarUrl": checkInChain = checkInChain.withAvatarUrl(value); break;
        case "pushToken": PrefSupervisor.setDeviceToken(this.reactContext, value);  break;
        default: checkInChain = checkInChain.withMeta(key, value);
      }
    }
    return checkInChain;
  }

  @ReactMethod
  public void track(String name, ReadableMap data) throws Exception {
    ReadableMapKeySetIterator iterator = data.keySetIterator();
    HashMap<String, Object> properties = new HashMap<>();
    Context context = getReactApplicationContext();
    while (iterator.hasNextKey()) {
      String key = iterator.nextKey();
      String value = getStringFromReadableMap(data, key);
      properties.put(key, value);
    }
    ChannelPlugin.track(context, name, properties);
  }

  @ReactMethod
  public void launch() {
    Log.d("RNChannel", "launch");
    Context context = this.getReactApplicationContext();
    ChannelPlugin.launch(context);
   }

  @ReactMethod
  public void logout() {
    Log.d("RNChannel", "check Out");
    ChannelPlugin.checkOut();
  }

  @ReactMethod
  public void onTokenRefresh(String token) {
    Log.d("RNChannel", "onTokenRefresh");
    Log.d("RNChannel", token);
    PrefSupervisor.setDeviceToken(this.reactContext , token);
  }

  @ReactMethod
  public void ananymousLogin(Promise promise) {
    try {
      ChannelPlugin.checkIn(new RNOnCheckInListener(promise));
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  @ReactMethod
  public void login(ReadableMap data, Promise promise) throws Exception {
    try {
        CheckIn checkIn = CheckIn.create();
        CheckIn checkInWithData = withData(checkIn, data);
        ChannelPlugin.checkIn(checkInWithData, new RNOnCheckInListener(promise));
        Log.d("RNChannel", "check In");
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  @ReactMethod
  public void onMessageReceive(ReadableMap data, Promise promise) throws Exception {
    try {
      Log.d("RNChannel", "onMessageReceive");
      ReadableMapKeySetIterator iterator = data.keySetIterator();
      Map<String, String> messages = new HashMap<>();
      while (iterator.hasNextKey()) {
        String key = iterator.nextKey();
        String value = getStringFromReadableMap(data, key);
        messages.put(key, value);
      }
      WritableMap resultMap = Arguments.createMap();

      if (ChannelPushManager.isChannelPluginMessage(messages)) {
        Log.d("RNChannel", "handlePush");
        ChannelPushManager.handlePush(this.reactContext, messages);
        resultMap.putBoolean("success", true);
        promise.resolve(resultMap);
      } else {
        resultMap.putBoolean("success", false);
        promise.resolve(resultMap);
      }
    } catch(Exception e) {
      e.printStackTrace();
      promise.reject(e);
    }
  }
}
