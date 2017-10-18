
package com.hwangjjung.channel;


import com.facebook.react.bridge.ReadableMap;

import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.zoyi.channel.plugin.android.ChannelPlugin;
import com.zoyi.channel.plugin.android.CheckIn;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;


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
      default:
        throw new Exception("Unknown data type: " + map.getType(key).name() + " for metaData key " + key );
    }
  }

  private CheckIn addMetaData(CheckIn checkIn, ReadableMap metaData) throws Exception {
    ReadableMapKeySetIterator iterator = metaData.keySetIterator();
    while (iterator.hasNextKey()) {
      String key = iterator.nextKey();
      String value = getStringFromReadableMap(metaData, key);
      checkIn.withMeta(key, value);
    }
    return checkIn;
  }

  @ReactMethod
  public void logout() {
    ChannelPlugin.checkOut();
  }
  
  @ReactMethod
  public void login(Promise promise) {
    try {
      ChannelPlugin.checkIn(new RNOnCheckInListener(promise));
    } catch(Exception e) {
      e.printStackTrace();
    }
  }


  @ReactMethod
  public void login(String userId, Promise promise) throws Exception {
    try {
      CheckIn checkIn = CheckIn.create()
              .withUserId(userId);
      ChannelPlugin.checkIn(checkIn, new RNOnCheckInListener(promise));
    } catch(Exception e) {
        e.printStackTrace();
    }
  }


  @ReactMethod
  public void login(String userId, ReadableMap metaData, Promise promise) throws Exception {
    try {
      CheckIn checkIn = CheckIn.create()
              .withUserId(userId);
      CheckIn checkInWithMeta = addMetaData(checkIn, metaData);
      ChannelPlugin.checkIn(checkInWithMeta, new RNOnCheckInListener(promise));
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  @ReactMethod
  public void login(String userId, String name, ReadableMap metaData, Promise promise)  throws Exception {
    try {
      CheckIn checkIn = CheckIn.create()
              .withUserId(userId)
              .withName(name);
      CheckIn checkInWithMeta = addMetaData(checkIn, metaData);
      ChannelPlugin.checkIn(checkInWithMeta, new RNOnCheckInListener(promise));
    } catch(Exception e) {
      e.printStackTrace();
    }
  }


  @ReactMethod
  public void login(String userId, String name, String mobile, ReadableMap metaData, Promise promise)  throws Exception {
    try {
      CheckIn checkIn = CheckIn.create()
              .withUserId(userId)
              .withName(name)
              .withMobileNumber(mobile);
      CheckIn checkInWithMeta = addMetaData(checkIn, metaData);
      ChannelPlugin.checkIn(checkInWithMeta, new RNOnCheckInListener(promise));
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  @ReactMethod
  public void login(String userId, String name, String mobile, String avartarUrl, ReadableMap metaData, Promise promise)  throws Exception {
    try {
      CheckIn checkIn = CheckIn.create()
              .withUserId(userId)
              .withName(name)
              .withMobileNumber(mobile)
              .withAvatarUrl(avartarUrl);
      CheckIn checkInWithMeta = addMetaData(checkIn, metaData);
      ChannelPlugin.checkIn(checkInWithMeta, new RNOnCheckInListener(promise));
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
}