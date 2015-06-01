// Tutorial guide by glaukonVedios (https://www.youtube.com/watch?v=y_xXybFwmYk&feature=youtu.be)

package philip.tipcalculatorenhanced;


import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener,
		OnFocusChangeListener, OnSeekBarChangeListener {

	private double totalTip;
	private double tipPercentValue;

	private double tax;
	private double taxPercentValue;

	private double grandTotal;

	private TableLayout mainTable;
	private ImageButton addDinerButton;

	private EditText firstCustomer;
	private EditText amount1of1;
	private ImageButton addButton1;
	private TextView textSplit1;
	private TextView textSplit1Dollar;
	private EditText tipPercent;
	private TextView tipDollar;
	private SeekBar tipSlider;
	private EditText taxDollar;
	private TextView textGTotal;

	private ArrayList<Diner> dinerList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		totalTip = 0.0;
		tipPercentValue = 0.15;
		tax = 0.0;
		taxPercentValue = 0.0;
		grandTotal = 0.0;

		mainTable = (TableLayout) findViewById(R.id.mainTable);
		addDinerButton = (ImageButton) findViewById(R.id.addDinerButton);
		firstCustomer = (EditText) findViewById(R.id.firstCustomer);
		amount1of1 = (EditText) findViewById(R.id.amount1of1);
		addButton1 = (ImageButton) findViewById(R.id.addButton1);
		textSplit1 = (TextView) findViewById(R.id.textSplit1);
		textSplit1Dollar = (TextView) findViewById(R.id.textSplit1Dollar);
		tipDollar = (TextView) findViewById(R.id.tipDollar);
		tipPercent = (EditText) findViewById(R.id.tipPercent);
		tipSlider = (SeekBar) findViewById(R.id.tipSlider);
		taxDollar = (EditText) findViewById(R.id.taxDollar);
		textGTotal = (TextView) findViewById(R.id.textGTotal);

		dinerList = new ArrayList<Diner>();
		Diner diner = new Diner(firstCustomer, amount1of1, addButton1,
				textSplit1, textSplit1Dollar);
		dinerList.add(diner);

		firstCustomer.setOnFocusChangeListener(this);
		amount1of1.setOnFocusChangeListener(this);
		tipPercent.setOnFocusChangeListener(this);
		taxDollar.setOnFocusChangeListener(this);
		addDinerButton.setOnClickListener(this);
		addButton1.setOnClickListener(this);
		tipSlider.setOnSeekBarChangeListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {

		if (v == addDinerButton) {

			TableRow row1 = new TableRow(this);
			EditText et1 = new EditText(this);
			et1.setText("Customer");
			et1.setSelectAllOnFocus(true);
			et1.setInputType(firstCustomer.getInputType());
			et1.setGravity(firstCustomer.getGravity());
			et1.setLayoutParams(firstCustomer.getLayoutParams());
			et1.setWidth(firstCustomer.getWidth());
			et1.setOnFocusChangeListener(this);
			EditText et2 = new EditText(this);
			et2.setText("$0.00");
			et2.setSelectAllOnFocus(true);
			et2.setInputType(amount1of1.getInputType());
			et2.setGravity(amount1of1.getGravity());
			et2.setLayoutParams(amount1of1.getLayoutParams());
			et2.setWidth(amount1of1.getWidth());
			et2.setOnFocusChangeListener(this);

			et2.requestFocus();
			ImageButton ib = new ImageButton(this);
			ib.setImageResource(R.drawable.additem);

			ib.setOnClickListener(this);

			row1.addView(et1);
			row1.addView(et2);
			row1.addView(ib);
			int rowIndex = 1;
			for (int i = 0; i < dinerList.size(); i++) {
				rowIndex += dinerList.get(i).orderList.size();
			}
			mainTable.addView(row1, rowIndex);
			TableRow row2 = new TableRow(this);
			TextView tv1 = new TextView(this);
			tv1.setText(et1.getText().toString());
			tv1.setGravity(textSplit1.getGravity());
			TextView tv2 = new TextView(this);
			tv2.setText(et2.getText().toString());
			tv2.setGravity(textSplit1Dollar.getGravity());
			row2.addView(tv1);
			row2.addView(tv2);

			mainTable.addView(row2, rowIndex + 11 + dinerList.size());

			Diner diner = new Diner(et1, et2, ib, tv1, tv2);
			dinerList.add(diner);

		} else {

			for (int i = 0; i < dinerList.size(); i++) {
				if (v == dinerList.get(i).ibAddOrder) {

					TableRow row3 = new TableRow(this);
					EditText emptyEditText = new EditText(this);
					emptyEditText.setVisibility(4);
					EditText newOrder = new EditText(this);
					newOrder.setText("$0.00");
					newOrder.setSelectAllOnFocus(true);
					newOrder.setInputType(amount1of1.getInputType());
					newOrder.setGravity(amount1of1.getGravity());
					newOrder.setLayoutParams(amount1of1.getLayoutParams());
					newOrder.setWidth(amount1of1.getWidth());
					newOrder.setOnFocusChangeListener(this);
					newOrder.requestFocus();
					row3.addView(emptyEditText);
					row3.addView(newOrder);

					int rowIndex2 = 1;
					for (int j = 0; j <= i; j++) {
						rowIndex2 += dinerList.get(j).orderList.size();
					}
					mainTable.addView(row3, rowIndex2);

					dinerList.get(i).newOrder(newOrder);
				}
			}
		}
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {

		if (v == tipPercent && hasFocus == false) {

			tipPercentValue = Double.parseDouble(((EditText) v).getText()
					.toString().replace("%", ""));

			if (tipPercentValue > 50) {
				tipPercentValue = 50;
			}

			tipSlider.setProgress((int) tipPercentValue);

			if (tipPercentValue > .5) {
				tipPercentValue = tipPercentValue / 100;
			}

		} else if (v == taxDollar && hasFocus == false) {

			tax = Double.parseDouble(((EditText) v).getText().toString()
					.replace("$", "").replace(",", ""));
			((EditText) v).setText("$" + String.format("%,.2f", tax));

			calcGrandTotal();

		} else {

			for (int i = 0; i < dinerList.size(); i++) {

				if (v == dinerList.get(i).etName && hasFocus == false) {

					dinerList.get(i).setName();
				} else {

					for (int j = 0; j < dinerList.get(i).orderList.size(); j++) {

						if (v == dinerList.get(i).orderList.get(j)
								&& hasFocus == false) {

							dinerList.get(i).updateTotal((EditText) v);
							calcGrandTotal();
						}
					}
				}
			}
		}
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {

		if (seekBar == tipSlider) {

			tipPercentValue = progress;
			tipPercent.setText(String.format("%.0f", tipPercentValue) + "%");
			tipPercentValue = tipPercentValue / 100;

			calcGrandTotal();
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {

	}

	public void calcGrandTotal() {

		grandTotal = 0.0;

		for (int i = 0; i < dinerList.size(); i++) {
			grandTotal += dinerList.get(i).total;
		}

		totalTip = grandTotal * tipPercentValue;
		tipDollar.setText("$" + String.format("%,.2f", totalTip));

		taxPercentValue = tax / grandTotal;

		for (int i = 0; i < dinerList.size(); i++) {
			dinerList.get(i).setTotalText(tipPercentValue, taxPercentValue);
		}

		textGTotal.setText("$"
				+ String.format("%,.2f", grandTotal + totalTip + tax));
	}
}
