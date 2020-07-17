package ca.ulaval.ima.mp.ui.profile

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RegisterRequest (
    var client_id : String,
    var client_secret : String,
    var last_name : String,
    var first_name : String,
    var email : String,
    var password : String
) : Parcelable