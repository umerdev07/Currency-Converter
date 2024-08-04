package com.maths.currencyconverter

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast

class CustomMenu {
        fun customMenu(context: Context, view: View) {
            val pop = PopupMenu(context, view)
            pop.inflate(R.menu.menu)

            pop.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.favourite -> {
                        Toast.makeText(context, "Clicked Favourite (Working)", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.crypto_track -> {
                        Toast.makeText(context, "Clicked Crypto Track (Working)", Toast.LENGTH_SHORT).show()
                        true

                    }
                    else -> false
                }
            }

            try {
                val fieldMpopup = PopupMenu::class.java.getDeclaredField("mPopup")
                fieldMpopup.isAccessible = true
                val mPopup = fieldMpopup.get(pop)
                mPopup.javaClass
                    .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(mPopup, true)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                pop.show()
            }
    }
}
