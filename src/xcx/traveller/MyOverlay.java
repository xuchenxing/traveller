package xcx.traveller;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.Overlay;

/**
 * 自定义移动路线图层类
 *   每次位置变化，都会新生成一个此类，并在当前点跟上一点之间画一条直线，添加到map对象上
 *   这样处理可能会让地图上的对象慢慢变的好多，估计会很占内存。
 * @author Administrator
 *
 */
public class MyOverlay extends Overlay {
	
	MapView mMapView = null;
	
	//当前点
	GeoPoint nowPoint = null;
	//上一点
	GeoPoint lastPoint = null;
	
	/*
	 * 构造方法，持有地图对象的引用
	 */
    public MyOverlay(MapView mMapView,GeoPoint point) {
		super();
		this.mMapView = mMapView;
		this.lastPoint = nowPoint;
		this.nowPoint = point;
	}

	//GeoPoint geoPoint1 = new GeoPoint((int) (39.915 * 1E6), (int) (116.404 * 1E6));
	//GeoPoint geoPoint2 = new GeoPoint((int) (39.815 * 1E6), (int) (116.404 * 1E6));
    Paint paint = new Paint();
 
    @Override
    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
        //在天安门的位置绘制一个String
        Point point1 = mMapView.getProjection().toPixels(nowPoint, null);
        Point point2 = mMapView.getProjection().toPixels(lastPoint, null);
        //在map上写字
        //canvas.drawText("★这里是天安门", point1.x, point1.y, paint);
        //在map上画线
        canvas.drawLine(point1.x, point1.y, point2.x, point2.y, paint);
    }
}