package ru.anatomica.cookstarter.ui.restaurants;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import java.math.BigDecimal;
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
    private TextView quantity;
    private TextView itemMenuPrice;
    private TextView itemMenuName;
    private Button addCount;
    private Button removeCount;
    private int currentCount = 1;

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

        @SuppressLint("SetTextI18n")
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
                quantity = newView.findViewById(R.id.quantity);
                addCount = newView.findViewById(R.id.plus);
                removeCount = newView.findViewById(R.id.minus);
                itemMenuName = newView.findViewById(R.id.itemMenuName);
                itemMenuPrice = newView.findViewById(R.id.itemMenuPrice);

                String nameItemMenu = selectedMenuItem.getName();
                BigDecimal price = selectedMenuItem.getPrice();
                String description = selectedMenuItem.getDescription();

                itemMenuName.setText(nameItemMenu);
                itemMenuPrice.setText(price + " руб.");

                imageView.setImageResource(selectedMenuItem.getPictureId());
                quantity.setTextSize(40);
                quantity.setText("1");
                quantity.setGravity(Gravity.CENTER | Gravity.BOTTOM);
                addToCart.setOnClickListener(vi -> {
                    for (int i = 0; i < currentCount; i++) {
                        mainActivity.getRequest("addToCart", selectedMenuItem.getId());
                    }
                });

                addCount.setOnClickListener(view1 -> {
                    currentCount = Integer.parseInt(quantity.getText().toString());
                    currentCount++;
                    quantity.setText(Integer.toString(currentCount));
                });

                removeCount.setOnClickListener(view2 -> {
                    currentCount = Integer.parseInt(quantity.getText().toString());
                    if (currentCount > 1) {
                        currentCount--;
                        quantity.setText(Integer.toString(currentCount));
                    }
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