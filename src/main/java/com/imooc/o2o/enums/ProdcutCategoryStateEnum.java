package com.imooc.o2o.enums;

public enum ProdcutCategoryStateEnum {
    SUCCESS(1,"操作成功"),
    INNER_ERROR(-1001,"失败"),
    EMPTY_LIST(-1002,"列表为空");

    private int state;
    private String stateInfo;

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }


    ProdcutCategoryStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }
    /**
     * 依据传入的state返回相应的enum值
     */
    public static ProdcutCategoryStateEnum stateOf(int state){
        for (ProdcutCategoryStateEnum stateEnum : values()){
            if(stateEnum.getState() == state){
                return stateEnum;
            }
        }
        return null;
    }
}

