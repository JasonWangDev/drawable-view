package com.github.jasonwangdev.drawableview.demo.baseadpter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.github.jasonwangdev.drawableview.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 2017/7/8.
 */

public class PersonAdapter extends BaseAdapter implements Filterable {

    private static class ViewHolder {
        private TextView tvId;
        private TextView tvName;
    }


    private class PersonFilter extends Filter {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Person) resultValue).getName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (null != constraint)
            {
                personsSuggest.clear();
                for (int i = 0, count = personsOrigin.size(); i < count ; i++)
                {
                    Person person = personsOrigin.get(i);
                    if (person.getName().startsWith((String) constraint))
                        personsSuggest.add(person);
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = personsSuggest;
                filterResults.count = personsSuggest.size();

                return filterResults;
            }
            else
                return new FilterResults();
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            personsFilter.clear();
            if (null != results && results.count > 0)
            {
                List<?> result = (List<?>) results.values;
                for (int i = 0, count = result.size() ; i < count ; i++)
                {
                    Object o = result.get(i);
                    if (o instanceof Person)
                        personsFilter.add((Person) o);
                }
            }
            else if (null == constraint)
                personsFilter.addAll(personsOrigin);

            notifyDataSetChanged();
        }
    }


    private List<Person> personsOrigin;
    private List<Person> personsFilter;
    private List<Person> personsSuggest;

    private Filter filter;


    public PersonAdapter(List<Person> persons) {
        this.personsOrigin = persons;
        this.personsFilter = new ArrayList<>(persons);
        this.personsSuggest = new ArrayList<>(0);
    }


    @Override
    public int getCount() {
        return null != personsFilter ? personsFilter.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return personsFilter.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
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

        Person person = personsFilter.get(position);
        if (null != person)
        {
            holder.tvId.setText(String.valueOf(person.getId()));
            holder.tvName.setText(String.valueOf(person.getName()));
        }

        return convertView;
    }


    @Override
    public Filter getFilter() {
        if (null == filter)
            filter = new PersonFilter();

        return filter;
    }

}
