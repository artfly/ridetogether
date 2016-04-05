package com.noveo.android.internship.ridetogether.app.view.viewgroup.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.noveo.android.internship.ridetogether.app.R;

public class ItemsClickSupport {
    private final RecyclerView recyclerView;
    private OnItemClickListener listener;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (listener != null) {
                RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(v);
                listener.onItemClicked(recyclerView, holder.getAdapterPosition(), v);
            }
        }
    };

    private RecyclerView.OnChildAttachStateChangeListener attachListener =
            new RecyclerView.OnChildAttachStateChangeListener() {
                @Override
                public void onChildViewAttachedToWindow(View view) {
                    if(listener != null) {
                        view.setOnClickListener(onClickListener);
                    }
                }

                @Override
                public void onChildViewDetachedFromWindow(View view) {
                    if (listener != null) {
                        view.setOnClickListener(onClickListener);
                    }
                }
            };

    private ItemsClickSupport(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        recyclerView.setTag(R.id.item_click_support, this);
        recyclerView.addOnChildAttachStateChangeListener(attachListener);
    }

    public static ItemsClickSupport addTo(RecyclerView view) {
        ItemsClickSupport support = (ItemsClickSupport) view.getTag(R.id.item_click_support);
        if (support == null) {
            support = new ItemsClickSupport(view);
        }
        return support;
    }

    public static ItemsClickSupport removeFrom(RecyclerView view) {
        ItemsClickSupport support = (ItemsClickSupport) view.getTag(R.id.item_click_support);
        if (support != null) {
            support.detach(view);
        }
        return support;
    }

    public ItemsClickSupport setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
        return this;
    }


    private void detach(RecyclerView view) {
        view.removeOnChildAttachStateChangeListener(attachListener);
        view.setTag(R.id.item_click_support, null);
    }

    public interface OnItemClickListener {
        void onItemClicked(RecyclerView recyclerView, int position, View v);
    }
}
