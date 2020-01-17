package com.example.tsuandroid.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tsuandroid.R
import com.example.tsuandroid.adapter.ElementAdapter
import com.example.tsuandroid.viewmodel.ViewModelMainActivity

class MainActivity : AppCompatActivity() {
    private lateinit var model: ViewModelMainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val elementAdapter: ElementAdapter = ElementAdapter()

        elementAdapter.onItemClick = { element ->
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("EXTRA_ID", element.id)
            intent.putExtra("EXTRA_NAME", element.name)
            intent.putExtra("EXTRA_RATING", element.rating)
            intent.putExtra("EXTRA_DESCRIPTION", element.description)
            intent.putExtra("EXTRA_IMAGE", element.icon)
            startActivity(intent)
        }


        val recyclerView: RecyclerView = findViewById(R.id.element_recycler_view)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = elementAdapter



        model = ViewModelProviders.of(this).get(ViewModelMainActivity::class.java)

        model.count.observe(this, Observer {
            val textView : TextView = findViewById(R.id.element_count)
            val plural: String = this.resources.getQuantityString(R.plurals.plurals_1, it, it)
            textView.text = plural

        })

        model.allElements.observe(this, Observer {
            elements -> elements?.let { elementAdapter.setElements(it) }
        })
    }
}
