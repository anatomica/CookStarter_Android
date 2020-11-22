package ru.anatomica.cookstarter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import ru.anatomica.cookstarter.entity.*;
import ru.anatomica.cookstarter.ui.login.LoginActivity;
import ru.anatomica.cookstarter.ui.profile.ProfileFragment;
import ru.anatomica.cookstarter.ui.restaurants.RestaurantsFragment;

public class HttpClients {

//    public final String restaurants = "https://marketcook.herokuapp.com/market/api/v1/restaurants";
//    public final String restaurantMenu = "https://marketcook.herokuapp.com/market/api/v1/products/";
//    public final String addToCart = "https://marketcook.herokuapp.com/market/api/v1/cart/add/";
//    public final String requestCart = "https://marketcook.herokuapp.com/market/api/v1/cart";
//    public final String getOrder = "https://marketcook.herokuapp.com/market/api/v1/orders";
//    public final String createOrder = "https://marketcook.herokuapp.com/market/api/v1/orders/confirm";
//    public final String getUser = "https://marketcook.herokuapp.com/market/profile/user";
//    public final String increment = "https://marketcook.herokuapp.com/market/api/v1/cart/add/";
//    public final String decrement = "https://marketcook.herokuapp.com/market/api/v1/cart/decrement/";

    public final String restaurants = "https://cookstarter-restaurant-service.herokuapp.com/restaurant/getAll";
    public final String restaurantMenu = "https://cookstarter-restaurant-service.herokuapp.com/menu/get/";
    public final String getRestaurantPicture = "https://picture-service.herokuapp.com/picture/restaurant/get/";
    public final String getMenuPicture = "https://picture-service.herokuapp.com/picture/menu/get/";

    public final String createOrder = "https://cs-order-service.herokuapp.com/orders/add";
    public final String getOrder = "https://cs-order-service.herokuapp.com/orders/get/";
    public final String setStatusPaid = "https://cs-order-service.herokuapp.com/orders/set-status";

    public final String increment = "https://marketcook.herokuapp.com/market/api/v1/cart/add/";
    public final String decrement = "https://marketcook.herokuapp.com/market/api/v1/cart/decrement/";
    public final String getUser = "https://marketcook.herokuapp.com/market/profile/user";

    private MainActivity mainActivity;
    private static Callback loadRestaurants;
    private static Callback loadRestaurantsMenu;
    private static Callback reloadCartTable;
    private static Callback setProfile;

    public static List<Restaurant> restaurantsList = new ArrayList<>();
    public static List<RestaurantMenu> restaurantListsMenu = new ArrayList<>();
    public static Map<Long, Bitmap> imagesRestaurants = new HashMap<>();
    public static Map<Long, Bitmap> imagesMenus = new HashMap<>();
    public static Status status;
    private Bitmap image;
    private int countOfRestaurants = 0;
    private int countOfMenus = 0;

    public HttpClients() {
    }

    public HttpClients(MainActivity mainActivity, ButtonsCreate buttonsCreate) {
        this.mainActivity = mainActivity;
        loadRestaurants = () -> buttonsCreate.createBtn(1);
        loadRestaurantsMenu = () -> buttonsCreate.createBtn(2);
        reloadCartTable = buttonsCreate::reloadCartTable;
        setProfile = buttonsCreate::setProfile;
    }

