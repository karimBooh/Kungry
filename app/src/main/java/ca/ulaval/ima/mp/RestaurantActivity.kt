package ca.ulaval.ima.mp

import android.nfc.tech.TagTechnology
import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextClock
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.ulaval.ima.mp.ui.restaurants.Restaurant
import ca.ulaval.ima.mp.ui.restaurants.RestaurantsAdapter
import ca.ulaval.ima.mp.ui.restaurants.ReviewAdapter
import com.android.volley.Response
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class RestaurantActivity : AppCompatActivity() {

    lateinit var mMapView: MapView
    private lateinit var googleMap: GoogleMap
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var restaurants: Array<Restaurant>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.restaurant_activity)

        supportActionBar!!.hide()

        val restaurant = intent.getParcelableExtra<Restaurant>("Restaurant")

        println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||$restaurant");
        mMapView = findViewById(R.id.mapView2)
        mMapView.onCreate(savedInstanceState)
        mMapView.onResume();
        try {
            MapsInitializer.initialize(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        mMapView.getMapAsync { mMap ->
            googleMap = mMap
            googleMap.setMyLocationEnabled(true)
        }


        val backButton = findViewById<FloatingActionButton>(R.id.fabBack)

        backButton.setOnClickListener{view ->
            finish()
        }

        RequestService.getInstance(this).getObjectRequest("restaurant/" + restaurant.id, Response.Listener { response ->
            val restaurant = Gson().fromJson(response.toString(), Restaurant::class.java)

            val image = findViewById<ImageView>(R.id.logoResto)
                Picasso.get().load(restaurant.image).into(image)

            val name = findViewById<TextView>(R.id.restaurantTitle)
            name.text = restaurant.name;

            val type = findViewById<TextView>(R.id.restauantType)

            type.text = restaurant.type +'-' +  restaurant.cuisine[0].name

            val distance = findViewById<TextView>(R.id.pinDistance)

            distance.text = restaurant.distance;

            val stars = findViewById<RatingBar>(R.id.ratingBar2);
            stars.numStars = restaurant.review_average.toInt()

            val nbr = findViewById<TextView>(R.id.ratingNumber)
            nbr.text = restaurant.review_count.toString()

            val phone = findViewById<TextView>(R.id.phone)
            phone.text = restaurant.phone_number

            val site = findViewById<TextView>(R.id.web)
            site.text = restaurant.website

            restaurant.opening_hours.forEach { hour ->

                println(hour.toString())
                if (hour.day == "SUN" && hour.opening_hour != null) {
                    val sun = findViewById<TextView>(R.id.sunday)
                    sun.text = hour.opening_hour.substring(0, 5) + " à " + hour.closing_hour.substring(0, 5)
                }
                if (hour.day == "MON" && hour.opening_hour != null) {
                    val mon = findViewById<TextView>(R.id.monday)
                    mon.text = hour.opening_hour.substring(0, 5) + " à " + hour.closing_hour.substring(0, 5)
                }
                if (hour.day == "TUE" && hour.opening_hour != null) {
                    val tue = findViewById<TextView>(R.id.tuesday)
                    tue.text = hour.opening_hour.substring(0, 5) + " à " + hour.closing_hour.substring(0, 5)
                }
                if (hour.day == "WED" && hour.opening_hour != null) {
                    val wed = findViewById<TextView>(R.id.wednesday)
                    wed.text = hour.opening_hour.substring(0, 5) + " à " + hour.closing_hour.substring(0, 5)
                }
                if (hour.day == "THU" && hour.opening_hour != null) {
                    val thu = findViewById<TextView>(R.id.thursday)
                    thu.text = hour.opening_hour.substring(0, 5) + " à " + hour.closing_hour.substring(0, 5)
                }
                if (hour.day == "FRI" && hour.opening_hour != null) {
                    val fri = findViewById<TextView>(R.id.friday)
                    fri.text = hour.opening_hour.substring(0, 5) + " à " + hour.closing_hour.substring(0, 5)
                }
                if (hour.day == "SAT" && hour.opening_hour != null) {
                    val sat = findViewById<TextView>(R.id.saturday)
                    sat.text = hour.opening_hour.substring(0, 5) + " à " + hour.closing_hour.substring(0, 5)
                }

            }

            recyclerView = findViewById<RecyclerView>(R.id.reviews).apply {
                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                setHasFixedSize(true)

                // use a linear layout manager
                layoutManager = LinearLayoutManager(context)

                // specify an viewAdapter (see also next example)
                adapter = ReviewAdapter(restaurant.reviews)

            }

        })

    }
}