import com.maths.currencyconverter.ResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("pair")
    fun getCurrencyConversion(
        @Query("apikey") apiKey: String,
        @Query("from") fromCurrency: String,
        @Query("to") toCurrency: String,
        @Query("amount") amount: Double
    ): Call<ResponseModel>
}
