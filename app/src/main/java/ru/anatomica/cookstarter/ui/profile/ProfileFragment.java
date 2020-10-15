package ru.anatomica.cookstarter.ui.profile;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import java.util.List;
import ru.anatomica.cookstarter.MainActivity;
import ru.anatomica.cookstarter.R;
import ru.anatomica.cookstarter.entity.User;

public class ProfileFragment extends Fragment {

    MainActivity mainActivity;
    public static List<User> userProfile;

    private ProfileViewModel profileViewModel;
    public static TextView username;
    public static TextView email;
    public static TextView phone;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = ((MainActivity) activity);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_person, container, false);

        username = root.findViewById(R.id.name_person);
        email = root.findViewById(R.id.email_person);
        phone = root.findViewById(R.id.phone_person);

        username.setTextColor(Color.BLACK);
        username.setTextSize(25);
        email.setTextColor(Color.BLACK);
        email.setTextSize(25);
        phone.setTextColor(Color.BLACK);
        phone.setTextSize(25);

        mainActivity.getRequest("getUser", 1L);

        return root;
    }
}