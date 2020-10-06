package ru.anatomica.cookstarter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import cz.msebera.android.httpclient.Header;
import ru.anatomica.cookstarter.entity.*;

public class HttpClients {

    private MainActivity mainActivity;
    private List<Integer> idPicture;

    private static Callback loadRestaurants;
    private static Callback loadRestaurantsDescription;
    private static Callback loadRestaurantsMenu;

    public static List<Restaurant> restaurantsList = new ArrayList<>();
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

    public void getRequest(String type, String name) {
        String restaurants = "https://my-json-server.typicode.com/anatomica/CookStarter_Android/restaurants";
        String restaurant = "https://my-json-server.typicode.com/anatomica/CookStarter_Android/restaurant/";
        String menu = "https://my-json-server.typicode.com/anatomica/CookStarter_Android/menu";

        String request = null;
        if (type.equals("restaurants")) request = restaurants;
        if (type.equals("restaurant")) request = restaurant + name;
        if (type.equals("menu")) request = menu + name;

        AsyncHttpClient client = new AsyncHttpClient();
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
                    String str1 = json.replace("\n", "");

                    // convert JSON array to List of objects
                    if (type.equals("restaurants")) {
                        restaurantsList = Arrays.asList(mapper.readValue(str1, Restaurant[].class));
                        restaurantsList.forEach(x -> System.out.println(x.getId() + ": " + x.getName()));
                        restaurantsList.forEach(x -> x.setAddress("ул. Ленина д.1"));
                        getRandomPicture();
                        for (int i = 0; i < restaurantsList.size(); i++) restaurantsList.get(i).setLogo(idPicture.get(i));
                        loadRestaurants.callBack();
                    }
                    if (type.equals("restaurant")) {
                        restaurantListsDescription = Arrays.asList(mapper.readValue(str1, RestaurantDescription.class));
                        restaurantListsDescription.forEach(x -> System.out.println(x.getId() + ": " + x.getDescription()));
                        restaurantsList.forEach(x -> {
                            if (x.getName().equals(restaurantListsDescription.get(0).getId())) {
                                restaurantListsDescription.get(0).setLogo(x.getLogo());
                            }
                        });
                        loadRestaurantsDescription.callBack();
                    }
                    if (type.equals("menu")) {
                        restaurantListsMenu = Arrays.asList(mapper.readValue(str1, RestaurantMenu[].class));
                        restaurantListsMenu.forEach(x -> System.out.println(x.getName() + ": " + x.getPrize()));
                        restaurantListsMenu.forEach(x -> x.setPrize(x.getPrize() + " руб."));
                        restaurantListsMenu.forEach(x -> x.setLogo(restaurantListsDescription.get(0).getLogo()));
                        loadRestaurantsMenu.callBack();
                    }
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

    private void getRandomPicture() {
        idPicture = new ArrayList<>();
        idPicture.add(R.drawable.roppolos);
        idPicture.add(R.drawable.pablochef);
        idPicture.add(R.drawable.mcdonalds);
        idPicture.add(R.drawable.hardrock);
        idPicture.add(R.drawable.rootslogo);
        idPicture.add(R.drawable.tacobell);

        final Random rand = new Random();
        final int rndInt = rand.nextInt(idPicture.size());
    }
}
