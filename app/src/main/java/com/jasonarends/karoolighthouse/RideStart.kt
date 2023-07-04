package com.jasonarends.karoolighthouse

import io.hammerhead.sdk.v0.SdkContext
import io.hammerhead.sdk.v0.Module
import io.hammerhead.sdk.v0.ModuleFactoryI
import io.hammerhead.sdk.v0.card.PostRideCard
import io.hammerhead.sdk.v0.card.RideDetailsI
import io.hammerhead.sdk.v0.datatype.SdkDataType
import com.jasonarends.karoolighthouse.BuildConfig
import timber.log.Timber

class RideStart(context: SdkContext) : Module(context) {
    init {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override val name: String = "RideStartName"
    override val version: String = "1.0"

    override fun onStart(): Boolean {
        Timber.i("RideStart received ride start event")
        val messageHelper = MessageHelper(context)

        val isSmsGatewayUsed = context.keyValueStore.getDouble("isSmsGatewayUsed") ?: 0.0
        if (isSmsGatewayUsed == 1.0) {
            messageHelper.sendMessage()
        } else {
            messageHelper.sendSms()
        }

        return false
    }

    override fun onPause(): Boolean {
        Timber.i("RideStart received ride pause event")
        return false
    }

    override fun onResume(): Boolean {
        Timber.i("RideStart received ride resume event")
        return false
    }

    override fun onEnd(): Boolean {
        Timber.i("RideStart received ride stop event")
        return false
    }

    override fun provideDataTypes(): List<SdkDataType> {
        return listOf()
    }

    override fun postRideCard(details: RideDetailsI): PostRideCard? {
        return null
    }

    companion object {
        @JvmField
        val factory = object : ModuleFactoryI {
            override fun buildModule(context: SdkContext): Module {
                return RideStart(context)
            }
        }
    }
}