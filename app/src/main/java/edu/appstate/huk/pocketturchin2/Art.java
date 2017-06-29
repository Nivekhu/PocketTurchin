package edu.appstate.huk.pocketturchin2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Art {

    public static final List<ArtItem> ITEMS = new ArrayList<ArtItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, ArtItem> ITEM_MAP = new HashMap<String, ArtItem>();


    static{
        ArtItem test1 = new ArtItem("1A","Picnic","Some Guy");
        ArtItem test2 = new ArtItem("1B","Dinner","That Guy");
        test1.addDescription("A beautiful picnic scene is seen in this piece done by" +
                "the not as well known artist 'Some Guy'.");
        test2.addDescription("A fantastic piece done by the very well known artist 'That Guy'");
        addItem(test1);
        addItem(test2);

    }


    private static void addItem(ArtItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }


    /**
     * A dummy item representing a piece of content.
     */
    public static class ArtItem {
        public String id;
        public String title;
        public String artist;
        public String description;


        public ArtItem(String id, String title, String artist) {
            this.id = id;
            this.title = title;
            this.artist = artist;
            description = "";
        }

        public void addDescription(String description)
        {
            this.description = description;
        }
    }
}