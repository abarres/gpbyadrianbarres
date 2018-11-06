package adrianbarres.geniusplaza.com.controller;

import adrianbarres.geniusplaza.com.utils.APIInterface;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class UserController {

    private static String baseUrl = "https://reqres.in/";

    public static APIInterface getUserManagerService(Converter.Factory converterFactory)
    {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        retrofitBuilder.baseUrl(baseUrl);

        if(converterFactory != null ) {
            retrofitBuilder.addConverterFactory(converterFactory);
        }

        Retrofit retrofit = retrofitBuilder.build();

        return retrofit.create(APIInterface.class);
    }
}
