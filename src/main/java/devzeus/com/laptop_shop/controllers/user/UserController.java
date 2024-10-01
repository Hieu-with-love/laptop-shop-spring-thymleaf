package devzeus.com.laptop_shop.controllers.user;

import devzeus.com.laptop_shop.dtos.requests.UserDTO;
import devzeus.com.laptop_shop.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long userId) {
        try {
            User user = User.builder()
                    .fullName("Hieu")
                    .build();
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Cannot get user with id = " + userId + "\n" + e);
        }
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO, BindingResult bindingResult) {
        try {
            User user = User.builder()
                    .fullName("Hieu")
                    .build();
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Cannot get user with id = " + "\n" + e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long userId, @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        try {
            User user = User.builder()
                    .fullName("Hieu")
                    .build();
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Cannot get user with id = " + "\n" + e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long userId) {
        return ResponseEntity.ok("Delete successful");
    }
}
