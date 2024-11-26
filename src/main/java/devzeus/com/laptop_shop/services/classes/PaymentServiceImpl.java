package devzeus.com.laptop_shop.services.classes;

import devzeus.com.laptop_shop.models.Payment;
import devzeus.com.laptop_shop.repositories.PaymentRepository;
import devzeus.com.laptop_shop.services.interfaces.IPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements IPaymentService {
    private final PaymentRepository paymentRepository;

    @Override
    public Payment findByType(String name) {
        return paymentRepository.findByType(name);
    }

    @Override
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    @Override
    public <S extends Payment> S save(S entity) {
        return paymentRepository.save(entity);
    }

    @Override
    public Optional<Payment> findById(Long aLong) {
        return paymentRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return paymentRepository.existsById(aLong);
    }

    @Override
    public long count() {
        return paymentRepository.count();
    }

    @Override
    public void deleteById(Long aLong) {
        paymentRepository.deleteById(aLong);
    }

    @Override
    public void deleteAll() {
        paymentRepository.deleteAll();
    }

    @Override
    public List<Payment> findAll(Sort sort) {
        return paymentRepository.findAll(sort);
    }
}
