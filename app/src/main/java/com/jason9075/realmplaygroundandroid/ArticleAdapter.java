package com.jason9075.realmplaygroundandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

import io.realm.RealmResults;

/**
 * Created by jason9075 on 2016/2/16.
 */
public class ArticleAdapter extends BaseAdapter {
    private RealmResults<ArticleData> realmResults;
    private Set<Integer> selectedIndex = new HashSet<>();
    LayoutInflater inflater;

    private OnCustomEventListener onCustomEventListener;

    public ArticleAdapter(Context context, RealmResults<ArticleData> realmResults) {
        this.realmResults = realmResults;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return realmResults.size();
    }

    @Override
    public ArticleData getItem(int position) {
        return realmResults.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_cell_layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ArticleData articleData = getItem(position);

        viewHolder.titleTextview.setText(articleData.getTitle());
        viewHolder.subtitleTextview.setText(articleData.getSubtitle());
        viewHolder.checkBox.setTag(position);
        viewHolder.checkBox.setChecked(selectedIndex.contains(position));

        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int getPosition = (Integer) buttonView.getTag();
                if (onCustomEventListener != null)
                    onCustomEventListener.onCheckboxChanged(realmResults.get(getPosition), isChecked);
                if (isChecked)
                    selectedIndex.add(getPosition);
                else
                    selectedIndex.remove(getPosition);
            }
        });

        return convertView;
    }

    public void clearSelectedIndex() {
        selectedIndex.clear();
    }

    private class ViewHolder {
        TextView titleTextview, subtitleTextview;
        CheckBox checkBox;

        public ViewHolder(View item) {
            titleTextview = (TextView) item.findViewById(R.id.title_textview);
            subtitleTextview = (TextView) item.findViewById(R.id.subtitle_textview);
            checkBox = (CheckBox) item.findViewById(R.id.article_checkbox);
        }
    }

    public interface OnCustomEventListener {
        void onCheckboxChanged(ArticleData articleData, boolean isSelected);
    }

    public void setCustomEventListener(OnCustomEventListener eventListener) {
        onCustomEventListener = eventListener;
    }
}
