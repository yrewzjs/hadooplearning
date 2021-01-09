package wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 相当于一个yarn集群的客户端，需要在此封装我们的mr程序相关运行参数，指定jar包,最后提交给yarn
 * 执行：hadoop jar hadooptest-1.0-SNAPSHOT.jar wordcount.WordcountDriver /test/ /test/output/
 *
 * @author ：yrewzjs
 * @date ：Created in 2019/10/13 15:48
 * @modified By：
 */
public class WordcountDriver {
    public static void main(String[] args) throws Exception {
        // 1 获取配置信息，或者job对象实例
        Configuration configuration = new Configuration();
        // 8 配置提交到yarn上运行,windows和Linux变量不一致
//        configuration.set("mapreduce.framework.name", "yarn");
//        configuration.set("yarn.resourcemanager.hostname", "node22");
        Job job = Job.getInstance(configuration);

        // 6 指定本程序的jar包所在的本地路径
//        job.setJar("/home/admin/wc.jar");
        job.setJarByClass(WordcountDriver.class);

        // 2 指定本业务job要使用的mapper/Reducer业务类
        job.setMapperClass(WordcountMapper.class);
        job.setReducerClass(WordcountReducer.class);

        // 3 指定mapper输出数据的kv类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        // 4 指定最终输出的数据的kv类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // 5 指定job的输入原始文件所在目录
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // 7 将job中配置的相关参数，以及job所用的java类所在的jar包， 提交给yarn去运行
//        job.submit();
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}
