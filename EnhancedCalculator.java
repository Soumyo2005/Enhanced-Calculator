import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Scanner;

public class EnhancedCalculator {
    
    private static final Scanner scanner = new Scanner(System.in);
    private static final MathContext MC = new MathContext(10, RoundingMode.HALF_UP);
    
    public static void main(String[] args) {
        System.out.println("ENHANCED CALCULATOR - Precision Edition");
        System.out.println();
        
        boolean continueRunning = true;
        
        while (continueRunning) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    basicArithmeticMenu();
                    break;
                case 2:
                    scientificCalculationsMenu();
                    break;
                case 3:
                    unitConversionsMenu();
                    break;
                case 4:
                    continueRunning = false;
                    System.out.println("\nThank you for using Enhanced Calculator!");
                    break;
                default:
                    System.out.println("Invalid choice! Please enter 1-4.");
            }
            
            if (continueRunning) {
                System.out.print("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    private static void displayMainMenu() {
        System.out.println("\nMAIN MENU");
        System.out.println("1. Basic Arithmetic Operations");
        System.out.println("2. Scientific Calculations");
        System.out.println("3. Unit Conversions");
        System.out.println("4. Exit");
    } 
    
    private static void basicArithmeticMenu() {
        System.out.println("\n--- Basic Arithmetic ---");
        System.out.println("Available operations: +, -, *, /, ^ (power), % (modulus)");
        
        BigDecimal num1 = getBigDecimalInput("Enter first number: ");
        String operator = getStringInput("Enter operator (+, -, *, /, ^, %): ");
        BigDecimal num2 = getBigDecimalInput("Enter second number: ");
        
        BigDecimal result = performArithmetic(num1, num2, operator);
        
        if (result != null) {
            System.out.println("\nResult: " + num1 + " " + operator + " " + num2 + " = " + result);
        }
    }
    
    private static BigDecimal performArithmetic(BigDecimal a, BigDecimal b, String operator) {
        try {
            switch (operator) {
                case "+":
                    return a.add(b);
                case "-":
                    return a.subtract(b);
                case "*":
                    return a.multiply(b);
                case "/":
                    if (b.compareTo(BigDecimal.ZERO) == 0) {
                        System.out.println("Error: Division by zero is not allowed!");
                        return null;
                    }
                    return a.divide(b, 10, RoundingMode.HALF_UP);
                case "^":
                    return a.pow(b.intValue(), MC);
                case "%":
                    return a.remainder(b);
                default:
                    System.out.println("Error: Invalid operator!");
                    return null;
            }
        } catch (ArithmeticException e) {
            System.out.println("Arithmetic Error: " + e.getMessage());
            return null;
        }
    }

    private static void scientificCalculationsMenu() {
        System.out.println("\n--- Scientific Calculations ---");
        System.out.println("1. Square Root (√x)");
        System.out.println("2. Exponentiation (x^y)");
        System.out.println("3. Natural Log (ln x)");
        System.out.println("4. Sine (sin x in degrees)");
        System.out.println("5. Cosine (cos x in degrees)");
        
        int choice = getIntInput("Select calculation: ");
        
        switch (choice) {
            case 1:
                calculateSquareRoot();
                break;
            case 2:
                calculateExponentiation();
                break;
            case 3:
                calculateNaturalLog();
                break;
            case 4:
                calculateSine();
                break;
            case 5:
                calculateCosine();
                break;
            default:
                System.out.println("Invalid selection!");
        }
    }
    
    private static void calculateSquareRoot() {
        BigDecimal num = getBigDecimalInput("Enter number: ");
        if (num.compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("Error: Cannot calculate square root of negative number!");
            return;
        }
        BigDecimal result = sqrt(num, 10);
        System.out.println("√" + num + " = " + result);
    }
    
    private static BigDecimal sqrt(BigDecimal value, int scale) {
        BigDecimal x0 = BigDecimal.ZERO;
        BigDecimal x1 = new BigDecimal(Math.sqrt(value.doubleValue()));
        
        while (!x0.equals(x1)) {
            x0 = x1;
            x1 = value.divide(x0, scale, RoundingMode.HALF_UP);
            x1 = x1.add(x0);
            x1 = x1.divide(BigDecimal.valueOf(2), scale, RoundingMode.HALF_UP);
        }
        return x1;
    }
    
    private static void calculateExponentiation() {
        BigDecimal base = getBigDecimalInput("Enter base: ");
        BigDecimal exponent = getBigDecimalInput("Enter exponent: ");
   
        double result = Math.pow(base.doubleValue(), exponent.doubleValue());
        System.out.println(base + " ^ " + exponent + " = " + new BigDecimal(result, MC));
    }
    
    private static void calculateNaturalLog() {
        BigDecimal num = getBigDecimalInput("Enter number (>0): ");
        if (num.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Error: Natural log only defined for positive numbers!");
            return;
        }
        
        double result = Math.log(num.doubleValue());
        System.out.println("ln(" + num + ") = " + new BigDecimal(result, MC));
    }
    
    private static void calculateSine() {
        BigDecimal degrees = getBigDecimalInput("Enter angle in degrees: ");
        double radians = Math.toRadians(degrees.doubleValue());
        double result = Math.sin(radians);
        System.out.println("sin(" + degrees + "°) = " + new BigDecimal(result, MC));
    }
    
    private static void calculateCosine() {
        BigDecimal degrees = getBigDecimalInput("Enter angle in degrees: ");
        double radians = Math.toRadians(degrees.doubleValue());
        double result = Math.cos(radians);
        System.out.println("cos(" + degrees + "°) = " + new BigDecimal(result, MC));
    }

    private static void unitConversionsMenu() {
        System.out.println("\n--- Unit Conversions ---");
        System.out.println("1. Temperature Conversion");
        System.out.println("2. Currency Conversion (USD to EUR/INR/GBP)");
        
        int choice = getIntInput("Select conversion type: ");
        
        switch (choice) {
            case 1:
                temperatureConversion();
                break;
            case 2:
                currencyConversion();
                break;
            default:
                System.out.println("Invalid selection!");
        }
    }
    
    private static void temperatureConversion() {
        System.out.println("\nTemperature Conversion:");
        System.out.println("1. Celsius to Fahrenheit");
        System.out.println("2. Fahrenheit to Celsius");
        System.out.println("3. Celsius to Kelvin");
        
        int choice = getIntInput("Select conversion: ");
        BigDecimal value = getBigDecimalInput("Enter temperature: ");
        
        BigDecimal result = null;
        String fromUnit = "", toUnit = "";
        
        switch (choice) {
            case 1:
                result = value.multiply(new BigDecimal("9"))
                            .divide(new BigDecimal("5"), 10, RoundingMode.HALF_UP)
                            .add(new BigDecimal("32"));
                fromUnit = "°C";
                toUnit = "°F";
                break;
            case 2:
                result = value.subtract(new BigDecimal("32"))
                            .multiply(new BigDecimal("5"))
                            .divide(new BigDecimal("9"), 10, RoundingMode.HALF_UP);
                fromUnit = "°F";
                toUnit = "°C";
                break;
            case 3:
                result = value.add(new BigDecimal("273.15"));
                fromUnit = "°C";
                toUnit = "K";
                break;
            default:
                System.out.println("Invalid choice!");
                return;
        }
        
        System.out.println(value + fromUnit + " = " + result + toUnit);
    }
    
    private static void currencyConversion() {
        System.out.println("\nCurrency Conversion (USD Base Rates):");
        System.out.println("1. USD to EUR (1 USD = 0.92 EUR)");
        System.out.println("2. USD to INR (1 USD = 83.50 INR)");
        System.out.println("3. USD to GBP (1 USD = 0.79 GBP)");
        System.out.println("4. Custom conversion");
        
        int choice = getIntInput("Select conversion: ");
        BigDecimal amount = getBigDecimalInput("Enter amount in USD: ");
        
        BigDecimal rate = null;
        String targetCurrency = "";
        
        switch (choice) {
            case 1:
                rate = new BigDecimal("0.92");
                targetCurrency = "EUR";
                break;
            case 2:
                rate = new BigDecimal("83.50");
                targetCurrency = "INR";
                break;
            case 3:
                rate = new BigDecimal("0.79");
                targetCurrency = "GBP";
                break;
            case 4:
                rate = getBigDecimalInput("Enter exchange rate: ");
                targetCurrency = getStringInput("Enter target currency code: ");
                break;
            default:
                System.out.println("Invalid choice!");
                return;
        }
        
        BigDecimal result = amount.multiply(rate).setScale(2, RoundingMode.HALF_UP);
        System.out.println(amount + " USD = " + result + " " + targetCurrency);
    }

    private static BigDecimal getBigDecimalInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("Input cannot be empty!");
                    continue;
                }
                return new BigDecimal(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number (e.g., 42, 3.14)");
            }
        }
    }
    
    private static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a whole number.");
            }
        }
    }
    
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}