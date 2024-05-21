package com.joey.core.vo;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 病人肝脏数据分析结果
 * @Author huangqiang
 * @Date 2024/5/17
 */
@Builder
@Accessors
@Data
public class PatientLiverDataAnalysisResultVo {

    /**
     * 获取Child-Pugh评分 结果描述
     */
    private String childResultDesc;


    /**
     * Child-Pugh评分 的分级
     */
    private String childResultLevel;


    /**
     * MELD评分
     */
    private int MELDScore;

    /**
     * MELD评分结果描述
     */
    private String meldResultDesc;
}
