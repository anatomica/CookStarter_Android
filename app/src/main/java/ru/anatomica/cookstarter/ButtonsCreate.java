package ru.anatomica.cookstarter;

import ru.anatomica.cookstarter.ui.restaurants.*;

public class ButtonsCreate {

    private MainActivity mainActivity;

    protected RestaurantsAdapter restaurantsAdapter;
    protected RestaurantsDescriptionAdapter restaurantsDescriptionAdapter;
    protected RestaurantsMenuAdapter restaurantsMenuAdapter;

    public ButtonsCreate(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void createBtn(int todo) {
        // создаем адаптер
        if (todo == 1)
            restaurantsAdapter = new RestaurantsAdapter(mainActivity, R.layout.restaurant_items, HttpClients.restaurantsList);
        if (todo == 2)
            restaurantsDescriptionAdapter = new RestaurantsDescriptionAdapter(mainActivity, R.layout.restaurant_items, HttpClients.restaurantListsDescription);
        if (todo == 3)
            restaurantsMenuAdapter = new RestaurantsMenuAdapter(mainActivity, R.layout.restaurant_items, HttpClients.restaurantListsMenu);
        // устанавливаем адаптер
        if (todo == 1) RestaurantsFragment.restaurantsList.setAdapter(restaurantsAdapter);
        if (todo == 2) RestaurantsFragment.restaurantsList.setAdapter(restaurantsDescriptionAdapter);
        if (todo == 3) RestaurantsFragment.restaurantsList.setAdapter(restaurantsMenuAdapter);
    }
}
