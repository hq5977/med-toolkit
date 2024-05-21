package com.joey.core.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.joey.core.dto.PatientLiverDataDTO;

/**
 *  Child-Pugh 计算工具类
 *  参考：https://cals.medlive.cn/calc/show/2?id=calc-20
 * @Author huangqiang
 * @Date 2024/5/17
 */
public class ChildCalculatorUtil {

    /**
     * 没有腹水
     */
    private static String[] NO_ASCITIES= new String[]{"无"};

    /**
     * 能够控制的腹水
     */
    private static String[] SMALL_ASCITIES= new String[]{"有（少量）","少量","中量","少-中","有 中量","有 （少量）"};

    /**
     * 难以控制的腹水
     */
    private static String[] LARGE_ASCITIES= new String[]{"有（大量）","大量","有（超声：中-大量）","顽固性腹水","中-大量"};

    /**
     * 无脑病
     */
    private static String[] NO_HE= new String[]{"无"};

    /**
     * 能够控制的脑病(I~II)
     */
    private static String[] SMALL_HE= new String[]{"有（I期）","I期","有（未分级）","2期","1期","0期","前驱期"};

    /**
     * 难以控制的脑病(III~IV)
     */
    private static String[] LARGE_HE= new String[]{"3期","4期"};

    /**
     * 计算 Child-Pugh评分
     * @param dataDTO
     * @return
     */
    public static int calculate(PatientLiverDataDTO dataDTO) {
        // 总胆红素
        Double totalBilirubin = dataDTO.getTotalBilirubin();
        // 总胆红素
        Double albumin = dataDTO.getAlbumin();
        // 国际标准化比率(INR)
        Double inr = dataDTO.getInr();
        // 腹水(有无腹水)
        String ascites = dataDTO.getAscites();
        // 肝性脑病(有无HE)
        String hepaticEncephalopathy = dataDTO.getHepaticEncephalopathy();
        if (null == totalBilirubin || null == albumin || null== inr|| StrUtil.isEmpty(ascites) || StrUtil.isEmpty(hepaticEncephalopathy)){
            return -1;
        }
        // 总胆红素分
        int totalBilirubinScore = getTotalBilirubinScore(totalBilirubin);
        // 白蛋白分
        int albuminScore = getAlbuminScore(albumin);
        // inr分
        int inrScore = getInrScore(inr);
        // 腹水分
        int ascitesScore = getAscitesScore(ascites);
        if (ascitesScore == -1){
            System.err.println("腹水未找到结果："+ JSONUtil.toJsonStr(dataDTO));
            return -1;
        }
        // 肝性脑病分
        int HEScore = getHEScore(hepaticEncephalopathy);
        if (HEScore == -1){
            System.err.println("肝性脑病分未找到结果："+ JSONUtil.toJsonStr(dataDTO));
            return -1;
        }

        int total = totalBilirubinScore+ albuminScore+ inrScore+ ascitesScore+ HEScore;

        return total;
    }

    /**
     * 肝性脑病分
     * @param hepaticEncephalopathy
     * @return
     */
    private static int getHEScore(String hepaticEncephalopathy) {
        if (StrUtil.isEmpty(hepaticEncephalopathy)){
            return -1;
        }
        if (StrUtil.equalsAny(hepaticEncephalopathy,NO_HE)){
            // 无脑病
            return 1;
        }
        if (StrUtil.equalsAny(hepaticEncephalopathy,SMALL_HE)){
            // 能够控制的脑病(I~II)
            return 2;
        }
        if (StrUtil.equalsAny(hepaticEncephalopathy,LARGE_HE)){
            // 难以控制的脑病(III~IV)
            return 3;
        }
        return -1;
    }

    /**
     * 腹水分
     * @param ascites
     * @return
     */
    private static int getAscitesScore(String ascites) {
        if (StrUtil.isEmpty(ascites)){
            return -1;
        }
        if (StrUtil.equalsAny(ascites,NO_ASCITIES)){
            // 无腹水
            return 1;
        }
        if (StrUtil.equalsAny(ascites,SMALL_ASCITIES)){
            // 能够控制的腹水
            return 2;
        }
        if (StrUtil.equalsAny(ascites,LARGE_ASCITIES)){
            // 难以控制的腹水
            return 3;
        }
        return -1;
    }

    /**
     * inr得分
     * @param inr
     * @return
     */
    private static int getInrScore(Double inr) {
        if (null == inr){
            return -1;
        }
        Double onePointSeven = new Double(1.7);
        Double twoPointTwo = new Double(2.2);
        // 与1.7比较
        int compareOnePointSeven = Double.compare(inr, onePointSeven);
        // 与2.2比较
        int compareTwoPointTwo = Double.compare(inr, twoPointTwo);
        if (compareOnePointSeven< 0){
            // <1.7
            return 1;
        } else if (compareOnePointSeven>=0 && compareTwoPointTwo<=0) {
            // 1.7~2.2
            return 2;
        } else if (compareTwoPointTwo>0) {
            // >2.2
            return 3;
        }
        return -1;
    }

    /**
     * 白蛋白得分
     * @param albumin
     * @return
     */
    private static int getAlbuminScore(Double albumin) {
        if (null == albumin){
            return -1;
        }
        Double thirtyFive = new Double(35);
        Double twentyEight = new Double(28);
        // 与35比较
        int compareThirtyFive = Double.compare(albumin, thirtyFive);
        //与28比较
        int compareTwentyEight = Double.compare(albumin, twentyEight);
        if (compareThirtyFive > 0){
            // >35
            return 1;
        } else if (compareTwentyEight>=0 && compareThirtyFive<=0) {
            // 28~35
            return 2;
        } else if (compareTwentyEight <0) {
            // < 28
            return 3;
        }
        return -1;
    }

    /**
     * 总胆红素得分
     * @param totalBilirubin
     * @return
     */
    private static int getTotalBilirubinScore(Double totalBilirubin) {
        if (null == totalBilirubin){
            return -1;
        }
        Double thirtyFour = new Double(34);
        Double fifty = new Double(50);
        int compareThirtyFour = Double.compare(totalBilirubin, thirtyFour);
        int compareFifty = Double.compare(totalBilirubin, fifty);
        if (compareThirtyFour< 0){
            // <34
            return 1;
        } else if (compareThirtyFour>=0 && compareFifty<=0) {
            // 34~50
            return 2;
        } else if (compareFifty>0) {
            // >50
            return 3;
        }
        return -1;
    }
}
