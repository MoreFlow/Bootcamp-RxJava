package pl.temomuko.bootcamprxjava.remote;

import pl.temomuko.bootcamprxjava.model.UserResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface UserService {
    @GET("api/")
    Observable<UserResponse> getUsers(@Query("results") int results);
}
