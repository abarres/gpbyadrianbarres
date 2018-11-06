package adrianbarres.geniusplaza.com.utils;

import adrianbarres.geniusplaza.com.models.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIInterface {

    @GET("api/users")
    Call<User> getUserAllList();

    @FormUrlEncoded
    @POST("api/users")
    Call<ResponseBody> addUser(@Field("name") String userName,
                               @Field("lastName") String userLastName,
                               @Field("avatar") String avatar);
}
