package ca.ulaval.ima.mp.ui.restaurants

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Creator(
    var first_name: String,
    var last_name: String
): Parcelable