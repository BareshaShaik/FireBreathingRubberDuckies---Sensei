package hr.foi.fbrd.sensei.network;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class ApiManager {

    public static final String API_ENDPOINT = "http://cambridgetest.azurewebsites.net/";

    private ApiService service;
    private static ApiManager instance;

    public synchronized static ApiManager getInstance() {
        if (instance == null) {
            instance = new ApiManager();
        }
        return instance;
    }

    public void setup() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .client(addClient());

        service = builder.build().create(ApiService.class);
    }

    private OkHttpClient addClient() {
        OkHttpClient client = new OkHttpClient();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.interceptors().add(interceptor);
        client.setReadTimeout(60, TimeUnit.SECONDS);
        return client;
    }

    public ApiService getService() {
        return service;
    }

}
