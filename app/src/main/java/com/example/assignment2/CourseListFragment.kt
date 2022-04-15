package com.example.assignment2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.assignment2.adapter.CourseAdapter
import com.example.assignment2.database.CourseListDatabase
import com.example.assignment2.database.course.Course
import com.example.assignment2.databinding.FragmentCourseListBinding
import com.google.android.material.snackbar.Snackbar


class CourseListFragment : Fragment() {
    private var _binding: FragmentCourseListBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCourseListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // viewmodel for selected courses
        val courseScheduleViewModel: CourseScheduleViewModel by viewModels {
            CourseScheduleViewModelFactory((activity?.application as CourseApplication).repository)
        }

        // Init database
        val database: CourseListDatabase by lazy {
            CourseListDatabase.getDatabase(requireContext())
        }
        val courseListDao = database.courseListDao()

        // get courseList data from database
        val courseList = courseListDao?.getAll()

        // Init required variables
        val recyclerView = binding.recyclerView
        val selectedCourses = ArrayList<Course>()

        // Set up recyclerView of courses
        recyclerView.adapter = CourseAdapter(courseList, selectedCourses)
        recyclerView.setHasFixedSize(true)


        // Get selected Courses, add preregistered ones
        val selected = (recyclerView.adapter as CourseAdapter).getSelectedCourses()
        val preregistered1 = Course(
            0,
            "CS255",
            "Advanced Data Structure",
            1,
            "none"
        )
        val preregistered2 = Course(
            0,
            "CS263",
            "Computer Architecture and Organization",
            2,
            "none"
        )
        selected.add(0, preregistered2)
        selected.add(0, preregistered1)

        // Add listener to submit course list
        val submitButton = binding.submitButton
        submitButton.setOnClickListener {

            when (handleCourses(selected)) {

                // Missing prerequisite outcome
                1 -> Snackbar.make(
                    binding.coordinatorLayout,
                    "Error Due to missing prerequisite.",
                    Snackbar.LENGTH_SHORT
                ).show()

                // Course overload outcome
                2 -> Snackbar.make(
                    binding.coordinatorLayout,
                    "Error due to course overload; maximum 3 courses per term.",
                    Snackbar.LENGTH_SHORT
                ).show()

                // Success outcome
                else -> {

                    // Reset database at each new selection
                    courseScheduleViewModel.clear()

                    // Satisfy unique id constraint
                    var id = 0

                    // Convert selected courses to CourseData
                    for (course in selected) {

                        // Add to database
                        // lifecycleScope is used to perform away from the main thread operations
                        //lifecycleScope.launch {
                        //    courseDataDao.insertCourse(currentCourse)
                        //}
                        courseScheduleViewModel.insert(course)
                        id += 1

                    }

                    // Go to course schedule fragment
                    val action = CourseListFragmentDirections.actionCourseListFragmentToCourseScheduleFragment()
                    Navigation.findNavController(view).navigate(action)
                }
            }
        }
    }

   override fun onDestroyView() {
       super.onDestroyView()
       _binding = null
   }

    private fun handleCourses(courseList: List<Course>): Int {
        /*
            Decides outcome of submitted course list
            0: Success
            1: Missing prerequisite
            2: Course overload
         */

        // Init vars to handle conditions
        var firstTermCount = 0
        var secondTermCount = 0
        val prereqList = listOf(
            "CS161",
            "CS162",
            "Math101",
            "none"
        )

        // Check all courses
        for (course in courseList) {

            // Check for prerequisite
            if (course.coursePrereq !in prereqList) {
                return 1
            }

            // Check courseload by term restrictions
            if (course.courseTerm == 1) {
                firstTermCount++

            } else {
                secondTermCount++
            }
            if (firstTermCount > 3 || secondTermCount > 3) {
                return 2
            }

        }
        return 0
    }
}