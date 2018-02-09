package com.example.adrey.imagesliderusingrecycleview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listImage = arrayOf(
                R.drawable.img1,
                R.drawable.img2,
                R.drawable.img3,
                R.drawable.img4
        )
        val listTitle = resources.getStringArray(R.array.title)
        rc_main.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val mainAdapter = MainAdapter(this, listImage, listTitle)
        rc_main.layoutManager = layoutManager
        rc_main.adapter = mainAdapter

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(rc_main)

        mainAdapter.showIndicator(ll_main)
        mainAdapter.autoSlider(rc_main, layoutManager)

        rc_main.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                mainAdapter.setSelected(layoutManager.findLastCompletelyVisibleItemPosition())
            }
        })
    }
}
