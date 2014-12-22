package com.herotculb.tencentmap;

import java.util.ArrayList;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.tencentmap.mapsdk.map.GeoPoint;
import com.tencent.tencentmap.mapsdk.map.MapActivity;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.Overlay;
import com.tencent.tencentmap.mapsdk.map.Projection;

/**
 * 在腾讯地图上显示我的位置.
 * 
 * <p>
 * 地图SDK相关内容请参考<a
 * href="http://open.map.qq.com/android_v1/index.html">腾讯地图SDK</a>
 */
public class MainActivity extends MapActivity implements
		TencentLocationListener, OnTouchListener {

	TextView my_address;
	private MapView mMapView;
	private LocationOverlay mLocationOverlay;
	public RequestQueue _Queue;
	
	private ListView mListView;
	ArrayList<Pois> arrayList = null;
	Pois pois = null;
	AdddressAdapter adapter;
	
	private TencentLocation mLocation;
	private TencentLocationManager mLocationManager;
	public static int count = 1;
	public static String app_id = "OUQBZ-HIRWV-W3UPA-UOXRD-KVUB7-U2FZL";

	// 用于记录定位参数, 以显示到 UI
	private String mRequestParams;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo_map);

		my_address = (TextView) findViewById(R.id.my_address);
		initMapView();

		mLocationManager = TencentLocationManager.getInstance(this);
		// 设置坐标系为 gcj-02, 缺省坐标为 gcj-02, 所以通常不必进行如下调用
		mLocationManager
				.setCoordinateType(TencentLocationManager.COORDINATE_TYPE_GCJ02);
		_Queue= Volley.newRequestQueue(getApplicationContext());
	}

	private final Location mCenter = new Location("");

	private void updatePosition() {
		GeoPoint c = mMapView.getMapCenter();
		double lat = c.getLatitudeE6() / 1E6;
		double lng = c.getLongitudeE6() / 1E6;
		mCenter.setLatitude(lat);
		mCenter.setLongitude(lng);
		getJson("http://apis.map.qq.com/ws/geocoder/v1/?location="
				+ lat + ","
				+ lng + "&key=" + app_id
				+ "&get_poi=1");
		Toast.makeText(MainActivity.this, lat + "," + lng, Toast.LENGTH_LONG)
				.show();

	}

	private void initMapView() {

		mMapView = (MapView) findViewById(R.id.mapviewOverlay);
		mMapView.setBuiltInZoomControls(false);
		mMapView.getController().setZoom(18);
		mMapView.setOnTouchListener(this);

		Bitmap bmpMarker = BitmapFactory.decodeResource(getResources(),
				R.drawable.location_direction);
		mLocationOverlay = new LocationOverlay(bmpMarker);
		mMapView.addOverlay(mLocationOverlay);
		mListView = (ListView) findViewById(R.id.id_listview);
		adapter = new AdddressAdapter(MainActivity.this, arrayList);
		mListView.setAdapter(adapter);
	}

	private void getJson(String url) {
		
		_Queue.add(new JsonObjectRequest(Method.GET, url, null,
                new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            	String status = "";
				String json = response.toString();
				if (!TextUtils.isEmpty(json)) {
					JSONObject jObject = null;
					try {
						jObject = new JSONObject(json);
						status = jObject.getString("status");
						String result = jObject.getString("result");
						if ("0".equals(status)) {
							JSONObject jo = new JSONObject(result);
							String pois2 = jo.getString("pois");
							JSONArray joArray = null;
							joArray = new JSONArray(pois2);
							arrayList = new ArrayList<Pois>();
							for (int i = 0; i < joArray.length(); i++) {
								pois = new Pois();
								jObject = joArray.getJSONObject(i);
								pois.setId(jObject.getString("id"));
								pois.setTitle(jObject.getString("title"));
								pois.setAddress(jObject.getString("address"));
								pois.setCategory(jObject.getString("category"));
//								pois.setLat(jObject.getString("lat"));
//								pois.setLng(jObject.getString("lng"));
								pois.set_distance(jObject.getString("_distance"));
								arrayList.add(pois);
							}
							
							adapter.changeData(arrayList);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
            }
        }, null));
		_Queue.start();
		
		
//		FinalHttp fh = new FinalHttp();
//		fh.get(url, new AjaxCallBack<Object>() {
//			@Override
//			public void onSuccess(Object t) {
//				super.onSuccess(t);
//				String status = "";
//				String json = t.toString();
//				if (!TextUtils.isEmpty(json)) {
//					JSONObject jObject = null;
//					try {
//						jObject = new JSONObject(json);
//						status = jObject.getString("status");
//						String result = jObject.getString("result");
//						if ("0".equals(status)) {
//							JSONObject jo = new JSONObject(result);
//							String pois2 = jo.getString("pois");
//							JSONArray joArray = null;
//							joArray = new JSONArray(pois2);
//							arrayList = new ArrayList<Pois>();
//							for (int i = 0; i < joArray.length(); i++) {
//								pois = new Pois();
//								jObject = joArray.getJSONObject(i);
//								pois.setId(jObject.getString("id"));
//								pois.setTitle(jObject.getString("title"));
//								pois.setAddress(jObject.getString("address"));
//								pois.setCategory(jObject.getString("category"));
////								pois.setLat(jObject.getString("lat"));
////								pois.setLng(jObject.getString("lng"));
//								pois.set_distance(jObject.getString("_distance"));
//								arrayList.add(pois);
//							}
//							
//							adapter.changeData(arrayList);
//						}
//					} catch (JSONException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//
//				System.out.println(t.toString());
//			}
//
//			@Override
//			public void onLoading(long count, long current) {
//				// TODO Auto-generated method stub
//				super.onLoading(count, current);
//			}
//
//			@Override
//			public void onStart() {
//				// TODO Auto-generated method stub
//				super.onStart();
//			}
//
//		});

	}

	@Override
	protected void onResume() {
		super.onResume();
		startLocation();
		count = 1;
	}

	@Override
	protected void onPause() {
		super.onPause();
		stopLocation();
		count = 1;
	}

	// ===== view listeners
	public void myLocation(View view) {
		if (mLocation != null) {
			mMapView.getController().animateTo(of(mLocation));
		}
	}

	public void myPoint(View view) {
		GeoPoint point = mMapView.getMapCenter();
		point.getLatitudeE6();
		Toast.makeText(
				MainActivity.this,
				point.getLatitudeE6() / 1E6 + "," + point.getLongitudeE6()
						/ 1E6, Toast.LENGTH_LONG).show();
	}

	// ===== view listeners

	// ====== location callback

	@Override
	public void onLocationChanged(TencentLocation location, int error,
			String reason) {
		if (error == TencentLocation.ERROR_OK) {
			mLocation = location;

			// 定位成功
			StringBuilder sb = new StringBuilder();
			sb.append("(纬度=").append(location.getLatitude()).append(",经度=")
					.append(location.getLongitude()).append(",精度=")
					.append(location.getAccuracy()).append("), 来源=")
					.append(location.getProvider()).append(", 地址=")
					.append(location.getAddress());

			// 更新 status
			my_address.setText("我的当前位置：" + location.getAddress());

			// 更新 location 图层
			mLocationOverlay.setAccuracy(mLocation.getAccuracy());
			mLocationOverlay.setGeoCoords(of(mLocation));
			mMapView.invalidate();
			getJson("http://apis.map.qq.com/ws/geocoder/v1/?location="
					+ location.getLatitude() + ","
					+ location.getLongitude() + "&key=" + app_id
					+ "&get_poi=1");
			if (mLocation != null && count == 1) {
				mMapView.getController().animateTo(of(mLocation));
				count = 2;
			}

		}
	}

	@Override
	public void onStatusUpdate(String name, int status, String desc) {
		// ignore
	}

	// ====== location callback

	private void startLocation() {
		TencentLocationRequest request = TencentLocationRequest.create();
		request.setInterval(5000);
		mLocationManager.requestLocationUpdates(request, this);

		mRequestParams = request.toString() + ", 坐标系="
				+ DemoUtils.toString(mLocationManager.getCoordinateType());
	}

	private void stopLocation() {
		mLocationManager.removeUpdates(this);
	}

	// ====== util methods

	private static GeoPoint of(TencentLocation location) {
		GeoPoint ge = new GeoPoint((int) (location.getLatitude() * 1E6),
				(int) (location.getLongitude() * 1E6));
		return ge;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			updatePosition();
		}
		return false;
	}
}

