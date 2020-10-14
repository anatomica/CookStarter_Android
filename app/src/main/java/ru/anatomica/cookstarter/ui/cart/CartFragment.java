package ru.anatomica.cookstarter.ui.cart;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ru.anatomica.cookstarter.MainActivity;
import ru.anatomica.cookstarter.R;
import ru.anatomica.cookstarter.entity.Order;
import ru.anatomica.cookstarter.entity.Restaurant;
import ru.anatomica.cookstarter.entity.RestaurantMenu;

public class CartFragment extends Fragment {

    MainActivity mainActivity;

    public static TableLayout cartTableLayout;
    public static List<Order> localFilesList;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = ((MainActivity) activity);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        cartTableLayout = root.findViewById(R.id.cartTableLayout);
        localFilesList = new ArrayList<>();

        mainActivity.getRequest("requestCart", 1L);

        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AdapterView.OnItemClickListener itemListener = (parent, v, position, id) -> {

        };
    }

}