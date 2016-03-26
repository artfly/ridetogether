package com.noveo.android.internship.ridetogether.app.ui.view.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.noveo.android.internship.ridetogether.app.R;

public class SectionViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.section_title)
    public TextView title;

    public SectionViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}