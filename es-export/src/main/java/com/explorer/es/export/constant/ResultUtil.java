package com.explorer.es.export.constant;

/**
 * @ProjectName: elasticsearch
 * @ClassName: ResultUtil
 * @Description: 统一返回值
 */
public class ResultUtil {

    public static Result success() {
        return success(null);
    }
    public static Result success(Object object) {
        Result result = new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg("成功");
        result.setData(object);
        return result;
    }
    public static Result success(Integer code, Object object) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg("成功");
        result.setData(object);
        return result;
    }

    public static Result error( String msg) {
        Result result = new Result();
        result.setCode(ResultEnum.ERROR.getCode());
        result.setMsg(msg);
        return result;
    }
    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