class LocationOverlay extends Overlay {

	GeoPoint geoPoint;
	Bitmap bmpMarker;
	float fAccuracy = 0f;

	public LocationOverlay(Bitmap mMarker) {
		bmpMarker = mMarker;
	}

	public void setGeoCoords(GeoPoint point) {
		if (geoPoint == null) {
			geoPoint = new GeoPoint(point.getLatitudeE6(),
					point.getLongitudeE6());
		} else {
			geoPoint.setLatitudeE6(point.getLatitudeE6());
			geoPoint.setLongitudeE6(point.getLongitudeE6());
		}
	}

	public void setAccuracy(float fAccur) {
		fAccuracy = fAccur;
	}

	@Override
	public void draw(Canvas canvas, MapView mapView) {
		if (geoPoint == null) {
			return;
		}
		Projection mapProjection = mapView.getProjection();
		Paint paint = new Paint();
		Point ptMap = mapProjection.toPixels(geoPoint, null);
		paint.setColor(Color.BLUE);
		paint.setAlpha(8);
		paint.setAntiAlias(true);

		float fRadius = mapProjection.metersToEquatorPixels(fAccuracy);
		canvas.drawCircle(ptMap.x, ptMap.y, fRadius, paint);
		paint.setStyle(Style.STROKE);
		paint.setAlpha(200);
		canvas.drawCircle(ptMap.x, ptMap.y, fRadius, paint);

		if (bmpMarker != null) {
			paint.setAlpha(255);
			canvas.drawBitmap(bmpMarker, ptMap.x - bmpMarker.getWidth() / 2,
					ptMap.y - bmpMarker.getHeight() / 2, paint);
		}

		super.draw(canvas, mapView);
	}

}
