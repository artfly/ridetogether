package com.noveo.android.internship.ridetogether.app.view.viewgroup.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.noveo.android.internship.ridetogether.app.R;
import com.noveo.android.internship.ridetogether.app.model.response.event.Event;
import com.noveo.android.internship.ridetogether.app.model.response.event.EventBased;
import com.noveo.android.internship.ridetogether.app.model.response.event.EventBased.EventViewType;
import com.noveo.android.internship.ridetogether.app.model.response.event.User;
import com.noveo.android.internship.ridetogether.app.view.viewgroup.Section;
import com.noveo.android.internship.ridetogether.app.view.viewgroup.holder.ErrorViewHolder;
import com.noveo.android.internship.ridetogether.app.view.viewgroup.holder.EventViewHolder;
import com.noveo.android.internship.ridetogether.app.view.viewgroup.holder.SectionViewHolder;
import com.noveo.android.internship.ridetogether.app.view.viewgroup.holder.UserViewHolder;
import com.noveo.android.internship.ridetogether.app.utils.EventUtil;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<EventBased> items;
    private Context context;

    public EventAdapter(List<EventBased> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getViewType().ordinal();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (EventViewType.values()[viewType]) {
            case EVENT:
                View eventView = inflater.inflate(R.layout.list_item_event, viewGroup, false);
                viewHolder = new EventViewHolder(eventView);
                break;
            case SECTION:
                View sectionView = inflater.inflate(R.layout.list_item_section, viewGroup, false);
                viewHolder = new SectionViewHolder(sectionView);
                break;
            case USER:
                View userView = inflater.inflate(R.layout.list_item_user, viewGroup, false);
                viewHolder = new UserViewHolder(userView);
                break;
            default:
                View errorView = inflater.inflate(R.layout.list_item_error, viewGroup, false);
                viewHolder = new ErrorViewHolder(errorView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (EventViewType.values()[viewHolder.getItemViewType()]) {
            case EVENT:
                EventViewHolder eventViewHolder = (EventViewHolder) viewHolder;
                configureEventViewHolder(eventViewHolder, position);
                break;
            case SECTION:
                SectionViewHolder sectionViewHolder = (SectionViewHolder) viewHolder;
                configureSectionViewHolder(sectionViewHolder, position);
                break;
            case USER:
                UserViewHolder userViewHolder = (UserViewHolder) viewHolder;
                configureUserViewHolder(userViewHolder, position);
                break;
        }
    }

    private void configureSectionViewHolder(SectionViewHolder holder, int position) {
        Section section = (Section) items.get(position);
        if (section != null) {
            holder.title.setText(section.title);
        }
    }

    private void configureUserViewHolder(final UserViewHolder holder, int position) {
        User user = (User) items.get(position);
        if (user != null) {
            holder.username.setText(user.getUsername());
            Glide.with(context)
                    .load(user.getImagePath())
                    .asBitmap()
                    .centerCrop()
                    .into(new BitmapImageViewTarget(holder.avatar) {
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circular =
                                    RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                            circular.setCircular(true);
                            holder.avatar.setImageDrawable(circular);
                        }
                    });
        }
    }

    private void configureEventViewHolder(EventViewHolder holder, int position) {
        Event event = (Event) items.get(position);
        if (event != null) {
            holder.eventDateView.setText(EventUtil.dateToString(event.getDate()));
            holder.eventDescView.setText(event.getDescription());
            holder.eventTitleView.setText(event.getTitle());
            if (EventUtil.isSubscribed(event.getParticipants(), event.getSubscribers())) {
                holder.subscribeButton.setText(context.getString(R.string.unsubscribe));
            } else {
                holder.subscribeButton.setText(context.getString(R.string.subscribe));
            }
        }
    }

}
