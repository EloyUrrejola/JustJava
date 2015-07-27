package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    int quantity = 2;
    boolean whipped_cream = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submitOrder(View view) {
        EditText nameEditText = (EditText) findViewById(R.id.name_edit_text);
        nameEditText.append(String.valueOf(" Urrejola"));
        String name = nameEditText.getText().toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        String message = createOrderSummary(name, calculatePrice(hasWhippedCream, hasChocolate), hasWhippedCream, hasChocolate);
        //displayMessage(message);
        composeEmail("JustJava order for " + name, message);
    }

    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int price = 5;
        if (addWhippedCream) {
            price += 1;
        }
        if (addChocolate) {
            price += 2;
        }
        return quantity * price;
    }

    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {
        String summary;
        summary = getString(R.string.name) + ": " + name + "\n";
        summary += getString(R.string.add_whipped_cream) + "? " + addWhippedCream + "\n";
        summary += getString(R.string.add_chocolate) + "?" + addChocolate + "\n";
        summary += getString(R.string.quantity) + ": " + quantity + "\n";
        summary += "Total: $" + price + "\n";
        summary += getString(R.string.thank_you);
        return summary;
    }

    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    /* private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    } */

    public void composeEmail(String subject, String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void increase(View view) {
        if (quantity < 10) {
            quantity++;
            display(quantity);
        } else {
            Toast.makeText(getApplicationContext(), "Too many coffees", 3000).show();
        }
    }

    public void decrease(View view) {
        if (quantity > 1) {
            quantity--;
            display(quantity);
        }
    }
}
