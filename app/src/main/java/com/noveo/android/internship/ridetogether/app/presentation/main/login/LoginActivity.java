package com.noveo.android.internship.ridetogether.app.presentation.main.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import com.noveo.android.internship.ridetogether.app.R;
import com.noveo.android.internship.ridetogether.app.model.response.User;
import com.noveo.android.internship.ridetogether.app.presentation.common.BaseViewActivity;
import com.noveo.android.internship.ridetogether.app.presentation.main.home.SplashScreenActivity;
import com.noveo.android.internship.ridetogether.app.utils.LoginUtil;

public class LoginActivity extends BaseViewActivity implements LoginView {
    @Bind(R.id.username)
    EditText username;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.activity_login)
    View content;

    private LoginPresenter loginPresenter = new LoginPresenter();

    @OnClick(R.id.signin)
    public void signin() {
        if (TextUtils.isEmpty(username.getText()) || TextUtils.isEmpty(password.getText())) {
            return;
        }
        loginPresenter.loginUser(username.getText().toString(), password.getText().toString());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attachPresenter();
        setContentView(R.layout.activity_login);
    }

    @Override
    public void attachPresenter() {
        loginPresenter.attachView(this);
    }

    @Override
    public void detachPresenter() {
        loginPresenter.detachView();
    }

    @Override
    public void showEvents() {
        startActivity(new Intent(this, SplashScreenActivity.class));
    }

    @Override
    public void onResult(User user) {
        if (user == null) {
            Snackbar snackbar = Snackbar.make(content, "Invalid username or password", Snackbar.LENGTH_SHORT);
            ((TextView) snackbar.getView().findViewById(R.id.snackbar_text)).setGravity(Gravity.CENTER_HORIZONTAL);
            snackbar.show();
        } else {
            LoginUtil.storeCredentials(this, username.getText().toString(), password.getText().toString());
            loginPresenter.loadImage(this, user.getImagePath());
            ProgressDialog.show(this, "", "");
        }
    }


}
