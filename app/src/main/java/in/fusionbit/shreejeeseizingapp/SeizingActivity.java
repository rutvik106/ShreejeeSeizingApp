package in.fusionbit.shreejeeseizingapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageButton;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.fusionbit.shreejeeseizingapp.api.Api;
import in.fusionbit.shreejeeseizingapp.apimodels.FormResponse;
import in.fusionbit.shreejeeseizingapp.apimodels.ImageResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeizingActivity extends BaseActivity {


    @BindView(R.id.et_rcUploaded)
    TextInputEditText etRcUploaded;
    @BindView(R.id.et_bikePicUploaded)
    TextInputEditText etBikePicUploaded;
    @BindView(R.id.et_vdacno)
    TextInputEditText etVdacno;
    @BindView(R.id.et_model)
    TextInputEditText etModel;
    @BindView(R.id.et_customerName)
    TextInputEditText etCustomerName;
    @BindView(R.id.et_date)
    TextInputEditText etDate;
    @BindView(R.id.et_sizerName)
    TextInputEditText etSizerName;
    @BindView(R.id.et_vehicleLocation)
    TextInputEditText etVehicleLocation;
    @BindView(R.id.et_remarks)
    TextInputEditText etRemarks;
    @BindView(R.id.ib_uploadRc)
    AppCompatImageButton ibUploadRc;
    @BindView(R.id.ib_uploadBikePic)
    AppCompatImageButton ibUploadBikePic;
    @BindView(R.id.et_rcUploaded2)
    TextInputEditText etRcUploaded2;
    @BindView(R.id.ib_uploadRc2)
    AppCompatImageButton ibUploadRc2;
    @BindView(R.id.et_bikePicUploaded2)
    TextInputEditText etBikePicUploaded2;
    @BindView(R.id.ib_uploadBikePic2)
    AppCompatImageButton ibUploadBikePic2;

    private Call<ImageResponse> uploadRcImage;
    private Call<ImageResponse> uploadVehicleImage;
    private Call<ImageResponse> uploadRcImage2;
    private Call<ImageResponse> uploadVehicleImage2;


    private Call<FormResponse> formUploadCall;

    private Calendar formDate = Calendar.getInstance();
    private DatePickerDialog datePickerFormDate;
    private int selectedPic = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Seize Vehicle");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ButterKnife.bind(this);


        setupBikePicUploadListeners();

        setupRcBookPicUploadListeners();

        setupDatePickerListeners();

        setupBikePicUploadListeners2();

        setupRcBookPicUploadListeners2();


    }

    private void setupRcBookPicUploadListeners2() {
        etRcUploaded2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean inFocus) {
                if (inFocus) {
                    final String image = etRcUploaded2.getText().toString();
                    if (image.contains("jpg")) {
                        ViewImageActivity.start(SeizingActivity.this, image);
                    } else if (image.isEmpty()) {
                        openRcBookImagePicker2();
                    } else {
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                        Toast.makeText(SeizingActivity.this, "Image not uploaded", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        etRcUploaded2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String image = etRcUploaded2.getText().toString();
                if (image.contains("jpg")) {
                    ViewImageActivity.start(SeizingActivity.this, image);
                } else if (image.isEmpty()) {
                    openRcBookImagePicker2();
                } else {
                    Toast.makeText(SeizingActivity.this, "Image not uploaded", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openRcBookImagePicker2() {
        selectedPic = 4;
        ImagePicker.create(this)
                .imageDirectory("shreejeeseizingapp")
                .single()
                .start();
    }

    private void openBikeImagePicker2() {
        selectedPic = 3;
        ImagePicker.create(this)
                .imageDirectory("shreejeeseizingapp")
                .single()
                .start();
    }

    private void openBikeImagePicker() {
        selectedPic = 1;
        ImagePicker.create(this)
                .imageDirectory("shreejeeseizingapp")
                .single()
                .start();
    }

    private void openRcBookImagePicker() {
        selectedPic = 2;
        ImagePicker.create(this)
                .imageDirectory("shreejeeseizingapp")
                .single()
                .start();
    }

    private void setupBikePicUploadListeners2() {
        etBikePicUploaded2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean inFocus) {
                if (inFocus) {
                    final String image = etBikePicUploaded2.getText().toString();
                    if (image.contains("jpg")) {
                        ViewImageActivity.start(SeizingActivity.this, image);
                    } else if (image.isEmpty()) {
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                        openBikeImagePicker2();
                    } else {
                        Toast.makeText(SeizingActivity.this, "Image not uploaded", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        etBikePicUploaded2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String image = etBikePicUploaded2.getText().toString();
                if (image.contains("jpg")) {
                    ViewImageActivity.start(SeizingActivity.this, image);
                } else if (image.isEmpty()) {
                    openBikeImagePicker2();
                } else {
                    Toast.makeText(SeizingActivity.this, "Image not uploaded", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupDatePickerListeners() {

        etDate.setText(formDate.get(Calendar.DAY_OF_MONTH) + "/" + (formDate.get(Calendar.MONTH) + 1) + "/" +
                formDate.get(Calendar.YEAR));

        datePickerFormDate = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                formDate.set(Calendar.YEAR, year);
                formDate.set(Calendar.MONTH, monthOfYear);
                formDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                etDate.setText(date);
            }
        }, formDate.get(Calendar.YEAR), formDate.get(Calendar.MONTH),
                formDate.get(Calendar.DAY_OF_MONTH));

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerFormDate.show();
            }
        });

        etDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if (focus) {
                    datePickerFormDate.show();
                }
            }
        });

    }

    private void setupRcBookPicUploadListeners() {
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
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
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

    private void setupBikePicUploadListeners() {
        etBikePicUploaded.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean inFocus) {
                if (inFocus) {
                    final String image = etBikePicUploaded.getText().toString();
                    if (image.contains("jpg")) {
                        ViewImageActivity.start(SeizingActivity.this, image);
                    } else if (image.isEmpty()) {
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
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

            switch (selectedPic) {
                case 1:
                    uploadBikePic(image.getPath());
                    break;
                case 2:
                    uploadRcBookPic(image.getPath());
                    break;
                case 3:
                    uploadBikePic2(image.getPath());
                    break;
                case 4:
                    uploadRcBookPic2(image.getPath());
                    break;
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadRcBookPic(final String path) {
        etRcUploaded.setText("Uploading...");

        cancelRcUploading();
        uploadRcImage = compressImageAndUpload(path, new Callback<ImageResponse>() {
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

    private void uploadRcBookPic2(String path) {
        etRcUploaded2.setText("Uploading...");

        cancelRcUploading2();
        uploadRcImage2 = compressImageAndUpload(path, new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            etRcUploaded2.setText(response.body().getImage_name());
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            etRcUploaded2.setText("(" + response.code() + ") " + response.message());
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        etRcUploaded2.setText(t.getMessage());
                    }
                });
            }
        });
    }

    private void uploadBikePic(final String path) {
        etBikePicUploaded.setText("Uploading...");

        cancelVehicleImageUpload();

        uploadVehicleImage = compressImageAndUpload(path, new Callback<ImageResponse>() {
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

    private void uploadBikePic2(String path) {
        etBikePicUploaded2.setText("Uploading...");

        cancelVehicleImageUpload2();

        uploadVehicleImage2 = compressImageAndUpload(path, new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            etBikePicUploaded2.setText(response.body().getImage_name());
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            etBikePicUploaded2.setText("(" + response.code() + ") " + response.message());
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        etBikePicUploaded2.setText(t.getMessage());
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

    private void cancelRcUploading2() {
        if (uploadRcImage2 != null) {
            if (!uploadRcImage2.isCanceled()) {
                uploadRcImage2.cancel();
            }
            uploadRcImage2 = null;
        }
    }

    private void cancelVehicleImageUpload() {
        if (uploadVehicleImage != null) {
            if (!uploadVehicleImage.isCanceled()) {
                uploadVehicleImage.cancel();
            }
            uploadVehicleImage = null;
        }
    }

    private void cancelVehicleImageUpload2() {
        if (uploadVehicleImage2 != null) {
            if (!uploadVehicleImage2.isCanceled()) {
                uploadVehicleImage2.cancel();
            }
            uploadVehicleImage2 = null;
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

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {

        confirmAndSubmitForm();

    }

    private void confirmAndSubmitForm() {

        new AlertDialog.Builder(this)
                .setTitle("Submit form?")
                .setMessage("Are you sure you want to submit the form?")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (formIsValid()) {
                            submitForm();
                        } else {
                            Toast.makeText(SeizingActivity.this,
                                    "Please input mandatory fieds", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).show();
    }

    private boolean formIsValid() {

        boolean isValid = true;

        if (TextUtils.isEmpty(etVdacno.getText())) {
            etVdacno.setError("Required");
            isValid = false;
        }

        if (TextUtils.isEmpty(etCustomerName.getText())) {
            etCustomerName.setError("Required");
            isValid = false;
        }

        if (TextUtils.isEmpty(etDate.getText())) {
            etDate.setError("Required");
            isValid = false;
        }

        if (TextUtils.isEmpty(etModel.getText())) {
            etModel.setError("Required");
            isValid = false;
        }

        if (TextUtils.isEmpty(etSizerName.getText())) {
            etSizerName.setError("Required");
            isValid = false;
        }

        if (TextUtils.isEmpty(etVehicleLocation.getText())) {
            etVehicleLocation.setError("Required");
            isValid = false;
        }

        return isValid;
    }

    private void submitForm() {

        cancelFormUploadCall();

        showProgressDialog("Submitting Form", "Please wait...");

        formUploadCall = Api.User.submitSeizingForm(App.getCurrentUser().getUser().getSession_id(),
                etVdacno.getText().toString(),
                etCustomerName.getText().toString(),
                etModel.getText().toString(),
                etDate.getText().toString(),
                etSizerName.getText().toString(),
                etVehicleLocation.getText().toString(),
                etRemarks.getText().toString(),
                etRcUploaded.getText().toString(),
                etBikePicUploaded.getText().toString(),
                etRcUploaded2.getText().toString(),
                etBikePicUploaded2.getText().toString(),
                new Callback<FormResponse>() {
                    @Override
                    public void onResponse(Call<FormResponse> call, Response<FormResponse> response) {

                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getError() == 0) {
                                    new AlertDialog.Builder(SeizingActivity.this)
                                            .setTitle("Success")
                                            .setMessage("Form submitted successfully")
                                            .setPositiveButton("Ok", null)
                                            .show();

                                    Toast.makeText(SeizingActivity.this, "Form submitted successfully", Toast.LENGTH_SHORT).show();

                                    clearForm();
                                } else {
                                    Toast.makeText(SeizingActivity.this, "Failed to submit form", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(SeizingActivity.this, "Response is null", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SeizingActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                        }

                        hideProgressDialog();

                    }

                    @Override
                    public void onFailure(Call<FormResponse> call, Throwable t) {
                        hideProgressDialog();
                        Toast.makeText(SeizingActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void clearForm() {

        etDate.setText(formDate.get(Calendar.DAY_OF_MONTH) + "/" + (formDate.get(Calendar.MONTH) + 1) + "/" +
                formDate.get(Calendar.YEAR));

        etVdacno.setText("");
        etVehicleLocation.setText("");
        etModel.setText("");
        etSizerName.setText("");
        etCustomerName.setText("");
        etRcUploaded.setText("");
        etBikePicUploaded.setText("");
        etRemarks.setText("");
        etRcUploaded2.setText("");
        etBikePicUploaded2.setText("");
    }

    private void cancelFormUploadCall() {

        if (formUploadCall != null) {
            if (!formUploadCall.isCanceled()) {
                formUploadCall.cancel();
            }
            formUploadCall = null;
        }

    }

    @OnClick(R.id.ib_uploadRc2)
    public void onIbUploadRc2Clicked() {
        openRcBookImagePicker2();
    }

    @OnClick(R.id.ib_uploadBikePic2)
    public void onIbUploadBikePic2Clicked() {
        openBikeImagePicker2();
    }

    private Call<ImageResponse> compressImageAndUpload(String path, Callback<ImageResponse> callback) {
        File file = new File(path);

        FileInputStream fis = null;
        File compressedFile = null;
        try {
            fis = new FileInputStream(file);

            if (fis != null) {
                Bitmap original = BitmapFactory.decodeStream(fis);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                original.compress(Bitmap.CompressFormat.JPEG, 40, out);

                compressedFile = new File(SeizingActivity.this.getCacheDir(), "image_compressed");
                compressedFile.createNewFile();

                //Convert bitmap to byte array
                byte[] bitmapdata = out.toByteArray();

                //write the bytes in file
                FileOutputStream fos = new FileOutputStream(compressedFile);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (compressedFile != null) {
            Toast.makeText(this, "File was compressed", Toast.LENGTH_SHORT).show();
        }

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/jpeg"), compressedFile != null ? compressedFile : file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("seizing_image", file.getName(), requestFile);


        return Api.User.uploadImage(body, callback);
    }
}
