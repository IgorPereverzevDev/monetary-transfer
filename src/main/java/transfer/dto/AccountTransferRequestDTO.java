package transfer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountTransferRequestDTO {
    private UUID accountIdFrom;
    private UUID accountIdTo;
    private BigDecimal amount;

}
