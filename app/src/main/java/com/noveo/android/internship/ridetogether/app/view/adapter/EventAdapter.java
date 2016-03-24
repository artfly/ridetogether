package com.noveo.android.internship.ridetogether.app.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.noveo.android.internship.ridetogether.app.R;
import com.noveo.android.internship.ridetogether.app.model.Section;
import com.noveo.android.internship.ridetogether.app.rest.model.Event;
import com.noveo.android.internship.ridetogether.app.rest.model.User;
import com.noveo.android.internship.ridetogether.app.utils.EventUtil;
import com.noveo.android.internship.ridetogether.app.view.viewholder.EventViewHolder;
import com.noveo.android.internship.ridetogether.app.view.viewholder.SectionViewHolder;
import com.noveo.android.internship.ridetogether.app.view.viewholder.UserViewHolder;

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

    private void configureUserViewHolder(UserViewHolder holder, int position) {
        User user = (User) items.get(position);
        if (user != null) {
            holder.username.setText(user.getUsername());
            //TODO
//            Glide.with(context).load(R.drawable.ic_circle).into(holder.avatar);
        }
    }

    private void configureEventViewHolder(EventViewHolder holder, int position) {
        Event event = (Event) items.get(position);
        if (event != null) {
            holder.eventDateView.setText(event.getDate().toString());
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
