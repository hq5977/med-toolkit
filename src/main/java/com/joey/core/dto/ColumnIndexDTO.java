package com.joey.core.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 列下标对象
 * @Author huangqiang
 * @Date 2024/5/17
 */
@Data
@Builder
@Accessors
public class ColumnIndexDTO {

    /**
     * 编号
     */
    private Integer no;

    /**
     * 总胆红素/血清肌酐
     */
    private Integer totalBilirubin;

    /**
     * 白蛋白
     */
    private Integer albumin;

    /**
     * 国际标准化比率(INR)
     */
    private Integer inr;

    /**
     * 腹水(有无腹水)
     */
    private Integer ascites;

    /**
     * 肝性脑病(有无HE)
     */
    private Integer hepaticEncephalopathy;

    /**
     * 血清胆红素（Cr
     */
    private Integer serumBilirubin;

    /**
     * 血钠（na）
     */
    private Integer na;

    /**
     * child分
     */
    private Integer child;

    /**
     * MELD分
     */
    private Integer meld;
}
