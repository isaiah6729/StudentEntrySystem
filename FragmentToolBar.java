package com.zybooks.wgu;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentToolBar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentToolBar extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentToolBar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentToolBar.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentToolBar newInstance(String param1, String param2) {
        FragmentToolBar fragment = new FragmentToolBar();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tool_bar, container, false);
    }


    // create toolbar
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // buttons to make up custom toolbar
        Button Home = view.findViewById(R.id.Home);
        Button Terms = view.findViewById(R.id.Terms);
        Button Courses = view.findViewById(R.id.Courses);
        Button Assessments = view.findViewById(R.id.Assessments);

        Home.setOnClickListener(home);
        Terms.setOnClickListener(terms);
        Courses.setOnClickListener(courses);
        Assessments.setOnClickListener(assessments);

    }

    // home page
    View.OnClickListener home = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent open = new Intent(getContext(), HomePage.class);
            startActivity(open);
        }
    };

    // view all terms
    View.OnClickListener terms = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent open = new Intent(getContext(), TermsView.class);
            startActivity(open);
        }
    };

    // view all courses
    View.OnClickListener courses = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent open = new Intent(getContext(), CoursesView.class);
            startActivity(open);
        }
    };

    // choose assessments to view
    View.OnClickListener assessments = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent open = new Intent(getContext(), AssessmentChoose.class);
            startActivity(open);
        }
    };


}