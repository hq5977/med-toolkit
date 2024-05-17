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
     * 总胆红素/血清肌酐
     */
    private Float totalBilirubin;

    /**
     * c
     */
    private Float albumin;

    /**
     * 国际标准化比率(INR)
     */
    private Float inr;

    /**
     * 腹水(有无腹水)
     */
    private String ascites;

    /**
     * 肝性脑病(有无HE)
     */
    private String hepaticEncephalopathy;

    /**
     * 血清胆红素（Cr
     */
    private Float serumBilirubin;

    /**
     * 血钠（na）
     */
    private Float na;
}
