package com.booking.bookingApp.service;

import com.booking.bookingApp.entity.Rule;
import com.booking.bookingApp.entity.Product;
import com.booking.bookingApp.repository.RuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RuleService {
    private final RuleRepository ruleRepository;

    public void removeRule(Long id) {
        Optional<Rule> optionalRule = ruleRepository.findById(id);
        if (optionalRule.isPresent()) {
            optionalRule.get().getProduct().removeRule(optionalRule.get());
            ruleRepository.deleteById(id);
        }
    }

    public void saveRules(Set<Rule> rules, Product product) {
        if (rules != null) {
            for (Rule rule : rules) {
                rule.setProduct(product);
                ruleRepository.save(rule);
            }
        }
    }
}
