package com.rulesengine.knowledgeBase;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Enums;
import com.rulesengine.knowledgeBase.db.RuleDbModel;
import com.rulesengine.knowledgeBase.db.RulesRepository;
import com.rulesengine.knowledgeBase.models.Rule;
import com.rulesengine.restAPI.RuleNamespace;

@Service
public class KnowledgeBaseService {
    @Autowired
    private RulesRepository rulesRepository;

    public List<Rule> getAllRules(){
        return rulesRepository.findAll().stream()
                .map(
                        ruleDbModel -> mapFromDbModel(ruleDbModel)
                )
                .collect(Collectors.toList());
    }

    public List<Rule> getAllRuleByNamespace(String ruleNamespace){
        return rulesRepository.findByRuleNamespace(ruleNamespace).stream()
                .map(
                        ruleDbModel -> mapFromDbModel(ruleDbModel)
                )
                .collect(Collectors.toList());
    }

    public List<Rule> save(Rule rule){
    	List<Rule> newRuleList  = Arrays.asList(mapFromDbModel(rulesRepository.save(mapFromRule(rule))));
    	 return newRuleList;
    }
    
    public boolean delete(Long id){
    	//rulesRepository.deleteById(new com.tech.knowledgeBase.db.RuleDbModel.IdClass("LOAN","1613857550446"));
    	return true;
    }
    
    private Rule mapFromDbModel(RuleDbModel ruleDbModel){
        RuleNamespace namespace = Enums.getIfPresent(RuleNamespace.class, ruleDbModel.getRuleNamespace().toUpperCase())
                .or(RuleNamespace.DEFAULT);
        return Rule.builder()
                .ruleNamespace(namespace)
                .ruleId(ruleDbModel.getRuleId())
                .condition(ruleDbModel.getRuleCondition())
                .action(ruleDbModel.getAction())
                .description(ruleDbModel.getDescription())
                .priority(ruleDbModel.getPriority())
                .build();
    }
    
    private RuleDbModel mapFromRule(Rule rule){
        RuleNamespace namespace = Enums.getIfPresent(RuleNamespace.class, String.valueOf(rule.getRuleNamespace()))
                .or(RuleNamespace.DEFAULT);
        
        return RuleDbModel.builder()
        .ruleNamespace(namespace.name())
        .ruleId(rule.getRuleId())
        .ruleCondition(rule.getCondition())
        .action(rule.getAction())
        .description(rule.getDescription())
        .priority(rule.getPriority())
        .build();
    }

	public Rule findByRuleId(String id) {
		return mapFromDbModel(rulesRepository.findByRuleId(id));
	}
	
	

}
