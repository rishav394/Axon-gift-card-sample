package space.rishav.giftcard.commands.impl;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import space.rishav.giftcard.commands.api.IssueCmd;
import space.rishav.giftcard.commands.api.IssuedEvt;
import space.rishav.giftcard.commands.api.RedeemCmd;
import space.rishav.giftcard.commands.api.RedeemedEvt;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor
@Slf4j
public class Giftcard {

    @AggregateIdentifier
    private UUID id;

    private int remainginAmount;

    @CommandHandler
    public Giftcard(IssueCmd cmd) {
        log.debug("Handling {}", cmd);

        if (cmd.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount cant be less than 0");
        }
        apply(new IssuedEvt(cmd.getId(), cmd.getAmount()));
    }

    @CommandHandler
    public void handle(RedeemCmd cmd) {
        log.debug("Handling {}", cmd);

        if (cmd.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount cant be less than 0");
        }
        if (cmd.getAmount() > this.remainginAmount) {
            throw new IllegalArgumentException("Cant redeem more than whats left");
        }
        apply(new RedeemedEvt(cmd.getId(), cmd.getAmount()));
    }


    @EventSourcingHandler
    public void on(IssuedEvt evt) {
        log.debug("Handled {}", evt);
        this.id = evt.getId();
        this.remainginAmount = evt.getAmount();
    }


    @EventSourcingHandler
    public void on(RedeemedEvt evt) {
        log.debug("Handled {}", evt);
        this.remainginAmount -= evt.getAmount();
    }
}
