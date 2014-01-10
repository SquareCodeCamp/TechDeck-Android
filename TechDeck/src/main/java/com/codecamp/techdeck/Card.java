package com.codecamp.techdeck;

/**
 * Created by square on 1/10/14.
 */
public class Card {
    int id;
    String name;
    String image_url;
    String bio;
    String wiki_link;

    public Card(int id, String name, String url, String bio, String wiki_link) {
        this.id = id;
        this.name = name;
        this.image_url = url;
        this.bio = bio;
        this.wiki_link = wiki_link;
    }
}
