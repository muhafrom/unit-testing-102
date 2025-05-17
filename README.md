## Bug Fix Details

### Issue
The original withdrawal method contained a logical error in credit limit validation for overdrafts:
- When calculating `expectedBalance = current balance - withdrawal amount`, negative values indicate overdrafts (customer owes money)
- The check `expectedBalance > MAX_CREDIT` incorrectly compared negative values against a positive credit limit
- Example: With `MAX_CREDIT = 1000`:
    - If `expectedBalance = -1500` (customer wants to overdraft $1500)
    - Original code: `-1500 > 1000` → `false` → incorrectly allows overdraft
    - Result: Non-VIP customers could exceed credit limit by any amount

### Solution
The fix properly validates overdraft amounts using absolute values:
- `Math.abs(expectedBalance)` converts negative overdraft to positive amount
- Compares absolute debt against `MAX_CREDIT` using `>` operator
- Example: With `MAX_CREDIT = 1000`:
    - `expectedBalance = -1500` → `Math.abs(-1500) = 1500`
    - Fixed code: `1500 > 1000` → `true` → correctly blocks excessive overdraft
- Ensures:
    - Non-VIP customers cannot exceed credit limit
    - VIP customers (if implemented) get special treatment through `customer.isVip()` check

### Before Fix
```java
public String withdraw(Customer customer, int amount) {
    int expectedBalance = customer.getBalance() - amount;
    if (expectedBalance < 0) {
        if (!customer.isCreditAllowed()) {
            return "insufficient account balance";
        } else if (expectedBalance > MAX_CREDIT && !customer.isVip()) {
            return "maximum credit exceeded";
        }
    }
    customer.setBalance(expectedBalance);
    return "success";
}
```
#### After Fix
```java
public String withdraw(Customer customer, int amount) {
    int expectedBalance = customer.getBalance() - amount;
    if (expectedBalance < 0) {
        if (!customer.isCreditAllowed()) {
            return "insufficient account balance";
        } else if (Math.abs(expectedBalance) > MAX_CREDIT && !customer.isVip()) {
            return "maximum credit exceeded";
        }
    }
    customer.setBalance(expectedBalance);
    return "success";
}
```
