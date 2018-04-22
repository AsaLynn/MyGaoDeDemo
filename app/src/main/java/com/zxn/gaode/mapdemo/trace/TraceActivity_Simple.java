package com.zxn.gaode.mapdemo.trace;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.trace.LBSTraceClient;
import com.amap.api.trace.TraceLocation;
import com.amap.api.trace.TraceOverlay;
import com.amap.api.trace.TraceStatusListener;
import com.zxn.gaode.R;

import java.util.List;

/**
 * 轨迹纠偏功能 示例 使用起来更简单
 */
public class TraceActivity_Simple extends Activity implements TraceStatusListener,
        OnClickListener {
	private MapView mMapView;
	private AMap mAMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trace_simple);
		mMapView = (MapView) findViewById(R.id.map);
		mMapView.onCreate(savedInstanceState);// 此方法必须重写
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		if (mAMap == null) {
			mAMap = mMapView.getMap();
			mAMap.getUiSettings().setRotateGesturesEnabled(false);
			mAMap.getUiSettings().setZoomControlsEnabled(false);
		}
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mMapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mMapView.onPause();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mMapView != null) {
			mMapView.onDestroy();
		}
	}


	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.start_bt:
			startTrace();
			break;
		case R.id.stop_bt:
			stopTrace();
			break;
		}
	}

	LBSTraceClient traceClient = null;
	TraceOverlay traceOverlay;

	/**
	 * 停止轨迹纠偏
	 */
	private void stopTrace() {
		if(traceClient == null) {
			traceClient = LBSTraceClient.getInstance(TraceActivity_Simple.this);
		}
		traceClient.stopTrace();
	}

	/**
	 * 开启轨迹纠偏
	 */
	private void startTrace() {

		if(traceClient == null) {
			traceClient = LBSTraceClient.getInstance(TraceActivity_Simple.this);
		}
        traceClient.startTrace(this);
	}

	@Override
	public void onTraceStatus(List<TraceLocation> locations, List<LatLng> rectifications, String errorInfo) {
		if(!LBSTraceClient.TRACE_SUCCESS.equals(errorInfo)) {
			Log.e("BasicActivity"," source count-> "+(locations == null ? 0 :locations.size())+"   result count-> "+(rectifications == null ? 0 :rectifications.size()));


			StringBuffer stringBuffer = new StringBuffer();

			for(TraceLocation location : locations) {
				stringBuffer.append("{");
				stringBuffer.append("\"lon\":").append(location.getLongitude()).append(",");
				stringBuffer.append("\"lat\":").append(location.getLatitude()).append(",");
				stringBuffer.append("\"loctime\":").append(location.getTime()).append(",");
				stringBuffer.append("\"speed\":").append(location.getSpeed()).append(",");
				stringBuffer.append("\"bearing\":").append(location.getBearing());
				stringBuffer.append("}");
				stringBuffer.append("\n");
			}
			Log.i("amap","轨迹纠偏失败，请先检查经纬度、时间、速度和角度信息是否为空，" +
					"若仍不能解决问题，请将以下内容通过官网(lbs.amap.com)工单系统反馈给我们 \n" + errorInfo + " \n "
					+ stringBuffer.toString());



		}

		if(traceOverlay!=null){
			traceOverlay.remove();
		}
		//将得到的轨迹点显示在地图上
		traceOverlay = new TraceOverlay(mAMap,rectifications);
		traceOverlay.zoopToSpan();

	}
}
