package ca.ulaval.ima.mp.ui.restaurants

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cuisine(
    var id : Int,
    var name : String
) : Parcelable
