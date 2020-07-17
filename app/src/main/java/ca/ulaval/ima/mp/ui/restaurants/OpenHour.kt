package ca.ulaval.ima.mp.ui.restaurants

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OpenHour(
    var id: Int,
    var opening_hour: String,
    var closing_hour: String,
    var day: String
):Parcelable