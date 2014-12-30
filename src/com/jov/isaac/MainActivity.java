package com.jov.isaac;

import java.io.IOException;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.jov.isaac.adapter.DataAdapter;
import com.jov.isaac.adapter.util.StringUtil;
import com.jov.isaac.db.DBHelper;
import com.jov.isaac.db.ToolBean;
import com.jov.isaac.view.PullDownView;

@SuppressLint("NewApi")
public class MainActivity extends Activity implements OnNavigationListener,
		PullDownView.OnPullDownListener, SearchView.OnQueryTextListener {
	private ActionBar actionBar;
	private List<ToolBean> list;
	private PullDownView mPullDownView;
	private DataAdapter adapter;
	private ListView mListView;
	private DBHelper db;
	private int total;
	private int pageNo = 1;
	private int totalPage;
	private SearchView searchView;
	private String type = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayShowTitleEnabled(false);
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setListNavigationCallbacks(new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item,
				android.R.id.text1, new String[] { "全部", "主动", "被动", "饰品",
						"塔牌", "符文", "胶囊", "人物", "成就" }), this);
		db = new DBHelper(this);
		UpdateManager update = new UpdateManager(this);
		update.checkVersionThread();
		checkInit();
		initData();
	}

	private void checkInit() {
		total = db.getTotalCount();
		if (total <= 0) {
			try {
				db.initInsert();
			} catch (IOException e) {
				e.printStackTrace();
			}
			total = db.getTotalCount();
		}
		if (total == 0) {
			totalPage = 1;
		} else {
			totalPage = total % 20 == 0 ? total / 20 : (total / 20 + 1);
		}
	}

	private void initData() {
		list = db.getBeans(pageNo);
		mPullDownView = (PullDownView) findViewById(R.id.main_listview);
		mPullDownView.setOnPullDownListener(this);
		adapter = new DataAdapter(this, list);
		mListView = mPullDownView.getListView();
		mListView.setAdapter(adapter);
		mPullDownView.setHideFooter();
		mPullDownView.enableAutoFetchMore(true, 3);
		mPullDownView.setHideHeader();
		mPullDownView.RefreshComplete();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		searchView = (SearchView) menu.findItem(R.id.menu_search)
				.getActionView();
		searchView.setSubmitButtonEnabled(true);
		searchView.setOnQueryTextListener(this);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.menu_setting) {
			Intent intent = new Intent(this, AboutActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onNavigationItemSelected(int arg0, long arg1) {
		pageNo = 1;
		switch (arg0) {
		case 0:
			type = null;
			total = db.getTotalCount();
			list.clear();
			list.addAll(db.getBeans(pageNo));
			break;
		case 1:
			type = "1";
			total = db.getTotalCount(type);
			list.clear();
			list.addAll(db.getBeans(pageNo, type));
			break;
		case 2:
			type = "2";
			total = db.getTotalCount(type);
			list.clear();
			list.addAll(db.getBeans(pageNo, type));
			break;
		case 3:
			type = "3";
			total = db.getTotalCount(type);
			list.clear();
			list.addAll(db.getBeans(pageNo, type));
			break;
		case 4:
			type = "4";
			total = db.getTotalCount(type);
			list.clear();
			list.addAll(db.getBeans(pageNo, type));
			break;
		case 5:
			type = "5";
			total = db.getTotalCount(type);
			list.clear();
			list.addAll(db.getBeans(pageNo, type));
			break;
		case 6:
			type = "6";
			total = db.getTotalCount(type);
			list.clear();
			list.addAll(db.getBeans(pageNo, type));
			break;
		case 7:
			type = "7";
			total = db.getTotalCount(type);
			list.clear();
			list.addAll(db.getBeans(pageNo, type));
			break;
		case 8:
			type = "8";
			total = db.getTotalCount(type);
			list.clear();
			list.addAll(db.getBeans(pageNo, type));
			break;
		case 9:
			type = "9";
			total = db.getTotalCount(type);
			list.clear();
			list.addAll(db.getBeans(pageNo, type));
			break;
		default:
			total = db.getTotalCount();
			list.clear();
			list.addAll(db.getBeans(pageNo));
			break;
		}
		totalPage = total % 20 == 0 ? total / 20 : (total / 20 + 1);
		mListView.setSelection(0);
		adapter.notifyDataSetChanged();
		return true;
	}

	@Override
	public void onRefresh() {
		mPullDownView.RefreshComplete();
	}

	@Override
	public void onMore() {
		if (pageNo > totalPage) {
			pageNo = totalPage;
		} else {
			pageNo++;
			if (StringUtil.isEmpty(type)) {
				list.addAll(db.getBeans(pageNo));
			} else {
				list.addAll(db.getBeans(pageNo, type));
			}
			adapter.notifyDataSetChanged();
		}
		mPullDownView.notifyDidMore();
	}

	@Override
	public boolean onQueryTextChange(String arg0) {
		System.out.println("arg0=" + arg0);
		if (StringUtil.isEmpty(arg0)) {
			list.clear();
			if (StringUtil.isEmpty(type)) {
				list.addAll(db.getBeans(1));
			} else {
				list.addAll(db.getBeans(1, type));
			}
		} else {
			list.clear();
			list.addAll(db.getBeansByKeywords(arg0));
		}
		adapter.notifyDataSetChanged();
		mPullDownView.notifyDidMore();
		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		if (StringUtil.isEmpty(arg0)) {
			return true;
		} else {
			list.clear();
			list.addAll(db.getBeansByKeywords(arg0));
		}
		adapter.notifyDataSetChanged();
		return true;
	}

	private long exitTime = 0;

	public void backTo() {
		if ((System.currentTimeMillis() - exitTime) > 2000) {
			Toast.makeText(getApplicationContext(), "亲，再按一次才能退出哦！",
					Toast.LENGTH_SHORT).show();
			exitTime = System.currentTimeMillis();
		} else {
			finish();
			System.exit(0);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			backTo();
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_MENU) {

		}
		return super.onKeyDown(keyCode, event);
	}

}
