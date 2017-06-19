/*
 * Copyright (c) 2011-2017 HERE Europe B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.here.android.example.routing;

import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.common.PositioningManager;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapBuildingGroup;
import com.here.android.mpa.mapping.MapFragment;
import com.here.android.mpa.mapping.MapMarker;
import com.here.android.mpa.mapping.MapRasterTileSource;
import com.here.odnp.util.Log;

import android.app.Activity;
import android.graphics.Color;
import android.widget.TextView;
import android.widget.Toast;

import java.util.EnumSet;

/**
 * This class encapsulates the properties and functionality of the Map view.
 */
public class MapFragmentView {
    private MapFragment m_mapFragment;
    private Activity m_activity;
    private Map m_map;
    private String LOG_TAG="MapFragmentView";
    MapBuildingGroup buildingGroup;
    public MapFragmentView(Activity activity) {
        m_activity = activity;
        initMapFragment();
    }

    private void initMapFragment() {
        /* Locate the mapFragment UI element */
        m_mapFragment = (MapFragment) m_activity.getFragmentManager()
                .findFragmentById(R.id.mapfragment);

        if (m_mapFragment != null) {
            /* Initialize the MapFragment, results will be given via the called back. */
            m_mapFragment.init(new OnEngineInitListener() {
                @Override
                public void onEngineInitializationCompleted(Error error) {

                    if (error == Error.NONE) {
                        /*
                         * If no error returned from map fragment initialization, the map will be
                         * rendered on screen at this moment.Further actions on map can be provided
                         * by calling Map APIs.
                         */
//                        m_map = m_mapFragment.getMap();
                  onMapFragmentInitializationCompleted();
                    } else {
                        Toast.makeText(m_activity,
                                "ERROR: Cannot initialize Map with error " + error,
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    private void onMapFragmentInitializationCompleted() {
        m_map = m_mapFragment.getMap();
        System.out.println("dog"+m_map);
        m_map.setCenter(new GeoCoordinate(19.174598,72.860722),
                Map.Animation.NONE);
        m_map.setZoomLevel(15.2);
        MapMarker marker = new MapMarker();
        PositioningManager posManager = PositioningManager.getInstance();
        posManager.start(PositioningManager.LocationMethod.GPS_NETWORK);
        posManager.start(PositioningManager.LocationMethod.GPS);
        Log.d(LOG_TAG, "onEngineInitializationCompleted: " + posManager.hasValidPosition(PositioningManager.LocationMethod.GPS_NETWORK));
        boolean temp = posManager.hasValidPosition(PositioningManager.LocationMethod.GPS_NETWORK);
        marker.setCoordinate(new GeoCoordinate(19.1745, 72.8));
        m_map.addMapObject(marker);


    }
}
