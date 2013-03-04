package se.mobileapplab.listdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditActivity extends Activity {
	EditText _name;
	EditText _price;
	
	public static final int RESULT_DELETE = 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		
		_name = (EditText)findViewById(R.id.name);
		_price = (EditText)findViewById(R.id.price);
		
		Product p = (Product)getIntent().getSerializableExtra("product");
		if (p!=null) {
			_name.setText(p.name);
			_price.setText(Integer.toString(p.price));
		}
	}

	public void onCancel(View src) {
		finish();
	}
	
	public void onDelete(View src) {
		AlertDialog.Builder dlg = new AlertDialog.Builder(this);
		dlg.setTitle(R.string.delete);
		dlg.setMessage(R.string.delete_confirm);
		dlg.setPositiveButton(R.string.yes, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				setResult(RESULT_DELETE);
				finish();
			} 
		});
		dlg.setNegativeButton(R.string.no, null);
		dlg.show();
	}

	public void onOk(View src) {
		Product p = new Product();
		p.name = _name.getText().toString();
		p.price = Integer.parseInt(_price.getText().toString());
				
		Intent result = new Intent();
		result.putExtra("product", p);
		setResult(RESULT_OK, result);
		finish();
	}
}
