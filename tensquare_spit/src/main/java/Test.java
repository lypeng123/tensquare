import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) {

        int age = judgeAge("141122199409190150");
        System.out.println(age);
    }

    /**
     * 根据身份证号判断年龄
     * 141122199409190150
     */
    private static int judgeAge(String s) {
        String year = s.substring(6,10);
        //当前年份-year年份
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        String nowYear = dateFormat.format(new Date());
        Integer age=Integer.parseInt(nowYear)-Integer.parseInt(year);
        return age;
    }
}
