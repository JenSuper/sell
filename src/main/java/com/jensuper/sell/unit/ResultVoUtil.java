package com.jensuper.sell.unit;

import com.jensuper.sell.VO.ResultVO;

/**
 * 返回结果
 */
public class ResultVoUtil {

    /* 返回成功结果 */
    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO<>();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(object);
        return resultVO;
    }

    /* 返回空值 */
    public static ResultVO success() {
        return null;
    }

    /* 返回失败结果 */
    public static ResultVO erro(Integer code,String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
}
