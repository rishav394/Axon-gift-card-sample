package space.rishav.giftcard.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import space.rishav.giftcard.commands.api.IssueCmd;
import space.rishav.giftcard.commands.api.RedeemCmd;
import space.rishav.giftcard.query.GiftCardEntity;
import space.rishav.giftcard.query.GiftCardRequest;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class TestRunner implements CommandLineRunner {
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    @Override
    public void run(String... args) throws Exception {
        UUID id = UUID.randomUUID();

        log.debug("Sending issue command");
        commandGateway.sendAndWait(new IssueCmd(id, 100));

        log.debug("Sending redeem command");
        commandGateway.sendAndWait(new RedeemCmd(id, 40));

        log.debug("Sending redeem command");
        commandGateway.sendAndWait(new RedeemCmd(id, 50));

        Thread.sleep(500);

        log.debug("Querying gift card");
        GiftCardEntity giftCardEntity = queryGateway
                .query(new GiftCardRequest(id), ResponseTypes.instanceOf(GiftCardEntity.class)).join();
        log.debug("Gift card value {}", giftCardEntity);
    }
}
