package chenhong.com.myjar;

import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 个人jar包工具库的开发
 * Created by Administrator on 2016/6/12.
 */
public class MyUtils {

    /**
     * 返回自定义View画的字的包含的矩形的高度
     * @param text View中具体的字
     */
    public static float getTextHeight(String text) {
        Rect mbounds=new Rect();
        Paint mpaint=new Paint();
        mpaint.getTextBounds(text, 0, text.length(), mbounds);
        return mbounds.height();
    }

    /**
     * 将汉字组合转换成拼音
     * @param Chinese 汉字
     * @return
     */
    public static String getPinYin(String Chinese){//需要拼音转换jar包
        String pinyin="";
       //设置转化pinyin的大小写或者声调
        HanyuPinyinOutputFormat format=new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);//大小写
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        if(!TextUtils.isEmpty(Chinese)){
        //只能对单个汉字转化
            char[] chars=Chinese.toCharArray();
            for(int i=0;i<chars.length;i++){
          //过滤空格
                if(Character.isWhitespace(chars[i]))continue;//不处理
                 //需要判断是否是汉字
                 //汉字占两个字节  一个字节范围 -128~127
                if(chars[i]>127){//可能是汉字
                    try {
                       //多音字
                        String[] pinyinStringArray = PinyinHelper.toHanyuPinyinStringArray(chars[i], format);
                        if(pinyinStringArray!=null){
                            pinyin+=pinyinStringArray[0];//如果有多音字那么只能取第一个拼音
                        }else {
                        //没有找到对应拼音则忽略
                        }
                    } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                        badHanyuPinyinOutputFormatCombination.printStackTrace();
                       //转换失败 ^_^那么则忽略
                    }
                }else {
                      //肯定是键盘上的字符 直接拼接
                    pinyin+=chars[i];
                }
            }
            return  pinyin;
        }
        return  null;
    }
}
