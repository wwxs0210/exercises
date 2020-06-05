package js;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class MeasureZonePointData implements Serializable {

    private String key;// MeasurePointRule.Key
    private Integer dataType;  // 数据类型：1，boolean；2，整数(int)；14，浮点数（float64）
    private String data; // 数据列表
    private Boolean designValueReqd; // 是否需要录入设计值
    private String designValue;// 设计值
    private Integer okTotal;  // 合格数
    private Integer total;  // 总数
    private String seq;// 合格情况序列：0，不合格；1，合格。例："0110001"，合格数=3，总数=7
    private String deviation;// 偏差值

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("key", key);
        map.put("data_type", dataType);
        map.put("data", data);
        map.put("design_value_reqd", designValueReqd);
        map.put("design_value", designValue);
        map.put("ok_total", okTotal);
        map.put("total", total);
        map.put("seq", seq);
        return map;
    }
}
