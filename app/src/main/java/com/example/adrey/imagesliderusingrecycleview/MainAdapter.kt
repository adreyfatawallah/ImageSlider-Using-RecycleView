package com.example.adrey.imagesliderusingrecycleview

import android.content.Context
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.item_main.view.*

/**
 * Created by Muh Adrey Fatawallah on 2/9/2018.
 */
class MainAdapter(private val context: Context,
                  private val listImage: Array<Int>,
                  private val listTitle: Array<out String>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_main, parent, false))

    override fun getItemCount(): Int = listImage.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindMain(listImage, listTitle, position)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindMain(listImage: Array<Int>, listTitle: Array<out String>, position: Int) {
            val image = listImage[position]
            val title = listTitle[position]

            itemView.im_item_main.setImageResource(image)
            itemView.tx_item_main.text = title
        }
    }

    private var linearLayout: LinearLayout? = null

    fun showIndicator(linearLayout: LinearLayout) {
        this.linearLayout = linearLayout

        val res = context.resources
        for (i in 0 until listImage.size) {
            val view = View(context)
            val dimen: Int = res.displayMetrics.density.times(9).toInt()
            val layoutParams = LinearLayout.LayoutParams(dimen, dimen)
            layoutParams.leftMargin = res.displayMetrics.density.times(3).toInt()
            layoutParams.rightMargin = res.displayMetrics.density.times(3).toInt()
            view.layoutParams = layoutParams
            view.setBackgroundResource(R.drawable.bg_indicator)
            view.isSelected = i == 0
            linearLayout.addView(view)
        }
    }

    fun autoSlider(recyclerView: RecyclerView, layoutManager: LinearLayoutManager) {
        val handler = Handler()
        val temp = layoutManager.findLastCompletelyVisibleItemPosition()
        var x = if (temp > 0) temp else 0
        var arrow = 0
        val runnable = object : Runnable {
            override fun run() {
                handler.postDelayed(this, 5000)

                /**
                 * loop left to right if last index to first index
                 */
//                val pos = layoutManager.findLastCompletelyVisibleItemPosition() + 1
//                if (pos < listImage.size)
//                    recyclerView.smoothScrollToPosition(pos)
//                else
//                    recyclerView.smoothScrollToPosition(0)

                /**
                 * loop left to right if last index right to left
                 */

                if (x <= listImage.size) {

                    if (arrow == 0)
                        arrow = 1

                    if (arrow == 1) {
                        recyclerView.smoothScrollToPosition(x)
                        x++
                    }

                    if (x == listImage.size) {
                        arrow = 2
                        x--
                    }

                    if (arrow == 2) {
                        recyclerView.smoothScrollToPosition(x)
                        x--

                        if (x == 0)
                            arrow = 1
                    }
                }
            }
        }
        handler.post(runnable)
    }

    fun setSelected(pos: Int) {
        for (i in 0 until listImage.size) {
            val view = linearLayout!!.getChildAt(i)
            view.isSelected = i == pos
        }
    }
}