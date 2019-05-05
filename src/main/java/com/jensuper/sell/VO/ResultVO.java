package com.jensuper.sell.VO;

import lombok.Data;

/**
 * 返回值
 * @param <T>
 */
@Data
public class ResultVO<T> {

    private Integer code;/* 返回code码 */

    private String msg;/* code码对应信息 */

    private T data;/* 返回的数据 */
}
