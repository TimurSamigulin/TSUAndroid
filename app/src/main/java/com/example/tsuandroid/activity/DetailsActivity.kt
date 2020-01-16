package com.example.tsuandroid.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.tsuandroid.R
import com.example.tsuandroid.viewmodel.ViewModelDetailsActivity


class DetailsActivity: AppCompatActivity() {
    private lateinit var model: ViewModelDetailsActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        model = ViewModelProviders.of(this).get(ViewModelDetailsActivity::class.java)

        val textViewName: TextView = findViewById(R.id.detail_name)
        val textViewDescription: TextView = findViewById(R.id.detail_description)
        val textViewRating: TextView = findViewById(R.id.detail_rating)
        val imageViewImage: ImageView = findViewById(R.id.detail_image)
        val imageUp: ImageButton = findViewById(R.id.up_rating)
        val imageDown: ImageButton = findViewById(R.id.down_rating)

        val intent: Intent = intent
        val elementId = intent.getLongExtra("EXTRA_ID", 0)

        /*val element = model.element

        element.observe(this, Observer {
            element ->
                textViewName.text = element.name
                textViewDescription.text = element.description
                textViewRating.text = String.format("%f", element.rating)

                Glide.with(this)
                    .load(element.icon)
                    .override(500, 500)
                    .fitCenter()
                    .error(R.drawable.no_image)
                    .into(imageViewImage)
        })*/

        textViewName.text = intent.getStringExtra("EXTRA_NAME")
        textViewDescription.text = intent.getStringExtra("EXTRA_DESCRIPTION")
        textViewRating.text = String.format("%f", intent.getFloatExtra("EXTRA_RATING", 0f))

        Glide.with(this)
            .load(intent.getStringExtra("EXTRA_IMAGE"))
            .override(1000, 1000)
            .fitCenter()
            .error(R.drawable.no_image)
            .into(imageViewImage)

        imageUp.setOnClickListener {
            var rating = textViewRating.text.toString().toFloat()
            Log.d("q", rating.toString())
            if (rating <= 4.5f) {
                Log.d("q", rating.toString())
                rating += 0.5f
                textViewRating.text = String.format("%f", rating)
                model.updateRating(elementId, rating)
            }
        }

        imageDown.setOnClickListener {
            var rating =  textViewRating.text.toString().toFloat()
            Log.d("q", rating.toString())
            if (rating >= 0.5f) {
                rating -= 0.5f
                Log.d("q", rating.toString())
                textViewRating.text = String.format("%f", rating)
                model.updateRating(elementId, rating)
            }
        }
    }
}