package dao;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Time;
import java.lang.String;
import java.util.Date;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.*;
import org.jaudiotagger.audio.flac.*;

import entity.Music;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.flac.FlacTag;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jetbrains.annotations.NotNull;
import utils.DBUtils;
import utils.PathUtils;
import utils.SongComparator;

public class MusicDao {
    /**
     * 查询全部歌单
     */
    @NotNull
    public List<Music> listMusic(int userID) {
        List<Music> musics = new Vector<Music>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "select * from music";
            if(userID>=0) {
                sql = "select * from \n" +
                        "(SELECT * FROM `favorites` WHERE userID = "+userID+") as a\n" +
                        "right JOIN\n" +
                        "music on music.id = a.musicID";
            }
            //select * from music left join favorites on music.id = favorites.musicID;
            conn = DBUtils.getConn();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Music music = new Music();
                    music.setId(rs.getInt("id"));
                    music.setTitle(rs.getString("title"));
                    music.setSinger(rs.getString("singer"));
                    music.setTime(rs.getDate("time"));
                    music.setUrl(rs.getString("url"));
                    //music.setUserid(rs.getInt("userid"));
                    music.setFavTag(rs.getString("userID")!=null);

                    musics.add(music);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            DBUtils.getClose(conn, ps, rs);
        }
        return musics;
    }


    /**
     * 根据关键字查询歌单
     */
    @NotNull
    public List<Music> searchMusic(int userID,String str) {
        List<Music> musics = new Vector<Music>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "select*from music where title like '%" + str + "%'" + " or singer like '%" + str + "%'";
            if(userID>=0) {
                sql = "select * from \n" +
                        "(SELECT * FROM `favorites` WHERE userID = "+userID+") as a\n" +
                        "right JOIN\n(" +
                        sql +") as b on b.id = a.musicID";
            }
            conn = DBUtils.getConn();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Music music = new Music();
                    music.setId(rs.getInt("id"));
                    music.setTitle(rs.getString("title"));
                    music.setSinger(rs.getString("singer"));
                    music.setTime(rs.getDate("time"));
                    music.setUrl(rs.getString("url"));
                    //music.setUserid(rs.getInt("userid"));
                    music.setFavTag(rs.getString("userID")!=null);
                    musics.add(music);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
//			throw new RuntimeException(e);
        } finally {
            DBUtils.getClose(conn, ps, rs);
        }
        return musics;
    }

    public List<Music> favoriteMusic(int userID) {
        List<Music> musics = new Vector<Music>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "select * from \n" +
                    "(SELECT * FROM `favorites` WHERE userID = "+userID+") as a\n" +
                    "left JOIN\n" +
                    "music on music.id = a.musicID";

            //select * from music left join favorites on music.id = favorites.musicID;
            conn = DBUtils.getConn();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Music music = new Music();
                    music.setId(rs.getInt("id"));
                    music.setTitle(rs.getString("title"));
                    music.setSinger(rs.getString("singer"));
                    music.setTime(rs.getDate("time"));
                    music.setUrl(rs.getString("url"));
                    //music.setUserid(rs.getInt("userid"));
                    music.setFavTag(rs.getString("userID")!=null);

                    musics.add(music);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            DBUtils.getClose(conn, ps, rs);
        }
        return musics;
    }


    public void addMusicInfo(String musicType, String musicPath) throws IOException {
        String songName = null, singer = null, author = null;
        String songTimeString = null;
        String fileUrl = musicPath.substring(musicPath.lastIndexOf("\\") + 1,musicPath.length());
        fileUrl = new String(fileUrl.getBytes(StandardCharsets.UTF_8));
        Time songTime = null;
        switch (musicType) {
            case "mp3":
                MP3File musicFile = null;
                try {
                    musicFile = new MP3File(musicPath);    //取mp3文件
                } catch (ReadOnlyFileException | CannotReadException | TagException | InvalidAudioFrameException | IOException e) {
                    e.printStackTrace();
                }
                if (musicFile != null) {
                    MP3AudioHeader mp3header = musicFile.getMP3AudioHeader();
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                    Date transverTime = null;
                    songTimeString = "00:"+mp3header.getTrackLengthAsString();
                    try {
                        transverTime = format.parse(songTimeString);
                        songTime = new Time(transverTime.getTime());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    AbstractID3v2Tag mp3Tag = musicFile.getID3v2Tag();

                    songName = new String(mp3Tag.frameMap.get("TIT2").toString().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
                    singer = new String(mp3Tag.frameMap.get("TPE1").toString().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
//                    author = new String(mp3Tag.frameMap.get("TALB").toString().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
                    songName = PathUtils.correctString(songName);
                    singer = PathUtils.correctString(singer);
                }
                break;
            case "flac":
                try {
                    File flac = new File(musicPath);
                    FlacFileReader flacFileReader = new FlacFileReader();
                    AudioFile flacfile = flacFileReader.read(flac);
                    FlacTag flacTag = (FlacTag) flacfile.getTag();
                    FlacTagReader flacTagReader = new FlacTagReader();
                    AudioHeader flacHeader = flacfile.getAudioHeader();

                    songName = flacTag.getFirst("TITLE");
                    singer = flacTag.getFirst("ARTIST");

                } catch (ReadOnlyFileException | CannotReadException | TagException | InvalidAudioFrameException | IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
        assert songName != null;
        if(songName.equals("")){
            songName = fileUrl;
        }

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtils.getConn();
            ps = conn.prepareStatement("insert into music (title,singer,time,url,userid) values(?,?,?,?,?)");
            ps.setString(1, songName);
            ps.setString(2, singer);
            ps.setTime(3,songTime);
            ps.setString(4, fileUrl);
            ps.setInt(5, 0);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.getClose(conn, ps, null);
        }
    }
}
