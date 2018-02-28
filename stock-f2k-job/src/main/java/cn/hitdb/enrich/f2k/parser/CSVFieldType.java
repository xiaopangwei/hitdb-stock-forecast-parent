package cn.hitdb.enrich.f2k.parser;

/**
 * <p>Description: </p>
 * <p>Company: Harbin Institute of Technology</p>
 *
 * @author weihuang
 * @date 2018/2/22
 * @time 下午11:21
 */
public enum CSVFieldType {
    STR(1), REAL(2), INT(3), Date(4);
    private int   code;


    private CSVFieldType(int code) {
        this.code = code;
    }

    public CSVFieldType getValueByCode(int code) {
        for (CSVFieldType item : CSVFieldType.values()) {
            if (item.code == code) {
                return item;
            }
        }
        return null;
    }


    public static CSVFieldType getValueByName(String fieldName) {
       return CSVFieldType.valueOf(fieldName);
    }
}
