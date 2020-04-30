package servlet.Controller;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MusicDao;
import dao.UserDao;
import entity.Music;
import entity.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import utils.DBUtils;

public class FindSinger extends HttpServlet{
    /*@Override
    protected void service(@NotNull HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String singer = uri.substring(uri.lastIndexOf("/")+1, uri.lastIndexOf("."));
        singer = singer.replace("%20"," ");
//        String[] tmp = singer.split("%20");
//        singer = String.join(" ",tmp);

        try {
            processFindSinger(request,response,singer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /*private void processFindSinger(@NotNull HttpServletRequest request, HttpServletResponse response, @Nullable String singer)throws ServletException,IOException{
        request.setCharacterEncoding("utf-8");

        MusicDao dao = new MusicDao();
        List<Music> musics = null;
        System.out.println(singer);
        if(singer!=null) {
            musics = dao.searchMusic(singer);//关键字查询
            for (Music music : musics) {
                System.out.println(music.getUrl());
            }
        }
        request.setAttribute("musics", musics);
        request.getRequestDispatcher("show.jsp").forward(request, response);//转发
    }*/
}
