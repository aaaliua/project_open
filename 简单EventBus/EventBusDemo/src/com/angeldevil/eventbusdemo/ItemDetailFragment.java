
package com.angeldevil.eventbusdemo;

import com.angeldevil.eventbusdemo.dummy.DummyContent;

import de.greenrobot.event.EventBus;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ItemDetailFragment extends Fragment {

	
	
    private TextView tvDetail;

    private DummyContent.DummyItem mItem;

    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // register
        EventBus.getDefault().register(this);
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        // Unregister
        EventBus.getDefault().unregister(this);
    }

    /** List点击时会发送些事件，接收到事件后更新详情 */
    public void onEventMainThread(DummyContent.DummyItem item) {
        Log.d(MainActivity.TAG, "Received event at ItemDetailFragment");
        mItem = item;
        updateDetail();
    }

    private void updateDetail() {
        if (mItem != null) {
            tvDetail.setText(mItem.content);
        }
    }
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);
        tvDetail = (TextView)rootView.findViewById(R.id.item_detail);
        return rootView;
    }
}
