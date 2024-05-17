package com.joey.core.util;

import cn.hutool.core.util.StrUtil;
import com.joey.core.dto.PatientLiverDataDTO;

/**
 *  Child-Pugh 计算工具类
 * @Author huangqiang
 * @Date 2024/5/17
 */
public class ChildCalculatorUtil {

    /**
     * 能够控制的腹水
     */
    private static String[] SMALL_ASCITIES= new String[]{"",""};

    /**
     * 能够控制的腹水
     */
    private static String[] large_ASCITIES= new String[]{"",""};

    /**
     * 计算 Child-Pugh评分
     * @param dataDTO
     * @return
     */
    public static int calculate(PatientLiverDataDTO dataDTO) {
        // 总胆红素
        Float totalBilirubin = dataDTO.getTotalBilirubin();
        // 总胆红素
        Float albumin = dataDTO.getAlbumin();
        // 国际标准化比率(INR)
        Float inr = dataDTO.getInr();
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
        // 肝性脑病分
        int HEScore = getHEScore(totalBilirubin);

        int total = totalBilirubinScore+ albuminScore+ inrScore+ ascitesScore+ HEScore;

        return 0;
    }

    /**
     * 肝性脑病分
     * @param totalBilirubin
     * @return
     */
    private static int getHEScore(Float totalBilirubin) {
        return 0;
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
        String[] s1 = new String[]{"",""};
        StrUtil.equalsAny("1",s1);
        return 0;
    }

    /**
     * inr得分
     * @param inr
     * @return
     */
    private static int getInrScore(Float inr) {
        if (null == inr){
            return -1;
        }
        Float onePointSeven = new Float(1.7);
        Float twoPointTwo = new Float(2.2);
        // 与1.7比较
        int compareOnePointSeven = Float.compare(inr, onePointSeven);
        // 与2.2比较
        int compareTwoPointTwo = Float.compare(inr, twoPointTwo);
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
    private static int getAlbuminScore(Float albumin) {
        if (null == albumin){
            return -1;
        }
        Float threePointFive = new Float(3.5);
        Float twoPointEight = new Float(2.8);
        // 与3.5比较
        int compareThreePointFive = Float.compare(albumin, threePointFive);
        //与2.8比较
        int compareTwoPointEight = Float.compare(albumin, twoPointEight);
        if (compareThreePointFive > 0){
            // >3.5
            return 1;
        } else if (compareTwoPointEight>=0 && compareThreePointFive<=0) {
            // 2.8~3.5
            return 2;
        } else if (compareTwoPointEight <0) {
            // < 2.8
            return 3;
        }
        return -1;
    }

    /**
     * 总胆红素得分
     * @param totalBilirubin
     * @return
     */
    private static int getTotalBilirubinScore(Float totalBilirubin) {
        if (null == totalBilirubin){
            return -1;
        }
        Float two = new Float(2);
        Float three = new Float(3);
        int compareTwo = Float.compare(totalBilirubin, two);
        int compareThree = Float.compare(totalBilirubin, three);
        if (compareTwo< 0){
            // <2
            return 1;
        } else if (compareTwo>=0 && compareThree<=0) {
            // 2~3
            return 2;
        } else if (compareThree>0) {
            // >3
            return 3;
        }
        return -1;
    }
}
