package com.dhb.qqhook.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dhb.qqhook.R;

import java.util.List;
import java.util.Map;

/**
 * Created by DHB on 2015/12/21.
 */
public class QqListViewAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String, Object>> listItems;
    private LayoutInflater listContainer;
    public final class QqListView{
        private TextView QqText;
        private TextView QqKeyText;
    }

    public QqListViewAdapter(Context context , List<Map<String, Object>> listItems){
        this.context = context;
        this.listItems = listItems;
        this.listContainer = LayoutInflater.from(context);
    }
    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return listItems.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return null;
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        final int selectID = position;
        QqListView qqListView;
        if (convertView == null){
            qqListView = new QqListView();
            convertView = listContainer.inflate(R.layout.qq_list_view_layout,null);

            qqListView.QqText = (TextView) convertView.findViewById(R.id.QQText);
            qqListView.QqKeyText = (TextView) convertView.findViewById(R.id.QQKey);
            convertView.setTag(qqListView);
        } else {
            qqListView = (QqListView) convertView.getTag();
        }

        qqListView.QqText.setText((String)listItems.get(position).get("QqText"));
        qqListView.QqKeyText.setText((String)listItems.get(position).get("QqKeyText"));
        return convertView;
    }
}
