package com.maths.currencyconverter

import ApiService
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.maths.currencyconverter.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var fromSpinner: Spinner
    private lateinit var toSpinner: Spinner

    private lateinit var binding: ActivityMainBinding

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
        "Afghanistan - AFN", "Albania - ALL", "Algeria - DZD", "Argentina - ARS",
        "Australia - AUD", "Austria - EUR", "Bangladesh - BDT", "Belgium - EUR", "Brazil - BRL",
        "Canada - CAD", "Chile - CLP", "China - CNY", "Colombia - COP", "Denmark - DKK",
        "Egypt - EGP", "France - EUR", "Germany - EUR", "Ghana - GHS", "Hong Kong - HKD",
        "India - INR", "Indonesia - IDR", "Iran - IRR", "Iraq - IQD", "Italy - EUR", "Japan - JPY",
        "Kenya - KES", "Korea South - KRW", "Malaysia - MYR", "Mexico - MXN", "Netherlands - EUR",
        "Nigeria - NGN", "Pakistan - PKR", "Philippines - PHP", "Poland - PLN", "Qatar - QAR",
        "Russia - RUB", "Saudi Arabia - SAR", "Singapore - SGD", "South Africa - ZAR", "Spain - EUR",
        "Sweden - SEK", "Switzerland - CHF", "Taiwan - TWD", "Thailand - THB", "Turkey - TRY",
        "United Arab Emirates - AED", "United Kingdom - GBP", "United States - USD", "Vietnam - VND"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupSpinners()

        binding.convertbutton.setOnClickListener {
            convertCurrency()
        }
    }

    private fun convertCurrency() {
        try {
            val fromCurrency = fromSpinner.selectedItem.toString().split(" - ")[1]
            val toCurrency = toSpinner.selectedItem.toString().split(" - ")[1]
            val amountStr = binding.inputamount.text.toString()

            if (amountStr.isBlank()) {
                Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show()
                return
            }

            val amount: Double
            try {
                amount = amountStr.toDouble()
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Invalid amount. Please enter a valid number", Toast.LENGTH_SHORT).show()
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
                    try {
                        if (response.isSuccessful) {
                            val data = response.body()
                            if (data != null) {
                                val convertedAmount = data.conversionResult
                                binding.convertedcurrency.text = convertedAmount.toString()
                            } else {
                                Toast.makeText(this@MainActivity, "Response body is null", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(
                                this@MainActivity, "Failed to fetch data: ${response.code()} - ${response.message()}", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(this@MainActivity, "Error processing response: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ConversionResponse>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } catch (e: Exception) {
            Toast.makeText(this, "An unexpected error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupSpinners() {
        fromSpinner = binding.fromSpinner
        toSpinner = binding.toSpinner

        val defaultFromIndex = countryList.indexOf("Pakistan - PKR")
        val defaultToIndex = countryList.indexOf("United States - USD")

        fromSpinner.setSelection(defaultFromIndex)
        toSpinner.setSelection(defaultToIndex)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, countryList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fromSpinner.adapter = adapter
        toSpinner.adapter = adapter

        fromSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateCurrencySymbol(binding.fromcurrency, position)
                validateCurrencySelection()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        toSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateCurrencySymbol(binding.tocurrency, position)
                validateCurrencySelection()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun updateCurrencySymbol(textView: TextView, position: Int) {
        if (position >= 0 && position < currencySymbols.size) {
            val symbol = currencySymbols[position]
            textView.text = symbol
        }
    }

    private fun validateCurrencySelection() {
        val fromIndex = fromSpinner.selectedItemPosition
        val toIndex = toSpinner.selectedItemPosition
        if (fromIndex == toIndex) {
            Toast.makeText(this, "Currency cannot be the same", Toast.LENGTH_SHORT).show()
        }
    }
}
