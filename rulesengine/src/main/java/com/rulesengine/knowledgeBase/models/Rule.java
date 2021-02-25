package com.rulesengine.knowledgeBase.models;

import com.rulesengine.restAPI.RuleNamespace;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class Rule {
	RuleNamespace ruleNamespace;
    String ruleId;
    String condition; // When 
    String action;    // Then 
    Integer priority;
    String description;
}
