package com.dazhongcun.merchants.fragment;

import java.io.File;
import java.util.List;

import org.json.JSONObject;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.dazhongcun.application.BaseApplication;
import com.dazhongcun.baseactivity.BaseFragment;
import com.dazhongcun.meifa.merchants.R;
import com.dazhongcun.merchants.activity.HistoryActivity;
import com.dazhongcun.merchants.application.AppApplication;
import com.dazhongcun.merchants.database.BaseAppDbHelper;
import com.dazhongcun.merchants.entity.MakeEntity;
import com.dazhongcun.merchants.entity.Status;
import com.dazhongcun.merchants.entity.UserEntity;
import com.dazhongcun.merchants.user.BasicMember;
import com.dazhongcun.merchants.user.LoginActivity;
import com.dazhongcun.merchants.utils.FileUtils;
import com.dazhongcun.merchants.utils.ParseJson;
import com.dazhongcun.merchants.utils.RequestUri;
import com.dazhongcun.utils.DownloadUtils;
import com.dazhongcun.utils.StringUtils;
import com.dazhongcun.views.Toaster;
import com.dazhongcun.widget.FancyButton;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;

import roboguice.inject.InjectView;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 订单fragmetn
 * 
 * @author LT
 * 
 */
public class TaskFragment extends BaseFragment implements
		android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener,
		OnClickListener {

	@InjectView(R.id.swipe_refresh)
	SwipeRefreshLayout layout;
	@InjectView(R.id.recycler_view)
	RecyclerView recyclerView;

	@InjectView(R.id.app_loading)
	View v;
	// @InjectView(R.id.empty)
	// View emp;

	@InjectView(R.id.edit_or_add)
	View moreView;
	@InjectView(R.id.storeID)
	Button storeID;

	boolean loading = false;
	private List<MakeEntity> makes;
	private RecyclerViewAdapter adapter;

	public int number = 1;

	public boolean loadFlag = false;

	private MsgReceiver updateListViewReceiver;

	private BaseAppDbHelper<UserEntity> dbHelper = new BaseAppDbHelper<UserEntity>();

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_task, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		layout.setColorScheme(android.R.color.holo_red_light,
				android.R.color.holo_green_light,
				android.R.color.holo_blue_bright,
				android.R.color.holo_orange_light);
		layout.setOnRefreshListener(this);

		updateListViewReceiver = new MsgReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.dazhongcun.activity.UPDATE_LISTVIEW");
		getActivity().registerReceiver(updateListViewReceiver, intentFilter);

		if (LoginActivity.getLoginKey() == -1) {
			// 用户没有登录 跳转登录
			Intent it = new Intent();
			it.setAction(BaseApplication.EXIT_APP);
			getActivity().sendBroadcast(it);
			getActivity().startActivity(new Intent(getActivity(),LoginActivity.class));
			getActivity().finish();
			return;
		}

		moreView.setOnClickListener(this);
		recyclerView.setHasFixedSize(true);
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		// final LinearLayoutManager layoutManager = new
		// LinearLayoutManager(getActivity());
		// layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		// recyclerView.setLayoutManager(layoutManager);
		final LinearLayoutManager layoutManager = new LinearLayoutManager(
				getActivity());
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
				DividerItemDecoration.VERTICAL_LIST));
		// LinearLayoutManager linearLayoutManager = new
		// LinearLayoutManager(getActivity());
		// linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		// recyclerView.setLayoutManager(linearLayoutManager);

		recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView,
					int newState) {
				super.onScrollStateChanged(recyclerView, newState);
			}

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				System.out.println(loadFlag);
				if (!loading
						&& layoutManager.findLastVisibleItemPosition() == makes
								.size() - 1 && loadFlag) {
					loading = true;
					MakeEntity obj = new MakeEntity();
					obj.setLoadtype(MakeEntity.LOADMORE);
					adapter.add(obj);
					// 去加载更多
					loadMore();
				}

			}
		});

		// 查询数据库中的用户
		UserEntity dblogin = new UserEntity();
		dblogin = dbHelper.queryObjForEq(UserEntity.class, UserEntity.JSON_ID,
				LoginActivity.getLoginKey());
		if (dblogin != null) {
			storeID.setText(String.format(getResources().getString(R.string.storeID), dblogin.getStoreid()));
		}
		getMakeDate(true);

	}

	private void loadMore() {
		RequestParams params = new RequestParams();
		params.put(BasicMember.PARAMS_USERID, LoginActivity.getLoginKey() + "");
		params.put(BasicMember.PARAMS_STATUS, 1 + "");
		params.put(RequestUri.CODE, RequestUri.MAKE_CODE);
		params.put(BasicMember.PARAMS_PAGESIZE, BasicMember.size + "");
		params.put(BasicMember.PARAMS_PAGENUMBER, (number += 1) + "");
		System.out.println(RequestUri.BASE_URL + params.toString());

		AppApplication.getHttpClient().get(RequestUri.BASE_URL, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onStart() {
						// 网络请求开始
					}

					@Override
					@Deprecated
					public void onSuccess(String content) {
						parseJson(content, true);
					}

					@Override
					@Deprecated
					public void onFailure(Throwable error) {
						// 设置list的Emptyview
						number -= 1;
						adapter.remove(adapter.getItemCount() - 1);
						loading = false;
						loadFlag = false;
						Toaster.showOneToast("数据加载异常");
					}

					@Override
					public void onFinish() {

					}

				});
	}

	private void getMakeDate(final boolean refresh) {

		RequestParams params = new RequestParams();
		params.put(BasicMember.PARAMS_USERID, LoginActivity.getLoginKey() + "");
		params.put(BasicMember.PARAMS_STATUS, 1 + "");
		params.put(RequestUri.CODE, RequestUri.MAKE_CODE);
		params.put(BasicMember.PARAMS_PAGESIZE, BasicMember.size + "");
		params.put(BasicMember.PARAMS_PAGENUMBER, number);
		System.out.println(RequestUri.BASE_URL + params.toString());
		// 不确定的dialog
		AppApplication.getHttpClient().get(RequestUri.BASE_URL, params,
				new AsyncHttpResponseHandler() {
					private boolean isSuc = false;

					@Override
					public void onStart() {
						// 网络请求开始
						if (refresh)
							v.setVisibility(View.VISIBLE);
					}

					@Override
					@Deprecated
					public void onSuccess(String content) {
						parseJson(content, false);
					}

					@Override
					@Deprecated
					public void onFailure(Throwable error) {
						v.setVisibility(View.GONE);
						// 设置list的Emptyview
						layout.setRefreshing(false);
					}

					@Override
					public void onFinish() {
						v.setVisibility(View.GONE);
					}

				});
	}

	private void parseJson(String json, boolean isload) {
		layout.setRefreshing(false);
		if (isload) {
			List<MakeEntity> more = ParseJson.getMakeEntityList(json);
			if (more != null) {
				if (more.size() == BasicMember.size) {
					loadFlag = true;
				} else {
					loadFlag = false;
				}
				adapter.remove(adapter.getItemCount() - 1);
				adapter.addAll(more);
				loading = false;
				// Toaster.showOneToast("数据添加");
			} else {
				adapter.remove(adapter.getItemCount() - 1);
				Toaster.showOneToast("数据加载完成");
			}

		} else {
			makes = ParseJson.getMakeEntityList(json);
			if (makes != null) {
				if (makes.size() == BasicMember.size) {
					loadFlag = true;
				} else {
					loadFlag = false;
				}
				if (makes.size() == 0) {
					MakeEntity obj = new MakeEntity();
					obj.setLoadtype(MakeEntity.NODATA);
					makes.add(obj);
				}
				adapter = new RecyclerViewAdapter(getActivity(), makes,
						getActivity());
				recyclerView.setAdapter(adapter);

				// if(makes.size() == 0){
				// emp.setVisibility(View.VISIBLE);
				// }else{
				// emp.setVisibility(View.GONE);
				// }
			} else {
				Status st = ParseJson.getStatus(json);
				Toaster.showOneToast(st.getMsg());
			}

		}

	}

	@Override
	public void onRefresh() {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				number = 1;
				getMakeDate(false);
			}
		}, 1000);
		// getActivity().runOnUiThread(new Runnable() {
		//
		// @Override
		// public void run() {
		// number = 1;
		// getMakeDate(false);
		// new GetDataTask().execute();
		// }
		// });

	}

	private class GetDataTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// Simulates a background job.
			String result = null;

			RequestParams ps = new RequestParams();
			ps.put(BasicMember.PARAMS_USERID, LoginActivity.getLoginKey() + "");
			ps.put(BasicMember.PARAMS_STATUS, 1 + "");
			ps.put(RequestUri.CODE, RequestUri.MAKE_CODE);
			ps.put(BasicMember.PARAMS_PAGESIZE, BasicMember.size + "");
			ps.put(BasicMember.PARAMS_PAGENUMBER, number);
			System.out.println(RequestUri.BASE_URL + params.toString());

			String url = RequestUri.BASE_URL + ps.toString();

			File file = new File(AppApplication.mSdcardCache + File.separator
					+ StringUtils.replaceUrlWithPlus(url));
			if (file.exists() && file.isFile()) {
				file.delete();
			}
			try {
				DownloadUtils.download(url, file, false, null);
				result = FileUtils.readTextFile(file);
			} catch (Exception e) {
				// TODO: handle exception
				// Logger.e("ContentListFragment", "下拉刷新", e);
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				parseJson(result, false);
			}
			// Call onRefreshComplete when the list has been refreshed.
			layout.setRefreshing(false);

			super.onPostExecute(result);
		}
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == moreView.getId()) {
			startActivity(new Intent(getActivity(), HistoryActivity.class));
		}
	}

	public void change(String position) {
		RequestParams params = new RequestParams();
		params.put(BasicMember.PARAMS_USERID, LoginActivity.getLoginKey() + "");
		params.put(BasicMember.PARAMS_STATUS, 4 + "");
		params.put(BasicMember.PARAMS_OID, position);
		params.put(BasicMember.PARAMS_TYPE, 3 + "");
		params.put(RequestUri.CODE, RequestUri.INFO_DONE);

		AppApplication.getHttpClient().get(RequestUri.BASE_URL, params,
				new AsyncHttpResponseHandler() {
					private boolean isSuc = false;

					@Override
					public void onStart() {
						// 网络请求开始
					}

					@Override
					@Deprecated
					public void onSuccess(String content) {
						try {
							JSONObject json = new JSONObject(content);
							String code = json.optString(Status.JSON_STATUS);
							if ("0".equals(code)) {
								// 完成
								getMakeDate(true);
							} else {
								Toaster.showOneToast("异常");
							}
						} catch (Exception e) {
						}
					}

					@Override
					@Deprecated
					public void onFailure(Throwable error) {
					}

					@Override
					public void onFinish() {
					}

				});
	}

	public class RecyclerViewAdapter extends
			RecyclerView.Adapter<RecyclerView.ViewHolder> {
		private final Context mContext;
		private List<MakeEntity> datas;
		public final Activity ac;

		public RecyclerViewAdapter(Context context, List<MakeEntity> datas,
				Activity ac) {
			mContext = context;
			this.datas = datas;
			this.ac = ac;
		}

		@Override
		public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
				int viewType) {
			switch (viewType) {
			case MakeEntity.LOADMORE:
				View v = LayoutInflater.from(parent.getContext()).inflate(
						R.layout.load_more, parent, false);
				ProgressBar bar = (ProgressBar) v.findViewById(R.id.progress);
				return new RecyclerView.ViewHolder(v) {
				};
			case MakeEntity.ITEM:
				View view = (View) LayoutInflater.from(parent.getContext())
						.inflate(R.layout.list_item, parent, false);
				return new ListViewHolder(mContext, view, ac);
			case MakeEntity.NODATA:

				View nodata = (View) LayoutInflater.from(parent.getContext())
						.inflate(R.layout.nodate, parent, false);
				return new RecyclerView.ViewHolder(nodata) {
				};
			default:
				View views = (View) LayoutInflater.from(parent.getContext())
						.inflate(R.layout.list_item, parent, false);
				return new ListViewHolder(mContext, views, ac);
			}

		}

		@Override
		public int getItemViewType(int position) {
			return datas.get(position).getLoadtype();
		}

		@Override
		public void onBindViewHolder(RecyclerView.ViewHolder viewHolder,
				final int position) {
			MakeEntity ent = datas.get(position);
			switch (ent.getLoadtype()) {
			case MakeEntity.LOADMORE:

				break;
			case MakeEntity.NODATA:

				break;
			case MakeEntity.ITEM:
				ListViewHolder viewHolders = (ListViewHolder) viewHolder;
				viewHolders.makeID
						.setText(String.format(mContext.getResources()
								.getString(R.string.makeID), ent.getId()));
				viewHolders.makeMan.setText(ent.getUserName());
				viewHolders.makeType.setText(ent.getOutType());
				viewHolders.makePhone.setText(ent.getMobile());
				viewHolders.makeName.setText(ent.getStyListName());
				viewHolders.makeTime.setText(ent.getOutTime());
				viewHolders.makeDate.setText(ent.getOutdate());
				viewHolders.makeCreateDate.setText(ent.getCreateDate());

				viewHolders.done.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						new MaterialDialog.Builder(ac)
								.title("确认订单")
								.content(
										"确定完成    "
												+ String.format(
														mContext.getResources()
																.getString(
																		R.string.makeID),
														getObj(position)
																.getId())
												+ "    该订单吗?")
								.theme(Theme.LIGHT)
								// the default is light, so you don't
								// need this line
								.positiveText(R.string.accept)
								// the default is 'Accept'
								.positiveColor(
										mContext.getResources().getColor(
												R.color.merchants_color))
								.negativeText(R.string.decline) // leaving this
																// line out
																// will remove
																// the negative
																// button
								.callback(new MaterialDialog.Callback() {

									@Override
									public void onPositive(MaterialDialog dialog) {
										change(getObj(position).getId());

									}

									@Override
									public void onNegative(MaterialDialog dialog) {
										dialog.dismiss();
									}
								}).build().show();
					}
				});
				break;
			default:
				ListViewHolder viewHolderd = (ListViewHolder) viewHolder;
				viewHolderd.makeID
						.setText(String.format(mContext.getResources()
								.getString(R.string.makeID), ent.getId()));
				viewHolderd.makeMan.setText(ent.getUserName());
				viewHolderd.makeType.setText(ent.getOutType());
				viewHolderd.makePhone.setText(ent.getMobile());
				viewHolderd.makeName.setText(ent.getStyListName());
				viewHolderd.makeTime.setText(ent.getOutTime());
				viewHolderd.makeDate.setText(ent.getOutdate());
				viewHolderd.makeCreateDate.setText(ent.getCreateDate());
				break;
			}

		}

		@Override
		public int getItemCount() {
			return datas.size();
		}

		public void remove(int i) {
			datas.remove(i);
			notifyItemRemoved(i);
		}

		public void add(MakeEntity entitye) {
			this.datas.add(entitye);
			notifyItemInserted(datas.size() - 1);
		}

		public void addAll(List<MakeEntity> list) {
			this.datas.addAll(list);
			notifyDataSetChanged();
		}

		public MakeEntity getObj(int position) {
			return datas.get(position);
		}

		public class ListViewHolder extends RecyclerView.ViewHolder {

			public View mTextView;
			private final Context mContext;

			private final Activity ac;

			public FancyButton makeID;
			public FancyButton done;
			public TextView makeMan;
			public TextView makeType;
			public TextView makePhone;
			public TextView makeName;
			public TextView makeTime;
			public TextView makeDate;
			public TextView makeCreateDate;

			public ListViewHolder(Context mCon, View v, Activity acd) {
				super(v);
				mTextView = v;
				mContext = mCon;
				this.ac = acd;

				this.makeID = (FancyButton) v.findViewById(R.id.makeID);
				this.makeID.setEnabled(false);
				this.makeMan = (TextView) v.findViewById(R.id.makeMan);
				this.makeType = (TextView) v.findViewById(R.id.makeType);
				this.makePhone = (TextView) v.findViewById(R.id.makePhone);
				this.makeName = (TextView) v.findViewById(R.id.makeName);
				this.makeTime = (TextView) v.findViewById(R.id.makeTime);
				this.makeDate = (TextView) v.findViewById(R.id.makedate);
				this.done = (FancyButton) v.findViewById(R.id.done);
				this.makeCreateDate = (TextView) v
						.findViewById(R.id.makeCreateDate);

			}

		}

	}

	public class MsgReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			number = 1;
			layout.setRefreshing(true);
			getMakeDate(false);

		}
	}

	public static final String TAG = "TaskFragment";

	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart(TAG);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd(TAG);
	}

	@Override
	public void onDestroy() {
		getActivity().unregisterReceiver(updateListViewReceiver);
		super.onDestroy();
	}
}
