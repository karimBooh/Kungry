package ca.ulaval.ima.mp.ui.restaurants

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.ulaval.ima.mp.R
import ca.ulaval.ima.mp.RequestService
import com.android.volley.Response
import com.google.gson.Gson

class RestaurantsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var restaurants: Array<Restaurant>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_restaurants, container, false)
        RequestService.getInstance(context as Context)
            .getArrayRequest("restaurant/", Response.Listener { response ->
                this.restaurants = Gson().fromJson(response.toString(), Array<Restaurant>::class.java)

                println(this.restaurants)
                recyclerView = root.findViewById<RecyclerView>(R.id.restaurants_list).apply {
                    // use this setting to improve performance if you know that changes
                    // in content do not change the layout size of the RecyclerView
                    setHasFixedSize(true)

                    // use a linear layout manager
                    layoutManager = LinearLayoutManager(context)

                    // specify an viewAdapter (see also next example)
                    adapter = RestaurantsAdapter(restaurants)

                }
            });


    return root
    }

}