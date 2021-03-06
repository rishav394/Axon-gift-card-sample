package space.rishav.giftcard.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftCardEntity {

    @Id
    UUID id;

    int remainingValue;
    int initialValue;

}
