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
import ru.anatomica.cookstarter.entity.RestaurantDescription;

public class RestaurantsDescriptionAdapter extends ArrayAdapter<RestaurantDescription> implements Filterable {

    private LayoutInflater inflater;
    private int layout;
    private List<RestaurantDescription> restaurants;
    private ValueFilter valueFilter;
    private List<RestaurantDescription> mStringFilterList;

    public RestaurantsDescriptionAdapter(@NonNull Context context, int resource, @NonNull List<RestaurantDescription> restaurants) {
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
    public RestaurantDescription getItem(int position) {
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

        RestaurantDescription restaurant = restaurants.get(position);

        logoView.setImageResource(restaurant.getLogo());
        nameView.setText(restaurant.getDescription());

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
                ArrayList<RestaurantDescription> filterList = new ArrayList<>();
                for (int i = 0; i < mStringFilterList.size(); i++) {
                    if ((mStringFilterList.get(i).getDescription().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {

                        RestaurantDescription restaurant = new RestaurantDescription(
                                mStringFilterList.get(i).getId(),
                                mStringFilterList.get(i).getDescription(),
                                mStringFilterList.get(i).getLogo());
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
            restaurants = (ArrayList<RestaurantDescription>) results.values;
            notifyDataSetChanged();
        }
    }
}
