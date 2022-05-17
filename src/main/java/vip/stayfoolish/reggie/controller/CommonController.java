package vip.stayfoolish.reggie.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vip.stayfoolish.reggie.common.R;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    @Value("${reggie.path}")
    private String basePath;


    /*
     * @Author LiuLiu
     * @Date 2022/5/17 21:27
     * @Description 文件上传
     * @Param
     * @Return
     * @Since version-1.0
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {

        // file 是一个临时文件，需要将文件进行转存，否则程序结束之后，文件会失效。
        log.info(file.toString());

        // 获取文件原始文件名
        String originalFilename = file.getOriginalFilename();

        // 获取文件后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        // 使用 UUID 生成文件名，防止文件名重复
        String newName = UUID.randomUUID().toString() + suffix;

        // 创建一个文件对象
        File dir = new File(basePath);

        // 判断当前目录是否存在
        if (!dir.exists()) {
            // 目录不存在，创建目录
            dir.mkdirs();
        }

        try {
            // 将临时文件转存到指定目录
            file.transferTo(new File(basePath + newName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return R.success(newName);
    }

    /*
     * @Author LiuLiu
     * @Date 2022/5/17 21:26
     * @Description 文件下载。这个对我来说很有难度，API 都不知道
     * @Param
     * @Return
     * @Since version-1.0
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {

        try {
            // 输入流，通过输入流获取内容
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));

            // 输出流，通过输出流将文件回显到浏览器
            ServletOutputStream servletOutputStream = response.getOutputStream();

            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                servletOutputStream.write(bytes, 0, len);
                servletOutputStream.flush();
            }

            // 关闭资源
            servletOutputStream.close();
            fileInputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

















