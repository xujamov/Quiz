package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class BodyActivity extends AppCompatActivity implements View.OnLongClickListener, View.OnDragListener{
    ImageView imageView, bone1, bone2, bone3, bone4;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body);

        imageView = (ImageView) findViewById(R.id.imageView);
        bone1 = (ImageView) findViewById(R.id.bone1);
        bone2 = (ImageView) findViewById(R.id.bone2);
        bone3 = (ImageView) findViewById(R.id.bone3);
        bone4 = (ImageView) findViewById(R.id.bone4);
        frameLayout = (FrameLayout) findViewById(R.id.body_frame);

        implementEvents();
    }

    //Implement long click and drag listener
    private void implementEvents() {
        findViewById(R.id.body_layout).setOnDragListener(BodyActivity.this);
        findViewById(R.id.parts_layout).setOnDragListener(BodyActivity.this);
    }

    //This is onclick listener for button
    //it will navigate from this activity to MainGameActivity
    public void PlayGame(View view) {
        Intent intent = new Intent(BodyActivity.this, MainGameActivity.class);
        intent.putExtra("bodyPartId", view.getId());
        startActivity(intent);
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        // Defines a variable to store the action type for the incoming event
        int action = dragEvent.getAction();

        // Handles each of the expected events
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:

                Log.e("DRAG_EVENT:", "ACTION_DRAG_STARTED");

                // Determines if this View can accept the dragged data
                if (dragEvent.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    // if you want to apply color when drag started to your view you can uncomment below lines
                    // to give any color tint to the View to indicate that it can accept
                    // data.
                    //view.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);

                    // Invalidate the view to force a redraw in the new tint
                    // view.invalidate();

                    // returns true to indicate that the View can accept the dragged data.
                    return true;

                }

                // Returns false. During the current drag and drop operation, this View will
                // not receive events again until ACTION_DRAG_ENDED is sent.
                return false;

            case DragEvent.ACTION_DRAG_ENTERED:

                Log.e("DRAG_EVENT:", "ACTION_DRAG_ENTERED");

                // Applies a MAGENTA or any color tint to the View,
                // Return true; the return value is ignored.

//                view.getBackground().setColorFilter(Color.MAGENTA, PorterDuff.Mode.SRC_IN);

                // Invalidate the view to force a redraw in the new tint
                view.invalidate();

                return true;

            case DragEvent.ACTION_DRAG_LOCATION:

                Log.e("DRAG_EVENT:", "ACTION_DRAG_LOCATION");

                // Ignore the event
                return true;

            case DragEvent.ACTION_DRAG_EXITED:

                Log.e("DRAG_EVENT:", "ACTION_DRAG_EXITED");

                // Re-sets the color tint to blue, if you had set the BLUE color or any color in ACTION_DRAG_STARTED. Returns true; the return value is ignored.

                //  view.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);

                //If u had not provided any color in ACTION_DRAG_STARTED then clear color filter.
//                view.getBackground().clearColorFilter();

                // Invalidate the view to force a redraw in the new tint
                view.invalidate();

                return true;

            case DragEvent.ACTION_DROP:

                Log.e("DRAG_EVENT:", "ACTION_DROP");

                // Gets the item containing the dragged data
                ClipData.Item item = dragEvent.getClipData().getItemAt(0);

                // Gets the text data from the item.
                String dragData = item.getText().toString();

                // Turns off any color tints
//                view.getBackground().clearColorFilter();

                // Invalidates the view to force a redraw
                view.invalidate();

                //get dragged view
                View v = (View) dragEvent.getLocalState();
                ViewGroup owner = (ViewGroup) v.getParent();

                LinearLayout container = (LinearLayout) view; //caste the view into LinearLayout as our drag acceptable layout is LinearLayout

                if (container.toString().contains("parts_layout")) {
                    v.setVisibility(View.VISIBLE);//finally set Visibility to VISIBLE
                } else {
                    owner.removeView(v); //remove the dragged view
                    // show part in shadow
                    switch (dragData) {
                        case "skelet1":
                            setImageResource(R.drawable.v4_skeletone_part_use_0);
                            break;
                        case "skelet2":
                            setImageResource(R.drawable.v4_skeletone_part_use_1);
                            break;
                        case "skelet3":
                            setImageResource(R.drawable.v4_skeletone_part_use_5);
                            break;
                        case "skelet4":
                            setImageResource(R.drawable.v4_skeletone_part_use_2);
                            break;
                    }
                }



                // Returns true. DragEvent.getResult() will return true.
                return true;

            case DragEvent.ACTION_DRAG_ENDED:

                Log.e("DRAG_EVENT:", "ACTION_DRAG_ENDED");

                // Turns off any color tinting
//                view.getBackground().clearColorFilter();

                // Invalidates the view to force a redraw
                view.invalidate();

                // invoke getResult(), and displays what happened.
                if (dragEvent.getResult()) {

                }

                // returns true; the value is ignored.
                return true;

            // An unknown action type was received.
            default:
                Log.e("DragDrop Example", "Unknown action type received by OnDragListener.");
                break;
        }
        return false;
    }

    private void setImageResource (int resId) {
        ImageView ivPart = new ImageView(BodyActivity.this);
        ivPart.setImageResource(resId);
        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (30 * scale + 0.5f);
        ivPart.setPadding(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels);
        frameLayout.addView(ivPart);
    }

    @Override
    public boolean onLongClick(View view) {

        // Create a new ClipData.Item from the tag
        ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());

        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};

        // Create a new ClipData using the tag as a label, the plain text MIME type, and
        // the already-created item. This will create a new ClipDescription object within the
        // ClipData, and set its MIME type entry to "text/plain"
        ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);

        // Instantiates the drag shadow builder.
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

        // Starts the drag
        view.startDragAndDrop(data //data to be dragged
                , shadowBuilder //drag shadow
                , view //local data about the drag and drop operation
                , 0 //flags (not currently used, set to 0)
        );

        //Set view visibility to INVISIBLE as we are going to drag the view
        view.setVisibility(View.INVISIBLE);

        return true;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(BodyActivity.this);
        int data = prefs.getInt("bodyPartId", 0); //no id: default value
        switch (data) {
            case R.id.bone1:
                bone1.setOnLongClickListener(BodyActivity.this);
                bone1.setOnClickListener(null);
                break;
            case R.id.bone2:
                bone2.setOnLongClickListener(BodyActivity.this);
                bone2.setOnClickListener(null);
                break;
            case R.id.bone3:
                bone3.setOnLongClickListener(BodyActivity.this);
                bone3.setOnClickListener(null);
                break;
            case R.id.bone4:
                bone4.setOnLongClickListener(BodyActivity.this);
                bone4.setOnClickListener(null);
                break;
        }
    }
}