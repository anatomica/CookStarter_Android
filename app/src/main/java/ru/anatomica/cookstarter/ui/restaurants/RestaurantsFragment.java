package ru.anatomica.cookstarter.ui.restaurants;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import ru.anatomica.cookstarter.MainActivity;
import ru.anatomica.cookstarter.R;
import ru.anatomica.cookstarter.entity.Restaurant;
import ru.anatomica.cookstarter.entity.RestaurantMenu;

public class RestaurantsFragment extends Fragment implements SearchView.OnQueryTextListener {

    public static ListView restaurantsList;

    private RestaurantsViewModel restaurantsViewModel;
    private MainActivity mainActivity;

    private ViewGroup placeholder;
    private LayoutInflater mInflater;
    private ViewGroup mContainer;

    private SearchView editText;
    private LinearLayout searchBar;
    private ImageView imageView;
    private Button addToCart;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = ((MainActivity) activity);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        restaurantsViewModel = ViewModelProviders.of(this).get(RestaurantsViewModel.class);
        mInflater = inflater;
        mContainer = container;

        View view = inflater.inflate(R.layout.fragment_restaurant, container,false);
        placeholder = (ViewGroup) view;

        editText = view.findViewById(R.id.editSearch);
        searchBar = view.findViewById(R.id.search_bar);

        // запрос списка
        mainActivity.getRequest("restaurants", 1L);
        // получаем элемент ListView
        restaurantsList = view.findViewById(R.id.restaurantsList);

        restaurantsViewModel.getText().observe(getViewLifecycleOwner(), s -> {
            // textView.setText(s);
        });
        return placeholder;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AdapterView.OnItemClickListener itemListener = (parent, v, position, id) -> {
            // получаем выбранный пункт
            Object o = parent.getItemAtPosition(position);
            if (o.getClass().getSimpleName().equals("Restaurant")) {
                Restaurant selectedRestaurant = (Restaurant) parent.getItemAtPosition(position);
                mainActivity.getRequest("restaurantMenu", selectedRestaurant.getId());
                searchBar.setVisibility(View.INVISIBLE);
            }
            if (o.getClass().getSimpleName().equals("RestaurantMenu")) {
                RestaurantMenu selectedMenuItem = (RestaurantMenu) parent.getItemAtPosition(position);

                View newView = mInflater.inflate(R.layout.restaurant_item, mContainer, false);
                placeholder.removeAllViews();
                placeholder.addView(newView);

                imageView = newView.findViewById(R.id.main_logo);
                addToCart = newView.findViewById(R.id.addToCart);
                imageView.setImageResource(selectedMenuItem.getLogoId());
                addToCart.setOnClickListener(vi -> {
                    // TODO: Send request to cart
                });
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