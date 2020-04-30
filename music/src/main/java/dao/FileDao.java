package dao;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Mr Tang
 *
 */
public class FileDao {
    /**
     *2018年10月24日下午10:41:33
     *这个函数的功能是获取前端的数据集合，将文件打包成File以便后续操作
     */
    @Nullable
    public static List<FileItem> getRequsetFileItems(@NotNull HttpServletRequest request, @NotNull ServletContext servletContext){
        boolean isMultipart=ServletFileUpload.isMultipartContent(request);
        final int MEMORY_THRESHOLD   = 1024 * 1024 * 50;  // 50MB
        final int MAX_FILE_SIZE      = 1024 * 1024 * 50; // 50MB
        final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
        if(isMultipart) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            String str="javax.servelet.context.webapp.";
            factory.setDefaultCharset("utf-8");
//            File repository= new File(servletContext.getContextPath());
//            factory.setRepository(repository);
            factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
            factory.setSizeThreshold(MEMORY_THRESHOLD);
            ServletFileUpload upload=new ServletFileUpload(factory);
            // 设置最大文件上传值
            upload.setFileSizeMax(MAX_FILE_SIZE);
            // 设置最大请求值 (包含文件和表单数据)
            upload.setSizeMax(MAX_REQUEST_SIZE);
            // 中文处理
            upload.setHeaderEncoding("UTF-8");
            try {
                return upload.parseRequest(request);
            }catch (FileUploadException e) {
                // TODO: handle exception
                return null;
            }
        }else {
            return null;
        }
    }
    /**
     *2018年10月24日下午10:37:59
     *这个函数的功能是将文件传到预先设置的绝对路径中，也就是项目里的imgs文件夹
     */
    public static boolean saveMusicFile(@NotNull FileItem item, String fileName) {
        File savePath=new File("webapp\\musicFile");
        if(!savePath.exists()) {
            savePath.mkdirs();
        }
        File uploadFile=new File(savePath+File.separator+fileName);
        try{
            item.write(uploadFile);
            System.out.println("保存文件成功");
            return true;
        }catch(Exception e){
            System.out.println("保存文件失败");
        }
        return false;
    }
    /**
     *2018年10月24日下午8:02:04
     *这个函数的功能是获取当前时间点与1970年的间隔秒数
     */
    public static int getSecondTimestamp(@Nullable Date date){
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime());
        System.out.println(timestamp);
        int length = timestamp.length();
        if (length > 3) {
            return Integer.parseInt(timestamp.substring(0,length-3));
        } else {
            return 0;
        }
    }

    /**
     *
     *2018年10月24日下午8:42:05
     *这个函数的功能是得到新的照片名称
     */
    @NotNull
    public static String getPhotoNewName() {
        Date date=new Date();
        int second=getSecondTimestamp(date);
        String fileName=String.valueOf(second)+".jpg";
        return fileName;
    }

    /**
     *2018年10月24日下午8:48:58
     *这个函数的功能是判断文件后缀是否是jpg格式
     */
    public static boolean isGif(@NotNull FileItem item) {
        String fileFullName=item.getName();
        File fileInfo=new File(fileFullName);
        String suffix = fileInfo.getName().substring(fileInfo.getName().lastIndexOf(".") + 1);
        if(suffix.equals("jpg")) {
            return true;
        }
        return false;
    }
}