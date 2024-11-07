package devzeus.com.laptop_shop.models;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class TrackingDate {

    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    // Hàm sẽ đuoc gọi khi đối tượng được tạo ra lần đầu tiên, chưa lưu xuống cơ sở dữ liệu
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }

    // Hàm sẽ được gọi khi đối tượng được chỉnh sửa, chưa được lưu xuống csdl
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDate.now();
    }
}
