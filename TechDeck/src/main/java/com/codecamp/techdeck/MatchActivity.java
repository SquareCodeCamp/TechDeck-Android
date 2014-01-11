package com.codecamp.techdeck;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MatchActivity extends Activity {

    Deck deck;
    ViewGroup bioGroup;
    ViewGroup picGroup;
    TextView zoomBio;
    ImageView zoomImage;
    ViewGroup portraitLayout;
    TextView nameView;
    int bioSelectionId = -1;
    int imageSelectionId = -1;
    int numMatches;
    private Handler handler;
    private Button button;
    private int length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        bioGroup = (ViewGroup) findViewById(R.id.bio_layout);
        picGroup = (ViewGroup) findViewById(R.id.pictures_layout);
        zoomBio = (TextView) findViewById(R.id.zoomBioView);
        zoomImage = (ImageView) findViewById(R.id.zoomImageView);
        nameView = (TextView) findViewById(R.id.name);
        portraitLayout = (ViewGroup) findViewById(R.id.portrait_layout);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetSelection();
            }
        });

        handler = new Handler();

        // Do something on another thread.
        Thread fetchDecks = new Thread(new Runnable() {

            @Override
            public void run() {
                final List<Deck> testDecks = ((TechDeckApplication) getApplication()).getTechDecks();

                // Do something back on the main thread, through the handler.
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        deck = testDecks.get(0);
                        buildGame();
                    }
                });
            }
        });
        fetchDecks.start();
    }

    public void enableSelection() {
        button.setVisibility(View.INVISIBLE);

        bioGroup.setAlpha(1.0f);
        picGroup.setAlpha(1.0f);
    }

    public void disableSelection() {
        button.setVisibility(View.VISIBLE);

        bioGroup.setAlpha(0.25f);
        picGroup.setAlpha(0.25f);
    }

    private void buildGame() {

        // Feel bad about this.
        length = bioGroup.getChildCount();

        List<Integer> range = new ArrayList<Integer>(length);
        for (int i = 0; i < bioGroup.getChildCount(); i++) {
            range.add(i);
        }

        List<Integer> pictureOrder = new ArrayList<Integer>(range);
        Collections.shuffle(pictureOrder);

        List<Integer> bioOrder = new ArrayList<Integer>(range);
        Collections.shuffle(bioOrder);

        for (int i = 0; i < length; i++) {
            final Card card = deck.cards[i];

            final BioCardView bioCard = (BioCardView) bioGroup.getChildAt(bioOrder.get(i));
            bioCard.getCardTextView().setText(deck.cards[i].bio);

            final PictureCardView pictureCard = (PictureCardView) picGroup.getChildAt(pictureOrder.get(i));
            Picasso.with(this).load(deck.cards[i].image_url).resizeDimen(R.dimen.card_height, R.dimen.card_width).centerCrop().into(pictureCard.getCardImage());

            bioCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (button.getVisibility() == View.VISIBLE) return;
                    zoomBio.setText(card.bio);
                    bioSelectionId = card.id;
                    if (bioCard.isCardMatched()) {
                        Picasso.with(MatchActivity.this).load(card.image_url).into(zoomImage);
                        nameView.setText(card.name);
                        portraitLayout.setVisibility(View.VISIBLE);
                        imageSelectionId = card.id;
                        disableSelection();
                    } else if (checkResults()) {
                        bioCard.setMatched();
                        pictureCard.setMatched();
                    }
                }
            });

            pictureCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (button.getVisibility() == View.VISIBLE) return;
                    portraitLayout.setVisibility(View.VISIBLE);
                    Picasso.with(MatchActivity.this).load(card.image_url).into(zoomImage);
                    nameView.setText(card.name);
                    imageSelectionId = card.id;
                    if (pictureCard.isCardMatched()) {
                        zoomBio.setText(card.bio);
                        bioSelectionId = card.id;
                        disableSelection();
                    } else if (checkResults()) {
                        bioCard.setMatched();
                        pictureCard.setMatched();
                    }
                }
            });


        }
    }

    private boolean checkResults() {
        if ((bioSelectionId >= 0) && (imageSelectionId >= 0)) {
            disableSelection();
            if (bioSelectionId == imageSelectionId) {
                Toast.makeText(this, R.string.success, Toast.LENGTH_SHORT).show();
                numMatches++;
                if (numMatches == length) {
                    startActivity(new Intent(this, WinnerActivity.class));
                    finish();
                }
                return true;
            } else {
                Toast.makeText(this, R.string.failure, Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }

    private void resetSelection() {
        bioSelectionId = -1;
        imageSelectionId = -1;
        portraitLayout.setVisibility(View.INVISIBLE);
        zoomBio.setText("");
        enableSelection();
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
