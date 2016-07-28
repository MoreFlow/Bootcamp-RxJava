package pl.temomuko.bootcamprxjava.remote;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIManager {
    private String baseUrl;
    private static APIManager instance;
    private UserService service;

    private APIManager(String baseUrl) {
        this.baseUrl = baseUrl;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(UserService.class);
    }

    public static APIManager getInstance(String baseUrl) {
        if (instance == null) {
            instance = new APIManager(baseUrl);
        }
        return instance;
    }

    public UserService getService() {
        return service;
    }
}