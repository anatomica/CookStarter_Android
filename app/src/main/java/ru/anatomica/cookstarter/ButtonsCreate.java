package ru.anatomica.cookstarter;

import android.graphics.Color;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

import ru.anatomica.cookstarter.ui.restaurants.RestaurantsFragment;

public class ButtonsCreate {

    private MainActivity mainActivity;
    private HttpClients httpClients;
    protected static ArrayList<Button> buttons = new ArrayList<>();

    public ButtonsCreate(MainActivity mainActivity, HttpClients httpClients) {
        this.mainActivity = mainActivity;
        this.httpClients = new HttpClients();
    }

    public void createBtn(int todo) {
        removeButton();
        if (todo == 1) {
            for (int i = 0; i < HttpClients.restaurantLists.size(); i++) {
                buttons.add(new Button(mainActivity));
            }
        }
        if (todo == 2) {
            for (int i = 0; i < HttpClients.restaurantListsDescription.size(); i++) {
                buttons.add(new Button(mainActivity));
            }
        }
        if (todo == 3) {
            for (int i = 0; i < HttpClients.restaurantListsMenu.size(); i++) {
                buttons.add(new Button(mainActivity));
            }
        }
        viewButton(todo);
    }

    public void removeButton() {
        if (buttons.size() > 0)
            for (int i = 0; i < buttons.size(); i++) {
                ((ViewManager)buttons.get(i).getParent()).removeView(buttons.get(i));
            }
        buttons.clear();
    }

    public void viewButton(int todo) {
        LinearLayout.MarginLayoutParams params = new LinearLayout.MarginLayoutParams(
                LinearLayout.MarginLayoutParams.MATCH_PARENT,
                LinearLayout.MarginLayoutParams.WRAP_CONTENT);

        int btnMargin = 10;
        String nameButton = null;
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setId(i);
            final int id_ = buttons.get(i).getId();
            if (todo == 1) nameButton = HttpClients.restaurantLists.get(i).getName();
            if (todo == 2) nameButton = HttpClients.restaurantListsDescription.get(i).getDescription();
            if (todo == 3) nameButton = HttpClients.restaurantListsMenu.get(i).getName() + "\n" + HttpClients.restaurantListsMenu.get(i).getPrize();
            buttons.get(i).setText(nameButton);
            buttons.get(i).getBackground().setAlpha(100);
            buttons.get(i).setTextColor(Color.BLACK);
            params.height = 270;
            buttons.get(i).setLayoutParams(params);
            buttons.get(i).setY(btnMargin);
            btnMargin = btnMargin + 270;
            RestaurantsFragment.buttonsView.addView(buttons.get(i), params);

            int finalI = i;
            buttons.get(i).setOnClickListener(v -> {
                if (todo == 1) {
                    httpClients.getRequest("restaurant", HttpClients.restaurantLists.get(finalI).getName());
                }
                if (todo == 2) {
                    httpClients.getRequest("menu", HttpClients.restaurantListsDescription.get(0).getId());
                }
            });
            buttons.get(i).setOnLongClickListener(v -> {

                return true;
            });
        }
    }
}
