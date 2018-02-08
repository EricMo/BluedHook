package com.conch.bluedhook.ui

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.location.Address
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.text.InputType
import android.text.TextUtils
import android.view.Menu
import android.view.View
import com.amap.api.maps2d.AMap
import com.amap.api.maps2d.CameraUpdateFactory
import com.amap.api.maps2d.UiSettings
import com.amap.api.maps2d.model.*
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.geocoder.GeocodeResult
import com.amap.api.services.geocoder.GeocodeSearch
import com.amap.api.services.geocoder.RegeocodeResult
import com.conch.bluedhook.R
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.include_location_info.*
import kotlinx.android.synthetic.main.include_toolbar.*
import java.util.*
import com.amap.api.services.geocoder.RegeocodeQuery


class MapActivity : AppCompatActivity(), AMap.OnMapClickListener, View.OnClickListener, GeocodeSearch.OnGeocodeSearchListener {


    //map object
    private lateinit var aMap: AMap

    //UiSettings
    lateinit var mUiSettings: UiSettings

    //address markOption
    lateinit var markerOption: MarkerOptions

    //address marker
    private var currentMarker: Marker? = null

    //geocoderSearch
    lateinit var geocoderSearch: GeocodeSearch

    //currentLatlng
    private var cLocation: LatLng? = null

    //mSearchView
    private var mSearchView: SearchView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        setSupportActionBar(toolbar)
        toolbar.title = title.toString()
        mMapView.onCreate(savedInstanceState)
        init()
        initUiSetting()
        initMarkerOptions()
        initGeocodeSearch()
    }

    /**
     * init map
     */
    private fun init() {
        aMap = mMapView.map
        // get user default language
        val locale = Locale.getDefault()
        val language = "${locale.language}-${locale.country}"
        //set map language,only English or Chinese
        aMap.setMapLanguage(if (language.contains("zh")) AMap.CHINESE else AMap.ENGLISH)
        //map click
        aMap.setOnMapClickListener(this)
    }

    /**
     * init uisetting
     */
    private fun initUiSetting() {
        mUiSettings = aMap.uiSettings
        mUiSettings.isCompassEnabled = true
        mUiSettings.isScaleControlsEnabled = true
        mUiSettings.isZoomControlsEnabled = false
    }

    /**
     * init markOptions
     */
    private fun initMarkerOptions() {
        markerOption = MarkerOptions()
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(resources, R.mipmap.icon_location_marker)))
    }

    /**
     * init GeocodeSearch
     */
    private fun initGeocodeSearch() {
        geocoderSearch = GeocodeSearch(this)
        geocoderSearch.setOnGeocodeSearchListener(this)
    }

    private fun initSearchView() {
        mSearchView?.queryHint = getString(R.string.searchHint)
        mSearchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                // don't do anything
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    if (query.contains(",")) {
                        //this is a LatLng
                        val latlng = query.split(",").filter {
                            it != ""
                        }
                        if (latlng.size == 2) {
                            searchLatlng(LatLng(latlng[0].toDouble(), latlng[1].toDouble()))
                        }
                    }
                }
                return false
            }
        })
    }


    /**
     * show marker on the map
     */
    private fun showMarker(location: LatLng?) {
        markerOption.position(location)
        currentMarker?.remove()
        currentMarker = aMap.addMarker(markerOption)
    }

    /**
     * show the location's detail
     */
    private fun showLocationInfo(location: LatLng, address: String) {
        cLocation = location
        layout_location.visibility = View.VISIBLE
        tv_address.text = if (TextUtils.isEmpty(address)) getString(R.string.noAddressHint) else address
        tv_latlng.text = getString(R.string.latlngFormat, location.latitude.toString(), location.longitude.toString())
    }


    /**
     * Latlng to Address
     */
    private fun searchLatlng(location: LatLng) {
        val point = LatLonPoint(location.latitude, location.longitude)
        val query = RegeocodeQuery(point, 100f, GeocodeSearch.GPS)
        geocoderSearch.getFromLocationAsyn(query)
    }


    override fun onMapClick(location: LatLng?) {
        if (location != null) {
            val mCameraUpdate = CameraUpdateFactory.newCameraPosition(CameraPosition(location, aMap.cameraPosition.zoom, 30f, 0f))
            aMap.moveCamera(mCameraUpdate)
        }
        if (location != null) {
            searchLatlng(location)
        }
        showMarker(location)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.pickup -> {
                val mIntent = Intent()
                mIntent.putExtra("LatLng", cLocation)
                setResult(Activity.RESULT_OK, mIntent)
                finish()
            }
        }
    }

    override fun onRegeocodeSearched(result: RegeocodeResult?, rCode: Int) {
        when (rCode) {
            1000 -> {
                val address = result!!.regeocodeAddress
                showLocationInfo(LatLng(result.regeocodeQuery.point.latitude, result.regeocodeQuery.point.longitude), address.formatAddress)
            }
            else -> {
                Snackbar.make(root, R.string.locationError, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onGeocodeSearched(result: GeocodeResult?, rCode: Int) {

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.map_menu, menu)
        val searchItem = menu?.findItem(R.id.menu_search)
        mSearchView = MenuItemCompat.getActionView(searchItem) as SearchView?
        initSearchView()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onResume() {
        super.onResume()
        mMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMapView.onPause()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mMapView.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView.onDestroy()
    }

}
