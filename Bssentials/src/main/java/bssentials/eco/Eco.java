package bssentials.eco;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.logging.Level;
import java.util.logging.Logger;

import bssentials.Bssentials;
import bssentials.User;

/**
 * Instead of using this api directly, we recommend to use Vault.
 */
public class Eco {
    public Eco() {
    }

    private static final Logger logger = Logger.getLogger("Bssentials");
    private static final String noCallBeforeLoad = "Bssentials API is called before Bssentials is loaded.";
    public static final MathContext MATH_CONTEXT = MathContext.DECIMAL128;

    private static User getUserByName(String name) {
        if (name == null) {
            throw new RuntimeException("Economy username cannot be null");
        }
        return User.getByName(name);
    }

    /**
     * Returns the balance of a user
     *
     * @param name
     *            Name of the user
     *
     * @return balance
     */
    @Deprecated
    public static double getMoney(String name) throws Exception {
        return getMoneyExact(name).doubleValue();
    }

    public static BigDecimal getMoneyExact(String name) throws Exception {
        User user = getUserByName(name);
        if (user == null) {
            throw new Exception("User does not exist: " + name);
        }
        return user.getMoney();
    }

    /**
     * Sets the balance of a user
     *
     * @param name
     *            Name of the user
     * @param balance
     *            The balance you want to set
     *
     * @throws Exception
     *             If a user by that name does not exists or If the user is not
     *             allowed to have a negative balance
     */
    @Deprecated
    public static void setMoney(String name, double balance) throws Exception {
        try {
            setMoney(name, BigDecimal.valueOf(balance));
        } catch (ArithmeticException e) {
            logger.log(Level.WARNING, "Failed to set balance of " + name + " to " + balance + ": " + e.getMessage(), e);
        }
    }

    public static void setMoney(String name, BigDecimal balance) throws Exception {
        User user = getUserByName(name);
        if (user == null) {
            throw new Exception(name);
        }
        if (balance.compareTo(new BigDecimal(0)) < 0) {
            throw new Exception();
        }
        if (balance.signum() < 0
                && !(user.isAuthorized("essentials.eco.loan") || user.isAuthorized("bssentials.eco.loan"))) {
            throw new Exception();
        }
        try {
            user.setMoney(balance);
        } catch (Exception ex) {
            // TODO: Update API to show max balance errors
        }
        // Trade.log("API", "Set", "API", name, new Trade(balance, ess), null,
        // null, null, ess);
    }

    /**
     * Adds money to the balance of a user
     *
     * @param name
     *            Name of the user
     * @param amount
     *            The money you want to add
     *
     * @throws UserDoesNotExistException
     *             If a user by that name does not exists
     * @throws NoLoanPermittedException
     *             If the user is not allowed to have a negative balance
     */
    @Deprecated
    public static void add(String name, double amount) throws Exception {
        try {
            add(name, BigDecimal.valueOf(amount));
        } catch (ArithmeticException e) {
            logger.log(Level.WARNING, "Failed to add " + amount + " to balance of " + name + ": " + e.getMessage(), e);
        }
    }

    public static void add(String name, BigDecimal amount) throws Exception {
        BigDecimal result = getMoneyExact(name).add(amount, MATH_CONTEXT);
        setMoney(name, result);
        // Trade.log("API", "Add", "API", name, new Trade(amount, ess), null,
        // null, null, ess);
    }

    /**
     * Substracts money from the balance of a user
     *
     * @param name
     *            Name of the user
     * @param amount
     *            The money you want to substract
     *
     * @throws UserDoesNotExistException
     *             If a user by that name does not exists
     * @throws NoLoanPermittedException
     *             If the user is not allowed to have a negative balance
     */
    @Deprecated
    public static void subtract(String name, double amount) throws Exception {
        try {
            substract(name, BigDecimal.valueOf(amount));
        } catch (ArithmeticException e) {
            logger.log(Level.WARNING,
                    "Failed to substract " + amount + " of balance of " + name + ": " + e.getMessage(), e);
        }
    }

    public static void substract(String name, BigDecimal amount) throws Exception {
        BigDecimal result = getMoneyExact(name).subtract(amount, MATH_CONTEXT);
        setMoney(name, result);
        // Trade.log("API", "Subtract", "API", name, new Trade(amount, ess),
        // null, null, null, ess);
    }

    /**
     * Divides the balance of a user by a value
     *
     * @param name
     *            Name of the user
     * @param value
     *            The balance is divided by this value
     *
     * @throws UserDoesNotExistException
     *             If a user by that name does not exists
     * @throws NoLoanPermittedException
     *             If the user is not allowed to have a negative balance
     */
    @Deprecated
    public static void divide(String name, double amount) throws Exception {
        try {
            divide(name, BigDecimal.valueOf(amount));
        } catch (ArithmeticException e) {
            logger.log(Level.WARNING, "Failed to divide balance of " + name + " by " + amount + ": " + e.getMessage(),
                    e);
        }
    }

