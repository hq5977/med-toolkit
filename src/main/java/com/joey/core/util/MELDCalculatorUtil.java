package com.joey.core.util;

import cn.hutool.core.math.MathUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.joey.core.dto.PatientLiverDataDTO;

/**
 * MELD 计算工具类
 * 参考：https://cals.medlive.cn/calc/show/2?id=calc-49
 * https://news.medlive.cn/liver/info-progress/show-122101_35.html
 * https://rs.yiigle.com/CN2021/391936.htm
 * https://m.medsci.cn/scale/show.do?id=556d2e68c0 (单位换算)
 * @Author huangqiang
 * @Date 2024/5/17
 */
public class MELDCalculatorUtil {


    /**
     * 计算MELD评分
     * @param dataDTO
     * @return
     */
    public static int calculate(PatientLiverDataDTO dataDTO) {
        // 血清胆红素
        Double totalBilirubin = dataDTO.getTotalBilirubin();
        // 血清肌酐
        Double serumBilirubin = dataDTO.getSerumBilirubin();
        // 国际标准化比率(INR)
        Double inr = dataDTO.getInr();
        // 血钠
        Double na = dataDTO.getNa();
        if (null == totalBilirubin || null == serumBilirubin || null== inr|| null == na){
            return -1;
        }
        // 国际单位计算
        // 单位转换
        totalBilirubin = totalBilirubin / 17.1;
        serumBilirubin = serumBilirubin / 88.4;

        if (totalBilirubin <1){
            totalBilirubin =new Double(1);
        }
        if (serumBilirubin <1){
            serumBilirubin =new Double(1);
        }
        if (inr <1){
            inr =new Double(1);
        }
        if (serumBilirubin> 4){
            serumBilirubin = new Double(4);
        }
        if (na < 125){
            na = new Double(125);
        }
        if (na > 137){
            na = new Double(137);
        }
        //计算公式
        // MELD(i)评分 = 10 x (0.957 x Log e(血清肌酐值) + 0.378 x Log e(血清胆红素值)+ 1.12 x Log e(国际标准化比值) + 0.643)
        // 若所得MELD(i)评分 > 11，执行附加MELD评分计算，如下所示：
        // MELD评分 = MELD(i)评分 + 1.32 *(137 – 血钠) –  [0.033* MELD(i)评分 * (137 – 血钠) ]
        // 补充评分规则：
        //  所有数值以美制单位表示(血清肌酐、血清胆红素mg/dL， 血钠mEq/L， INR无单位) 。
        //  若血清胆红素、血清肌酐或IN R<1.0， 则设定1.0。
        //  如果存在以下任一情况，则血清肌酐直接设定4.0：
        //    血清肌酐>4.0
        //    最近一周内进行≥2次透析治疗；
        //    最近一周内进行24小时持续性静脉-静脉血液透析(CVV HD)
        //  若血钠<125mmo/L， 则设定125；若血钠>137mmol/L，则设定137。
        //  MELD评分最大值为40。

        double totalBilirubinLn = Math.log(totalBilirubin);
        double serumBilirubinLn = Math.log(serumBilirubin);
        double inrLn = Math.log(inr);

        //终末期肝病模型（MELD）评分的计算公式为：MELD=3.78×ln [T-BiL(mg/dl)]+11.2×ln[INR]+9.57×ln[Cr (mg/dl)] + 6.43。
        //公式中的T-BiL为总胆红素，INR为国际标准化比值，Cr为血清肌酐，ln即loge为自然对数。
        // 需要注意的是，在欧美国家，公式中总胆红素和血清肌酐的单位用的是“mg/dl”。
        // 而我国使用的是国际单位制(SI)，总胆红素和血清肌酐的单位是“μmol/L”。
        // 在使用该公式计算MELD分数时，需要先把总胆红素和血清肌酐“μmol/L”换算为“mg/dl”。
        // 总胆红素的换算系数为17.1，血清肌酐的换算系数为88.4。因此，在使用国际单位制时，MELD的计算公式为：
        // MELD=3.78×ln[T-BiL(μmol/L)÷17.1]+11.2×ln[INR]+9.57×ln[Cr (μmol/L)÷88.4] + 6.43。
        double result = 3.78* totalBilirubinLn + 11.2* inrLn+ 9.57* serumBilirubinLn  + 6.43;
        if (result < 6){
            result = 6;
        }
        result = format(result);
        if (result > 11){
            double temp = result;
            result = temp + 1.32* (137-na) - (0.033 * temp* (137-na));
        }
        int score = (int) Math.round(result);
//        if (null != dataDTO.getMeld()){
//            int oldScore = (int) Math.round(dataDTO.getMeld());
//            if (score != oldScore){
//                System.err.println("Child与原结果不一致:"+ JSONUtil.toJsonStr(dataDTO)+"；新结果："+score);
//            }
//        }
        return score;
    }

    /**
     * 返回四舍五入的整数
     * @param number
     * @return
     */
    private static double format(double number){
        return  Math.round(number);
    }
}
