package com.example.justjava;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
//import android.support.*;
import android.provider.CalendarContract;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */

    int quantity = 0;
    public void increment(View view){

        if(quantity == 100){
            //Show error message
            Toast.makeText(this,"You cannot have more than 100 coffee",Toast.LENGTH_SHORT).show();
            //Exit this method early because nothing is left to do.
            return;
        }
        quantity= quantity  + 1;
        displayQuantity(quantity);
    }
    public void decrement(View view){
        if ( quantity == 1){
            Toast.makeText(this,"You cannot have less than 1 coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity=quantity - 1;
        displayQuantity(quantity);

    }
    public void submitOrder(View view){
        // Reading data from checkbox
        CheckBox whippedCreamCheckBox = (CheckBox)findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        //Reading data from chocolate check box
        CheckBox chocolateCheckBox = (CheckBox)findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        // Reading text from Edit text view
        EditText nameField = (EditText) findViewById(R.id.input_name);
        String name = nameField.getText().toString();
        // Calculating price including prices of checkbox
        int price = calculatePrice(hasWhippedCream,hasChocolate);
        //Display summery message on screen
        String message = createOrderSummery(name,price,hasWhippedCream,hasChocolate);
       //Intent to launch mail app
        //send order summery in email body
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.order_summary_email_subject,name) );
            intent.putExtra(Intent.EXTRA_TEXT,message);
            if (intent.resolveActivity(getPackageManager()) != null)
            {
                startActivity(intent);
            }

    }
    /**
     * Calculates the price of the order.
     *
     * @param addWhippedCream is whether or not we should include whipped cream topping in the price
     * @param addChocolate    is whether or not we should include chocolate topping in the price
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream , boolean addChocolate)
    {
        int basePrice = 50;
        //If user selects whipped cream add $1 to base price
        if(addWhippedCream){
            basePrice += 10;
        }
        // if user selects chocolate add $2 in price
        if (addChocolate){
            basePrice += 20;
        }
        return quantity*basePrice;
    }
    /**
     * Create summery method
     *       @param name            on the order
     *      * @param price           of the order
     *      * @param addWhippedCream is whether or not to add whipped cream to the coffee
     *      * @param addChocolate    is whether or not to add chocolate to the coffee
     *      * @return text summary
     */
    private String createOrderSummery(String name,int price, boolean addWhippedCream, boolean addChocolate)
    {
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
        priceMessage += "\n" + getString(R.string.order_summary_chocolate, addChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_price,
                NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number ) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    /**
     * This method displays the given text on the screen.
     */

}