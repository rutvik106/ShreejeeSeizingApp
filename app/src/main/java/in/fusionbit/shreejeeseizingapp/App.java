package in.fusionbit.shreejeeseizingapp;

import android.app.Application;

import in.fusionbit.shreejeeseizingapp.apimodels.UserModel;

public class App extends Application {

    public static final String APP_TAG = "SEIZING_APP ";

    private static UserModel currentUser;

    public static UserModel getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(UserModel currentUser) {
        App.currentUser = currentUser;
    }

}
