package com.seniorproj.thunderbird

import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.seniorproj.thunderbird.databinding.ActivityDisplayBinding

class DisplayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDisplayBinding

    // Counter variable to track the number of copied draggable views
    private var copiedCount = 0
    private var maxCopies = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisplayBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Binding all of the sections of the container for use.
        binding.section1Top.setOnDragListener(dragListener)
        binding.section1Bottom.setOnDragListener(dragListener)
        binding.section2Top.setOnDragListener(dragListener)
        binding.section2Bottom.setOnDragListener(dragListener)
        binding.section3Top.setOnDragListener(dragListener)
        binding.section3Bottom.setOnDragListener(dragListener)
        binding.section4Top.setOnDragListener(dragListener)
        binding.section4Bottom.setOnDragListener(dragListener)
        binding.section5Top.setOnDragListener(dragListener)
        binding.section5Bottom.setOnDragListener(dragListener)
        binding.section6Top.setOnDragListener(dragListener)
        binding.section6Bottom.setOnDragListener(dragListener)


        maxCopies = 5

        //Binding the draggable object(s)
        //TODO: Figure out how to get dynamically populated draggable objects.
        binding.dragView.setOnLongClickListener {
            val clipText = "This is our ClipData text"
            val item = ClipData.Item(clipText)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipText, mimeTypes, item)

            it.startDragAndDrop(data, View.DragShadowBuilder(it), it, 0)

            it.visibility = View.INVISIBLE
            true
        }
    }

    //The logic for the dragging actions.
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

                if (view is LinearLayout) {
//                    val draggableView = createDraggableView()
//                    view.addView(draggableView)
//                    draggableView.visibility = View.VISIBLE
                    if (copiedCount < maxCopies) {
                        val draggableView = createDraggableView()
                        view.addView(draggableView)
                        draggableView.visibility = View.VISIBLE

                        // Increment the counter variable
                        copiedCount++
                        // Increment the copiedCount and update the count tex
                        binding.countText.text = (maxCopies - copiedCount).toString()
                    }
                }

                // Revert the visibility of the original view
                val originalView = event.localState as View
                originalView.visibility = View.VISIBLE

                true
            }


            DragEvent.ACTION_DRAG_ENDED -> {
                view.invalidate()

                // Check the result of the drag operation
                if (!event.result) {
                    // Drag operation was not successful, restore visibility of the draggable box
                    val draggableBox = event.localState as View
                    draggableBox.visibility = View.VISIBLE
                }

                true
            }

            else -> false
        }
    }

    private fun createDraggableView(): View {
        val draggableView = View(this)
        draggableView.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        draggableView.setBackgroundColor(Color.BLUE) // Set the background color or other desired properties
        draggableView.setOnTouchListener(touchListener)

        return draggableView
    }
    private val touchListener = View.OnTouchListener { view, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // Start the drag operation when the view is touched
                val clipText = "This is our ClipData text"
                val item = ClipData.Item(clipText)
                val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                val data = ClipData(clipText, mimeTypes, item)

                val dragShadowBuilder = View.DragShadowBuilder(view)
                view.startDragAndDrop(data, dragShadowBuilder, view, 0)

                view.visibility = View.INVISIBLE
                true
            }
            else -> false
        }
    }

}