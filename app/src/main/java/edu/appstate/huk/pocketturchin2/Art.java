package edu.appstate.huk.pocketturchin2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Art {

    public static final List<ArtItem> ITEMS = new ArrayList<ArtItem>();
    public static final List<String> FAVITEMS = new ArrayList<String>();

    public static final Map<String, ArtItem> ITEM_MAP = new HashMap<String, ArtItem>();

    //THIS IS WHERE ALL THE ART ITEMS ARE ADDED
    static{
        ArtItem test1 = new ArtItem("1A","The Picnic","Thomas Cole");
        test1.addDescription("A piece painted prior to the 1860s, The piece displays many layers of depth " +
                "that from the man standing in front of the tree, to the distant mountains the in the background. " +
                "This sense of depth draws the eye naturally towards the left. This coupled with the colors and activity " +
                "being primarily focused on the left side allow the work as a whole to have a strong sense of magnitude " +
                "while also conveying a sense of concise scenery.");
        test1.addPicture(R.drawable.picnic);
        test1.addFavorite(false);

        ArtItem test2 = new ArtItem("1B","Dinner","That Guy");
        test2.addDescription("A fantastic piece done by the very well known artist 'That Guy'");
        test2.addPicture(R.drawable.ic_picnic);
        test2.addFavorite(true);

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
        public boolean favorite;
        public int picture;



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

        public void addPicture(int pic) {
            this.picture = pic;
        }

        public void addFavorite(boolean f)
        {
            this.favorite = f;
        }
    }
}