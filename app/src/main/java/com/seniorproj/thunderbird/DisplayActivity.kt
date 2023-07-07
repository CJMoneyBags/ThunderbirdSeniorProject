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

        // TODO incorporate these objects
//        val container: Database.Container
//        val cargoList: ArrayList<Database.CargoPair>
//        val bundle: Bundle? = intent.extras
//        if (bundle != null) {
//            container = bundle.getParcelable("container")!!
//            cargoList = bundle.getParcelableArrayList("cargoList")!!
//
//            // an example of how to get the first cargo's data
//            val firstCargo: Database.Cargo = cargoList[0].cargo
//            val numberOfCargo: Int = cargoList[0].number
//        }

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
                //TODO: Debug and make updates for Toast to print screen.
                //val Toast.makeText(this, dragData, Toast.LENGTH_SHORT).show()

                val v = event.localState as View
                val owner = v.parent as ViewGroup
                owner.removeView(v)
                val destination = view as LinearLayout
                destination.addView(v)
                v.visibility = View.VISIBLE
                true

//                // Create a copy of the draggable image
//                val draggedImage = event.localState as ImageView
//                val copyImage = ImageView(this)
//                copyImage.setImageDrawable(draggedImage.drawable)
            }

            DragEvent.ACTION_DRAG_ENDED -> {
                view.invalidate()
                true
            }

            else -> false
        }
    }
}