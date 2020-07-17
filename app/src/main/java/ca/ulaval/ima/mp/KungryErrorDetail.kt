package ca.ulaval.ima.mp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class KungryErrorDetail (
    var detail: String
) : Parcelable