package transfer.controller;


import org.jooby.Result;
import org.jooby.Results;
import org.jooby.mvc.*;
import transfer.dto.AccountDTO;
import transfer.dto.AccountTransferRequestDTO;
import transfer.service.AccountService;

import javax.inject.Inject;
import java.io.IOException;
import java.util.UUID;

@Path("/monetary-transfer/accounts")
public class AccountController {
    private final AccountService accountService;

    @Inject
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GET
    public Result getAllAccounts() {
        return Results.with(accountService.getAllAccounts(), 200);
    }

    @GET
    @Path("/{accountId}")
    public  AccountDTO getAccount(UUID accountId) {
        return accountService.getAccount(accountId);
    }

    @POST
    @Path("/transfer")
    public Result monetaryTransfer(@Body AccountTransferRequestDTO accountTransferRequestDTO) throws IOException {
        accountService.transfer(accountTransferRequestDTO.getAccountIdFrom(),
                accountTransferRequestDTO.getAccountIdTo(), accountTransferRequestDTO.getAmount());
        return Results.ok();
    }

    @PUT
    @Path("/account")
    public Result addAccount(@Body AccountDTO accountDTO) {
        return Results.with(accountService.addAccount(accountDTO), 202);
    }

    @DELETE
    @Path("/{accountId}")
    public void deleteAccount(UUID accountId) {
        accountService.deleteAccount(accountId);
    }

}
