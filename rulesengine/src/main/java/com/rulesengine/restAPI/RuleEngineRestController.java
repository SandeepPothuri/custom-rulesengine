package com.rulesengine.restAPI;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Enums;
import com.rulesengine.knowledgeBase.KnowledgeBaseService;
import com.rulesengine.knowledgeBase.models.Rule;
import com.rulesengine.ruleEngine.RuleEngine;
import com.rulesengine.rulesImpl.insuranceRuleEngine.InsuranceDetails;
import com.rulesengine.rulesImpl.insuranceRuleEngine.InsuranceInferenceEngine;
import com.rulesengine.rulesImpl.insuranceRuleEngine.PolicyHolderDetails;
import com.rulesengine.rulesImpl.loanRuleEngine.LoanDetails;
import com.rulesengine.rulesImpl.loanRuleEngine.LoanInferenceEngine;
import com.rulesengine.rulesImpl.loanRuleEngine.UserDetails;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
@RestController
public class RuleEngineRestController {
    @Autowired
    private KnowledgeBaseService knowledgeBaseService;
    @Autowired
    private RuleEngine ruleEngine;
    @Autowired
    private LoanInferenceEngine loanInferenceEngine;
    @Autowired
    private InsuranceInferenceEngine insuranceInferenceEngine;

    @GetMapping(value = "/get-all-rules/{ruleNamespace}")
    public ResponseEntity<?> getRulesByNamespace(@PathVariable("ruleNamespace") String ruleNamespace) {
        RuleNamespace namespace = Enums.getIfPresent(RuleNamespace.class, ruleNamespace.toUpperCase()).or(RuleNamespace.DEFAULT);
        List<Rule> allRules = knowledgeBaseService.getAllRuleByNamespace(namespace.toString());
        return ResponseEntity.ok(allRules);
    }

    @GetMapping(value = "/get-all-rules")
    public ResponseEntity<?> getAllRules() {
        List<Rule> allRules = knowledgeBaseService.getAllRules();
        return ResponseEntity.ok(allRules);
    }

    @PostMapping(value = "/loan")
    public ResponseEntity<?> postUserLoanDetails(@RequestBody UserDetails userDetails) {
	        LoanDetails result = (LoanDetails) ruleEngine.run(loanInferenceEngine, userDetails);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/insurance")
    public ResponseEntity<?> postCarLoanDetails(@RequestBody PolicyHolderDetails policyHolderDetails) {
        InsuranceDetails result = (InsuranceDetails) ruleEngine.run(insuranceInferenceEngine, policyHolderDetails);
        return ResponseEntity.ok(result);
    }
    
    @PostMapping(value = "/addRule")
    public ResponseEntity<?> addNewRule(@RequestBody Rule rule) {
    	List<Rule> allRules = knowledgeBaseService.save(rule);
        return ResponseEntity.ok(allRules);
    }
    
    @GetMapping(value = "/get-rule-ById/{id}")
    public ResponseEntity<?> getRuleByRuleID(@PathVariable("id") String id) {
    	log.debug("In Controller->getRuleByRuleID(id) "+id);
    	Rule rule = knowledgeBaseService.findByRuleId(id);
        return ResponseEntity.ok(rule);
    }
    
    @DeleteMapping(value = "/deleteRule/{id}")
    public ResponseEntity<?> deleteRule(@PathVariable("id") String id) {
    	log.debug("In Controller->deleteRule(id)" + Long.valueOf(id));
    	boolean result = knowledgeBaseService.delete(Long.valueOf(id));
        return ResponseEntity.ok(result);
    }
    
}
