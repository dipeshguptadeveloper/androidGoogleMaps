package com.dkgtech.googlemaps

import android.content.ContentValues.TAG
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Geocoder.GeocodeListener
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.dkgtech.googlemaps.databinding.ActivityMainBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

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
        mMaps.addMarker(
            MarkerOptions()
                .position(myLocation) // for marker position
                .title("Mumbai") // for marker title
                .snippet("My Mumbai") // for marker subtitle
                .draggable(true) // for marker dragging
//                .alpha(0.5f) // for marker opacity
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.location)) // for marker icon
        )
        mMaps.moveCamera(CameraUpdateFactory.newLatLng(myLocation)) // to move on marker location when map load
        mMaps.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 16f)) // to zoom

        mMaps.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDrag(p0: Marker) {
                Toast.makeText(this@MainActivity, "Dragging", Toast.LENGTH_SHORT).show()
            }

            override fun onMarkerDragEnd(p0: Marker) {
                val lat = p0.position.latitude
                val long = p0.position.longitude
                Log.d("Location Lat Long", "onMarkerDragEnd: $lat, $long")
                val geocoder = Geocoder(this@MainActivity)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    geocoder.getFromLocation(lat, long, 5, object : GeocodeListener {
                        override fun onGeocode(p0: MutableList<Address>) {
                            Log.d("Address", p0[0].getAddressLine(0))
                        }

                    })
                } else {
                    val address = geocoder.getFromLocation(lat, long, 5)
                    address?.let {
                        Log.d("Address", address[0].getAddressLine(0))
                    }

                }
            }

            override fun onMarkerDragStart(p0: Marker) {
                Toast.makeText(this@MainActivity, "Drag", Toast.LENGTH_SHORT).show()
            }

        })

        val listLatLng = mutableListOf<LatLng>().apply {
            add(LatLng(26.2389, 73.0243))
        }

        mMaps.addPolyline(PolylineOptions().addAll(listLatLng))

        mMaps.addCircle(
            CircleOptions()
                .center(myLocation)
                .radius(200.0)
                .strokeColor(Color.MAGENTA)
                .fillColor(Color.YELLOW)
                .clickable(true)
        )


    }

}