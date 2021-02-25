package com.rulesengine.rulesImpl.loanRuleEngine;

import com.rulesengine.restAPI.RuleNamespace;
import com.rulesengine.ruleEngine.InferenceEngine;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoanInferenceEngine extends InferenceEngine<UserDetails, LoanDetails> {

    @Override
    protected RuleNamespace getRuleNamespace() {
        return RuleNamespace.LOAN;
    }

    @Override
    protected LoanDetails initializeOutputResult() {
        return LoanDetails.builder().build();
    }
}
