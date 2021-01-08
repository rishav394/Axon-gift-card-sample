package space.rishav.giftcard.query;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import space.rishav.giftcard.commands.api.IssuedEvt;
import space.rishav.giftcard.commands.api.RedeemedEvt;

import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
@Slf4j
public class GiftCardProjector {
    private final EntityManager em;

    @EventHandler
    public void on(IssuedEvt evt) {
        log.debug("projecting {}", evt);
        em.persist(new GiftCardEntity(evt.getId(), evt.getAmount(), evt.getAmount()));
    }

    @EventHandler
    public void on(RedeemedEvt evt) {
        log.debug("projecting {}", evt);
        em.find(GiftCardEntity.class, evt.getId()).remainingValue -= evt.getAmount();
    }

    @QueryHandler
    public GiftCardEntity handle(GiftCardRequest qry) {
        log.debug("Query handing {}", qry);
        return em.find(GiftCardEntity.class, qry.getId());
    }
}
