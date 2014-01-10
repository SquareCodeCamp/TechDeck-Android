package com.codecamp.techdeck;

/**
 * Created by square on 1/10/14.
 */
public class FakeDeck extends Deck {
    public FakeDeck() {
        name = "Test Deck";
        cards = new Card[4];
        cards[0] = new Card(0, "Dog", "http://1.bp.blogspot.com/-1uQRYMklACU/ToQ6aL-5uUI/AAAAAAAAAgQ/9_u0922cL14/s1600/cute-puppy-dog-wallpapers.jpg", "Ruff", "http://en.wikipedia.org/wiki/Dog");
        cards[1] = new Card(1, "Cat", "http://upload.wikimedia.org/wikipedia/commons/2/22/Turkish_Van_Cat.jpg", "Meow", "http://en.wikipedia.org/wiki/Cat");
        cards[2] = new Card(2, "Dolphin", "http://www.defenders.org/sites/default/files/styles/large/public/dolphin-kristian-sekulic-isp.jpg", "Eeee", "http://en.wikipedia.org/wiki/Dolphin");
        cards[3] = new Card(3, "Fox", "http://www.state.nj.us/dep/fgw/images/wildlife/fox_gl.jpg", "Ringdingding","http://en.wikipedia.org/wiki/Fox");
    }
}
