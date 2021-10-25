package comp5216.sydney.edu.au.myproject_v1.historyPage;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import comp5216.sydney.edu.au.myproject_v1.R;

public class showRequestActivity extends AppCompatActivity {

    TextView titleTV;
    TextView itemDescription1TV;
    TextView itemDescription2TV;
    TextView itemDescription3TV;
    TextView price1TV;
    TextView price2TV;
    TextView price3TV;
    TextView totalPriceTV;
    TextView createTimeTV;
    TextView dueTimeTV;
    TextView statusTV;
    TextView acceptorPhoneNumberTV;
    TextView acceptorNameTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_history_request);

        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");
        String itemDescription1 = bundle.getString("itemDescription1");
        String itemDescription2 = bundle.getString("itemDescription2");
        String itemDescription3 = bundle.getString("itemDescription3");
        String price1 = bundle.getString("price1");
        String price2 = bundle.getString("price2");
        String price3 = bundle.getString("price3");
        String totalPrice = bundle.getString("totalPrice");
        String createTime = bundle.getString("createTime");
        String dueTime = bundle.getString("dueTime");
        String status = bundle.getString("status");
        String acceptorPhoneNumber = bundle.getString("acceptorPhoneNumber");
        String acceptorName = bundle.getString("acceptorName");


        titleTV = findViewById(R.id.requestTitle);
        titleTV.setText(title);
        itemDescription1TV = findViewById(R.id.itemDescription1);
        itemDescription1TV.setText(itemDescription1);
        itemDescription2TV = findViewById(R.id.itemDescription2);
        itemDescription2TV.setText(itemDescription2);
        itemDescription3TV = findViewById(R.id.itemDescription3);
        itemDescription3TV.setText(itemDescription3);
        price1TV = findViewById(R.id.price1);
        price1TV.setText(price1);
        price2TV = findViewById(R.id.price2);
        price2TV.setText(price2);
        price3TV = findViewById(R.id.price3);
        price3TV.setText(price3);
        totalPriceTV = findViewById(R.id.textViewTotalPrice);
        totalPriceTV.setText(totalPrice);
        createTimeTV = findViewById(R.id.textViewCreateTime);
        createTimeTV.setText(createTime);
        dueTimeTV = findViewById(R.id.textViewDueTime);
        dueTimeTV.setText(dueTime);
        statusTV = findViewById(R.id.textViewStatus);
        statusTV.setText(status);
        acceptorPhoneNumberTV = findViewById(R.id.textViewAcceptorPhoneNumber);
        acceptorPhoneNumberTV.setText(acceptorPhoneNumber);
        acceptorNameTV = findViewById(R.id.textViewAcceptorName);
        acceptorNameTV.setText(acceptorName);


        ImageButton imageButton = (ImageButton) this.findViewById(R.id.back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRequestActivity.this.finish();
            }
        });
        Button button = (Button) this.findViewById(R.id.btnAccept);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRequestActivity.this.finish();
            }
        });

    }
}