    public void getRequest(String type, Long id) {

        String request = null;
        if (type.equals("restaurants")) request = restaurants;
        if (type.equals("restaurantMenu")) request = restaurantMenu + id;
        if (type.equals("getOrder")) request = getOrder + id;
        if (type.equals("getUser")) request = getUser;
        if (type.equals("increment")) request = increment + id;
        if (type.equals("decrement")) request = decrement + id;

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", "Bearer " + LoginActivity.token);
        client.get(request, new AsyncHttpResponseHandler() {

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
                    String str = json.replace("\"roles\" : [ ],", "");

                    // convert JSON array to List of objects
                    switch (type) {
                        case ("restaurants"):
                            restaurantsList = mapper.readValue(json, new TypeReference<List<Restaurant>>(){});
                            restaurantsList.forEach(x -> System.out.println(x.getId() + ": " + x.getName() + ": " + x.getPictureId()));
                            restaurantsList.forEach(x -> getPicture("getRestaurantPicture", x.getId(), x.getPictureId()));
                            break;
                        case ("restaurantMenu"):
                            restaurantListsMenu = mapper.readValue(json, new TypeReference<List<RestaurantMenu>>(){});
                            restaurantListsMenu.forEach(x -> System.out.println(x.getName() + ": " + x.getPrice() + ": " + x.getPictureId()));
                            restaurantListsMenu.forEach(x -> getPicture("getMenuPicture", x.getId(), x.getPictureId()));
                            break;
                        case ("addToCart"):
                            mainActivity.runOnUiThread(() -> mainActivity.serviceMessage("Добавлено"));
                            break;
                        case ("getOrder"):
                            RestaurantsFragment.cartFilesList.clear();
                            RestaurantsFragment.cartFilesList = mapper.readValue(json, new TypeReference<List<Order>>(){});
                            RestaurantsFragment.cartFilesList.forEach(x -> System.out.println(x.getName() + " " + x.getPrice()));
                            reloadCartTable.callBack();
                            break;
                        case ("getUser"):
                            ProfileFragment.userProfile = Arrays.asList(mapper.readValue(str, User.class));
                            setProfile.callBack();
                            break;
                        case ("increment"):
                        case ("decrement"):
                            mainActivity.getRequest("requestCart", 1L);
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                try {
                    System.out.println(new String(errorResponse, "UTF-8") + " " + statusCode);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onRetry(int retryNo) {
                System.out.println("called when request is retried");
            }
        });
    }

    public void getPicture(String type, Long id, Long pictureId) {

        String request = null;
        if (type.equals("getRestaurantPicture")) request = getRestaurantPicture + pictureId + "?Authorization=Bearer " + LoginActivity.token;
        if (type.equals("getMenuPicture")) request = getMenuPicture + pictureId + "?Authorization=Bearer " + LoginActivity.token;

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", "Bearer " + LoginActivity.token);
        client.get(request, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                System.out.println("called before request is started");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                try {
                    switch (type) {
                        case ("getRestaurantPicture"):
                            image = BitmapFactory.decodeByteArray(response, 0, response.length);
                            imagesRestaurants.put(id, image);
                            countOfRestaurants++;
                            if (countOfRestaurants == restaurantsList.size())  {
                                loadRestaurants.callBack();
                                countOfRestaurants = 0;
                                break;
                            }
                            break;
                        case ("getMenuPicture"):
                            image = BitmapFactory.decodeByteArray(response, 0, response.length);
                            imagesMenus.put(id, image);
                            countOfMenus++;
                            if (countOfMenus == restaurantListsMenu.size()) {
                                loadRestaurantsMenu.callBack();
                                countOfMenus = 0;
                                break;
                            }
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                try {
                    System.out.println(new String(errorResponse, "UTF-8") + " " + statusCode);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onRetry(int retryNo) {
                System.out.println("called when request is retried");
            }
        });
    }

    public void postRequest(String request) {

        JSONObject myOrder = new JSONObject();
        JSONObject dishes = new JSONObject();
        JSONObject items = new JSONObject();

        try {
            myOrder.put("customerId", 55);
            myOrder.put("restaurantId", RestaurantsFragment.cartFilesList.get(0).getRestaurantId());

            for (Order order: RestaurantsFragment.cartFilesList) {
                items.put("price", order.getPrice());
                items.put("quantity", order.getQuantity());
                dishes.put(order.getId().toString(), items);
            }

            myOrder.put("dishes", dishes);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        StringEntity entity = new StringEntity(myOrder.toString(), "UTF-8");
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", "Bearer " + LoginActivity.token);
        client.post(mainActivity, createOrder, entity, "application/json", new AsyncHttpResponseHandler() {

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
                    status = mapper.readValue(json, new TypeReference<Status>(){});
                    mainActivity.getRequest("getOrder", status.getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                try {
                    System.out.println(new String(errorResponse, "UTF-8") + " " + statusCode);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onRetry(int retryNo) {
                System.out.println("called when request is retried");
            }
        });
    }

    public void setStatus() {

        JSONObject setStatus = new JSONObject();
        try {
            setStatus.put("id", String.format("%s", status.getId()));
            setStatus.put("status", "PAID");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        StringEntity entity = new StringEntity(setStatus.toString(), "UTF-8");
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", "Bearer " + LoginActivity.token);
        client.post(mainActivity, setStatusPaid, entity, "application/json", new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                System.out.println("called before request is started");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                try {
                    String json = new String(response, "UTF-8");
                    System.out.println(json);
                    mainActivity.runOnUiThread(() -> mainActivity.serviceMessage("Оплачено!"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                try {
                    System.out.println(new String(errorResponse, "UTF-8") + " " + statusCode);
                    mainActivity.runOnUiThread(() -> mainActivity.serviceMessage("Оплачено!")); // Ошибка возврата нормального статуса!
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onRetry(int retryNo) {
                System.out.println("called when request is retried");
            }
        });
    }
}
