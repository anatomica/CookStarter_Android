package ru.anatomica.cookstarter.ui.restaurants;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import ru.anatomica.cookstarter.MainActivity;
import ru.anatomica.cookstarter.R;
import ru.anatomica.cookstarter.entity.Restaurant;
import ru.anatomica.cookstarter.entity.RestaurantDescription;

public class RestaurantsFragment extends Fragment implements SearchView.OnQueryTextListener {

    private RestaurantsViewModel restaurantsViewModel;
    private MainActivity mainActivity;

    public static ListView restaurantsList;
    private SearchView editText;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = ((MainActivity) activity);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        restaurantsViewModel = ViewModelProviders.of(this).get(RestaurantsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_restaurant, container, false);
        editText = root.findViewById(R.id.editSearch);

        // запрос списка
        mainActivity.getRequest("restaurants", "");
        // получаем элемент ListView
        restaurantsList = root.findViewById(R.id.restaurantsList);

        restaurantsViewModel.getText().observe(getViewLifecycleOwner(), s -> {
            // textView.setText(s);
        });
        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AdapterView.OnItemClickListener itemListener = (parent, v, position, id) -> {
            // получаем выбранный пункт
            Object o = parent.getItemAtPosition(position);
            if (o.getClass().getSimpleName().equals("Restaurant")) {
                Restaurant selectedState = (Restaurant) parent.getItemAtPosition(position);
                mainActivity.getRequest("restaurant", selectedState.getName());
            }
            if (o.getClass().getSimpleName().equals("RestaurantDescription")) {
                RestaurantDescription selectedState = (RestaurantDescription) parent.getItemAtPosition(position);
                mainActivity.getRequest("menu", selectedState.getId());
            }
        };
        restaurantsList.setOnItemClickListener(itemListener);
        editText.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mainActivity.getFilter(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}