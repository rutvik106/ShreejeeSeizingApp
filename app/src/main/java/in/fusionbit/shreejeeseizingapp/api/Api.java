package in.fusionbit.shreejeeseizingapp.api;

import java.util.ArrayList;

import in.fusionbit.shreejeeseizingapp.App;
import in.fusionbit.shreejeeseizingapp.apimodels.FormResponse;
import in.fusionbit.shreejeeseizingapp.apimodels.ImageResponse;
import in.fusionbit.shreejeeseizingapp.apimodels.Report;
import in.fusionbit.shreejeeseizingapp.apimodels.UserModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class Api {

    public static class User {
        private static ApiInterface.User user =
                ApiClient.getClient().create(ApiInterface.User.class);

        public static Call<UserModel> tryLogin(final String username, final String password, final Callback<UserModel> callback) {
            Call<UserModel> call = user.tryLogin("try_login", username, password);
            call.enqueue(callback);
            return call;
        }

        public static Call<ImageResponse> uploadImage(final MultipartBody.Part image,
                                                      final Callback<ImageResponse> callback) {
            Call<ImageResponse> call = user.uploadImage(image);
            call.enqueue(callback);
            return call;
        }

        public static Call<FormResponse> submitSeizingForm(String sessionId,
                                                           String vehicleAcNo,
                                                           String customerName,
                                                           String vehicleModel,
                                                           String seizingDate,
                                                           String seizerName,
                                                           String vehicleLocation,
                                                           String remarks,
                                                           String rcBookImage,
                                                           String vehicleImage,
                                                           String rcBookImage2,
                                                           String vehicleImage2,
                                                           final Callback<FormResponse> callback) {
            Call<FormResponse> call = user.submitSeizingForm("insert_seizing_details",
                    sessionId,
                    vehicleAcNo, customerName, vehicleModel, seizingDate, seizerName, vehicleLocation,
                    remarks, rcBookImage, vehicleImage,rcBookImage2,vehicleImage2);
            call.enqueue(callback);
            return call;
        }

        public static Call<ArrayList<Report>> generateReport(String sessionId,
                                                  String fromDate,
                                                  String toDate,
                                                  final Callback<ArrayList<Report>> callback) {
            Call<ArrayList<Report>> call = user.generateReport("seizing_report", fromDate, toDate, sessionId);
            call.enqueue(callback);
            return call;
        }

    }


}
