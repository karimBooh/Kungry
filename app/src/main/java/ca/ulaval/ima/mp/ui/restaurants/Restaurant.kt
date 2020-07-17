package ca.ulaval.ima.mp.ui.restaurants

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Restaurant(
    var id : Int,
    var name : String,
    var cuisine : Array<Cuisine>,
    var type : String,
    var review_count : Int,
    var review_average : Double,
    var image : String,
    var distance : String,
    var location : Location,

    var opening_hours : Array<OpenHour>,
    var reviews : Array<Review>,
    var website : String,
    var phone_number : String

) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Restaurant

        if (id != other.id) return false
        if (name != other.name) return false
        if (!cuisine.contentEquals(other.cuisine)) return false
        if (type != other.type) return false
        if (review_count != other.review_count) return false
        if (review_average != other.review_average) return false
        if (image != other.image) return false
        if (distance != other.distance) return false
        if (location != other.location) return false
        if (!opening_hours.contentEquals(other.opening_hours)) return false
        if (!reviews.contentEquals(other.reviews)) return false
        if (website != other.website) return false
        if (phone_number != other.phone_number) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + cuisine.contentHashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + review_count
        result = 31 * result + review_average.hashCode()
        result = 31 * result + image.hashCode()
        result = 31 * result + distance.hashCode()
        result = 31 * result + location.hashCode()
        result = 31 * result + opening_hours.contentHashCode()
        result = 31 * result + reviews.contentHashCode()
        result = 31 * result + website.hashCode()
        result = 31 * result + phone_number.hashCode()
        return result
    }
}