package ca.ulaval.ima.mp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class KungryMeta (
    var status_code: Int
) : Parcelable