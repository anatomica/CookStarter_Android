package ru.anatomica.cookstarter.ui.restaurants;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import ru.anatomica.cookstarter.MainActivity;
import ru.anatomica.cookstarter.R;

public class RestaurantsFragment extends Fragment {

    private RestaurantsViewModel restaurantsViewModel;
    private MainActivity mainActivity;

    public static ConstraintLayout buttonsView;
    public Button findButton;
    public Button chooseButton;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = ((MainActivity) activity);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        restaurantsViewModel = ViewModelProviders.of(this).get(RestaurantsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_restaurant, container, false);

        buttonsView = root.findViewById(R.id.buttons_layout);
        findButton = root.findViewById(R.id.btn_find);
        chooseButton = root.findViewById(R.id.btn_choose);

        restaurantsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                // textView.setText(s);
            }
        });
        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btn_choose).setOnClickListener(view1 -> {
            mainActivity.getRestaurants();
            findButton.setVisibility(View.INVISIBLE);
            chooseButton.setVisibility(View.INVISIBLE);
        });
    }
}