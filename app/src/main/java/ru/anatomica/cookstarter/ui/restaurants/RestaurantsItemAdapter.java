package ru.anatomica.cookstarter.ui.restaurants;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;
import ru.anatomica.cookstarter.R;
import ru.anatomica.cookstarter.entity.RestaurantMenu;

public class RestaurantsItemAdapter extends ArrayAdapter<RestaurantMenu> {

    private LayoutInflater inflater;
    private int layout;
    private List<RestaurantMenu> restaurants;
    private List<RestaurantMenu> mStringFilterList;
    private ImageView imageView;
    private Button addToCart;

    public RestaurantsItemAdapter(@NonNull Context context, int resource, @NonNull List<RestaurantMenu> restaurants) {
        super(context, resource, restaurants);
        this.restaurants = restaurants;
        this.inflater = LayoutInflater.from(context);
        this.layout = resource;
        mStringFilterList = restaurants;
    }

    @SuppressLint("SetTextI18n")
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = inflater.inflate(this.layout, parent, false);

        imageView = view.findViewById(R.id.main_logo);
        addToCart = view.findViewById(R.id.addToCart);

        RestaurantMenu restaurant = restaurants.get(0);

        imageView.setImageResource(restaurant.getLogoId());
        addToCart.setOnClickListener(v -> {

        });

        return view;
    }

}
