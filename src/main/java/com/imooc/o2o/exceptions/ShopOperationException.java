package com.imooc.o2o.exceptions;

//继承RuntimeException 执行数据库操作时可以回滚
public class ShopOperationException extends RuntimeException{
    public ShopOperationException(String msg){
        super(msg);
    }
}
