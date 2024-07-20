package com.example.doctormaster.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import com.example.doctormaster.R;
import com.example.doctormaster.activities.DoctorDetailActivity;

public class FieldItemView extends AppCompatTextView {

    public FieldItemView(Context context, String detail, String field) {
        super(context);
        init(context, detail, field);
    }

    public FieldItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, null, null);
    }

    public FieldItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, null, null);
    }

    private void init(Context context, String detail, String field) {
        this.setText(detail);
        this.setTypeface(null, Typeface.BOLD);
        this.setTextSize(24);
        this.setTextColor(Color.WHITE);  // Set the text color to white
        this.setBackgroundColor(Color.parseColor("#308C9B"));  // Set the background color to a darker blue
        this.setPadding(32, 24, 32, 24);  // Increase padding for larger size
        this.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);  // Center the text vertically and align start

        // Add right arrow drawable
        this.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(context, R.drawable.ic_right_arrow), null);
        this.setCompoundDrawablePadding(16);  // Add padding between text and drawable

        // Define layout parameters
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = 0;  // Make the width stretch across the available space
        params.height = 100;  // Set a fixed height to make the squares larger
        params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1);
        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);  // Stretch horizontally
        params.setMargins(8, 8, 8, 8);  // Add margins between items
        this.setLayoutParams(params);

        this.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(context, DoctorDetailActivity.class);
                newIntent.putExtra("speciality", detail);
                newIntent.putExtra("field", field);
                context.startActivity(newIntent);
            }
        });
    }
}
