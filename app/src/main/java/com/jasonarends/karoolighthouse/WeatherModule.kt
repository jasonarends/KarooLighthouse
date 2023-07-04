package com.jasonarends.karoolighthouse

import io.hammerhead.sdk.v0.Module
import io.hammerhead.sdk.v0.SdkContext

class WeatherModule(context: SdkContext) : Module(context) {
    override val name: String = "WeatherModule"
    override val version: String = "1.0"
}
