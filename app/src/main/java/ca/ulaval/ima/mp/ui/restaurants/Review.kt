package ca.ulaval.ima.mp.ui.restaurants

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Review(
    var id: Int,
    var creator: Creator,
    var stars: Int,
    var image: String,
    var comment : String,
    var date : String
): Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Review

        if (id != other.id) return false
        if (creator != other.creator) return false
        if (stars != other.stars) return false
        if (!image.contentEquals(other.image)) return false
        if (comment != other.comment) return false
        if (date != other.date) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + creator.hashCode()
        result = 31 * result + stars
        result = 31 * result + image.hashCode()
        result = 31 * result + comment.hashCode()
        result = 31 * result + date.hashCode()
        return result
    }
}