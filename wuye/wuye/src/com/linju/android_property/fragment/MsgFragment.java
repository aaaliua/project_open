package com.linju.android_property.fragment;

import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.linju.android_property.adapter.MailAdapter;
import com.linju.android_property.base.BaseFragment;
import com.linju.android_property.viewutils.WPTextView;
import com.linju.android_property2.R;
import com.tonicartos.widget.stickygridheaders.StickyGridHeadersGridView;


/**
 * 信息管理的fragmetn
 * @author LT
 *
 */
public class MsgFragment extends BaseFragment{
	@InjectView(R.id.back)
	Button back;
	@InjectView(R.id.title)
	WPTextView title;
	@InjectView(R.id.edit_or_add)
	Button editOrAdd;
	@InjectView(R.id.lists)
	ListView mListView;
	private MailAdapter adapter;
	
	//售收件箱跟发件箱的string
	int[] mailList = {R.string.inbox,R.string.sendbox};
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		back.setVisibility(View.GONE);
		title.setText(getActivity().getResources().getString(R.string.message_fragment_title));
		editOrAdd.setText(getActivity().getResources().getString(R.string.edit_mail));
		adapter = new MailAdapter(getActivity(),mailList);
		mListView.setAdapter(adapter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_msg, null);
	}

	
}
