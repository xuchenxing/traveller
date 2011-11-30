package xcx.traveller;

import android.R.anim;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.LocationListener;
import com.baidu.mapapi.MKLocationManager;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapController;
import com.baidu.mapapi.MapView;

public class TravellerActivity extends MapActivity {
	// 地图对象
	BMapManager mBMapMan = null;

	private MKLocationManager mLocationManager;
	// 位置监听器
	private LocationListener locationlistener;

	// map对象
	MapView mMapView = null;

	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mBMapMan = new BMapManager(getApplication());
		mBMapMan.init("32C220E8C75EC88BA2725726351E32C997D66FEF", null);
		super.initMapActivity(mBMapMan);


		mMapView = (MapView) findViewById(R.id.bmapsView);
		mMapView.setBuiltInZoomControls(true); // 设置启用内置的缩放控件

		MapController mMapController = mMapView.getController(); // 得到mMapView的控制权,可以用它控制和驱动平移和缩放
		GeoPoint point = new GeoPoint((int) (39.915 * 1E6),
				(int) (116.404 * 1E6)); // 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
		mMapController.setCenter(point); // 设置地图中心点
		mMapController.setZoom(12); // 设置地图zoom级别

	}

	/*
	 * 开始
	 */
	public void startListen() {
		mLocationManager = mBMapMan.getLocationManager();
		locationlistener = new LocationListener() {
			public void onLocationChanged(Location location) {
				System.out.println("location changed:纬度"
						+ location.getLatitude() + ",经度"
						+ location.getLongitude());
				// 生成当前位置点
				GeoPoint nowPoint = new GeoPoint((int) location.getLatitude(),
						(int) location.getLongitude());
				// 用map对象生成自定义覆盖层对象
				MyOverlay overlay = new MyOverlay(
						TravellerActivity.this.mMapView, null);
				// 在map对象上加上覆盖层
				mMapView.getOverlays().add(overlay);
			}
		};
		// 注册监听
		mLocationManager.requestLocationUpdates(locationlistener);
	}

	/*
	 * 结束按钮监听类
	 */
	public void endListen() {
		mLocationManager.removeUpdates(locationlistener);
	}

	@Override
	protected void onDestroy() {
		if (mBMapMan != null) {
			mBMapMan.destroy();
			mBMapMan = null;
		}
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		if (mBMapMan != null) {
			mBMapMan.stop();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		if (mBMapMan != null) {
			mBMapMan.start();
		}
		super.onResume();
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * 菜单 (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.addSubMenu(0, 1, 1, "开始");
		menu.addSubMenu(0, 2, 2, "停止");
		menu.addSubMenu(0, 3, 3, "保存");
		menu.addSubMenu(0, 4, 4, "分享");
		menu.addSubMenu(0, 5, 5, "关于");
		menu.addSubMenu(0, 6, 6, "退出");
		return super.onCreateOptionsMenu(menu);
	}

	/*
	 * 菜单点击监听方法
	 * (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1: //开始
			startListen();
			break;
		case 2:	//停止
			endListen();
			break;
		case 3://保存
			
			break;
		case 4://分享
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), ShareToWeibo.class);
			startActivity(intent);
			break;
		case 5://关于
			startActivity(new Intent().setClass(getApplicationContext(), About.class));
		case 6:
			finish();
			break;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

}