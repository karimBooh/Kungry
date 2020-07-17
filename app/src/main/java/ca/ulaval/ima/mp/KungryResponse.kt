package ca.ulaval.ima.mp

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize
import org.json.JSONObject

@Parcelize
data class KungryResponse (
    var meta: KungryMeta,
    var error: KungryError?
) : Parcelable