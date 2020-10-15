package ru.anatomica.cookstarter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import java.io.IOException;
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

    public final String restaurants = "https://marketcook.herokuapp.com/market/api/v1/restaurants";
    public final String restaurantMenu = "https://marketcook.herokuapp.com/market/api/v1/products/";
    public final String addToCart = "https://marketcook.herokuapp.com/market/api/v1/cart/add/";
    public final String requestCart = "https://marketcook.herokuapp.com/market/api/v1/cart";
    public final String getOrder = "https://marketcook.herokuapp.com/market/api/v1/orders";
    public final String createOrder = "https://marketcook.herokuapp.com/market/api/v1/orders/confirm";
    public final String getUser = "https://marketcook.herokuapp.com/market/profile/user";

    private MainActivity mainActivity;
    private List<Integer> idPicture;

    private static Callback loadRestaurants;
    private static Callback loadRestaurantsMenu;
    private static Callback reloadCartTable;
    private static Callback setProfile;

    public static List<Restaurant> restaurantsList = new ArrayList<>();
    public static List<RestaurantMenu> restaurantListsMenu = new ArrayList<>();

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
        if (type.equals("addToCart")) request = addToCart + id;
        if (type.equals("requestCart")) request = requestCart;
        if (type.equals("getOrder")) request = getOrder;
        if (type.equals("getUser")) request = getUser;

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
                    System.out.println(json);
                    String str = json.replace("\n", "");
                    String str1 = json.replace("\"roles\" : [ ],", "");

                    // convert JSON array to List of objects
                    switch (type) {
                        case ("restaurants"):
                            restaurantsList = Arrays.asList(mapper.readValue(str, Restaurant[].class));
                            restaurantsList.forEach(x -> System.out.println(x.getId() + ": " + x.getName()));
                            getRandomPicture();
                            for (int i = 0; i < restaurantsList.size(); i++) restaurantsList.get(i).setLogoId(idPicture.get(i));
                            loadRestaurants.callBack();
                            break;
                        case ("restaurantMenu"):
                            restaurantListsMenu = Arrays.asList(mapper.readValue(str, RestaurantMenu[].class));
                            restaurantListsMenu.forEach(x -> System.out.println(x.getTitle() + ": " + x.getPrice()));
                            restaurantListsMenu.forEach(x -> x.setLogoId(idPicture.get(x.getLogoId() - 1)));
                            loadRestaurantsMenu.callBack();
                            break;
                        case ("addToCart"):
                            mainActivity.runOnUiThread(() -> mainActivity.serviceMessage("Добавлено"));
                            break;
                        case ("requestCart"):
                            CartFragment.cartFilesList.clear();
                            CartFragment.cartFilesList = Arrays.asList(mapper.readValue(str, Order[].class));
                            CartFragment.cartFilesList.forEach(x -> System.out.println(x.getProductTitle() + " " + x.getPrice()));
                            reloadCartTable.callBack();
                            break;
                        case ("getOrder"):
                            CartFragment.cartFilesList = Arrays.asList(mapper.readValue(str, Order[].class));
                            CartFragment.cartFilesList.forEach(x -> System.out.println(x.getProductTitle() + " " + x.getPrice()));
                            reloadCartTable.callBack();
                            break;
                        case ("getUser"):
                            ProfileFragment.userProfile = Arrays.asList(mapper.readValue(str1, User.class));
                            setProfile.callBack();
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

    private void getRandomPicture() {
        idPicture = new ArrayList<>();
        idPicture.add(R.drawable.hardrock);
        idPicture.add(R.drawable.mcdonalds);
        idPicture.add(R.drawable.pablochef);
        idPicture.add(R.drawable.rootslogo);
        idPicture.add(R.drawable.roppolos);
        idPicture.add(R.drawable.tacobell);

        final Random rand = new Random();
        final int rndInt = rand.nextInt(idPicture.size());
    }

}
