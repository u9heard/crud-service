package org.example.criteria;

public class SearchCriteria {
    String key;
    SearchOperator operator;
    Object value;

    public SearchCriteria(String key, SearchOperator operator, Object value) {
        this.key = key;
        this.operator = operator;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public SearchOperator getOperator() {
        return operator;
    }

    public Object getValue() {
//        try {
            return value;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new ClassCastException(String.format("Unable to cast %s -> %s", value, valueType.toString()));
//        }
    }

    @Override
    public String toString() {
        return "SearchCriteria{" +
                "key='" + key + '\'' +
                ", operator=" + operator +
                ", value='" + value + '\'' +
                '}';
    }
}
