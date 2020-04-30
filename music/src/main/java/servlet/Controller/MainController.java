package servlet.Controller;

import dao.FileDao;
import dao.MusicDao;
import dao.UserDao;
import entity.Music;
import entity.User;
import org.apache.commons.fileupload.FileItem;
import org.jetbrains.annotations.NotNull;
import utils.SongComparator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.Date;
import java.util.List;

public class MainController extends HttpServlet {

    @Override
    protected void service(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String uri = request.getRequestURI();
        String path = uri.substring(uri.lastIndexOf("/") + 1, uri.lastIndexOf("."));
        if (path.lastIndexOf("-") != -1) {
            path = path.substring(0, path.indexOf("-"));
        }

        switch (path) {
            case "register":
                processRegister(request, response);
                break;
            case "login":
                processLogin(request, response);
                break;
            case "findMusic":
                try {
                    processFindMusic(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "cancel":
                processCancel(request, response);
                break;
            case "message":
                processMessage(request, response);
                break;
            case "UploadMusic":
                processUploadMusic(request, response);
                break;
            case "toggleFavorite":
                processToggleFavorite(request, response);
                break;
            case "myFavorites":
                processMyFavorites(request, response);
                break;
        }
    }


    /**
     * 上传音乐
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void processUploadMusic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String UPLOAD_PATH = request.getServletContext().getRealPath("./") + "musicFile";
        UPLOAD_PATH = URLDecoder.decode(UPLOAD_PATH, "utf-8");
        ServletContext servletContext = null;
        servletContext = request.getSession().getServletContext();
        servletContext.getContextPath();
        List<FileItem> fileList = FileDao.getRequsetFileItems(request, servletContext);
        String message = "";

        try {
            if (fileList != null && fileList.size() > 0) {
                // 迭代表单数据
                for (FileItem item : fileList) {
                    // 处理不在表单中的字段
					if(item.isFormField()){
						continue;
					}
                    String fileName = new File(item.getName()).getName();
					fileName = URLDecoder.decode(fileName,"utf-8");
                    boolean isMusic = item.getContentType().contains("audio");
                    if (isMusic) {
                        String filePath = UPLOAD_PATH + File.separator + fileName;
                        File storeFile = new File(filePath);
                        // 在控制台输出文件的上传路径
                        String musicType = fileName.substring(fileName.lastIndexOf(".")+1);
                        MusicDao curMusicDao = new MusicDao();
                        System.out.println(filePath);
                        // 保存文件到硬盘
                        curMusicDao.addMusicInfo(musicType,filePath); //上传信息到数据库
                        if(!storeFile.exists()) {
                            item.write(storeFile);
                            message = "音乐上传成功!";
                        }
                        request.setAttribute("Message",
                                "音乐上传成功!");
                    } else {
                        request.getSession().setAttribute("Message", "该文件不是MP3或Flac");
						message = "非音乐文件";
                        System.out.println("非音乐文件");
                    }
                }
            }
        } catch (Exception e) {
        	message = "上传失败";
            System.out.println("上传失败");
            e.printStackTrace();
        }
        PrintWriter out = response.getWriter();
        out.println("<script>" +
//				"window.location.href='findMusic.do'"+
				"alert('hello');" +
				"</script>");

        request.getRequestDispatcher("findMusic.do").forward(request, response);//转发
//        response.sendRedirect("findMusic.do");
    }

    private void processMyFavorites(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        MusicDao dao = new MusicDao();
        List<Music> musics = null;
        User user = (User)request.getSession().getAttribute("user");
        int userID = -1;
        if(user != null) {
            userID = user.getId();
        }
        musics = dao.favoriteMusic(userID);//关键字查询

        musics.sort(new SongComparator.Singer());
        for (Music music : musics) {
            System.out.println(music.getUrl());
        }
        request.getSession().setAttribute("musics", musics);
        request.getRequestDispatcher("show.jsp").forward(request, response);//转发

    }

    /**
     * 添加收藏
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void processToggleFavorite(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response) throws ServletException, IOException{
        // TODO Auto-generated method stub
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        int musicID = Integer.valueOf(request.getParameter("musicId"));
        int userID = Integer.valueOf(request.getParameter("userId"));
        UserDao dao = new UserDao();
        dao.toggleFavorite(userID,musicID);
        //request.getRequestDispatcher("show.jsp").forward(request, response);
    }

    /**
     * 留言
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void processMessage(@NotNull HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String username = request.getParameter("username");
        String comment = request.getParameter("comment");

//		request.getParameter("username").

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
        String time = sdf.format(date);
        String str = username + "留言:" + comment + "   " + time;
        List<String> messages = (List<String>) request.getSession().getAttribute("messages");
        if (messages == null) {
            messages = new Vector<String>();
        }
        messages.add(str);
        request.getSession().setAttribute("messages", messages);//session绑定
        request.getRequestDispatcher("message.jsp").forward(request, response);//转发

    }


    /**
     * 注销登陆
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void processCancel(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response) throws IOException {
        request.getSession().invalidate();//删除
        response.sendRedirect("index.jsp");
    }


    /**
     * 歌单
     *
     * @throws IOException
     * @throws ServletException
     */
    private void processFindMusic(@NotNull HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String str = request.getParameter("str");
        MusicDao dao = new MusicDao();
        List<Music> musics = null;
        User user = (User)request.getSession().getAttribute("user");
        int userID = -1;
        if(user != null) {
            userID = user.getId();
        }
        if (str != null) {
            musics = dao.searchMusic(userID,str);//关键字查询
        } else {
            musics = dao.listMusic(userID);
        }

        musics.sort(new SongComparator.Singer());
        for (Music music : musics) {
            System.out.println(music.getUrl());
        }
        request.getSession().setAttribute("musics", musics);
        request.getRequestDispatcher("show.jsp").forward(request, response);//转发
    }

//	private void processFindSinger(HttpServletRequest request, HttpServletResponse response,String singer) throws SQLException,ServletException,IOException{
//		try {
//
//		}
//	}


    /**
     * 登录
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void processLogin(@NotNull HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String validatecode = request.getParameter("validatecode").toLowerCase();
        UserDao dao = new UserDao();

        try {
            User user = dao.selectUser(username);
            if (user != null && user.getPassword().equals(password)) {
                String code = request.getSession().getAttribute("code").toString().toLowerCase();//获取绑定数据
                if (!validatecode.equalsIgnoreCase(code)) {
                    request.setAttribute("login", "验证码错误");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }else {
                    request.getSession().setAttribute("user", user);//绑定数据
                    request.getRequestDispatcher("index.jsp").forward(request, response);//转发到首页
                }
            } else {
                request.setAttribute("login", "用户名或密码错误");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "网络不稳定,请稍后再尝试");
            request.getRequestDispatcher("err.jsp").forward(request, response);
        }
    }


    /**
     * 注册
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void processRegister(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String OKpassword = request.getParameter("OKpassword");
        String gender = request.getParameter("gender");
        String age = request.getParameter("age");
        String email = request.getParameter("email");
        if (username == null || password == null || age == null || email == null || username.equals("") || password.equals("") || age.equals("") || email.equals("")) {
            request.setAttribute("error", "内容不能为空");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {
            if (password.equals(OKpassword)) {
                UserDao dao = new UserDao();
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                user.setGender(gender);
                user.setAge(Integer.parseInt(age));
                user.setEmail(email);
                try {
                    dao.insertUser(user);
                    response.sendRedirect("login.jsp");//重定向到登陆页面
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("msg", "网络不稳定,请稍后再尝试");
                    request.getRequestDispatcher("err.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("error", "两次输入的密码不一致！");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
            }
        }
    }


}
