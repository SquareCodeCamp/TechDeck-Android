package com.codecamp.techdeck;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class MatchActivity extends Activity {

    Deck deck = new FakeDeck();
    ViewGroup bioGroup;
    ViewGroup picGroup;
    TextView zoomBio;
    ImageView zoomImage;
    int bioSelectionId = -1;
    int imageSelectionId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        bioGroup = (ViewGroup) findViewById(R.id.bio_layout);
        picGroup = (ViewGroup) findViewById(R.id.pictures_layout);
        zoomBio = (TextView) findViewById(R.id.zoomBioView);
        zoomImage = (ImageView) findViewById(R.id.zoomImageView);

        for (int i = 0; i < bioGroup.getChildCount(); i++) {
            final Card card = deck.cards[i];

            final TextView bio = (TextView) bioGroup.getChildAt(i);
            bio.setText(deck.cards[i].bio);

            final PictureCardView pictureCard = (PictureCardView) picGroup.getChildAt(i);
            Picasso.with(this).load(deck.cards[i].url).resizeDimen(R.dimen.card_height, R.dimen.card_width).centerCrop().into(pictureCard.getCardImage());

            bio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    zoomBio.setText(card.bio);
                    bioSelectionId = card.id;
                    checkResults();
                }
            });

            pictureCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Picasso.with(MatchActivity.this).load(card.url).into(zoomImage);
                    imageSelectionId = card.id;
                    checkResults();
                }
            });


        }

    }

    private void checkResults() {
        if ((bioSelectionId >= 0) && (imageSelectionId >= 0)) {
            if (bioSelectionId == imageSelectionId)
            {
                Toast.makeText(this, R.string.success, Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, R.string.failure, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.match, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
