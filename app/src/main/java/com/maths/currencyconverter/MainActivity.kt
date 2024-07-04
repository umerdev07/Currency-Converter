package com.maths.currencyconverter

import ApiService
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.maths.currencyconverter.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var fromSpinner: TextView
    private lateinit var toSpinner: TextView
    private lateinit var fromCurrencyTextView: TextView
    private lateinit var toCurrencyTextView: TextView
    private lateinit var binding: ActivityMainBinding
    private var dialog: Dialog? = null
    val customMenu = CustomMenu()

    private val currencySymbols = arrayOf(
        "AFN (؋)", "ALL (L)", "DZD (د.ج)", "ARS ($)", "AUD ($)", "EUR (€)", "BDT (৳)", "EUR (€)",
        "BRL (R$)", "CAD ($)", "CLP ($)", "CNY (¥)", "COP ($)", "DKK (kr)", "EGP (ج.م)", "EUR (€)",
        "EUR (€)", "GHS (₵)", "HKD ($)", "INR (₹)", "IDR (Rp)", "IRR (﷼)", "IQR (ع.د)", "EUR (€)",
        "JPY (¥)", "KES (KSh)", "KRW (₩)", "MYR (RM)", "MXN ($)", "EUR (€)", "NGN (₦)", "PKR (₨)",
        "PHP (₱)", "PLN (zł)", "QAR (ر.ق)", "RUB (₽)", "SAR (ر.س)", "SGD ($)", "ZAR (R)", "EUR (€)",
        "SEK (kr)", "CHF (CHF)", "TWD (NT$)", "THB (฿)", "TRY (₺)", "AED (د.إ)", "GBP (£)", "USD ($)",
        "VND (₫)"
    )

    private val countryList = arrayOf(
        "\uD83C\uDDE6\uD83C\uDDEB Afghanistan - AFN", "\uD83C\uDDE6\uD83C\uDDF1 Albania - ALL",
        "\uD83C\uDDE9\uD83C\uDDFF Algeria - DZD", "\uD83C\uDDE6\uD83C\uDDF7 Argentina - ARS",
        "\uD83C\uDDE6\uD83C\uDDFA Australia - AUD", "\uD83C\uDDE6\uD83C\uDDF9 Austria - EUR",
        "\uD83C\uDDE7\uD83C\uDDEC Bangladesh - BDT", "\uD83C\uDDE7\uD83C\uDDEA Belgium - EUR",
        "\uD83C\uDDE7\uD83C\uDDF7 Brazil - BRL", "\uD83C\uDDE8\uD83C\uDDE6 Canada - CAD",
        "\uD83C\uDDE8\uD83C\uDDF1 Chile - CLP", "\uD83C\uDDE8\uD83C\uDDF3 China - CNY",
        "\uD83C\uDDE8\uD83C\uDDF4 Colombia - COP", "\uD83C\uDDE9\uD83C\uDDF0 Denmark - DKK",
        "\uD83C\uDDEA\uD83C\uDDEC Egypt - EGP", "\uD83C\uDDEB\uD83C\uDDF7 France - EUR",
        "\uD83C\uDDE9\uD83C\uDDEA Germany - EUR", "\uD83C\uDDEC\uD83C\uDDED Ghana - GHS",
        "\uD83C\uDDED\uD83C\uDDF0 Hong Kong - HKD", "\uD83C\uDDEE\uD83C\uDDF3 India - INR",
        "\uD83C\uDDEE\uD83C\uDDE9 Indonesia - IDR", "\uD83C\uDDEE\uD83C\uDDF7 Iran - IRR",
        "\uD83C\uDDEE\uD83C\uDDF6 Iraq - IQD", "\uD83C\uDDEE\uD83C\uDDF9 Italy - EUR",
        "\uD83C\uDDEF\uD83C\uDDF5 Japan - JPY", "\uD83C\uDDF0\uD83C\uDDEA Kenya - KES",
        "\uD83C\uDDF0\uD83C\uDDF7 Korea South - KRW", "\uD83C\uDDF2\uD83C\uDDFE Malaysia - MYR",
        "\uD83C\uDDF2\uD83C\uDDFD Mexico - MXN", "\uD83C\uDDF3\uD83C\uDDF1 Netherlands - EUR",
        "\uD83C\uDDF3\uD83C\uDDEC Nigeria - NGN", "\uD83C\uDDF5\uD83C\uDDF0 Pakistan - PKR",
        "\uD83C\uDDF5\uD83C\uDDED Philippines - PHP", "\uD83C\uDDF5\uD83C\uDDF1 Poland - PLN",
        "\uD83C\uDDF6\uD83C\uDDE6 Qatar - QAR", "\uD83C\uDDF7\uD83C\uDDFA Russia - RUB",
        "\uD83C\uDDF8\uD83C\uDDE6 Saudi Arabia - SAR", "\uD83C\uDDF8\uD83C\uDDEC Singapore - SGD",
        "\uD83C\uDDFF\uD83C\uDDE6 South Africa - ZAR", "\uD83C\uDDEA\uD83C\uDDF8 Spain - EUR",
        "\uD83C\uDDE8\uD83C\uDDED Sweden - SEK", "\uD83C\uDDE8\uD83C\uDDEC Switzerland - CHF",
        "\uD83C\uDDF9\uD83C\uDDFC Taiwan - TWD", "\uD83C\uDDF9\uD83C\uDDED Thailand - THB",
        "\uD83C\uDDF9\uD83C\uDDF7 Turkey - TRY", "\uD83C\uDDE6\uD83C\uDDEA United Arab Emirates - AED",
        "\uD83C\uDDEC\uD83C\uDDE7 United Kingdom - GBP", "\uD83C\uDDFA\uD83C\uDDF8 United States - USD",
        "\uD83C\uDDFB\uD83C\uDDF3 Vietnam - VND"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.menue.setOnClickListener{
            customMenu.customMenu(this , it)
        }

        fromSpinner = binding.fromSpinner
        toSpinner = binding.toSpinner
        fromCurrencyTextView = binding.fromcurrency
        toCurrencyTextView = binding.tocurrency

        setupSpinners()

        binding.convertbutton.setOnClickListener {
            convertCurrency()
        }
    }

    private fun convertCurrency() {
        try {
            val fromCurrency = fromSpinner.text.toString().split(" - ")[1]
            val toCurrency = toSpinner.text.toString().split(" - ")[1]
            val amountStr = binding.inputamount.text.toString()

            if (amountStr.isBlank()) {
                showAlertDialog("Please enter any amount")
                return
            }

            val amount: Double
            try {
                amount = amountStr.toDouble()
            } catch (e: NumberFormatException) {
                showAlertDialog("Invalid amount. Please enter a valid number")
                return
            }

            val retrofit = Retrofit.Builder()
                .baseUrl("https://v6.exchangerate-api.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService = retrofit.create(ApiService::class.java)

           val call = apiService.getCurrencyConversion("c3852155f648f6ed278ace03", fromCurrency, toCurrency, amount)
            call.enqueue(object : Callback<ConversionResponse> {
                override fun onResponse(call: Call<ConversionResponse>, response: Response<ConversionResponse>) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data != null) {
                            val convertedAmount = data.conversionResult
                            binding.convertedcurrency.text = convertedAmount.toString()
                        } else {
                            showAlertDialog("Response body is null")
                        }
                    } else {
                       showAlertDialog("Failed to get conversion rate: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<ConversionResponse>, t: Throwable) {
                    showAlertDialog("Error: Failed to connect server or Internet Connection")
                }
            })
        } catch (e: Exception) {
            showAlertDialog("Error: Connect your Internet}")
        }
    }

    private fun setupSpinners() {
        fromSpinner.setOnClickListener {
            showFromSpinnerDialog()
        }
        toSpinner.setOnClickListener {
            showToSpinnerDialog()
        }
    }

    private fun showFromSpinnerDialog() {
        dialog = Dialog(this@MainActivity)
        dialog!!.setContentView(R.layout.dialog_searchable_spinner)
        dialog!!.window!!.setLayout(950, 1400)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.show()

        val editText = dialog!!.findViewById<EditText>(R.id.search)
        val listView = dialog!!.findViewById<ListView>(R.id.list_view)

        val adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, countryList)
        listView.adapter = adapter

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                adapter.filter.filter(s)
            }

            override fun afterTextChanged(s: Editable) {}
        })

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selectedCountry = adapter.getItem(position)!!
            val selectedCurrency = currencySymbols[countryList.indexOf(selectedCountry)]

            fromCurrencyTextView.text = selectedCurrency.split(" ")[0]

            val tocountry = toSpinner.text.toString()
            if (selectedCountry == tocountry) {
                showAlertDialog("Both countries must be different")
            } else {
                fromSpinner.text = selectedCountry
                dialog!!.dismiss()
            }
        }
    }

    private fun showToSpinnerDialog() {
        dialog = Dialog(this@MainActivity)
        dialog!!.setContentView(R.layout.dialog_searchable_spinner)
        dialog!!.window!!.setLayout(950, 1400)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.show()

        val editText = dialog!!.findViewById<EditText>(R.id.search)
        val listView = dialog!!.findViewById<ListView>(R.id.list_view)

        val adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, countryList)
        listView.adapter = adapter

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                adapter.filter.filter(s)
            }

            override fun afterTextChanged(s: Editable) {}
        })

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selectedCountry = adapter.getItem(position)!!
            val selectedCurrency = currencySymbols[countryList.indexOf(selectedCountry)]
            toCurrencyTextView.text = selectedCurrency.split(" ")[0]

            val fromCountry = fromSpinner.text.toString()
            if (selectedCountry == fromCountry) {
                showAlertDialog("Both countries must be different")
            } else {
                toSpinner.text = selectedCountry
                dialog!!.dismiss()
            }
        }
    }

    private fun showAlertDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)

            .setCancelable(true)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

}