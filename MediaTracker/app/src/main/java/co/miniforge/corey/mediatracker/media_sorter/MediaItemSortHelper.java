package co.miniforge.corey.mediatracker.media_sorter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import co.miniforge.corey.mediatracker.model.MediaItem;

/**
 * Created by Jeff on 11/17/2017.
 */

public class MediaItemSortHelper {
    static public List<MediaItem> sortByName(List<MediaItem> items){
        List<MediaItem> newList = new LinkedList<>();

        //Initialize a map and array to store the values of the title & item
        HashMap<String, MediaItem> map = new HashMap<>();
        String[] titles = new String[items.size()];
        for(int i = 0; i < items.size(); i++){
            MediaItem item = items.get(i);

            map.put(item.title, item);
            titles[i] = (item.title);
        }

        Arrays.sort(titles);

        for(String s : titles){
            newList.add(map.get(s));
        }

        return newList;
    }
    static public List<MediaItem> sortByType(List<MediaItem> items){
        MediaItem[] itemArray = new MediaItem[items.size()];
        Arrays.sort(itemArray, new Comparator<MediaItem>() {
            @Override
            public int compare(MediaItem mediaItem, MediaItem t1) {
                return mediaItem.type.compareTo(t1.type);
            }
        });

        return new LinkedList<>(Arrays.asList(itemArray));
    }
}
