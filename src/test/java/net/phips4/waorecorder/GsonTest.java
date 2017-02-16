package net.phips4.waorecorder;

import com.google.gson.*;
import net.phips4.waorecorder.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by phips4
 */
public class GsonTest {

    public static void main(String[] args) {
        System.out.println(getDj(StreamType.TECHNO_BASE));

        for ( PlayedTrack pt : getTracksOfDj(StreamType.TECHNO_BASE, getDj(StreamType.TECHNO_BASE))) {
            System.out.println(pt.toString());
        }
    }

    public static String getDj(StreamType type) {
        try {
            String body = Util.getBody("https://api.tb-group.fm/v1/radio");
            JsonParser parser = new JsonParser();
            JsonObject element = (JsonObject) parser.parse(body);
            JsonObject obj = (JsonObject) element.get(type.toString());

            if( obj.get("p") != null) {
                return "Playlist";
            } else {
                return obj.get("dj").toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "unknown";
    }

    public static List<PlayedTrack> getTracksOfDj(StreamType type, String dj) {
        ArrayList<PlayedTrack> list = new ArrayList<PlayedTrack>();

        try {
            String body = Util.getBody("https://api.tb-group.fm/v1/tracklist/"+type.getID()+"?count="+90);
            JsonParser parser = new JsonParser();
            JsonArray arr = (JsonArray) parser.parse(body);

            for( JsonElement element : arr) {
                JsonObject obj = element.getAsJsonObject();

                if(obj.get("u").toString().equalsIgnoreCase(dj)) {

                    list.add(new PlayedTrack(obj.get("t").getAsString(),
                            obj.get("a").getAsString(),
                            obj.get("p").getAsLong(),
                            obj.get("u").getAsString())
                    );
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }
}
