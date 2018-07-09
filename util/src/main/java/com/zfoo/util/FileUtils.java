package com.zfoo.util;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 文件操作工具类
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 07.17 11:00
 */
public abstract class FileUtils {

    // The file copy buffer size (30 MB)
    private static final long FILE_COPY_BUFFER_SIZE = IOUtils.BYTES_PER_MB * 30;

    public static final String CHARSET = "utf-8";


    /**
     * User's current working directory
     *
     * @return 绝对路径路径
     */
    public static String getProAbsPath() {
        return System.getProperty("user.dir");
    }

    /**
     * 获取类文件的绝对路径
     *
     * @param clazz 类Class
     * @return 对应类的绝对路径
     */
    public static String getClassAbsPath(Class<?> clazz) {
        File file = new File(clazz.getResource("").getPath());
        return file.getAbsolutePath();
    }


    //---------------------------------搜索文件--------------------------------------

    /**
     * 深度优先搜索文件
     *
     * @param file     需要搜索的文件
     * @param fileName 需要搜索的目标文件名
     * @return 搜索到的文件
     */
    private static File serachFileInProject(File file, String fileName) {
        // System.out.println(file.getName());
        if (file.isFile() && file.getName().equals(fileName)) {
            return file;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File temp : files) {
                File result = serachFileInProject(temp, fileName);
                if (result == null) {
                    continue;
                }
                return result;
            }
        }
        return null;
    }

    /**
     * 广度优先搜索文件
     *
     * @param fileOrDirectory 需要查找的文件夹
     * @return 所有可读的文件
     */
    public static List<File> getAllReadableFiles(File fileOrDirectory) {
        List<File> readableFileList = new ArrayList<>();
        Queue<File> queue = new LinkedList<>();
        queue.add(fileOrDirectory);
        while (!queue.isEmpty()) {
            File file = queue.poll();
            if (file.isDirectory()) {
                for (File f : file.listFiles()) {
                    queue.offer(f);
                }
                continue;
            }

            if (file.canRead()) {
                readableFileList.add(file);
            }
        }

        return readableFileList;
    }

    /**
     * 搜索文件
     *
     * @param file 需要查找的文件
     * @return 如果没有搜索到返回null
     */
    public static File serachFileInProject(File file) {
        return serachFileInProject(new File(getProAbsPath()), file.getName());
    }


    /**
     * 搜索文件
     * <p>
     * 注意：文件名必须是文件全称，包括文件名的后缀
     *
     * @param fileName 文件名的全称，包括文件名的后缀
     * @return 如果没有搜索到返回null
     */
    public static File serachFileInProject(String fileName) {
        return serachFileInProject(new File(getProAbsPath()), fileName);
    }

    //---------------------------------创建，删除文件--------------------------------------

    /**
     * 在path文件夹下创建一个fileName文件
     *
     * @param absPath  绝对路径
     * @param fileName 文件名
     */
    public static File createFile(String absPath, String fileName) throws IOException {
        File file = new File(absPath);

        if (!file.exists()) {
            if (!file.mkdirs()) {
                FormattingTuple message = MessageFormatter.format("Directory [file:{}] could not be created", file);
                throw new IOException(message.getMessage());
            }
        }

        if (!file.isDirectory()) {
            throw new RuntimeException("不是文件夹absPath:" + absPath);
        }

        File newFile = new File(file.getAbsoluteFile() + File.separator + fileName);
        if (newFile.exists()) {
            throw new RuntimeException("文件已经存在fileName:" + fileName);
        }

        try {
            newFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFile;
    }


    /**
     * Deletes a file. If file is a directory, delete it and all sub-directories.
     *
     * @param file file or directory to delete, must not be null
     */
    public static void deleteFile(final File file) throws IOException {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (!f.delete()) {
                        throw new IOException("Unable to delete file: " + file);
                    }
                }
            }
            if (!file.delete()) {
                throw new IOException("Unable to delete file directory: " + file);
            }
        } else {
            boolean filePresent = file.exists();
            if (!file.delete()) {
                if (!filePresent) {
                    throw new FileNotFoundException("File does not exist: " + file);
                }
                throw new IOException("Unable to delete file: " + file);
            }
        }
    }

    // ------------------------------------------------复制文件------------------------------------------------

    /**
     * Copies a file to a new location preserving the file date.
     * <p>
     * This method copies the contents of the specified source file to the
     * specified destination file. The directory holding the destination file is
     * created if it does not exist. If the destination file exists, then this
     * method will overwrite it.
     * <p>
     * <strong>Note:</strong> This method tries to preserve the file's last
     * modified date/times using {@link File#setLastModified(long)}, however
     * it is not guaranteed that the operation will succeed.
     * If the modification operation fails, no indication is provided.
     *
     * @param srcFile  an existing file to copy, must not be null
     * @param destFile the new file, must not be null
     * @throws IOException if source or destination is invalid
     * @throws IOException if an IO error occurs during copying
     * @throws IOException if the output file length is not the same as the input file length after the copy completes
     */
    public static void copyFile(final File srcFile, final File destFile) throws IOException {
        copyFile(srcFile, destFile, true);
    }

    public static void copyFileToDirectory(final File srcFile, final File destDir) throws IOException {
        copyFileToDirectory(srcFile, destDir, true);
    }

    public static void copyFileToDirectory(final File srcFile, final File destDir, final boolean preserveFileDate)
            throws IOException {
        if (destDir == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (destDir.exists() && !destDir.isDirectory()) {
            FormattingTuple message = MessageFormatter.format("Destination [destDir:{}] is not a directory", destDir);
            throw new IllegalArgumentException(message.getMessage());
        }
        final File destFile = new File(destDir, srcFile.getName());
        copyFile(srcFile, destFile, preserveFileDate);
    }

    /**
     * Copies a file to a new location.
     * <p>
     * This method copies the contents of the specified source file
     * to the specified destination file.
     * The directory holding the destination file is created if it does not exist.
     * If the destination file exists, then this method will overwrite it.
     * <p>
     * <strong>Note:</strong> Setting <code>preserveFileDate</code> to
     * {@code true} tries to preserve the file's last modified
     * date/times using {@link File#setLastModified(long)}, however it is
     * not guaranteed that the operation will succeed.
     * If the modification operation fails, no indication is provided.
     *
     * @param srcFile          an existing file to copy, must not be {@code null}
     * @param destFile         the new file, must not be {@code null}
     * @param preserveFileDate true if the file date of the copy
     *                         should be the same as the original
     * @throws IOException if source or destination is invalid
     * @throws IOException if an IO error occurs during copying
     * @throws IOException if the output file length is not the same as the input file length after the copy completes
     */
    public static void copyFile(final File srcFile, final File destFile,
                                final boolean preserveFileDate) throws IOException {
        checkFileRequirements(srcFile, destFile);
        if (srcFile.isDirectory()) {
            FormattingTuple message = MessageFormatter.format("Source [srcFile:{}] exists but is a directory", srcFile);
            throw new IOException(message.getMessage());
        }
        if (srcFile.getCanonicalPath().equals(destFile.getCanonicalPath())) {
            FormattingTuple message = MessageFormatter.format("Source [srcFile:{}] and destination [destFile:{}] are the same", srcFile, destFile);
            throw new IOException(message.getMessage());
        }
        final File parentFile = destFile.getParentFile();
        if (parentFile != null) {
            if (!parentFile.mkdirs() && !parentFile.isDirectory()) {
                FormattingTuple message = MessageFormatter.format("Destination [parentFile:{}] directory cannot be created", parentFile);
                throw new IOException(message.getMessage());
            }
        }
        if (destFile.exists() && !destFile.canWrite()) {
            FormattingTuple message = MessageFormatter.format("Destination [destFile:{}] exists but is read-only", destFile);
            throw new IOException(message.getMessage());
        }
        doCopyFile(srcFile, destFile, preserveFileDate);
    }

    /**
     * checks requirements for file copy
     *
     * @param src  the source file
     * @param dest the destination
     * @throws FileNotFoundException if the destination does not exist
     */
    private static void checkFileRequirements(File src, File dest) throws FileNotFoundException {
        if (src == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (dest == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (!src.exists()) {
            FormattingTuple message = MessageFormatter.format("Source [src:{}] does not exist", src);
            throw new FileNotFoundException(message.getMessage());
        }
    }

    /**
     * Internal copy file method.
     * This caches the original file length, and throws an IOException
     * if the output file length is different from the current input file length.
     * So it may fail if the file changes size.
     * It may also fail with "IllegalArgumentException: Negative size" if the input file is truncated part way
     * through copying the data and the new file size is less than the current position.
     *
     * @param srcFile          the validated source file, must not be {@code null}
     * @param destFile         the validated destination file, must not be {@code null}
     * @param preserveFileDate whether to preserve the file date
     * @throws IOException              if an error occurs
     * @throws IOException              if the output file length is not the same as the input file length after the
     *                                  copy completes
     * @throws IllegalArgumentException "Negative size" if the file is truncated so that the size is less than the
     *                                  position
     */
    private static void doCopyFile(final File srcFile, final File destFile, final boolean preserveFileDate)
            throws IOException {
        if (destFile.exists() && destFile.isDirectory()) {
            FormattingTuple message = MessageFormatter.format("Destination [destFile:{}] exists but is a directory", destFile);
            throw new IOException(message.getMessage());
        }

        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel input = null;
        FileChannel output = null;
        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);
            input = fis.getChannel();
            output = fos.getChannel();
            final long size = input.size(); // TODO See IO-386
            long pos = 0;
            long count;
            while (pos < size) {
                final long remain = size - pos;
                count = remain > FILE_COPY_BUFFER_SIZE ? FILE_COPY_BUFFER_SIZE : remain;
                final long bytesCopied = output.transferFrom(input, pos, count);
                if (bytesCopied == 0) { // IO-385 - can happen if file is truncated after caching the size
                    break; // ensure we don't loop forever
                }
                pos += bytesCopied;
            }
        } finally {
            IOUtils.closeIO(output, fos, input, fis);
        }

        final long srcLen = srcFile.length(); // TODO See IO-386
        final long dstLen = destFile.length(); // TODO See IO-386
        if (srcLen != dstLen) {
            FormattingTuple message = MessageFormatter.arrayFormat("Failed to copy full contents from [srcFile:{}] to [destFile:{}] Expected length:[srcLen:{}] Actual [dstLen:{}]"
                    , new Object[]{srcFile, destFile, srcLen, dstLen});
            throw new IOException(message.getMessage());
        }
        if (preserveFileDate) {
            destFile.setLastModified(srcFile.lastModified());
        }
    }


    // ------------------------------------------------读取文件------------------------------------------------

    /**
     * Reads the contents of a file into a byte array.
     * The file is always closed.
     *
     * @param file the file to read, must not be {@code null}
     * @return the file contents, never {@code null}
     * @throws IOException in case of an I/O error
     * @since 1.1
     */
    public static byte[] readFileToByteArray(final File file) throws IOException {
        InputStream in = null;
        try {
            in = openInputStream(file);
            return IOUtils.toByteArray(in); // Do NOT use file.length() - see IO-453
        } finally {
            IOUtils.closeIO(in);
        }
    }


    public static String readFileToString(final File file) {
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        StringBuilder str = new StringBuilder();
        try {
            fileInputStream = openInputStream(file);
            inputStreamReader = new InputStreamReader(fileInputStream, CHARSET);
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                str.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeIO(bufferedReader, inputStreamReader, fileInputStream);
        }
        return str.toString();
    }


    /**
     * 以追加的方式写入一个content
     *
     * @param file    文件的绝对路径
     * @param content 写入的内容
     */
    public static void writeStringToFile(File file, String content) {
        FileOutputStream fileOutputStream = null;// 字节流
        OutputStreamWriter outputStreamWriter = null;// 转换流，设置编码集和解码集 .处理乱码问题，是字节到字符的桥梁
        BufferedWriter bufferedWriter = null;//处理流中的缓冲流，提高效率
        // 如果不用缓冲流的话，程序是读一个数据，写一个数据，这样在数据量大的程序中非常影响效率。
        // 缓冲流作用是把数据先写入缓冲区，等缓冲区满了，再把数据写到文件里。这样效率就大大提高了
        try {
            fileOutputStream = openOutputStream(file, true);// 以追加的方式打开文件
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, CHARSET);
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(content);// 写数据
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Java的垃圾回收机制不会回收任何的物理资源，只会回收堆内存中对象所占用的内存
            // finally总会被执行，即使try块中和catch块中有return，也会被执行。
            // 用来显示回收数据库连接，网络连接，磁盘文件
            IOUtils.closeIO(bufferedWriter, outputStreamWriter, fileOutputStream);
        }
    }

    //---------------------------------打开，关闭，文件流--------------------------------------

    /**
     * Opens a {@link FileInputStream} for the specified file, providing better
     * error messages than simply calling <code>new FileInputStream(file)</code>.
     *
     * @param file the file to open for input, must not be {@code null}
     * @return a new {@link FileInputStream} for the specified file
     * @throws FileNotFoundException if the file does not exist
     * @throws IOException           if the file object is a directory
     * @throws IOException           if the file cannot be read
     */
    public static FileInputStream openInputStream(final File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                FormattingTuple message = MessageFormatter.format("File [file:{}] exists but is a directory", file);
                throw new IOException(message.getMessage());
            }
            if (!file.canRead()) {
                FormattingTuple message = MessageFormatter.format("File [file:{}] cannot be read", file);
                throw new IOException(message.getMessage());
            }
        } else {
            FormattingTuple message = MessageFormatter.format("File [file:{}] does not exist", file);
            throw new FileNotFoundException(message.getMessage());
        }
        return new FileInputStream(file);
    }

    /**
     * 如果文件不存在，则创建该文件。最好指定为true，以追加的方式打开文件
     * <p>
     * The parent directory will be created if it does not exist.The file will be created if it does not exist.
     *
     * @param file   the file to open for output, must not be {@code null}
     * @param append if {@code true}, then bytes will be added to the end of the file rather than overwriting
     * @return a new {@link FileOutputStream} for the specified file
     * @throws IOException if the file object is a directory
     * @throws IOException if the file cannot be written to
     * @throws IOException if a parent directory needs creating but that fails
     */
    public static FileOutputStream openOutputStream(final File file, final boolean append) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                FormattingTuple message = MessageFormatter.format("File [file:{}] exists but is a directory", file);
                throw new IOException(message.getMessage());
            }
            if (!file.canWrite()) {
                FormattingTuple message = MessageFormatter.format("File [file:{}] cannot be written to", file);
                throw new IOException(message.getMessage());
            }
        } else {
            final File parentFile = file.getParentFile();
            if (parentFile != null) {
                if (!parentFile.mkdirs() && !parentFile.isDirectory()) {
                    FormattingTuple message = MessageFormatter.format("Directory [parentFile:{}] could not be created", parentFile);
                    throw new IOException(message.getMessage());
                }
            }
        }
        return new FileOutputStream(file, append);
    }

}
