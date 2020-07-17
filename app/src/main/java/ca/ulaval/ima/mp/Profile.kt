package ca.ulaval.ima.mp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Profile (
    var total_review_count : Int,
    var last_name : String,
    var first_name : String,
    var email : String,
    var created : String,
    var updated : String,
    var user : Int
    ) : Parcelable