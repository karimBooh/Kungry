package ca.ulaval.ima.mp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class KungryError (
    var display: String?,
    var details: ArrayList<KungryErrorDetail>?
) : Parcelable