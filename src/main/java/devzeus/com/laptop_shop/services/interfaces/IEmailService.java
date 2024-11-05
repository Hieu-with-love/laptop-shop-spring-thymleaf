package devzeus.com.laptop_shop.services.interfaces;

public interface IEmailService {
    void sendEmailToVerifyAccount(String name, String to, String token);

    boolean verifyToken(String token);

    void sendEmailToRenewPassword(String email);
}
