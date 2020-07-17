package ca.ulaval.ima.mp.ui.restaurants

import android.content.Intent
import android.view.LayoutInflater
import android.view.PointerIcon
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import ca.ulaval.ima.mp.R
import ca.ulaval.ima.mp.RestaurantActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.restaurant_view.view.*

class RestaurantsAdapter(private val myDataset: Array<Restaurant>) :
    RecyclerView.Adapter<RestaurantsAdapter.MyViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val logo= view.restaurantLogo;
        val name = view.restaurantTitle;
        val type = view.restauantType;
        val rateNumber = view.ratingNumber;
        val rate = view.ratingBar;
        val distance = view.pinDistance
    }

    fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
        itemView.setOnClickListener {
            event.invoke(adapterPosition, itemViewType)
        }
        return this
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.restaurant_view, parent, false)

        // set the view's size, margins, paddings and layout parameters

        return MyViewHolder(view).listen { pos, type ->
            val intent = Intent(parent.context, RestaurantActivity::class.java)
            myDataset[pos].opening_hours = emptyArray()
            myDataset[pos].reviews = emptyArray()
            myDataset[pos].website = ""
            myDataset[pos].phone_number = ""

            intent.putExtra("Restaurant", myDataset[pos])
            startActivity(parent.context, intent, null)
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val restaurant : Restaurant = myDataset.get(position)



        Picasso.get().load(restaurant.image).into(holder.logo)
        holder.name.setText(restaurant.name)
        holder.type.setText(restaurant.type + ' ' + restaurant.cuisine[0].name)
        holder.rateNumber.setText(restaurant.review_count.toString())
        holder.rate.numStars = restaurant.review_average.toInt()
        holder.distance.setText(restaurant.distance)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}