package com.jasonarends.karoolighthouse

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.hammerhead.sdk.v0.SdkContext
import io.hammerhead.sdk.v0.datatype.view.SdkView

class CustomWindDirectionSdkView(context: SdkContext) : SdkView(context) {


    companion object CREATOR : Parcelable.Creator<CustomWindDirectionSdkView> {
        override fun createFromParcel(p0: Parcel?): CustomWindDirectionSdkView {
            TODO("Not yet implemented")
        }

        override fun newArray(size: Int): Array<CustomWindDirectionSdkView?> {
            return arrayOfNulls(size)
        }
    }

    override fun createView(layoutInflater: LayoutInflater, parent: ViewGroup): View {
        TODO("Not yet implemented")
    }

    override fun onInvalid(view: View) {
        TODO("Not yet implemented")
    }

}
