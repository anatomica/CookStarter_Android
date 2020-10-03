package ru.anatomica.cookstarter.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import ru.anatomica.cookstarter.MainActivity;
import ru.anatomica.cookstarter.R;

public class FirstFragment extends Fragment {

    private MainActivity mainActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = ((MainActivity) activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btn_choose).setOnClickListener(view1 -> {
            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_SecondFragment);
            mainActivity.getRestaurants();
        });
    }

}