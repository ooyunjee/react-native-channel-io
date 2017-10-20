
package com.hwangjjung.channel;


import android.content.Context;

import com.facebook.react.bridge.ReadableMap;

import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.zoyi.channel.plugin.android.ChannelPlugin;
import com.zoyi.channel.plugin.android.CheckIn;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;

import java.util.HashMap;



public class RNChannelModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  private Boolean isCheckedIn = false;
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
  public void logout() {
    ChannelPlugin.checkOut();
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
      if (isCheckedIn) {
        return;
      } else {
        CheckIn checkIn = CheckIn.create();
        CheckIn checkInWithData = withData(checkIn, data);
        ChannelPlugin.checkIn(checkInWithData, new RNOnCheckInListener(promise));
        isCheckedIn = true;
      }

    } catch(Exception e) {
      e.printStackTrace();
    }
  }
}
