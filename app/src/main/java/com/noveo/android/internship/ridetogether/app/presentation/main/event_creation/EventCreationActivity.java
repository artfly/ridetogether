package com.noveo.android.internship.ridetogether.app.presentation.main.event_creation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import com.noveo.android.internship.ridetogether.app.R;
import com.noveo.android.internship.ridetogether.app.model.request.RequestEvent;
import com.noveo.android.internship.ridetogether.app.model.request.RequestProperties;
import com.noveo.android.internship.ridetogether.app.model.request.RequestRoute;
import com.noveo.android.internship.ridetogether.app.presentation.common.BaseViewActivity;
import com.noveo.android.internship.ridetogether.app.utils.EventUtil;
import com.noveo.android.internship.ridetogether.app.utils.IntentUtil;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;


public class EventCreationActivity extends BaseViewActivity implements EventCreationView,
        TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private static final String DATE_FORMAT = "yyyyMMdd_HHmm";
    private static final String DATE_PICKER_DIALOG_TAG = "DATE_PICKER_DIALOG_TAG";
    private static final String TIME_PICKER_DIALOG_TAG = "TIME_PICKER_DIALOG_TAG";
    private static final int SELECT_PICTURE = 1;
    private static final String CALENDAR_STATE = "CALENDAR_STATE";

    private final EventCreationPresenter presenter = new EventCreationPresenter();
    @Bind(R.id.activity_event_creation)
    View content;
    @Bind(R.id.toolbar_create_event)
    Toolbar toolbar;
    @Bind(R.id.event_title)
    EditText title;
    @Bind(R.id.event_description)
    EditText description;
    @Bind(R.id.event_date)
    TextView dateView;
    private Calendar calendar = Calendar.getInstance();
    private Uri image = null;

    @OnClick(R.id.event_add_image)
    void selectImage() {
        Intent pickIntent = new Intent(Intent.ACTION_GET_CONTENT);
        pickIntent.setType("image/*");

        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String title = "Select image";
        Intent chooserIntent = Intent.createChooser(takePhotoIntent, title);
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { takePhotoIntent });

        startActivityForResult(pickIntent, SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case SELECT_PICTURE:
                if (data == null) {
                    return;
                }
                image = data.getData();
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.event_date)
    void selectDate() {
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show(getFragmentManager(), DATE_PICKER_DIALOG_TAG);
    }

    private void showRouteCreation() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        if (TextUtils.isEmpty(description.getText()) || TextUtils.isEmpty(title.getText())) {
            Snackbar.make(content, "Some fields are empty", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (calendar.before(Calendar.getInstance())) {
            Snackbar.make(content, "This date has already past!", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (image == null) {
            Snackbar.make(content, "Please select image.", Snackbar.LENGTH_SHORT).show();
            return;
        }
        presenter.loadImage(this, image);

    }

    public void chooseRoute(String imagePath) {
        RequestEvent event = new RequestEvent();
        event.setDate(calendar.getTime().getTime() / 1000);
        event.setDescription(description.getText().toString());
        event.setTitle(title.getText().toString());
        event.setImagePath(imagePath);
        startActivity(IntentUtil.createIntent(this, event));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_event_creation);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState != null) {
            calendar = (Calendar) savedInstanceState.getSerializable(CALENDAR_STATE);
        }

        title.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dateView.setText(EventUtil.dateToString(calendar.getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create_route:
                showRouteCreation();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(CALENDAR_STATE, calendar);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void attachPresenter() {
        presenter.attachView(this);
    }

    @Override
    public void detachPresenter() {
        presenter.detachView();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        dateView.setText(EventUtil.dateToString(calendar.getTime()));

        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                this,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show(getFragmentManager(), TIME_PICKER_DIALOG_TAG);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        dateView.setText(EventUtil.dateToString(calendar.getTime()));
    }
}
