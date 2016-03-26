package com.noveo.android.internship.ridetogether.app.ui.view.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.noveo.android.internship.ridetogether.app.R;


public class CommentViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.comment_creator)
    public TextView creator;
    @Bind(R.id.comment_date)
    public TextView date;
    @Bind(R.id.comment_text)
    public TextView text;
    @Bind(R.id.comment_creator_avatar)
    public ImageView avatar;

    public CommentViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
