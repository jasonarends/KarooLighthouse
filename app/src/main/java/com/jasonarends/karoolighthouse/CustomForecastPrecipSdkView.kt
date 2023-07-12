package com.jasonarends.karoolighthouse

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.hammerhead.sdk.v0.SdkContext
import io.hammerhead.sdk.v0.datatype.view.SdkView

class CustomForecastPrecipSdkView(context: SdkContext) : SdkView(context), Parcelable {

    override fun createView(layoutInflater: LayoutInflater, parent: ViewGroup): View {
        TODO("Not yet implemented")
    }

    override fun onInvalid(view: View) {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CustomForecastPrecipSdkView> {
        override fun createFromParcel(p0: Parcel?): CustomForecastPrecipSdkView {
            TODO("Not yet implemented")
        }


        override fun newArray(size: Int): Array<CustomForecastPrecipSdkView?> {
            return arrayOfNulls(size)
        }
    }

}
