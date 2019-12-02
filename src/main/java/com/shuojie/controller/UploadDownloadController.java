package com.shuojie.controller;
 
import com.alibaba.fastjson.JSONObject;
import com.shuojie.domain.system.PictureList;
import com.shuojie.service.sysService.PictureListService;
import com.shuojie.utils.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * Created by jack on 2017/10/30.
 */
@RestController
@RequestMapping("/uploadDownload")
public class UploadDownloadController {
    @Autowired
    private PictureListService pictureListService;
    private static final Logger logger = LoggerFactory.getLogger(UploadDownloadController.class);
    @Value("${uploadDir}")
    private String uploadDir;
    Result result=new Result(200,"图片不能为空","");
    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public Result uploadImage(@RequestParam(value = "file",required = true) MultipartFile file) throws RuntimeException {

        if (file.isEmpty()) {

            return result;
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        logger.info("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        logger.info("上传的后缀名为：" + suffixName);
        // 文件上传后的路径
        String filePath = uploadDir;
        // 解决中文问题，liunx下中文路径，图片显示问题
        // fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            logger.info("上传成功后的文件路径未：" + filePath + fileName);
            result.setMessage(fileName+"");
            return result;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        result.setMessage("文件上传失败");
        return result;
    }
    /**
     * 多文件上传
     * @param files
     * @return
     * @throws RuntimeException
     */
    @RequestMapping(value = "/uploadFiles", method = RequestMethod.POST)
    public Result uploadFiles(@RequestParam(value = "file") MultipartFile[] files){
        StringBuffer result1 = new StringBuffer();
        try {
            for (int i = 0; i < files.length; i++) {
                if (files[i] != null) {
                    //调用上传方法
                    String fileName = executeUpload(files[i]);
                    result1.append(fileName+";");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("文件上传失败");
        }
        result.setMessage(result1.toString());
        return result ;
    }

    /**
     * 提取上传方法为公共方法
     * @param file
     * @return
     * @throws Exception
     */
    private String executeUpload(MultipartFile file)throws Exception{
        //文件后缀名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //上传文件名
        String fileName = UUID.randomUUID()+suffix;
        //服务端保存的文件对象
        File serverFile = new File(uploadDir + fileName);
        // 检测是否存在目录
        if (!serverFile.getParentFile().exists()) {
            serverFile.getParentFile().mkdirs();
        }
        //将上传的文件写入到服务器端文件内
        file.transferTo(serverFile);
        return fileName;
    }
    //文件下载相关代码
    @RequestMapping(value = "/downloadImage",method = RequestMethod.GET)
    public String downloadImage(String imageName,HttpServletRequest request, HttpServletResponse response) {
        //String fileName = "123.JPG";
        logger.debug("the imageName is : "+imageName);
        String fileUrl = uploadDir+imageName;
        if (fileUrl != null) {
            //当前是从该工程的WEB-INF//File//下获取文件(该目录可以在下面一行代码配置)然后下载到C:\\users\\downloads即本机的默认下载的目录
           /* String realPath = request.getServletContext().getRealPath(
                    "//WEB-INF//");*/
            /*File file = new File(realPath, fileName);*/
            File file = new File(fileUrl);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition",
                        "attachment;fileName=" + imageName);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("success");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }

    @GetMapping("/download")
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) {
        String imageName = "thumb.jpg";// 文件名
        if (imageName != null) {
            String fileUrl = uploadDir+imageName;
            //设置文件路径
            File file = new File(fileUrl);
            //File file = new File(realPath , fileName);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + imageName);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    return "下载成功";
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return "下载失败";
    }
    @RequestMapping( "/test")
    public List<PictureList> test(@RequestBody PictureList pictureList){
        List<PictureList> pictureLists = pictureListService.selectList(pictureList);

        pictureLists.forEach(System.out::println);
        return pictureLists;
    }
}