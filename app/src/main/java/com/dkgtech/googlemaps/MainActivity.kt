package com.dkgtech.googlemaps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dkgtech.googlemaps.databinding.ActivityMainBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var binding: ActivityMainBinding
    lateinit var mMaps: GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val myMap = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        myMap.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        mMaps = map
        val myLocation = LatLng(19.0760, 72.8777)
        mMaps.addMarker(MarkerOptions().position(myLocation).title("Mumbai"))
        mMaps.moveCamera(CameraUpdateFactory.newLatLng(myLocation)) // to move on marker location when map load
        mMaps.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 16f)) // to zoom
    }
}