package com.factorit.android.androidmaps;

import java.io.File;

import org.mapsforge.android.maps.MapActivity;
import org.mapsforge.android.maps.MapController;
import org.mapsforge.android.maps.MapView;
import org.mapsforge.android.maps.overlay.OverlayItem;
import org.mapsforge.core.GeoPoint;
import org.mapsforge.map.reader.header.FileOpenResult;
import org.mapsforge.map.reader.header.MapFileInfo;

import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

public class MainActivity extends MapActivity {
	private static final File MAP_FILE = new File(Environment
			.getExternalStorageDirectory().getAbsolutePath(),
			"maps/corozal.map");
	private MapView mapView;
	private MapController mapController;
	private MapFileInfo mapFileInfo;

	private MapMarkerLayer redMapMarkerLayer;
	private MapMarkerLayer greenMapMarkerLayer;
	private MapMarkerLayer blueMapMarkerLayer;
	private MapMarkerLayer purpleMapMarkerLayer;

	
	// estructura de prueba para los puntos [<latitude>,<longitude>,<estado>]
	
	private String[] points = { "-34.202508,-71.660144,1", "9.3114,-75.2918,2,",
			"9.3163, -75.2912,3", "9.3176, -75.2976,4" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mapView = (MapView) findViewById(R.id.mapView);
		mapView.setClickable(true);
		mapView.setBuiltInZoomControls(true);
		
		
		// inicializamos las capas de mapas
		redMapMarkerLayer = new MapMarkerLayer(MainActivity.this,
				getResources().getDrawable(R.drawable.ic_marker_red));
		blueMapMarkerLayer = new MapMarkerLayer(MainActivity.this,
				getResources().getDrawable(R.drawable.ic_marker_blue));
		greenMapMarkerLayer = new MapMarkerLayer(MainActivity.this,
				getResources().getDrawable(R.drawable.ic_marker_green));
		purpleMapMarkerLayer = new MapMarkerLayer(MainActivity.this,
				getResources().getDrawable(R.drawable.ic_marker_purple));

		// seteamos el archivo del mapa
		FileOpenResult fileOpenResult = mapView.setMapFile(MAP_FILE);

		if (!fileOpenResult.isSuccess()) {
			Toast.makeText(this, fileOpenResult.getErrorMessage(),
					Toast.LENGTH_LONG).show();

		} else {

			// si se carga bien el mapa
			mapFileInfo = mapView.getMapDatabase().getMapFileInfo();
			mapController = mapView.getController();
			
			mapView.setCenter(mapFileInfo.boundingBox.getCenterPoint());
			mapController.setZoom(14);
			drawPoints(points);

		}

	}

	
	private GeoPoint getGeoPoint(double lat, double lon) {
		return (new GeoPoint((int) (lat * 1000000.0), (int) (lon * 1000000.0)));
	}

	private void drawPoints(String[] points) {
		for (int i = 0; i < points.length; i++) {
			String[] point = points[i].split(",");
			// ahora segun el estado lo agregamos a la capa con un color
			// especifico
			if (point[2].equals("1")) {
				redMapMarkerLayer.addItem(new OverlayItem(getGeoPoint(
						Double.valueOf(point[0]), Double.valueOf(point[1])),
						point[0]+","+point[1], ""));
			}
			if (point[2].equals("2")) {
				blueMapMarkerLayer.addItem(new OverlayItem(getGeoPoint(
						Double.valueOf(point[0]), Double.valueOf(point[1])),
						point[0]+","+point[1], ""));
			}
			if (point[2].equals("3")) {
				greenMapMarkerLayer.addItem(new OverlayItem(getGeoPoint(
						Double.valueOf(point[0]), Double.valueOf(point[1])),
						point[0]+","+point[1], ""));
			}
			if (point[2].equals("4")) {
				purpleMapMarkerLayer.addItem(new OverlayItem(getGeoPoint(
						Double.valueOf(point[0]), Double.valueOf(point[1])),
						point[0]+","+point[1], ""));
			}
		}

		mapView.getOverlays().add(blueMapMarkerLayer);
		mapView.getOverlays().add(redMapMarkerLayer);
		mapView.getOverlays().add(greenMapMarkerLayer);
		mapView.getOverlays().add(purpleMapMarkerLayer);

	}

}
