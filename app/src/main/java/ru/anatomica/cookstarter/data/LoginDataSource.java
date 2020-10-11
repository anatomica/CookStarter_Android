package ru.anatomica.cookstarter.data;

import ru.anatomica.cookstarter.data.model.LoggedInUser;
import ru.anatomica.cookstarter.ui.login.LoginActivity;
import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource extends LoginActivity {

    public static int success = 0;

    public Result<LoggedInUser> login(String username, String password) {
        try {
            LoggedInUser user = new LoggedInUser(java.util.UUID.randomUUID().toString(),"Test User!");
            if (success == 1) return new Result.Success<>(user);
            else return new Result.Error(new IOException("Error logging in"));
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}