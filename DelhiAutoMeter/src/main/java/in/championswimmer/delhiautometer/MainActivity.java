package in.championswimmer.delhiautometer;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
    public Double kilometers;
    public Double oldFare;
    public Double newFare;
    public EditText editText_kilometers;
    public EditText editText_oldFare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText_kilometers = (EditText) findViewById(R.id.kilometers);
        editText_oldFare = (EditText) findViewById(R.id.oldFare);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void getFareFromOldFare (View v) {
        oldFare = null; newFare = null;
        try {
            oldFare = new Double(editText_oldFare.getText().toString());
        } catch (Exception e) {
            oldFare = 0.00;
        }
        newFare = ((((oldFare - 19) / 6.5) * 8) + 25);
        setShowFare(newFare);

    }

    public void getFareFromKilometers (View v) {
        kilometers = null; newFare = null;
        try {
            kilometers = new Double(editText_kilometers.getText().toString());
        } catch (Exception e) {
            kilometers = 0.00;
        }
        newFare = (((kilometers - 2) * 8) + 25);
        setShowFare(newFare);

    }

    public void setShowFare (Double fare) {
        Context context = this;
        AlertDialog.Builder showFare = new AlertDialog.Builder(context);

        if ((newFare == null) || (newFare < 25.00)) {newFare = 25.00;}
        showFare.setTitle("FARE");
        showFare.setMessage("Your fare is Rs. " + (String.format("%.2f", newFare)));
        showFare.show();
    }
    
}
