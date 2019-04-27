package in.fusionbit.shreejeeseizingapp.api;

import java.util.List;

import in.fusionbit.shreejeeseizingapp.apimodels.ImageResponse;
import in.fusionbit.shreejeeseizingapp.apimodels.UserModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {

    interface User {

        @FormUrlEncoded
        @POST("webservice/webservice.php")
        Call<UserModel> tryLogin(@Field("method") String method, @Field("username") String username,
                                 @Field("password") String password);

        @Multipart
        @POST("webservice/uploadImage.php")
        Call<ImageResponse> uploadImage(@Part MultipartBody.Part image);

    }

}
