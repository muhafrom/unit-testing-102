package example.store;

import example.account.AccountManager;
import example.account.Customer;
import example.account.AccountManagerImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StoreTest {

    @Test
    void givenProductWithEnoughStock_whenBuy_thenSuccess() {
        //arrange
        AccountManager accountManager = new AccountManagerImpl();
        Product product = new Product();
        product.setQuantity(5);
        Customer customer = new Customer();
        Store store = new StoreImpl(accountManager);
        //act
        store.buy(product, customer);
        //assert
        Assertions.assertEquals(4, product.getQuantity());
    }
    public static class AlwaysSuccessAccountMangerTest implements AccountManager {
        @Override
        public void deposit(Customer customer, int amount) {

        }

        @Override
        public String withdraw(Customer customer, int amount) {
            return "success";
        }
    }
}
