package com.booking.bookingApp.service;

import com.booking.bookingApp.entity.Rule;
import com.booking.bookingApp.entity.Product;
import com.booking.bookingApp.repository.RuleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RuleService {
    private final RuleRepository ruleRepository;

    @Transactional
    public void removeRules(Set<Rule> oldRules, Set<Rule> newRules) {
        List<Rule> deleteRules = new ArrayList<>();
        for (Rule oldRule : oldRules) {
            boolean find = false;
            for (Rule newRule : newRules) {
                if (Objects.equals(oldRule.getId(), newRule.getId())) {
                    find = true;
                    break;
                }
            }
            if (!find) {
                deleteRules.add(oldRule);
            }
        }

        ruleRepository.deleteAll(deleteRules);
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
