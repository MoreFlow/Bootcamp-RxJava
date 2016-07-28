package pl.temomuko.bootcamprxjava.remote;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CountryAPIManager {
    private static CountryAPIManager instance;
    private final String BASE_URL = "https://randomuser.me/";
    private CountryService service;

    private CountryAPIManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(CountryService.class);
    }

    public static CountryAPIManager getInstance() {
        if (instance == null) {
            instance = new CountryAPIManager();
        }
        return instance;
    }

    public CountryService getService() {
        return service;
    }
}