package com.sakura.git;

import cn.hutool.core.io.FileUtil;

import java.io.File;
import java.util.List;

/**
 * @ClassName ImageTest
 * @Author Sakura
 * @DateTime 2024-06-08 00:28:49
 * @Version 1.0
 */
public class ImageTest {

    public static void main(String[] args) {

        // 指定要读取的文件夹路径
        String folderPath = "E:\\学习资料\\Git\\1.课件\\image";

        // 使用Hutool FileUtil 列出文件夹下的所有文件
        List<File> fileList = FileUtil.loopFiles(folderPath);

        // 遍历文件列表，重命名每个文件
        for (File file : fileList) {
            // 获取文件所在目录
            String parentDir = file.getParent();
            String fileName = file.getName();
            System.out.println(fileName);
            if (fileName.contains("%5CAppData%")) {
                // 新文件名，增加前缀
                String newFileName = fileName.replaceAll("%5CUsers%5Cmerge%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%", "image-");
                File newFile = new File(parentDir, newFileName);

                // 执行重命名操作，实际上是把文件移动到同一目录下的新文件名
                try {
                    FileUtil.move(file, newFile, false);
                    System.out.println(fileName + ",成功重命名文件：" + file.getName() + " -> " + newFileName);
                } catch (Exception e) {
                    System.out.println("重命名文件失败：" + file.getName() + "，原因：" + e.getMessage());
                }
            }


        }

    }

}
