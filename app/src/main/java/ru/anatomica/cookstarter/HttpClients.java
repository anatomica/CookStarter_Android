package ru.anatomica.cookstarter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import cz.msebera.android.httpclient.Header;
import ru.anatomica.cookstarter.Object.RestaurantDescription;
import ru.anatomica.cookstarter.Object.RestaurantList;
import ru.anatomica.cookstarter.Object.RestaurantMenu;

public class HttpClients {

    private MainActivity mainActivity;

    private static Callback loadRestaurants;
    private static Callback loadRestaurantsDescription;
    private static Callback loadRestaurantsMenu;

    public static List<RestaurantList> restaurantLists = new ArrayList<>();
    public static List<RestaurantDescription> restaurantListsDescription = new ArrayList<>();
    public static List<RestaurantMenu> restaurantListsMenu = new ArrayList<>();

    public HttpClients() {
    }

    public HttpClients(MainActivity mainActivity, ButtonsCreate buttonsCreate) {
        this.mainActivity = mainActivity;
        loadRestaurants = () -> buttonsCreate.createBtn(1);
        loadRestaurantsDescription = () -> buttonsCreate.createBtn(2);
        loadRestaurantsMenu = () -> buttonsCreate.createBtn(3);
    }

    public void getRestaurants() {
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
                    restaurantLists.forEach(x -> System.out.println(x.getId() + ": " + x.getName()));
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

    public void getRestaurantsDescription(String name) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://my-json-server.typicode.com/kunAndrew/cookstarter/restaurant/" + name, new AsyncHttpResponseHandler() {

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
                    restaurantListsDescription = Arrays.asList(mapper.readValue(str2, RestaurantDescription.class));
                    restaurantListsDescription.forEach(x -> System.out.println(x.getId() + ": " + x.getDescription()));
                    loadRestaurantsDescription.callBack();
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

    public void getRestaurantsMenu(String name) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://my-json-server.typicode.com/kunAndrew/cookstarter/menu" + name, new AsyncHttpResponseHandler() {

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
                    restaurantListsMenu = Arrays.asList(mapper.readValue(str2, RestaurantMenu[].class));
                    restaurantListsMenu.forEach(x -> System.out.println(x.getName() + ": " + x.getPrize()));
                    loadRestaurantsMenu.callBack();
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

}
