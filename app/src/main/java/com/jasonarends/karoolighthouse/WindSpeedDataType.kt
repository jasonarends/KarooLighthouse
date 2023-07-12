package com.jasonarends.karoolighthouse

import io.hammerhead.sdk.v0.SdkContext
import io.hammerhead.sdk.v0.datatype.SdkDataType
import io.hammerhead.sdk.v0.datatype.formatter.BuiltInFormatter
import io.hammerhead.sdk.v0.datatype.formatter.SdkFormatter
import io.hammerhead.sdk.v0.datatype.transformer.BuiltInTransformer
import io.hammerhead.sdk.v0.datatype.transformer.SdkTransformer
import io.hammerhead.sdk.v0.datatype.view.SdkView

class WindSpeedDataType(context: SdkContext) : SdkDataType(context) {
    override val typeId: String = "wind-speed"
    override val displayName: String = "Wind Speed"
    override val description: String = "Displays current wind speed"
    override val sampleValue: Double = 5.0

    // override newView, newFormatter, and newTransformer methods as per your requirements
    // the below methods are just placeholders

    override fun newView(): SdkView = CustomWindSpeedSdkView(context)
    override fun newFormatter(): SdkFormatter = BuiltInFormatter.Numeric(1)
    override fun newTransformer(): SdkTransformer = BuiltInTransformer.Identity(context)


}
