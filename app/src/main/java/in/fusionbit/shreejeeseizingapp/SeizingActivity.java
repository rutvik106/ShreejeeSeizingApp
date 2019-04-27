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

import java.io.ByteArrayInputStream;
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
import okhttp3.ResponseBody;
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
    private Boolean isBikePicSelected = false;
    private Call<ImageResponse> uploadRcImage;
    private Call<ImageResponse> uploadVehicleImage;
    private Call<FormResponse> formUploadCall;

    private Calendar formDate = Calendar.getInstance();
    private DatePickerDialog datePickerFormDate;

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


    }

    private void setupDatePickerListeners() {

        etDate.setFocusable(false);
        etDate.setFocusableInTouchMode(false);

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
    }

    private void cancelFormUploadCall() {

        if (formUploadCall != null) {
            if (!formUploadCall.isCanceled()) {
                formUploadCall.cancel();
            }
            formUploadCall = null;
        }

    }
}
