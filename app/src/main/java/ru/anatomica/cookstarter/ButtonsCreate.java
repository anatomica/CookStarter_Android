package ru.anatomica.cookstarter;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TableRow;
import android.widget.TextView;

import ru.anatomica.cookstarter.ui.cart.CartFragment;
import ru.anatomica.cookstarter.ui.restaurants.*;

public class ButtonsCreate {

    private MainActivity mainActivity;

    protected RestaurantsAdapter restaurantsAdapter;
    protected RestaurantsMenuAdapter restaurantsMenuAdapter;

    public ButtonsCreate(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void createBtn(int todo) {
        // создаем адаптер
        if (todo == 1) restaurantsAdapter = new RestaurantsAdapter(mainActivity, R.layout.restaurant_items, HttpClients.restaurantsList);
        if (todo == 2) restaurantsMenuAdapter = new RestaurantsMenuAdapter(mainActivity, R.layout.restaurant_items, HttpClients.restaurantListsMenu);
        // устанавливаем адаптер
        if (todo == 1) RestaurantsFragment.restaurantsList.setAdapter(restaurantsAdapter);
        if (todo == 2) RestaurantsFragment.restaurantsList.setAdapter(restaurantsMenuAdapter);
    }

    public void reloadCartTable() {
        // CartFragment.cartTableLayout.removeAllViews();
        for (int i = 0; i < CartFragment.localFilesList.size(); i++) {
            int checkDirColor = 0;
            TableRow tableRow = new TableRow(mainActivity);
            TableRow.LayoutParams params;
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            tableRow.setGravity(Gravity.CENTER);
            tableRow.setWeightSum(1f);

            TextView name = new TextView(mainActivity);
            params = new TableRow.LayoutParams(0, 150, 0.75f);
            params.setMargins(60, 20,50, 20);
            name.setText(CartFragment.localFilesList.get(i).getRestaurantMenu().getTitle());
            name.setTextSize(TypedValue.TYPE_STRING,10);
            name.setTextColor(Color.BLACK);
            name.setGravity(Gravity.START | Gravity.CENTER);
            name.setLayoutParams(params);

            TextView size = new TextView(mainActivity);
            params = new TableRow.LayoutParams(0, 150, 0.25f);
            String sizeOfValue = String.valueOf(CartFragment.localFilesList.get(i).getPrice());
            if (sizeOfValue.equals("0")) size.setText("");
            else size.setText(String.format("%s руб.", sizeOfValue));
            size.setGravity(Gravity.START | Gravity.CENTER);
            size.setLayoutParams(params);

            tableRow.addView(name);
            tableRow.addView(size);
            tableRow.setClickable(true);
            tableRow.setOnClickListener(v -> {

            });
            tableRow.setOnLongClickListener(v -> {

                return true;
            });
            CartFragment.cartTableLayout.addView(tableRow);
        }
    }
}
