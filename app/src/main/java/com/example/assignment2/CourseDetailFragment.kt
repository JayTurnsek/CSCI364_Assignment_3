package com.example.assignment2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.assignment2.databinding.FragmentCourseDetailBinding

class CourseDetailFragment : Fragment() {

    private var _binding: FragmentCourseDetailBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCourseDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    // get args
    private val args: CourseDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // get textview
        val courseCodeTextView: TextView = binding.courseCode
        val courseNameTextView: TextView = binding.courseName
        val courseTermTextView: TextView = binding.courseTerm
        val coursePrereqTextView: TextView = binding.coursePrereq

        // apply args to tv's
        courseCodeTextView.text = args.coursecode
        courseNameTextView.text = args.coursename
        courseTermTextView.text = args.courseterm
        coursePrereqTextView.text = args.courseprereq

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}