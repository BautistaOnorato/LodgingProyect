package com.booking.bookingApp.service;

import com.booking.bookingApp.entity.Security;
import com.booking.bookingApp.entity.Product;
import com.booking.bookingApp.repository.SecurityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final SecurityRepository securityRepository;

    public void removeSecurity(Long id) {
        Optional<Security> optionalSecurity = securityRepository.findById(id);
        if (optionalSecurity.isPresent()) {
            optionalSecurity.get().getProduct().removeSecurity(optionalSecurity.get());
            securityRepository.deleteById(id);
        }
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
