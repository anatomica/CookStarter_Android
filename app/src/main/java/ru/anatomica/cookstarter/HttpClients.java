package ru.anatomica.cookstarter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import cz.msebera.android.httpclient.Header;
import ru.anatomica.cookstarter.entity.*;
import ru.anatomica.cookstarter.ui.cart.CartFragment;
import ru.anatomica.cookstarter.ui.login.LoginActivity;
import ru.anatomica.cookstarter.ui.profile.ProfileFragment;

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
    public final String getPicture = "https://picture-service.herokuapp.com/picture/restaurant/get/";

    public final String addToCart = "https://marketcook.herokuapp.com/market/api/v1/cart/add/";
    public final String requestCart = "https://marketcook.herokuapp.com/market/api/v1/cart";
    public final String getOrder = "https://marketcook.herokuapp.com/market/api/v1/orders";
    public final String createOrder = "https://marketcook.herokuapp.com/market/api/v1/orders/confirm";
    public final String getUser = "https://marketcook.herokuapp.com/market/profile/user";
    public final String increment = "https://marketcook.herokuapp.com/market/api/v1/cart/add/";
    public final String decrement = "https://marketcook.herokuapp.com/market/api/v1/cart/decrement/";

    private MainActivity mainActivity;
    private List<Integer> idPicture;

    private static Callback loadRestaurants;
    private static Callback loadRestaurantsMenu;
    private static Callback reloadCartTable;
    private static Callback setProfile;

    public static List<Restaurant> restaurantsList = new ArrayList<>();
    public static List<RestaurantMenu> restaurantListsMenu = new ArrayList<>();
    public static List<Bitmap> images = new ArrayList<>();
    private Bitmap image;
    private int count = 1;

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
        if (type.equals("getPicture")) request = getPicture + id + "?Authorization=Bearer " + LoginActivity.token;
        if (type.equals("addToCart")) request = addToCart + id;
        if (type.equals("requestCart")) request = requestCart;
        if (type.equals("getOrder")) request = getOrder;
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
                    // System.out.println(json);
                    String str = json.replace("\"roles\" : [ ],", "");

                    // convert JSON array to List of objects
                    switch (type) {
                        case ("restaurants"):
                            restaurantsList = mapper.readValue(json, new TypeReference<List<Restaurant>>(){});
                            restaurantsList.forEach(x -> System.out.println(x.getId() + ": " + x.getName() + ": " + x.getPictureId()));
                            // getPicture();
                            restaurantsList.forEach(x -> mainActivity.getRequest("getPicture", (long) x.getPictureId()));
                            break;
                        case ("restaurantMenu"):
                            restaurantListsMenu = mapper.readValue(json, new TypeReference<List<RestaurantMenu>>(){});
                            restaurantListsMenu.forEach(x -> System.out.println(x.getName() + ": " + x.getPrice()));
                            // restaurantListsMenu.forEach(x -> x.setPictureId(idPicture.get(x.getPictureId() - 1)));
                            restaurantListsMenu.forEach(x -> x.setPictureId(idPicture.get(getRandomPicture())));
                            loadRestaurantsMenu.callBack();
                            break;
                        case ("getPicture"):
                            if (count <= restaurantsList.size())  {
                                image = BitmapFactory.decodeByteArray(response, 0, response.length);
                                images.add(image);
                                count++;
                            }
                            if (count == restaurantsList.size())  {
                                loadRestaurants.callBack();
                                count = 1;
                            }
                            break;
                        case ("addToCart"):
                            mainActivity.runOnUiThread(() -> mainActivity.serviceMessage("Добавлено"));
                            break;
                        case ("requestCart"):
                        case ("getOrder"):
                            CartFragment.cartFilesList.clear();
                            CartFragment.cartFilesList = mapper.readValue(json, new TypeReference<List<Order>>(){});
                            CartFragment.cartFilesList.forEach(x -> System.out.println(x.getProductTitle() + " " + x.getPrice()));
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

    public void postRequest(String address, String phone) {

        RequestParams params = new RequestParams();
        params.put("address", address);
        params.put("phone", phone);

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", "Bearer " + LoginActivity.token);
        client.post(createOrder, params, new AsyncHttpResponseHandler() {

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

    private void getPicture() {
        idPicture = new ArrayList<>();
        idPicture.add(R.drawable.hardrock);
        idPicture.add(R.drawable.mcdonalds);
        idPicture.add(R.drawable.pablochef);
        idPicture.add(R.drawable.rootslogo);
        idPicture.add(R.drawable.roppolos);
        idPicture.add(R.drawable.tacobell);
        idPicture.add(R.drawable.tacobell);
    }

    private int getRandomPicture() {
        final Random rand = new Random();
        return rand.nextInt(idPicture.size());
    }
}