    public static void divide(String name, BigDecimal amount) throws Exception {
        BigDecimal result = getMoneyExact(name).divide(amount, MATH_CONTEXT);
        setMoney(name, result);
        // Trade.log("API", "Divide", "API", name, new Trade(amount, ess), null,
        // null, null, ess);
    }

    /**
     * Multiplies the balance of a user by a value
     *
     * @param name
     *            Name of the user
     * @param value
     *            The balance is multiplied by this value
     */
    @Deprecated
    public static void multiply(String name, double amount) throws Exception {
        try {
            multiply(name, BigDecimal.valueOf(amount));
        } catch (ArithmeticException e) {
            logger.log(Level.WARNING, "Failed to multiply balance of " + name + " by " + amount + ": " + e.getMessage(),
                    e);
        }
    }

    public static void multiply(String name, BigDecimal amount) throws Exception {
        BigDecimal result = getMoneyExact(name).multiply(amount, MATH_CONTEXT);
        setMoney(name, result);
        // Trade.log("API", "Multiply", "API", name, new Trade(amount, ess),
        // null, null, null, ess);
    }

    /**
     * Resets the balance of a user to the starting balance
     *
     * @param name
     *            Name of the user
     *
     * @throws UserDoesNotExistException
     *             If a user by that name does not exists
     * @throws NoLoanPermittedException
     *             If the user is not allowed to have a negative balance
     */
    public static void resetBalance(String name) throws Exception {
        if (Bssentials.get() == null) {
            throw new RuntimeException(noCallBeforeLoad);
        }
        setMoney(name, 100); // TODO: configure
        // Trade.log("API", "Reset", "API", name, new Trade(BigDecimal.ZERO,
        // ess), null, null, null, ess);
    }

    /**
     * @param name
     *            Name of the user
     * @param amount
     *            The amount of money the user should have
     *
     * @return true, if the user has more or an equal amount of money
     */
    @Deprecated
    public static boolean hasEnough(String name, double amount) throws Exception {
        try {
            return hasEnough(name, BigDecimal.valueOf(amount));
        } catch (ArithmeticException e) {
            logger.log(Level.WARNING,
                    "Failed to compare balance of " + name + " with " + amount + ": " + e.getMessage(), e);
            return false;
        }
    }

    public static boolean hasEnough(String name, BigDecimal amount) throws Exception {
        return amount.compareTo(getMoneyExact(name)) <= 0;
    }

    /**
     * @param name
     *            Name of the user
     * @param amount
     *            The amount of money the user should have
     *
     * @return true, if the user has more money
     */
    @Deprecated
    public static boolean hasMore(String name, double amount) throws Exception {
        try {
            return hasMore(name, BigDecimal.valueOf(amount));
        } catch (ArithmeticException e) {
            logger.log(Level.WARNING,
                    "Failed to compare balance of " + name + " with " + amount + ": " + e.getMessage(), e);
            return false;
        }
    }

    public static boolean hasMore(String name, BigDecimal amount) throws Exception {
        return amount.compareTo(getMoneyExact(name)) < 0;
    }

    /**
     * @param name
     *            Name of the user
     * @param amount
     *            The amount of money the user should not have
     *
     * @return true, if the user has less money
     */
    @Deprecated
    public static boolean hasLess(String name, double amount) throws Exception {
        try {
            return hasLess(name, BigDecimal.valueOf(amount));
        } catch (ArithmeticException e) {
            logger.log(Level.WARNING,
                    "Failed to compare balance of " + name + " with " + amount + ": " + e.getMessage(), e);
            return false;
        }
    }

    public static boolean hasLess(String name, BigDecimal amount) throws Exception {
        return amount.compareTo(getMoneyExact(name)) > 0;
    }

    /**
     * Test if the user has a negative balance
     *
     * @param name
     *            Name of the user
     *
     * @return true, if the user has a negative balance
     */
    public static boolean isNegative(String name) throws Exception {
        return getMoneyExact(name).signum() < 0;
    }

    /**
     * Formats the amount of money like all other Essentials functions. Example:
     * $100000 or $12345.67
     *
     * @return Formatted money
     */
    @Deprecated
    public static String format(double amount) {
        try {
            return format(BigDecimal.valueOf(amount));
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Failed to display " + amount + ": " + e.getMessage(), e);
            return "NaN";
        }
    }

    public static String format(BigDecimal amount) {
        return "$" + amount; // TODO return NumberUtil.displayCurrency(amount,
        // ess);
    }

    /**
     * Test if a player exists to avoid the UserDoesNotExistException
     *
     * @param name
     *            Name of the user
     *
     * @return true, if the user exists
     */
    public static boolean playerExists(String name) {
        return getUserByName(name) != null;
    }

    public static boolean isNPC(String name) throws Exception {
        return false;
    }
}
