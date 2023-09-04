package org.example.criteria;

import java.util.List;

public class SpecificationBuilder {
    public static String build(List<SearchCriteria> criteriaList){
        StringBuilder query = new StringBuilder();

        for(int i=0; i<criteriaList.size(); i++){
            query.append(String.format("%s %s ?", criteriaList.get(i).getKey(), criteriaList.get(i).getOperator().getOp()));
            if(i != criteriaList.size()-1){
                query.append(" and ");
            }
        }

        return query.toString();
    }
}
