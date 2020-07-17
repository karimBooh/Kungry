package ca.ulaval.ima.mp.ui.restaurants

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location(
    var latitude : Double,
    var longitude : Double
) : Parcelable