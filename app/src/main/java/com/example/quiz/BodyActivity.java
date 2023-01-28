package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.List;

public class BodyActivity extends AppCompatActivity implements View.OnLongClickListener, View.OnDragListener{
    ImageView imageView;
    FrameLayout frameLayout;
    List<Integer> bonesList = new ArrayList<>();
    List<Integer> boneFrameList = new ArrayList<>();

    List<Integer> partsList = new ArrayList<>();
    List<Integer> frameList = new ArrayList<>();
    private int correctAnswers = 0;

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body);

        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.v4_skeleton_shadow);
        frameLayout = (FrameLayout) findViewById(R.id.body_frame);
        implementLists();
        // if category is skelet
        partsList = new ArrayList<>(bonesList);
        frameList = new ArrayList<>(boneFrameList);
        LinearLayout tableLayout = (LinearLayout) findViewById(R.id.table_layout);
        tableLayout.addView(tableImageView(partsList.get(0)));
        partsList.remove(0);
        tableLayout.addView(tableImageView(partsList.get(0)));
        partsList.remove(0);

        mediaPlayer = MediaPlayer.create(this, R.raw.game_level);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        implementEvents();
    }

    public void playMusic(int resId){
        MediaPlayer mp = MediaPlayer.create(this, resId);
        mp.start();
    }

    //Implement lists
    private void implementLists() {
        bonesList.add(R.drawable.v4_skeletone_part_panal_0);
        bonesList.add(R.drawable.v4_skeletone_part_panal_1);
        bonesList.add(R.drawable.v4_skeletone_part_panal_2);
        bonesList.add(R.drawable.v4_skeletone_part_panal_3);
        bonesList.add(R.drawable.v4_skeletone_part_panal_4);
        bonesList.add(R.drawable.v4_skeletone_part_panal_5);
        bonesList.add(R.drawable.v4_skeletone_part_panal_6);

        boneFrameList.add(R.drawable.v4_skeletone_part_use_0);
        boneFrameList.add(R.drawable.v4_skeletone_part_use_1);
        boneFrameList.add(R.drawable.v4_skeletone_part_use_2);
        boneFrameList.add(R.drawable.v4_skeletone_part_use_3);
        boneFrameList.add(R.drawable.v4_skeletone_part_use_4);
        boneFrameList.add(R.drawable.v4_skeletone_part_use_5);
        boneFrameList.add(R.drawable.v4_skeletone_part_use_6);
    }

    //Implement long click and drag listener
    private void implementEvents() {
        findViewById(R.id.body_layout).setOnDragListener(BodyActivity.this);
        findViewById(R.id.parts_layout).setOnDragListener(BodyActivity.this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
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
                    if (!partsList.isEmpty()) {
                        owner.addView(tableImageView(partsList.get(0)));
                        partsList.remove(0);
                    }
                    // show part in shadow
                    if(bonesList.contains(v.getId())){
                        setBlackboardResource(boneFrameList.get(bonesList.indexOf(v.getId())));
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
                    if (correctAnswers == bonesList.size()) {
                        frameLayout.removeAllViews();
                        imageView.setImageResource(R.drawable.v3_skeleton);
                        setBlackboard(imageView);
                        // TODO should wait some seconds
                        playMusic(R.raw.complete);
                        setBlackboard(imageView);
                        new CountDownTimer(2100,1000){
                            public void onTick(long millisUntilFinished){
                                YoYo.with(Techniques.Tada)
                                        .duration(700)
                                        .repeat(3)
                                        .playOn(imageView);
                            }
                            public void onFinish(){
                                Intent intent = new Intent(BodyActivity.this, GameWonActivity.class);
                                startActivity(intent);
                                mediaPlayer.reset();
                                finish();

                            }
                        } .start();
                    }
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

    private int dpAsPixels(int dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    private void setBlackboardResource(int resId) {
        ImageView ivPart = new ImageView(BodyActivity.this);
        ivPart.setImageResource(resId);
        ivPart.setId(resId);
        int padding = dpAsPixels(30);
        ivPart.setPadding(padding, padding, padding, padding);
        frameLayout.addView(ivPart);
        correctAnswers++;
    }

    private void setBlackboard(ImageView ivPart) {
        int padding = dpAsPixels(30);
        ivPart.setPadding(padding, padding, padding, padding);
        frameLayout.removeAllViews();
        frameLayout.addView(ivPart);
//        YoYo.with(Techniques.Tada)
//                .duration(700)
//                .repeat(5)
//                .playOn(ivPart);
    }

    private ImageView tableImageView(int resId) {
        ImageView ivPart = new ImageView(BodyActivity.this);
        ivPart.setImageResource(resId);
        ivPart.setId(resId);
        int margin = dpAsPixels(10);
        ivPart.setTag("");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(margin, margin, margin, margin);
        ivPart.setLayoutParams(lp);
        ivPart.setOnClickListener(view -> {
            Intent intent = new Intent(BodyActivity.this, QuestionGameActivity.class);
            intent.putExtra("bodyPartId", view.getId());
            startActivity(intent);
        });
        return ivPart;
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
        if (data != 0 && !partsList.contains(data)) {
            ImageView iv = (ImageView) findViewById(data);
            if (iv != null) {
                iv.setOnLongClickListener(BodyActivity.this);
                iv.setOnClickListener(null);
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .repeat(5)
                        .playOn(iv);

                iv.setOnTouchListener((v, event) -> {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        ClipData data1 = ClipData.newPlainText("", "");
                        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(iv);

                        iv.startDragAndDrop(data1, shadowBuilder, iv, 0);
                        iv.setVisibility(View.INVISIBLE);
                        return true;
                    } else {
                        return false;
                    }
                });
            }
        }
    }
}