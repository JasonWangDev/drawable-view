package com.github.jasonwangdev.drawableview.demo.baseadapter_realm;

import android.widget.Filter;
import android.widget.Filterable;

import io.realm.RealmBaseAdapter;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by Jason on 2017/7/9.
 */

public abstract class FilterableBaseAdapterRealm<T extends RealmObject> extends RealmBaseAdapter<T> implements Filterable {

    protected abstract CharSequence getItemName(T itemObject);
    protected abstract String getFilterFieldName();


    private RealmFilter filter;
    private RealmResults resultsOrigin;


    public FilterableBaseAdapterRealm(RealmResults data) {
        super(data);

        resultsOrigin = data;
    }


    @Override
    public Filter getFilter() {
        if (null == filter)
            filter = new RealmFilter();

        return filter;
    }


    private class RealmFilter extends Filter {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return getItemName((T) resultValue);
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            filterResults.count = 1;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (null != constraint)
                adapterData = resultsOrigin.where().contains(getFilterFieldName(), (String) constraint).findAll();
            else
                adapterData = resultsOrigin.where().findAll();

            notifyDataSetChanged();
        }
    }


}
