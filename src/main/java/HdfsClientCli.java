import java.io.File;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 * @author ：yrewzjs
 * @date ：Created in 2021/1/10 0:12
 * @modified By：
 */
public class HdfsClientCli {

    public static void main(String[] args) {
        Configuration conf = new Configuration();
        conf.addResource(new Path("conf/hdfs-site.xml"));
        String defaultFs = conf.get("fs.defaultFS");

        try {
            FileSystem fs = FileSystem.get(conf);
            FileStatus fileStatus = fs.getFileStatus(new Path("/test/start-dfs.sh"));
            System.out.println(fileStatus);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
