import com.maths.currencyconverter.ResponseModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("convert")
    fun getCurrencyConversion(
        @Query("from") fromCurrency: String,
        @Query("to") toCurrency: String,
        @Query("apikey") apiKey: String
    ): Call<ResponseModel>
}
