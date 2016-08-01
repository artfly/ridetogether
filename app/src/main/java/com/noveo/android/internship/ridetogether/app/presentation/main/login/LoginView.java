package com.noveo.android.internship.ridetogether.app.presentation.main.login;

import com.noveo.android.internship.ridetogether.app.model.response.User;

public interface LoginView {
    void onResult(User user);

    void showEvents();
}
