package com.jasonarends.karoolighthouse

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.hammerhead.sdk.v0.SdkContext
import io.hammerhead.sdk.v0.datatype.view.SdkView

class CustomWindSpeedSdkView(context: SdkContext) : SdkView(context) {
    private var valueView: TextView? = null

    override fun createView(layoutInflater: LayoutInflater, parent: ViewGroup): View {
        val view = layoutInflater.inflate(R.layout.custom_windspeed_view, parent, false)
        valueView = view.findViewById(R.id.valueView)
        return view
    }

    override fun onInvalid(view: View) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<CustomWindSpeedSdkView> {
        override fun createFromParcel(p0: Parcel?): CustomWindSpeedSdkView {
            TODO("Not yet implemented")
        }

        override fun newArray(size: Int): Array<CustomWindSpeedSdkView?> {
            return arrayOfNulls(size)
        }
    }

}
