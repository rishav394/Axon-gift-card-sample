package space.rishav.giftcard.commands.api;

import lombok.Value;

import java.util.UUID;

@Value
public class RedeemedEvt {

    UUID id;
    int amount;
    
}
