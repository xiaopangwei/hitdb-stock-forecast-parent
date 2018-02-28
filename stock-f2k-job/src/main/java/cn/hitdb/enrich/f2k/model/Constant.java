package cn.hitdb.enrich.f2k.model;

import java.util.Date;

/**
 * <p>Description: </p>
 * <p>Company: Harbin Institute of Technology</p>
 *
 * @author weihuang
 * @date 2018/2/23
 * @time 上午2:19
 */
public interface Constant {
    static final String DATE_PATTERN        = "yyyy-MM-dd hh:mm:ss";
    static final String DEFAULT_CHARSET     = "UTF-8";
    static final int    INT_SIZE_IN_BYTE    = 4;
    static final int    DOUBLE_SIZE_IN_BYTE = 8;
    static final int    DATE_SIZE_IN_BYTE   = 19;
    static final Class  INT_CLASS           = int.class;
    static final Class  DOUBLE_CLASS        = double.class;
    static final Class  DATE_CLASS          = Date.class;
    static final Class  STRING_CLASS        = String.class;

}
