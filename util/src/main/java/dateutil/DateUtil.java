package dateutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * ���ڹ�����
 * �ο�����:http://www.jianshu.com/p/5675690b351e
 * @author Niu Li
 * @date 2016/12/11
 */
public abstract class DateUtil {

    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    private static Map<String,ThreadLocal<SimpleDateFormat>> sdfMap = new HashMap<String, ThreadLocal<SimpleDateFormat>>();

    public final static String MDHMSS = "MMddHHmmssSSS";
    public final static String YMD = "yyyyMMdd";
    public final static String YMD_ = "yyyy-MM-dd";
    public final static String HMS = "HHmmss";
    public final static String YMDHMS = "yyyyMMddHHmmss";
    public final static String YM = "yyMM";
    public final static String YMDHMS_ = "yyyy-MM-dd HH:mm:ss";

    /**
     * ����һ��ThreadLocal��sdf,ÿ���߳�ֻ��newһ��sdf
     *
     * @param pattern SimpleDateFormat����
     * @return ��ʵ��
     */
    private static SimpleDateFormat getSdf(final String pattern){
        ThreadLocal<SimpleDateFormat> t = sdfMap.get(pattern);
        // �˴���˫���жϺ�ͬ����Ϊ�˷�ֹsdfMap������������put�ظ���sdf
        if (t == null){
            synchronized (DateUtil.class){
                // ֻ��Map�л�û�����pattern��sdf�Ż������µ�sdf������map
                logger.debug("put new sdf of pattern " + pattern + " to map");
                // �����ǹؼ�,ʹ��ThreadLocal<SimpleDateFormat>���ԭ��ֱ��new SimpleDateFormat
                t = sdfMap.get(pattern);
                if (t == null){
                    t = new ThreadLocal<SimpleDateFormat>(){
                        @Override
                        protected SimpleDateFormat initialValue() {
                            logger.debug("thread: " + Thread.currentThread() + " init pattern: " + pattern);
                            return new SimpleDateFormat(pattern);
                        }
                    };
                }
                sdfMap.put(pattern,t);
            }
        }
        return t.get();
    }

    /**
     * Ϊָ��ʱ�䰴����Ӧ�����ֶ�����ʱ��
     * @param date ��ʼʱ��
     * @param time Ҫ���ӵ�ʱ��
     * @param filed �����ֶ� �ο�Calendar�ľ�̬�ֶ�
     * @return �޸ĺ��ʱ��
     */
    public static Date addDate(Date date, int time, int filed) {
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        calendar.setTime(date);
        calendar.add(filed, time);
        return calendar.getTime();
    }
    /**
     * ��ָ�����SDF��ʽ��ʱ��
     * @param date ʱ��
     * @param sdf ָ��ת����ʽ
     * @return ת����Ĵ�
     */
    public static String format(Date date, String sdf) {
        return getSdf(sdf).format(date);
    }

    /**
     * ���ַ�������ָ����ʽת��
     * @param str ʱ�䴮
     * @param sdf ����ת����ʽ
     * @return ת�����ʱ��
     */
    public static Date parse(String str, String sdf) {

        Date date = null;
        try {
            date = parseCanThrow(str, sdf);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    /**
     * ���ַ�������ָ����ʽת��,��Ҫ�������쳣�����ʹ��
     * @param str ʱ�䴮
     * @param sdf ����ת����ʽ
     * @return ת�����ʱ��
     */
    public static Date parseCanThrow(String str, String sdf) throws ParseException {
        return getSdf(sdf).parse(str);
    }

}
