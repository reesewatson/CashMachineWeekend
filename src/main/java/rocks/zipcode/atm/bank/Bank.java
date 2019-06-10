package rocks.zipcode.atm.bank;

import com.sun.org.apache.xpath.internal.operations.Bool;
import rocks.zipcode.atm.ActionResult;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZipCodeWilmington
 */
public class Bank {

    private Map<Integer, Account> accounts = new HashMap<>();

    public Bank() {
        accounts.put(1234, new BasicAccount(new AccountData(
                1234, "Dolio Durant", "gangstaBlueGrass@gmail.com", 1000000d
        )));

        accounts.put(4321, new PremiumAccount(new AccountData(
                4321, "Kris Younger", "codeBeneathMySails@gmail.com", 2000000d
        )));
    }

    public Boolean newAccount(Integer id, AccountData data){
        if(accounts.get(id)==null) {
            accounts.put(id, new BasicAccount(data));
            return true;
        } else
            return false;
    }

    public ActionResult<AccountData> getAccountById(int id) {
        Account account = accounts.get(id);

        if (account != null) {
            return ActionResult.success(account.getAccountData());
        } else {
            return ActionResult.fail("No account with id: " + id + "\nTry account 1000 or 2000");
        }
    }

    public ActionResult<AccountData> deposit(AccountData accountData, Double amount) {
        Account account = accounts.get(accountData.getId());
        account.deposit(amount);

        return ActionResult.success(account.getAccountData());
    }

    public ActionResult<AccountData> withdraw(AccountData accountData, Double amount) {
        Account account = accounts.get(accountData.getId());
        boolean ok = account.withdraw(amount);

        if (ok) {
            return ActionResult.success(account.getAccountData());
        } else {
            return ActionResult.fail("Withdraw failed: " + amount + ". Account has: " + account.getBalance());
        }
    }

    public Boolean checkExisting(Integer id){
        return accounts.containsKey(id);
    }
}
