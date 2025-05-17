package example.account;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class AccountMangerTest {
    AccountManager accountManager = new AccountManagerImpl();

    @Test
    void givenCustomerWithEnoughBalance_whenWithdraw_thenSuccess(){
        //arrange
        Customer customer = new Customer();
        customer.setBalance(100);
        //act
        String result = accountManager.withdraw(customer, 50);
        //assert
        Assertions.assertEquals(50, customer.getBalance());
        Assertions.assertEquals("success", result);
    }

    @Test
    void givenCustomerWithCreditAllowed_whenWithdraw_thenSuccess(){
        //arrange
        Customer customer = new Customer();
        customer.setBalance(100);
        customer.setCreditAllowed(true);
        //act
        String result = accountManager.withdraw(customer, 50);
        //assert
        Assertions.assertEquals(50, customer.getBalance());
        Assertions.assertEquals("success", result);
    }

    @Test
    void givenCustomerWithCreditIsNotAllowed_whenWithdraw_thenFail(){
        //arrange
        Customer customer = new Customer();
        customer.setBalance(100);
        customer.setCreditAllowed(false);
        //act
        String result = accountManager.withdraw(customer, 200);
        //assert
        Assertions.assertEquals(100, customer.getBalance());
        Assertions.assertEquals("insufficient account balance", result);
    }

    @Test
    void givenCustomerWithCreditIsVip_whenWithdraw_thenSuccess(){
        //arrange
        Customer customer = new Customer();
        customer.setBalance(0);
        customer.setCreditAllowed(true);
        customer.setVip(true);
        //act
        String result = accountManager.withdraw(customer, 1100);
        //assert
        Assertions.assertEquals(-1100, customer.getBalance());
        Assertions.assertEquals("success", result);
    }

    @Test
    void givenCustomerWithCreditIsNotVip_whenWithdraw_thenFail(){
        //arrange
        Customer customer = new Customer();
        customer.setBalance(0);
        customer.setCreditAllowed(true);
        customer.setVip(false);
        //act
        String result = accountManager.withdraw(customer, 1100);
        //assert
        Assertions.assertEquals(0, customer.getBalance());
        Assertions.assertEquals("maximum credit exceeded", result);
    }

}
