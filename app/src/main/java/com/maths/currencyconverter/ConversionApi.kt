import com.maths.currencyconverter.ConversionResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("v6/{api}/pair/{from}/{to}/{amount}")
    fun getCurrencyConversion(
        @Path("api", encoded = true) apiKey: String,
        @Path("from") fromCurrency: String,
        @Path("to") toCurrency: String,
        @Path("amount") amount: Double
    ): Call<ConversionResponse>

}
