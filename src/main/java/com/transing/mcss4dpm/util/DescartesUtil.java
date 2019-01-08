package com.transing.mcss4dpm.util;

import com.transing.mcss4dpm.integration.bo.SubTaskParam;

import java.util.ArrayList;
import java.util.List;

public class DescartesUtil {

    /**
     * 递归实现dimValue中的笛卡尔积，结果放在result中
     * @param dimValue 原始数据
     * @param result 结果数据
     * @param layer dimValue的层数
     * @param curList 每次笛卡尔积的结果
     */
    public static void recursive (List<List<SubTaskParam>> dimValue, List<List<SubTaskParam>> result, int layer, List<SubTaskParam> curList) {

        if (layer < dimValue.size() - 1) {
            if (dimValue.get(layer).size() == 0) {
                recursive(dimValue, result, layer + 1, curList);
            } else {
                for (int i = 0; i < dimValue.get(layer).size(); i++) {
                    List<SubTaskParam> list = new ArrayList<SubTaskParam>(curList);
                    list.add(dimValue.get(layer).get(i));
                    recursive(dimValue, result, layer + 1, list);
                }
            }
        } else if (layer == dimValue.size() - 1) {
            if (dimValue.get(layer).size() == 0) {
                result.add(curList);
            } else {
                for (int i = 0; i < dimValue.get(layer).size(); i++) {
                    List<SubTaskParam> list = new ArrayList<SubTaskParam>(curList);
                    list.add(dimValue.get(layer).get(i));
                    result.add(list);
                }
            }
        }
    }
}
