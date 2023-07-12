package com.jasonarends.karoolighthouse

import io.hammerhead.sdk.v0.SdkContext
import io.hammerhead.sdk.v0.datatype.SdkDataType
import io.hammerhead.sdk.v0.datatype.formatter.BuiltInFormatter
import io.hammerhead.sdk.v0.datatype.formatter.SdkFormatter
import io.hammerhead.sdk.v0.datatype.transformer.BuiltInTransformer
import io.hammerhead.sdk.v0.datatype.transformer.SdkTransformer
import io.hammerhead.sdk.v0.datatype.view.SdkView

class ForecastPrecipDataType(context: SdkContext) : SdkDataType(context) {
    override val typeId: String = "forecast-precip"
    override val displayName: String = "Forecast Precip Chance"
    override val description: String = "Displays chance of precip in forecast"
    override val sampleValue: Double = 50.0

    // override newView, newFormatter, and newTransformer methods as per your requirements
    // the below methods are just placeholders

    override fun newFormatter(): SdkFormatter = BuiltInFormatter.Numeric(1)
    override fun newTransformer(): SdkTransformer = BuiltInTransformer.Identity(context)
    override fun newView(): SdkView {
        TODO("Not yet implemented")
    }
}
