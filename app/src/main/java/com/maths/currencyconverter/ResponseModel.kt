package com.maths.currencyconverter

import com.google.gson.annotations.SerializedName

data class ConversionResponse(
    @SerializedName("result") val result: String,
    @SerializedName("base_code") val baseCode: String,
    @SerializedName("target_code") val targetCode: String,
    @SerializedName("conversion_rate") val conversionRate: Double,
    @SerializedName("conversion_result") val conversionResult: Double
)