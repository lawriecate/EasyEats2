package com.lawriecate.apps.easyeats2;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MealDetailActivity extends AppCompatActivity {
    public Meal meal;
    public MenuChangeListener menuChangeListener;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        setContentView(R.layout.activity_meal_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        final Meal meal = this.getIntent().getExtras().getParcelable("meal");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.video_play);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //       .setAction("Action", null).show();
                Intent intent = new Intent(view.getContext(), VideoActivity.class);
                intent.putExtra("video", meal.getVideo());
                startActivity(intent);
            }
        });

        FloatingActionButton btnCookingView = (FloatingActionButton) findViewById(R.id.button_cooking_view);
        btnCookingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MealCookingViewActivity.class);
                intent.putExtra("meal",meal);
                startActivity(intent);
            }
        });

        FloatingActionButton addGroceriesBtn = (FloatingActionButton) findViewById(R.id.add_groceries_button);
        addGroceriesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHandler db = new DatabaseHandler(view.getContext());
                for (String ingredient : meal.getIngredients()
                     ) {
                    Log.i("ING",ingredient + " added");
                    db.addGrocery(new Grocery(ingredient));
                   // Toast.makeText(view.getContext(),"Ingredients Added To Groceries List",Toast.LENGTH_SHORT).show();
                    Snackbar.make(view, "Ingredients Added To Groceries List", Snackbar.LENGTH_LONG)
                            .setAction("Tap To View",new View.OnClickListener(){
                                @Override
                                //On click function
                                public void onClick(View view) {
// code to switch main activity to groceries view
                                }
                            }).show();


                }
            }
        });

        Button shareBtn = (Button) findViewById(R.id.shareButton);
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle(meal.getTitle())
                            .setImageUrl(Uri.parse(meal.getImage()))
                            .setContentDescription(
                                   meal.getDescription())
                            .setContentUrl(Uri.parse("http://easyeats.lawriecate.com"))
                            .build();

                    shareDialog.show(linkContent);
                }
            }
        });

        //add_groceries_button


        setTitle(meal.getTitle());
        TextView content = (TextView) findViewById(R.id.meal_detail_content_text);
        String contentText = meal.getDescription();
        contentText = contentText + "\n\nIngredients\n";
        int i = 1;
        for (String ingredient : meal.getIngredients()
             ) {
            contentText = contentText + i + ") " + ingredient + "\n";
            i++;

        }
        i =1;
        contentText = contentText + "\n\nInstructions\n";
        for (String instruction : meal.getInstructions()
                ) {
            contentText = contentText + i + ") " + instruction + "\n";
            i++;

        }

        content.setText(contentText);

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        final ImageView img = new ImageView(this);
        Picasso.with(this)
                .load(meal.getImage())
                .fit()
                .centerCrop()
                .into(img, new Callback() {
                    @Override
                    public void onSuccess() {

                        collapsingToolbarLayout.setBackground(img.getDrawable());
                    }

                    @Override
                    public void onError() {

                    }
                });
    }

}
