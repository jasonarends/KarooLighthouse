package com.jasonarends.karoolighthouse

import io.hammerhead.sdk.v0.Module
import io.hammerhead.sdk.v0.ModuleFactoryI
import io.hammerhead.sdk.v0.SdkContext
import io.hammerhead.sdk.v0.card.PostRideCard
import io.hammerhead.sdk.v0.card.RideDetailsI
import io.hammerhead.sdk.v0.datatype.SdkDataType
import timber.log.Timber

class WeatherModule(context: SdkContext) : Module(context) {
    override val name: String = "WeatherModule"
    override val version: String = "1.0"

    override fun onStart(): Boolean {
        Timber.i("WeatherModule received ride start event")
        return false
    }

    override fun provideDataTypes(): List<SdkDataType> {
        return listOf(
            WindSpeedDataType(context),
            WindDirectionDataType(context),
            ForecastPrecipDataType(context),
            //WeatherAlertsDataType(context)
        )
    }

    override fun postRideCard(details: RideDetailsI): PostRideCard? {
        return null // adjust this as needed for your module
    }

    companion object {
        @JvmField
        val factory = object : ModuleFactoryI {
            override fun buildModule(context: SdkContext): Module {
                return WeatherModule(context)
            }
        }
    }
}
