package br.com.androidpipplugin.android_pip_plugin;

import android.app.Activity;
import android.app.PictureInPictureParams;
import android.os.Build;
import android.util.Rational;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** AndroidPipPlugin */
public class AndroidPipPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {

  private MethodChannel channel;
  private Activity activity;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "android_pip_plugin");
    channel.setMethodCallHandler(this);
  }

  public void onAttachedToActivity(ActivityPluginBinding activityPluginBinding) {
    activity = activityPluginBinding.getActivity();
  }

  @Override
  public void onReattachedToActivityForConfigChanges(ActivityPluginBinding activityPluginBinding) {
    // TODO: your plugin is now attached to a new Activity
    // after a configuration change.
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  @Override
  public void onDetachedFromActivity() {
    // TODO: your plugin is no longer associated with an Activity.
    // Clean up references.
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {
    // TODO: the Activity your plugin was attached to was
    // destroyed to change configuration.
    // This call will be followed by onReattachedToActivityForConfigChanges().
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if(call.method.equals("enterPictureInPictureMode")) {

      Double width = call.argument("width");
      Double height = call.argument("height");

      boolean hasExecuted = enterPictureInPictureMode(width, height);
      result.success(hasExecuted);
    } else if (call.method.equals("isPipModeSupported")) {

      boolean isPipModeSupported = isPipModeSupported();
      result.success(isPipModeSupported);
    } else if(call.method.equals("isPip")) {

      boolean hasExecuted = isPip();
      result.success(hasExecuted);
    } else {

      result.notImplemented();
    }
  }

  public boolean isPip() {
    if (!isPipModeSupported()) {
      return false;
    }

    return this.activity.isInPictureInPictureMode();
  }

  public boolean enterPictureInPictureMode(Double width, Double height) {
    if (!isPipModeSupported()) {
      return false;
    }

    PictureInPictureParams.Builder pictureInPictureParamsBuilder = new PictureInPictureParams.Builder();
    Rational aspectRatio = new Rational(Integer.valueOf(width.intValue()), Integer.valueOf(height.intValue()));
    pictureInPictureParamsBuilder.setAspectRatio(aspectRatio).build();
    this.activity.enterPictureInPictureMode(pictureInPictureParamsBuilder.build());
    return true;
  }

  public boolean isPipModeSupported() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
  }
}
