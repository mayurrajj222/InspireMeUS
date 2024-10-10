package com.example.inspireme

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class FavoriteQuotesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_quotes)

        // Reference to the TextView
        val favoriteQuotesTextView: TextView = findViewById(R.id.favorite_quotes_text)

        // Display favorite quotes (joining the list with new lines)
        val favoriteQuotesString = MainActivity.favoriteQuotes.joinToString(separator = "\n\n")
        favoriteQuotesTextView.text = favoriteQuotesString
    }
}
