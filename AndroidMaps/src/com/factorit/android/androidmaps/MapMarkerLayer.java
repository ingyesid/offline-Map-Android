package com.factorit.android.androidmaps;

import org.mapsforge.android.maps.overlay.ArrayItemizedOverlay;
import org.mapsforge.android.maps.overlay.OverlayItem;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

public class MapMarkerLayer extends ArrayItemizedOverlay {
private Context mContext;
	public MapMarkerLayer(Context context,Drawable defaultMarker) {
		super(boundCenter(defaultMarker));
		mContext=context;
	}
	 @Override
	    protected boolean onTap(int index) {
	        OverlayItem item = createItem(index);	 
	        Toast.makeText(mContext, "Tap "+item.getTitle(), Toast.LENGTH_LONG).show();
			return false;
	    }
   
}
