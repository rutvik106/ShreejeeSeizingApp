package in.fusionbit.shreejeeseizingapp.api;

import java.util.List;

import in.fusionbit.shreejeeseizingapp.apimodels.FormResponse;
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

        //insert_seizing_details
        @FormUrlEncoded
        @POST("webservice/webservice.php")
        Call<FormResponse> submitSeizingForm(@Field("method") String method,
                                             @Field("session_id") String sessionId,
                                             @Field("vd_no") String vdNo,
                                             @Field("customer_name") String customerName,
                                             @Field("vehicle_model") String vehicleModel,
                                             @Field("seizing_date") String seizingDate,
                                             @Field("seizer_name") String seizerName,
                                             @Field("vehicle_location") String vehicleLocation,
                                             @Field("remarks") String remarks,
                                             @Field("rc_book_image") String rcBookImage,
                                             @Field("vehicle_image") String vehicleImage);

    }

}
