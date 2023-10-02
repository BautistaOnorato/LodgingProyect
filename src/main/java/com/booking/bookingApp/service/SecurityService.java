package com.booking.bookingApp.service;

import com.booking.bookingApp.entity.Security;
import com.booking.bookingApp.entity.Product;
import com.booking.bookingApp.repository.SecurityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final SecurityRepository securityRepository;

    @Transactional
    public void removeSecurities(Set<Security> oldSecurities, Set<Security> newSecurities) {
        List<Security> deleteSecurities = new ArrayList<>();
        for (Security oldSecurity : oldSecurities) {
            boolean find = false;
            for (Security newSecurity : newSecurities) {
                if (Objects.equals(oldSecurity.getId(), newSecurity.getId())) {
                    find = true;
                    break;
                }
            }
            if (!find) {
                deleteSecurities.add(oldSecurity);
            }
        }

        securityRepository.deleteAll(deleteSecurities);
    }

    public void saveSecurities(Set<Security> securities, Product product) {
        if (securities != null) {
            for (Security security : securities) {
                security.setProduct(product);
                securityRepository.save(security);
            }
        }
    }
}
