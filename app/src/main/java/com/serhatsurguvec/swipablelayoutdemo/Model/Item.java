package com.serhatsurguvec.swipablelayoutdemo.Model;

/**
 * Created by serhatsurguvec on 10/14/15.
 */
/**
 * Represents an Item in our application. Each item has a name, id, full size image url and
 * thumbnail url.
 */
public class Item {

    private static final String LARGE_BASE_URL = "http://storage.googleapis.com/androiddevelopers/sample_data/activity_transition/large/";
    private static final String THUMB_BASE_URL = "http://storage.googleapis.com/androiddevelopers/sample_data/activity_transition/thumbs/";

    public static Item[] ITEMS = new Item[] {
            new Item("Flying in the Light", "Romain Guy", "flying_in_the_light.jpg"),
            new Item("Caterpillar", "Romain Guy", "caterpillar.jpg"),
            new Item("Look Me in the Eye", "Romain Guy", "look_me_in_the_eye.jpg"),
            new Item("Flamingo", "Romain Guy", "flamingo.jpg"),
            new Item("Rainbow", "Romain Guy", "rainbow.jpg"),
            new Item("Over there", "Romain Guy", "over_there.jpg"),
            new Item("Jelly Fish 2", "Romain Guy", "jelly_fish_2.jpg"),
            new Item("Lone Pine Sunset", "Romain Guy", "lone_pine_sunset.jpg"),
    };

    public static Item getItem(int id) {
        for (Item item : ITEMS) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    private final String mName;
    private final String mAuthor;
    private final String mFileName;

    Item (String name, String author, String fileName) {
        mName = name;
        mAuthor = author;
        mFileName = fileName;
    }

    public int getId() {
        return mName.hashCode() + mFileName.hashCode();
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getName() {
        return mName;
    }

    public String getPhotoUrl() {
        return LARGE_BASE_URL + mFileName;
    }

    public String getThumbnailUrl() {
        return THUMB_BASE_URL + mFileName;
    }

}
