package net.phips4.weorecorder;

/**
 * Created by phips4
 */
public enum StreamType {
    HOUSE_TIME("http://mp3.stream.tb-group.fm/ht.mp3", 6),
    TECHNO_BASE("http://mp3.stream.tb-group.fm/tb.mp3", 5),
    HARD_BASE("http://mp3.stream.tb-group.fm/hb.mp3", 7),
    TRANCE_BASE("http://mp3.stream.tb-group.fm/trb.mp3", 8),
    CORE_TIME("http://mp3.stream.tb-group.fm/ct.mp3", 10),
    CLUB_TIME("http://mp3.stream.tb-group.fm/clt.mp3", 11),
    TEA_TIME("http://mp3.stream.tb-group.fm/tt.mp3", 13);

    private String url;
    private int id;

    // no constructor
    private StreamType(String url, int id) {
        this.url = url;
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public String getStreamUrl() {
        return url;
    }

    public static StreamType parseFrom(String str) {
        switch (str.toLowerCase()) {
            case "ht":
                return StreamType.HOUSE_TIME;

            case "housetime":
                return StreamType.HOUSE_TIME;

            case "tb":
                return StreamType.TECHNO_BASE;

            case "technobase":
                return StreamType.TECHNO_BASE;

            case "hb":
                return StreamType.HARD_BASE;

            case "hardbase":
                return StreamType.HARD_BASE;

            case "trb":
                return StreamType.TRANCE_BASE;

            case "trancebase":
                return StreamType.TRANCE_BASE;

            case "ct":
                return StreamType.CORE_TIME;

            case "coretime":
                return StreamType.CORE_TIME;

            case "clt":
                return StreamType.CLUB_TIME;

            case "clubtime":
                return StreamType.HARD_BASE;

            case "tt":
                return StreamType.TEA_TIME;

            case "teatime":
                return StreamType.TEA_TIME;

            default:
                return null;
        }
    }
}