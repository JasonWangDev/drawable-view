package com.github.jasonwangdev.drawableview.demo.baseadapter_realm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.jasonwangdev.drawableview.demo.R;

import io.realm.RealmResults;

/**
 * Created by Jason on 2017/7/8.
 */

public class PersonAdapterRealm extends FilterableBaseAdapterRealm<PersonRealm> {

    private static class ViewHolder {
        private TextView tvId;
        private TextView tvName;
    }


    public PersonAdapterRealm(RealmResults persons) {
        super(persons);

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView)
        {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person, null);

            holder = new ViewHolder();
            holder.tvId = (TextView) convertView.findViewById(R.id.id);
            holder.tvName = (TextView) convertView.findViewById(R.id.name);

            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder) convertView.getTag();

        PersonRealm person = getItem(position);
        if (null != person)
        {
            holder.tvId.setText(String.valueOf(person.getId()));
            holder.tvName.setText(String.valueOf(person.getName()));
        }

        return convertView;
    }


    @Override
    protected CharSequence getItemName(PersonRealm itemObject) {
        return itemObject.getName();
    }

    @Override
    protected String getFilterFieldName() {
        return "name";
    }

}
