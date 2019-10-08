package transfer;

import org.jooby.Jooby;
import org.jooby.apitool.ApiTool;
import org.jooby.json.Jackson;
import transfer.controller.AccountController;
import transfer.repository.AccountRepository;
import transfer.repository.AccountRepositoryImpl;
import transfer.service.AccountService;
import transfer.service.AccountServiceImpl;
import transfer.service.CurrencyService;
import transfer.service.CurrencyServiceImpl;

public class App extends Jooby {
    {
        use(new Jackson());
        use(AccountController.class);
        use(new ApiTool().swagger());
        use((environment, configuration, binder) -> {
            binder.bind(AccountService.class).to(AccountServiceImpl.class);
            binder.bind(CurrencyService.class).to(CurrencyServiceImpl.class);
            binder.bind(AccountRepository.class).to(AccountRepositoryImpl.class);
        });
    }

    public static void main(String[] args) {
        run(App::new, args);
    }
}
