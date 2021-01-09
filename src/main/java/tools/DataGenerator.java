package tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import cn.binarywang.tools.generator.ChineseAddressGenerator;
import cn.binarywang.tools.generator.ChineseIDCardNumberGenerator;
import cn.binarywang.tools.generator.ChineseMobileNumberGenerator;
import cn.binarywang.tools.generator.ChineseNameGenerator;
import cn.binarywang.tools.generator.EmailAddressGenerator;

import java.util.Calendar;

import org.apache.commons.lang3.RandomUtils;

/**
 * @author ：yrewzjs
 * @date ：Created in 2019/10/20 15:35
 * @modified By：
 */
public class DataGenerator {

    protected File file = null;

    protected FileWriter fw = null;

    public static void main(String[] args) {
//        String path = args[0];
        String path = "G:\\CS_Data\\Data_Java\\IdeaProjects\\hadooplearning\\target\\hadooplearningcli\\bigdata_personalinfo.txt";
//        int rows = Integer.parseInt(args[1]);
        int rows = 1000000;

        DataGenerator generator = new DataGenerator();
        try {
            // pre
            generator.preWriteData(path);
            // do write
            generator.doWriteData(rows);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            generator.afterWriteData();
        }
    }

    public void preWriteData(String path) throws IOException {
        file = new File(path);

        if (!file.exists()) {
            file.createNewFile();
        } else {
            file.delete();
            file.createNewFile();
        }
    }

    public void doWriteData(int rows) throws IOException {
        System.out.println("start write data...");
        fw = new FileWriter(file);
        for (int i = 0; i < rows; i++) {
            fw.write(getLineContext()); //向文件中写内容
            fw.write("\r\n");
            fw.flush();
        }
        System.out.println("write data successfully!");
    }

    public void afterWriteData() {
        if (fw != null) {
            try {
                fw.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * generate a person info
     *
     * @return
     */
    public String getLineContext() {
        StringBuilder sb = new StringBuilder();
        //中文姓名
        ChineseNameGenerator cng = ChineseNameGenerator.getInstance();
        sb.append(cng.generate());
        sb.append(" ");
        //身份证号码
        ChineseIDCardNumberGenerator cidcng = (ChineseIDCardNumberGenerator) ChineseIDCardNumberGenerator.getInstance();
        String cid = cidcng.generate();
        sb.append(cid);
        sb.append(" ");
        // 年龄
        // StringUtils.leftPad("" + RandomUtils.nextInt(0, 100000000), 8, "0");
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        int age = Integer.parseInt(year) - Integer.parseInt(cid.substring(6, 10));
        sb.append(age);
        sb.append(" ");
        //手机号
        ChineseMobileNumberGenerator cmng = ChineseMobileNumberGenerator.getInstance();
        sb.append(cmng.generate());
        sb.append(" ");
        //电子邮箱
        EmailAddressGenerator eag = (EmailAddressGenerator) EmailAddressGenerator.getInstance();
        sb.append(eag.generate());
        sb.append(" ");
        // 薪水
        sb.append(RandomUtils.nextInt(1000, 100000));
        sb.append(" ");
        //居住地址
        ChineseAddressGenerator cag = (ChineseAddressGenerator) ChineseAddressGenerator.getInstance();
        sb.append(cag.generate());

        return sb.toString();
    }
}
