package se.mobileapplab.listdemo;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("DefaultLocale")
public class MainActivity extends Activity implements OnItemClickListener {
	TextView _heading;
	ListView _list;
	
	ArrayList<Product> _data;
	
	final int ADD = 1;
	final int EDIT = 2;
	int _currentIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		_heading = (TextView)findViewById(R.id.heading);
		_list = (ListView)findViewById(R.id.list);
		
		_data = new ArrayList<Product>();
		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		String[] array = prefs.getString("products", "").split("&");
		for (int i = 0; (i+1)<array.length; i += 2) {
			Product p = new Product();
			p.name = array[i];
			p.price = Integer.parseInt(array[i+1]);
			_data.add(p);
		}
		
		ArrayAdapter<Product> adapter = new ArrayAdapter<Product>(this,
			android.R.layout.simple_list_item_1, _data);
		_list.setAdapter(adapter);
		_list.setOnItemClickListener(this);
		
		updateView();
	}

	public void onAdd(View src) {
		Intent intent = new Intent(this, EditActivity.class);
		startActivityForResult(intent, ADD);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode==RESULT_OK) {
			Product p = (Product)data.getSerializableExtra("product");
			if (requestCode==ADD) {
				_data.add(p);
			}
			else if (requestCode==EDIT) {
				_data.set(_currentIndex, p);
			}
			updateView();
			saveData();
		}
		else if (resultCode==EditActivity.RESULT_DELETE) {
			_data.remove(_currentIndex);
			updateView();
			saveData();
		}
	}
	
	private void updateView() {
		String msg = String.format("%d products", _data.size());
		_heading.setText(msg);
		
		((ArrayAdapter<?>)_list.getAdapter()).notifyDataSetChanged();
	}
	
	private void saveData() {
		StringBuilder storage = new StringBuilder();
		for (Product p : _data) {
			if (storage.length()>0)
				storage.append("&");
			storage.append(p.name);
			storage.append("&");
			storage.append(Integer.toString(p.price));
		}
		
		SharedPreferences.Editor prefs = getPreferences(MODE_PRIVATE).edit();
		prefs.putString("products", storage.toString());
		prefs.commit();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		_currentIndex = position;
		Product p = _data.get(position);
		Intent intent = new Intent(this, EditActivity.class);
		intent.putExtra("product", p);
		startActivityForResult(intent, EDIT);
	}
}
