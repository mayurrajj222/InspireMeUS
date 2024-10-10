package com.example.inspireme

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        val favoriteQuotes = mutableListOf<String>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)  // Inflate the layout

        // Reference to the TextView for displaying the quote
        val quoteTextView: TextView = findViewById(R.id.quote_text)

        // List of random quotes
        val quotes = listOf(
            "The best way to get started is to quit talking and begin doing.",
            "The pessimist sees difficulty in every opportunity. The optimist sees opportunity in every difficulty.",
            "Don’t let yesterday take up too much of today.",
            "You learn more from failure than from success. Don’t let it stop you. Failure builds character.",
            "It’s not whether you get knocked down, it’s whether you get up."
        )

        // Display a random quote on app launch
        val randomQuote = quotes[Random().nextInt(quotes.size)]
        quoteTextView.text = randomQuote

        // Handle Share button
        val shareButton: Button = findViewById(R.id.share_button)
        shareButton.setOnClickListener {
            shareQuote(randomQuote)
        }

        // Handle Save to Favorites button
        val saveButton: Button = findViewById(R.id.save_favorite_button)
        saveButton.setOnClickListener {
            saveToFavorites(randomQuote)
        }

        // Handle View Favorites button - Add this after setContentView is called!
        val favoritesButton: Button = findViewById(R.id.view_favorites_button)
        favoritesButton.setOnClickListener {
            val intent = Intent(this, FavoriteQuotesActivity::class.java)
            startActivity(intent)
        }
    }

    // Function to share the current quote
    private fun shareQuote(quote: String) {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, quote)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(intent, "Share via"))
    }

    // Save the quote to the favorite list
    private fun saveToFavorites(quote: String) {
        if (!favoriteQuotes.contains(quote)) {
            favoriteQuotes.add(quote)
            println("Saved to Favorites: $quote")
        } else {
            println("Quote already in favorites.")
        }
    }
}
