package ru.anatomica.cookstarter.ui.restaurants;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import ru.anatomica.cookstarter.HttpClients;
import ru.anatomica.cookstarter.R;
import ru.anatomica.cookstarter.entity.RestaurantMenu;

public class RestaurantsMenuAdapter extends ArrayAdapter<RestaurantMenu> implements Filterable {

    private LayoutInflater inflater;
    private int layout;
    private List<RestaurantMenu> restaurants;
    private ValueFilter valueFilter;
    private List<RestaurantMenu> mStringFilterList;

    public RestaurantsMenuAdapter(@NonNull Context context, int resource, @NonNull List<RestaurantMenu> restaurants) {
        super(context, resource, restaurants);
        this.restaurants = restaurants;
        this.inflater = LayoutInflater.from(context);
        this.layout = resource;
        mStringFilterList = restaurants;
    }

    @Override
    public int getCount() {
        return restaurants.size();
    }

    @Nullable
    @Override
    public RestaurantMenu getItem(int position) {
        return restaurants.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = inflater.inflate(this.layout, parent, false);

        ImageView logoView = view.findViewById(R.id.logo);
        TextView nameView = view.findViewById(R.id.name);
        TextView addressView = view.findViewById(R.id.address);

        RestaurantMenu restaurant = restaurants.get(position);

        logoView.setImageBitmap(HttpClients.imagesMenus.get(HttpClients.restaurantListsMenu.get(position).getId()));
        nameView.setText(restaurant.getName());
        addressView.setText(restaurant.getPrice().toString() + " руб.");

        return view;
    }

    @NonNull
    @Override
    public Filter getFilter() {

        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                ArrayList<RestaurantMenu> filterList = new ArrayList<>();
                for (int i = 0; i < mStringFilterList.size(); i++) {
                    if ((mStringFilterList.get(i).getName().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {

                        RestaurantMenu restaurant = new RestaurantMenu(
                                mStringFilterList.get(i).getId(),
                                mStringFilterList.get(i).getName(),
                                mStringFilterList.get(i).getPrice(),
                                mStringFilterList.get(i).getPictureId());
                        filterList.add(restaurant);
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            restaurants = (ArrayList<RestaurantMenu>) results.values;
            notifyDataSetChanged();
        }
    }
}
