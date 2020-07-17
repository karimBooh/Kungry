package ca.ulaval.ima.mp.ui.restaurants

import kotlinx.android.synthetic.main.review_view.view.*

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ca.ulaval.ima.mp.R
import com.squareup.picasso.Picasso

class ReviewAdapter(private val myDataset: Array<Review>) :
    RecyclerView.Adapter<ReviewAdapter.MyViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val date= view.date;
        val name = view.username;
        val comment = view.comment;
        val rate = view.ratingBar3;
        val image = view.photo
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
            .inflate(R.layout.review_view, parent, false)

        return MyViewHolder(view).listen { pos, type ->

        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val review : Review = myDataset.get(position)
        Picasso.get().load(review.image).into(holder.image)
        holder.name.setText(review.creator.first_name + " " + review.creator.last_name)
        holder.date.setText(review.date)
        holder.rate.numStars = review.stars
        holder.comment.setText(review.comment)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        if (myDataset.size > 5)
              return 5;
        return myDataset.size
    }
}