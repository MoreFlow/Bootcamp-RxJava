package pl.temomuko.bootcamprxjava.remote;

import pl.temomuko.bootcamprxjava.model.CountryResponse;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface CountryService {
    @GET("rest/v1/capital/{capital}")
    Observable<CountryResponse> getCountryByCapital(@Path("capital") String capital);
}
