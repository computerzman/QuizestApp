package com.quizest.quizestapp.FragmentPackage.DashboardFragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quizest.quizestapp.AdapterPackage.CategoryRecyclerAdapter;
import com.quizest.quizestapp.ModelPackage.CategoryModel;
import com.quizest.quizestapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class HomeFragment extends Fragment {

    List<CategoryModel> categoryModels;
    CategoryRecyclerAdapter categoryRecyclerAdapter;
    RecyclerView categoryRecycler;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViews();


        /*adding options to category recycler*/


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);

        categoryRecycler.setHasFixedSize(true);

        categoryRecycler.setLayoutManager(gridLayoutManager);

        categoryModels = new ArrayList<>();

        categoryModels.add(new CategoryModel("Science", R.drawable.ic_science));
        categoryModels.add(new CategoryModel("Science", R.drawable.ic_science));
        categoryModels.add(new CategoryModel("Science", R.drawable.ic_science));
        categoryModels.add(new CategoryModel("Science", R.drawable.ic_science));
        categoryModels.add(new CategoryModel("Science", R.drawable.ic_science));
        categoryModels.add(new CategoryModel("Science", R.drawable.ic_science));
        categoryModels.add(new CategoryModel("Science", R.drawable.ic_science));
        categoryModels.add(new CategoryModel("Science", R.drawable.ic_science));
        categoryModels.add(new CategoryModel("Science", R.drawable.ic_science));
        categoryModels.add(new CategoryModel("Science", R.drawable.ic_science));
        categoryModels.add(new CategoryModel("Science", R.drawable.ic_science));
        categoryModels.add(new CategoryModel("Science", R.drawable.ic_science));
        categoryModels.add(new CategoryModel("Science", R.drawable.ic_science));
        categoryModels.add(new CategoryModel("Science", R.drawable.ic_science));
        categoryModels.add(new CategoryModel("Science", R.drawable.ic_science));
        categoryModels.add(new CategoryModel("Science", R.drawable.ic_science));
        categoryModels.add(new CategoryModel("Science", R.drawable.ic_science));
        categoryModels.add(new CategoryModel("Science", R.drawable.ic_science));
        categoryModels.add(new CategoryModel("Science", R.drawable.ic_science));
        categoryModels.add(new CategoryModel("Science", R.drawable.ic_science));
        categoryModels.add(new CategoryModel("Science", R.drawable.ic_science));

        categoryRecyclerAdapter = new CategoryRecyclerAdapter(categoryModels, getActivity());

        categoryRecycler.setAdapter(categoryRecyclerAdapter);


    }

    private void initViews() {
        View view = getView();
        if (view != null) {
            categoryRecycler = view.findViewById(R.id.recycler_categories);
        }

    }
}

