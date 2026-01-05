package dev.hspl.hspl2shop.shop.model.impl.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

//@Entity(name = "CategoryDetail")
@Table(name = "category_detail")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CategoryDetail {
    @Id
    private String id;
}
