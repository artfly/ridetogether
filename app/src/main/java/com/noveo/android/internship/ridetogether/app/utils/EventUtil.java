package com.noveo.android.internship.ridetogether.app.utils;

import android.content.Context;
import com.noveo.android.internship.ridetogether.app.R;
import com.noveo.android.internship.ridetogether.app.model.Section;
import com.noveo.android.internship.ridetogether.app.rest.model.Event;
import com.noveo.android.internship.ridetogether.app.rest.model.User;
import com.noveo.android.internship.ridetogether.app.stub.RideTogetherStub;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arty on 20.03.16.
 */
public class EventUtil {
    public static void updateItems(List<Object> items, Event event, Context context) {
        items.clear();
        items.add(event);
        items.add(new Section(context.getString(R.string.participants)));
        items.addAll(event.getParticipants());
        items.add(new Section(context.getString(R.string.subscribers)));
        items.addAll(event.getSubscribers());
    }

    public static boolean isSubscribed(List<User> participants, List<User> subscribers) {
        List<User> users = new ArrayList<>(participants);
        users.addAll(subscribers);
        for (User user : users) {
            if (user.getUsername().equals(RideTogetherStub.username)) {
                return true;
            }
        }
        return false;
    }

    public static int removeUser(List<Object> items) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) instanceof User &&
                    ((User) items.get(i)).getUsername().equals(RideTogetherStub.username)) {
                items.remove(i);
                return i;
            }
        }
        return -1;
    }
}
