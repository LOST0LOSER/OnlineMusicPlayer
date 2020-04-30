package utils;

import entity.Music;

import java.util.Comparator;

public class SongComparator{
    public static class Singer implements Comparator<Music>{
        public int compare(Music e1, Music e2) {
            return Integer.compare(e1.getSinger().compareTo(e2.getSinger()), 0);
        }
    }
}
