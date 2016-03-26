package com.noveo.android.internship.ridetogether.app.ui.view.adapter;

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
import com.noveo.android.internship.ridetogether.app.ui.view.Section;
import com.noveo.android.internship.ridetogether.app.model.response.event.Event;
import com.noveo.android.internship.ridetogether.app.model.response.event.User;
import com.noveo.android.internship.ridetogether.app.ui.utils.EventUtil;
import com.noveo.android.internship.ridetogether.app.ui.view.holder.EventViewHolder;
import com.noveo.android.internship.ridetogether.app.ui.view.holder.SectionViewHolder;
import com.noveo.android.internship.ridetogether.app.ui.view.holder.UserViewHolder;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int EVENT_TYPE = 0;
    private final int SECTION_TYPE = 1;
    private final int USER_TYPE = 2;
    private List<Object> items;
    private Context context;

    public EventAdapter(List<Object> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof User) {
            return USER_TYPE;
        } else if (items.get(position) instanceof Event) {
            return EVENT_TYPE;
        } else if (items.get(position) instanceof Section) {
            return SECTION_TYPE;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case EVENT_TYPE:
                View eventView = inflater.inflate(R.layout.list_item_event, viewGroup, false);
                viewHolder = new EventViewHolder(eventView);
                break;
            case SECTION_TYPE:
                View sectionView = inflater.inflate(R.layout.list_item_section, viewGroup, false);
                viewHolder = new SectionViewHolder(sectionView);
                break;
            default:
                View userView = inflater.inflate(R.layout.list_item_user, viewGroup, false);
                viewHolder = new UserViewHolder(userView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case EVENT_TYPE:
                EventViewHolder eventViewHolder = (EventViewHolder) viewHolder;
                configureEventViewHolder(eventViewHolder, position);
                break;
            case SECTION_TYPE:
                SectionViewHolder sectionViewHolder = (SectionViewHolder) viewHolder;
                configureSectionViewHolder(sectionViewHolder, position);
                break;
            default:
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
                        @Override
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
