package ru.anatomica.cookstarter.ui.cart;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import ru.anatomica.cookstarter.MainActivity;
import ru.anatomica.cookstarter.R;

public class CartFragment extends Fragment {

    MainActivity mainActivity;

    private ViewGroup placeholder;
    private LayoutInflater mInflater;
    private ViewGroup mContainer;

    public static TableLayout cartTableLayout;
    public static Button sendOrder;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = ((MainActivity) activity);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflater = inflater;
        mContainer = container;

        View view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        placeholder = (ViewGroup) view;

        cartTableLayout = view.findViewById(R.id.cartTableLayout);
        sendOrder = view.findViewById(R.id.send_order);

        mainActivity.reloadCartTable();
        return placeholder;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sendOrder.setOnClickListener(vi -> {

            mainActivity.postRequest("createOrder");

            View newView = mInflater.inflate(R.layout.order_content, mContainer, false);
            placeholder.removeAllViews();
            placeholder.addView(newView);
        });
    }

}