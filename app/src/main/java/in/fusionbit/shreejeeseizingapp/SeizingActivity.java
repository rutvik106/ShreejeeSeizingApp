package in.fusionbit.shreejeeseizingapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.fusionbit.shreejeeseizingapp.api.Api;
import in.fusionbit.shreejeeseizingapp.apimodels.ImageResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeizingActivity extends BaseActivity {


    @BindView(R.id.et_rcUploaded)
    TextInputEditText etRcUploaded;
    @BindView(R.id.et_bikePicUploaded)
    TextInputEditText etBikePicUploaded;
    private Boolean isBikePicSelected = false;
    private Call<ImageResponse> uploadRcImage;
    private Call<ImageResponse> uploadVehicleImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Seize Vehicle");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ButterKnife.bind(this);


        etBikePicUploaded.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean inFocus) {
                if (inFocus) {
                    final String image = etBikePicUploaded.getText().toString();
                    if (image.contains("jpg")) {
                        ViewImageActivity.start(SeizingActivity.this, image);
                    } else if (image.isEmpty()) {
                        openBikeImagePicker();
                    } else {
                        Toast.makeText(SeizingActivity.this, "Image not uploaded", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        etBikePicUploaded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String image = etBikePicUploaded.getText().toString();
                if (image.contains("jpg")) {
                    ViewImageActivity.start(SeizingActivity.this, image);
                } else if (image.isEmpty()) {
                    openBikeImagePicker();
                } else {
                    Toast.makeText(SeizingActivity.this, "Image not uploaded", Toast.LENGTH_SHORT).show();
                }
            }
        });


        etRcUploaded.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean inFocus) {
                if (inFocus) {
                    final String image = etRcUploaded.getText().toString();
                    if (image.contains("jpg")) {
                        ViewImageActivity.start(SeizingActivity.this, image);
                    } else if (image.isEmpty()) {
                        openRcBookImagePicker();
                    } else {
                        Toast.makeText(SeizingActivity.this, "Image not uploaded", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        etRcUploaded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String image = etRcUploaded.getText().toString();
                if (image.contains("jpg")) {
                    ViewImageActivity.start(SeizingActivity.this, image);
                } else if (image.isEmpty()) {
                    openRcBookImagePicker();
                } else {
                    Toast.makeText(SeizingActivity.this, "Image not uploaded", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        confirmExit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            confirmExit();
        }
        return super.onOptionsItemSelected(item);
    }

    private void confirmExit() {
        new AlertDialog.Builder(this)
                .setTitle("Go Back")
                .setMessage("Are you sure you want to go back? All the changes will be lost.")
                .setPositiveButton("Go Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SeizingActivity.this.finish();
                    }
                })
                .setNegativeButton("Dismiss", null)
                .show();
    }

    @Override
    int getLayoutResourceId() {
        return R.layout.activity_sezing;
    }

    public static void start(final Context context) {
        final Intent intent = new Intent(context, SeizingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            // Get a list of picked images
            List<Image> images = ImagePicker.getImages(data);
            // or get a single image only
            Image image = ImagePicker.getFirstImageOrNull(data);

            if (isBikePicSelected) {
                uploadBikePic(image.getPath());
            } else {
                uploadRcBookPic(image.getPath());
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadRcBookPic(final String path) {
        etRcUploaded.setText("Uploading...");

        File file = new File(path);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/jpeg"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("seizing_image", file.getName(), requestFile);

        cancelRcUploading();
        uploadRcImage = Api.User.uploadImage(body, new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            etRcUploaded.setText(response.body().getImage_name());
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            etRcUploaded.setText("(" + response.code() + ") " + response.message());
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        etRcUploaded.setText(t.getMessage());
                    }
                });
            }
        });

    }

    private void cancelRcUploading() {
        if (uploadRcImage != null) {
            if (!uploadRcImage.isCanceled()) {
                uploadRcImage.cancel();
            }
            uploadRcImage = null;
        }
    }

    private void uploadBikePic(final String path) {
        etBikePicUploaded.setText("Uploading...");

        File file = new File(path);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/jpeg"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("seizing_image", file.getName(), requestFile);

        cancelVehicleImageUpload();
        uploadVehicleImage = Api.User.uploadImage(body, new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            etBikePicUploaded.setText(response.body().getImage_name());
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            etBikePicUploaded.setText("(" + response.code() + ") " + response.message());
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        etBikePicUploaded.setText(t.getMessage());
                    }
                });
            }
        });

    }

    private void cancelVehicleImageUpload() {
        if (uploadVehicleImage != null) {
            if (!uploadVehicleImage.isCanceled()) {
                uploadVehicleImage.cancel();
            }
            uploadVehicleImage = null;
        }
    }

    @OnClick({R.id.ib_uploadRc, R.id.ib_uploadBikePic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_uploadRc:
                openRcBookImagePicker();
                break;
            case R.id.ib_uploadBikePic:
                openBikeImagePicker();
                break;
        }
    }

    private void openBikeImagePicker() {
        isBikePicSelected = true;
        ImagePicker.create(this)
                .imageDirectory("shreejeeseizingapp")
                .single()
                .start();
    }

    private void openRcBookImagePicker() {
        isBikePicSelected = false;
        ImagePicker.create(this)
                .imageDirectory("shreejeeseizingapp")
                .single()
                .start();
    }
}
