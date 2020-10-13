package ru.anatomica.cookstarter;

import java.util.Arrays;

import ru.anatomica.cookstarter.ui.restaurants.*;

public class ButtonsCreate {

    private MainActivity mainActivity;

    protected RestaurantsAdapter restaurantsAdapter;
    protected RestaurantsMenuAdapter restaurantsMenuAdapter;
    protected RestaurantsItemAdapter restaurantsItemAdapter;

    public ButtonsCreate(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void createBtn(int todo) {
        // создаем адаптер
        if (todo == 1)
            restaurantsAdapter = new RestaurantsAdapter(mainActivity, R.layout.restaurant_items, HttpClients.restaurantsList);
        if (todo == 2)
            restaurantsMenuAdapter = new RestaurantsMenuAdapter(mainActivity, R.layout.restaurant_items, HttpClients.restaurantListsMenu);
        if (todo == 3)
            restaurantsItemAdapter = new RestaurantsItemAdapter(mainActivity, R.layout.restaurant_item, Arrays.asList(HttpClients.restaurantListsMenu.get(0)));
        // устанавливаем адаптер
        if (todo == 1) RestaurantsFragment.restaurantsList.setAdapter(restaurantsAdapter);
        if (todo == 2) RestaurantsFragment.restaurantsList.setAdapter(restaurantsMenuAdapter);
        if (todo == 3) RestaurantsFragment.restaurantsList.setAdapter(restaurantsItemAdapter);
    }
}
