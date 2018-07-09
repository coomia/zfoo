package utils;

import com.zfoo.util.FileUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 07.17 12:03
 */
@Ignore
public class FileUtilTest {

    @Test
    public void test() {
        System.out.println("[amazing]");
    }

    @Test
    public void createFile() throws IOException {
        FileUtils.createFile(FileUtils.getProAbsPath() + File.separator + "hello", "hhh");
    }

    @Test
    public void deleteFile() throws IOException {
        FileUtils.deleteFile(new File(FileUtils.getProAbsPath() + File.separator + "hello"));
    }

    @Test
    public void writeFile() {
        FileUtils.writeStringToFile(new File(FileUtils.getProAbsPath() + File.separator + "test.txt"), "hello world!");
    }


    @Test
    public void readFile() {
        String str = FileUtils.readFileToString(new File(FileUtils.getProAbsPath() + File.separator + "test.txt"));
        System.out.println(str);
    }


    @Test
    public void getProjectPath() {
        System.out.println(FileUtils.getProAbsPath());
    }


    @Test
    public void getClassPath() {
        System.out.println(FileUtils.getClassAbsPath(User.class));
    }

    @Test
    public void serachFile() {
        FileUtils.serachFileInProject(new File(FileUtils.getProAbsPath()));
    }

    @Test
    public void getAllFiles() {
        List<File> list = FileUtils.getAllReadableFiles(new File(FileUtils.getProAbsPath()));
        for (File file : list) {
            System.out.println(file.getName());
        }
    }

    @Test
    public void serachFileInProject() {
        System.out.println(FileUtils.serachFileInProject("User"));
    }

}
