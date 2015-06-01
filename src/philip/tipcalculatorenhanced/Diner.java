// Tutorial guide by glaukonVedios (https://www.youtube.com/watch?v=y_xXybFwmYk&feature=youtu.be)

package philip.tipcalculatorenhanced;

import java.util.ArrayList;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class Diner {

	public double total;
	public ArrayList<EditText> orderList;
	public EditText etName;
	public EditText etFirstOrder;
	public ImageButton ibAddOrder;
	public TextView tvName;
	public TextView tvSplitBill;

	public Diner(EditText et1, EditText et2, ImageButton ib, TextView tv1,
			TextView tv2) {

		total = 0.0;

		orderList = new ArrayList<EditText>();
		etName = et1;
		etFirstOrder = et2;
		ibAddOrder = ib;
		tvName = tv1;
		tvSplitBill = tv2;

		orderList.add(etFirstOrder);
	}

	public void setName() {

		tvName.setText(etName.getText().toString());
	}

	public void newOrder(EditText newOrder) {
		orderList.add(newOrder);
	}

	public void updateTotal(EditText toBeUpdated) {

		total = 0.0;

		toBeUpdated.setText("$"
				+ String.format("%,.2f", editTextToDouble(toBeUpdated)));

		for (int i = 0; i < orderList.size(); i++) {
			total += editTextToDouble(orderList.get(i));
		}
	}

	public double editTextToDouble(EditText et) {
		double db = 0.0;
		db = Double.parseDouble(et.getText().toString().replace("$", "")
				.replace(",", ""));
		return db;
	}

	public void setTotalText(double tip, double tax) {
		tvSplitBill.setText("$"
				+ String.format("%,.2f", total * (1 + tip) + total * tax));
	}
}
