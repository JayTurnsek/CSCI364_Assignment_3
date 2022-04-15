package com.example.assignment2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.viewModels
import com.example.assignment2.databinding.FragmentCourseScheduleBinding

class CourseScheduleFragment : Fragment() {

    private var _binding: FragmentCourseScheduleBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCourseScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get viewModel for course schedule
        val courseDataViewModel: CourseScheduleViewModel by viewModels {
            CourseScheduleViewModelFactory((activity?.application as CourseApplication).repository)
        }

        // Populate first term courses from database
        courseDataViewModel.firstTermCourses.observe(viewLifecycleOwner) { firstTermCourses ->
            firstTermCourses.let {
                val lv1: ListView = binding.courseListTerm1
                val adapter1 = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    it
                )
                lv1.adapter = adapter1
            }
        }

        // Populate second term courses from database
        courseDataViewModel.secondTermCourses.observe(viewLifecycleOwner) { secondTermCourses ->
            secondTermCourses.let {
                val lv2: ListView = binding.courseListTerm2
                val adapter2 = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    it
                )
                lv2.adapter = adapter2
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}