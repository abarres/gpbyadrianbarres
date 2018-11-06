package adrianbarres.geniusplaza.com.utils;

import adrianbarres.geniusplaza.com.models.User;
import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {

    @GET("api/users")
    Call<User> getUserAllList();
}
