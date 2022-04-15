package com.example.assignment2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment2.CourseListFragmentDirections
import com.example.assignment2.R
import com.example.assignment2.database.course.Course

class CourseAdapter(
    private val dataset: List<Course>,
    private var selectedCourses: ArrayList<Course>
) :
    RecyclerView.Adapter<CourseAdapter.ItemViewHolder>() {
    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val codeText: TextView = view.findViewById(R.id.course_id)
        val titleText: TextView = view.findViewById(R.id.course_title)
        val addCheck: CheckBox = view.findViewById(R.id.checkBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.course_item, parent, false)
        return ItemViewHolder(adapterLayout)

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        // Populate each item in recyclerview with course data
        val item = dataset[position]
        holder.codeText.text = item.courseId
        holder.titleText.text = item.courseName

        // Handles data of which checkboxes selected
        holder.addCheck.setOnClickListener {
            if (holder.addCheck.isChecked) {
                selectedCourses.add(item)
            } else {
                selectedCourses.remove(item)
            }
        }


        // handles detailed view
        holder.codeText.setOnClickListener {
            val action =
                CourseListFragmentDirections.actionCourseListFragmentToCourseDetailFragment(
                    item.courseName,
                    item.courseId,
                    item.courseTerm.toString(),
                    item.coursePrereq
                )
            holder.view.findNavController().navigate(action)
        }
        holder.titleText.setOnClickListener {
            val action =
                CourseListFragmentDirections.actionCourseListFragmentToCourseDetailFragment(
                    item.courseName,
                    item.courseId,
                    item.courseTerm.toString(),
                    item.coursePrereq
                )
            holder.view.findNavController().navigate(action)
        }


    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    fun getSelectedCourses(): ArrayList<Course> {
        return selectedCourses
    }

}