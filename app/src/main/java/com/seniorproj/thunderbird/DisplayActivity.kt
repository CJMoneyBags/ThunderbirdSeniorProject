package com.seniorproj.thunderbird

import android.content.ClipData
import android.content.ClipDescription
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.seniorproj.thunderbird.databinding.ActivityDisplayBinding

class DisplayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDisplayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisplayBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val llTop = binding.llTop
        val llBottom = binding.llBottom
        val dragView = binding.dragView

        llTop.setOnDragListener(dragListener)
        llBottom.setOnDragListener(dragListener)
        dragView.setOnLongClickListener {
            val clipText = "This is our ClipData text"
            val item = ClipData.Item(clipText)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipText, mimeTypes, item)

            //TODO:Figure out how DragShadowBuilder works and input it here.
            it.startDragAndDrop(data, View.DragShadowBuilder(it), it, 0)

            it.visibility = View.INVISIBLE
            true
        }
    }

    val dragListener = View.OnDragListener {view, event->
        when(event.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
            }

            DragEvent.ACTION_DRAG_ENTERED -> {
                view.invalidate()
                true
            }

            DragEvent.ACTION_DRAG_LOCATION -> {
                true
            }

            DragEvent.ACTION_DRAG_EXITED -> {
                view.invalidate()
                true
            }

            DragEvent.ACTION_DROP -> {
                val item = event.clipData.getItemAt(0)
                val dragData = item.text
                //TODO:Debug and make updates for Toast to print screen.
//                val Toast.makeText(this, dragData, Toast.LENGTH_SHORT).show()

                val v = event.localState as View
                val owner = v.parent as ViewGroup
                owner.removeView(v)
                val destination = view as LinearLayout
                destination.addView(v)
                v.visibility = View.VISIBLE
                true
            }

            DragEvent.ACTION_DRAG_ENDED -> {
                view.invalidate()
                true
            }

            else -> false
        }
    }
}