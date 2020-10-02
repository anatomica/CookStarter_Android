package ru.anatomica.cookstarter;

import android.graphics.Color;
import android.os.Bundle;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.LinearLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import cz.msebera.android.httpclient.Header;
import ru.anatomica.cookstarter.Object.RestaurantList;

public class MainActivity extends AppCompatActivity {

    private Callback loadRestaurants;
    public ConstraintLayout chatLayout;
    public ConstraintLayout chatLayoutInto;
    public static List<RestaurantList> restaurantLists = new ArrayList<>();
    protected static ArrayList<Button> buttons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        chatLayout = findViewById(R.id.activity_chat);
        chatLayoutInto = findViewById(R.id.activity_chat_layout);
        loadRestaurants = this::createBtn;

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view ->
                createBtn()
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
        );
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        if (chatLayout.getVisibility() == View.VISIBLE) {
            chatLayout.setVisibility(View.INVISIBLE);
            return;
        }
        else {
            shutdown();
        }
    }

    public void onClick(View view) throws InterruptedException {
        switch (view.getId()) {
            case R.id.btn_find:
                break;
            case R.id.btn_choose:
                // httpClient();
                break;
        }
    }

    public void httpClient() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://my-json-server.typicode.com/kunAndrew/cookstarter/restaurants", new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                System.out.println("called before request is started");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                try {
                    String json = new String(response, "UTF-8");
                    ObjectMapper mapper = new ObjectMapper();
                    System.out.println(json);
                    String str1 = json.replace("\n", "");
                    String str2 = str1.replace(" ", "");

                    // convert JSON array to List of objects
                    restaurantLists = Arrays.asList(mapper.readValue(str2, RestaurantList[].class));
                    restaurantLists.forEach(x -> System.out.println(x.id + ": " + x.name));
                    loadRestaurants.callBack();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                System.out.println(Arrays.toString(errorResponse) + " " + statusCode);
            }

            @Override
            public void onRetry(int retryNo) {
                System.out.println("called when request is retried");
            }
        });
    }

    public void createBtn() {
        removeButton();
        for (int i = 0; i < restaurantLists.size(); i++) {
            buttons.add(new Button(this));
        }
        viewButton();
    }

    public void removeButton() {
        if (buttons.size() > 0)
            for (int i = 0; i < buttons.size(); i++) {
                ((ViewManager)buttons.get(i).getParent()).removeView(buttons.get(i));
            }
        buttons.clear();
    }

    public void viewButton() {
        chatLayout.setVisibility(View.VISIBLE);
        LinearLayout.MarginLayoutParams params = new LinearLayout.MarginLayoutParams(
                LinearLayout.MarginLayoutParams.MATCH_PARENT,
                LinearLayout.MarginLayoutParams.WRAP_CONTENT);

        int btnMargin = 10;
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setId(i);
            final int id_ = buttons.get(i).getId();
            String nameButton = restaurantLists.get(i).name;
            buttons.get(i).setText(nameButton);
            buttons.get(i).getBackground().setAlpha(100);
            buttons.get(i).setTextColor(Color.WHITE);
            params.height = 270;
            buttons.get(i).setLayoutParams(params);
            buttons.get(i).setY(btnMargin);
            btnMargin = btnMargin + 270;
            chatLayoutInto.addView(buttons.get(i), params);

            int finalI = i;
            buttons.get(i).setOnClickListener(v -> {
                String textButton = restaurantLists.get(finalI).name;

            });
            buttons.get(i).setOnLongClickListener(v -> {

                return true;
            });
        }
    }

    public void shutdown() {
        System.exit(0);
    }

}