package pl.temomuko.bootcamprxjava.remote;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAPIManager {
    private static UserAPIManager instance;
    private final String BASE_URL = "https://randomuser.me/";
    private UserService service;

    private UserAPIManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(UserService.class);
    }

    public static UserAPIManager getInstance() {
        if (instance == null) {
            instance = new UserAPIManager();
        }
        return instance;
    }

    public UserService getService() {
        return service;
    }
}