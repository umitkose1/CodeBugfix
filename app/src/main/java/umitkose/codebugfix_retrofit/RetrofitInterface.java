package umitkose.codebugfix_retrofit;

import java.util.List;


import retrofit2.Call;
import retrofit2.http.GET;
import umitkose.codebugfix_retrofit.Model.User;


public interface RetrofitInterface {
    @GET("posts")
    Call<List<User>> getUsers();
}
