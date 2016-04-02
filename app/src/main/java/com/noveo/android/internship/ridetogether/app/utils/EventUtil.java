package com.noveo.android.internship.ridetogether.app.utils;

import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateFormat;
import com.noveo.android.internship.ridetogether.app.R;
import com.noveo.android.internship.ridetogether.app.model.RideTogetherStub;
import com.noveo.android.internship.ridetogether.app.model.response.event.Event;
import com.noveo.android.internship.ridetogether.app.model.response.event.EventBased;
import com.noveo.android.internship.ridetogether.app.model.response.event.User;
import com.noveo.android.internship.ridetogether.app.ui.view.Section;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public final class EventUtil {
    public static final CharSequence fullDateFormat = "dd.MM.yyyy HH:mm";
    public static final CharSequence dayAndTimeFormat = "EEEE, HH:mm";
    public static final CharSequence timeOnlyFormat = "HH:mm";

    private EventUtil() {
    }

    public static void updateItems(List<EventBased> items, Event event, Context context) {
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
            if (TextUtils.equals(user.getUsername(), RideTogetherStub.username)) {
                return true;
            }
        }
        return false;
    }

    public static CharSequence dateToString(Date date) {
        return DateFormat.format(fullDateFormat, date);
    }

    public static CharSequence dateToTimedString(Date date, Context context) {
        Calendar now = Calendar.getInstance();
        Calendar eventCal = Calendar.getInstance();
        eventCal.setTime(date);

        if ((now.get(Calendar.ERA) != eventCal.get(Calendar.ERA))) {
            return DateFormat.format(fullDateFormat, date);
        }

        if (now.get(Calendar.DAY_OF_YEAR) == eventCal.get(Calendar.DAY_OF_YEAR) &&
                now.get(Calendar.YEAR) == eventCal.get(Calendar.YEAR)) {
            return context.getString(R.string.today) + ", " + DateFormat.format(timeOnlyFormat, date);
        }
        now.add(Calendar.DAY_OF_MONTH, 1);
        if (now.get(Calendar.DAY_OF_YEAR) == eventCal.get(Calendar.DAY_OF_YEAR) &&
                now.get(Calendar.YEAR) == eventCal.get(Calendar.YEAR)) {
            return context.getString(R.string.tomorrow) + ", " + DateFormat.format(timeOnlyFormat, date);
        }
        now.add(Calendar.DAY_OF_MONTH, -1);
        if (now.get(Calendar.YEAR) == eventCal.get(Calendar.YEAR) &&
                now.get(Calendar.WEEK_OF_YEAR) == eventCal.get(Calendar.WEEK_OF_YEAR)) {
            return DateFormat.format(dayAndTimeFormat, date);
        }
        return dateToString(date);
    }

    public static List<Event> getDateInRange(List<Event> events, Range range) {
        if (range == Range.ANY) {
            return events;
        }
        Calendar now = Calendar.getInstance();
        Calendar eventCal = Calendar.getInstance();
        List<Event> eventsInRange = new ArrayList<>();
        for (Event event : events) {
            eventCal.setTime(event.getDate());
            if (now.get(Calendar.ERA) != eventCal.get(Calendar.ERA) ||
                    now.get(Calendar.YEAR) != eventCal.get(Calendar.YEAR)) {
                continue;
            }

            switch (range) {
                case TODAY:
                    if (now.get(Calendar.DAY_OF_YEAR) == eventCal.get(Calendar.DAY_OF_YEAR)) {
                        eventsInRange.add(event);
                    }
                    break;
                case TOMORROW:
                    now.add(Calendar.DAY_OF_MONTH, 1);
                    if (now.get(Calendar.DAY_OF_YEAR) == eventCal.get(Calendar.DAY_OF_YEAR)) {
                        eventsInRange.add(event);
                    }
                    now.add(Calendar.DAY_OF_MONTH, -1);
                    break;
                case WEEK:
                    if (now.get(Calendar.WEEK_OF_YEAR) == eventCal.get(Calendar.WEEK_OF_YEAR)) {
                        eventsInRange.add(event);
                    }
                    break;
                case MONTH:
                    if (now.get(Calendar.MONTH) == eventCal.get(Calendar.MONTH)) {
                        eventsInRange.add(event);
                    }
            }
        }
        return eventsInRange;
    }

    public enum Range {
        TODAY, TOMORROW, WEEK, MONTH, ANY
    }
}
