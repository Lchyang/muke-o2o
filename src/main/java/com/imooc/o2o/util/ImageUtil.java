package com.imooc.o2o.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

public class ImageUtil {

    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);
    private static String basePath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
    private static final SimpleDateFormat sDateFormate = new SimpleDateFormat("yyyMMddHHmmss");
    private static final Random r = new Random();

    /**
     * 将CommonsMutipartFile转化成File
     */
    public static File transferCommonsMutipartFileToFile(CommonsMultipartFile cFile) {
        File newFile = new File(Objects.requireNonNull(cFile.getOriginalFilename()));
        try {
            cFile.transferTo(newFile);
        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return newFile;
    }

    /**
     * 处理用户传过来的文件流,并返回相对路径
     */
    public static String GenerateThumbnail(InputStream thumbnailInputStream, String fileName, String targetAddr) {
        // 获取随机文件名
        String realFileName = getRandomFileName();
        // 获取文件扩展名
        String extension = getFileExtension(fileName);
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        logger.debug("current relativeAddr:" + relativeAddr);
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        logger.debug("current complete Addr:" + PathUtil.getImgBasePath() + relativeAddr);
        try {
            logger.debug("basePath: {}", basePath);
            Thumbnails.of(thumbnailInputStream)
                    .size(200, 200).watermark(Positions.BOTTOM_RIGHT, ImageIO
                    .read(new File(basePath + "/watermark.jpg")), 0.25f)
                    .outputQuality(0.8f).toFile(dest);
        } catch (IOException e) {
            logger.error(e.toString());
            throw new RuntimeException("创建缩略图失败: " + e.toString());
        }
        return relativeAddr;
    }

    /**
     * 创建目标路径所涉及到的目录，即/home/work/xiangzi/xxx.jpg
     * home work xiangzi 这三个文件夹自动创建。
     *
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

    /**
     * 获取输入文件名的扩展名
     *
     */
    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成随机文件，当前年月日小时分钟秒钟+五位随机数
     */
    private static String getRandomFileName() {
        // 获取随机五位数
        int rannum = r.nextInt(89999) + 10000;
        String nowTimeStr = sDateFormate.format(new Date());
        return nowTimeStr + rannum;
    }

    public static void main(String[] args) throws IOException {
        Thumbnails.of(new File("/Users/lichunyang/Pictures/picture.jpg")).size(200, 200)
                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.jpg")), 0.25f)
                .outputQuality(0.8f).toFile("/Users/lichunyang/Pictures/picturenew.jpg");
    }

    /**
     * storePath是文件还是路径
     * 如果是文件删除文件
     * 如果是路径删除路径下的所有文件
     *
     */
    public static void deleteFileOrPath(String storePath) {
        File filePath = new File(PathUtil.getImgBasePath() + storePath);
        if (filePath.exists()) {
            if (filePath.isDirectory()) {
                File[] file = filePath.listFiles();
                for (int i = 0; i < file.length; i++) {
                    file[i].delete();
                }
            }
            filePath.delete();
        }
    }
}
