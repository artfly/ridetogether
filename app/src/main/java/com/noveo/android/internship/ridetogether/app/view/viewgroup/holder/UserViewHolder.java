package com.noveo.android.internship.ridetogether.app.view.viewgroup.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.noveo.android.internship.ridetogether.app.R;

public class UserViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.username)
    public TextView username;
    @Bind(R.id.avatar)
    public ImageView avatar;

    public UserViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}