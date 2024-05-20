package com.joey.core.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 病人肝脏指标数据
 * @Author huangqiang
 * @Date 2024/5/17
 */
@Builder
@Accessors
@Data
public class PatientLiverDataDTO {

    /**
     * 编号
     */
    private Double no;

    /**
     * 总胆红素/血清胆红素
     */
    private Double totalBilirubin;

    /**
     * 白蛋白
     */
    private Double albumin;

    /**
     * 国际标准化比率(INR)
     */
    private Double inr;

    /**
     * 腹水(有无腹水)
     */
    private String ascites;

    /**
     * 肝性脑病(有无HE)
     */
    private String hepaticEncephalopathy;

    /**
     * 血清肌酐（Cr
     */
    private Double serumBilirubin;

    /**
     * 血钠（na）
     */
    private Double na;


    /**
     * child分
     */
    private String child;

    /**
     * MELD分
     */
    private Double meld;
}
