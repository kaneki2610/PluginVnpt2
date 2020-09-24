package com.example.flutter_plugin_vnpt;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.PluginRegistry.Registrar;

public class FlutterPluginVnptPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware, PluginRegistry.ActivityResultListener {
	private MethodChannel channel;
	private static final String channelName = "flutterPlugin2";
	private static final int REQUEST_CODE_FOR_START_ACTIVITY_NATIVE2 = 1999;
	private static Activity activityNative;
	private Result pluginResult;

	@Override
	public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
		channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "flutter_plugin_vnpt");
		channel.setMethodCallHandler(this);
	}

	public static void registerWith(Registrar registrar) {
		activityNative = registrar.activity();
		final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_plugin_vnpt");
		channel.setMethodCallHandler(new FlutterPluginVnptPlugin());
	}

	@Override
	public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
		if (call.method.equals("startActivityNative")) {
			this.pluginResult = result;
			String type = call.argument("type");
			if (type == null || (type != null && type.isEmpty())) {
				result.error("ERROR", "type can not null", null);
			} else {
				Intent intent = new Intent(activityNative, StartActivity.class);
				intent.putExtra("type", type);
				activityNative.startActivityForResult(intent, REQUEST_CODE_FOR_START_ACTIVITY_NATIVE2);
			}
		} else {
			result.notImplemented();
		}
	}

	@Override
	public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
		channel.setMethodCallHandler(null);
	}

	@Override
	public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
		activityNative = binding.getActivity();
		binding.addActivityResultListener(this);
	}

	@Override
	public void onDetachedFromActivityForConfigChanges() {

	}

	@Override
	public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {

	}

	@Override
	public void onDetachedFromActivity() {
		activityNative = null;
	}

	@Override
	public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_FOR_START_ACTIVITY_NATIVE2 && data != null) {
			if (resultCode == Activity.RESULT_OK) {
				String result = data.getStringExtra("dataNative");
				pluginResult.success(result);
			} else {
				pluginResult.success("");
			}
			return true;
		}
		return false;
	}
}
