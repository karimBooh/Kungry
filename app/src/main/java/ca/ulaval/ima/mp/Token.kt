package ca.ulaval.ima.mp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Token (
    var access_token: String,
    var token_type: String,
    var refresh_token: String,
    var scope: String,
    var expires_in: Long
) : Parcelable