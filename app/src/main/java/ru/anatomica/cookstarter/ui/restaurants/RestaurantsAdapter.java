package ru.anatomica.cookstarter.ui.restaurants;

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
import ru.anatomica.cookstarter.R;
import ru.anatomica.cookstarter.entity.Restaurant;

public class RestaurantsAdapter extends ArrayAdapter<Restaurant> implements Filterable {

    private LayoutInflater inflater;
    private int layout;
    private List<Restaurant> restaurants;
    private ValueFilter valueFilter;
    private List<Restaurant> mStringFilterList;

    public RestaurantsAdapter(@NonNull Context context, int resource, @NonNull List<Restaurant> restaurants) {
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
    public Restaurant getItem(int position) {
        return restaurants.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = inflater.inflate(this.layout, parent, false);

        ImageView logoView = view.findViewById(R.id.logo);
        TextView nameView = view.findViewById(R.id.name);
        TextView addressView = view.findViewById(R.id.address);

        Restaurant restaurant = restaurants.get(position);

        logoView.setImageResource(restaurant.getLogoId());
        nameView.setText(restaurant.getName());
        addressView.setText(restaurant.getAddress());

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
                ArrayList<Restaurant> filterList = new ArrayList<Restaurant>();
                for (int i = 0; i < mStringFilterList.size(); i++) {
                    if ((mStringFilterList.get(i).getName().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {

                        Restaurant restaurant = new Restaurant(
                                mStringFilterList.get(i).getId(),
                                mStringFilterList.get(i).getName(),
                                mStringFilterList.get(i).getAddress(),
                                mStringFilterList.get(i).getLogoId());
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
            restaurants = (ArrayList<Restaurant>) results.values;
            notifyDataSetChanged();
        }
    }
}
