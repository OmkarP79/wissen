package main;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Day4 {
	public static void main(String[] args) {
		
		// Creating sample employees
		List<Employee> employees = Arrays.asList(
				new Employee("John", "Doe", 101, LocalDate.of(1990, 5, 15), 2500.0, "IT"),
				new Employee("Jane", "Smith", 102, LocalDate.of(1985, 3, 20), 1800.0, "HR"),
				new Employee("Alice", "Brown", 103, LocalDate.of(1992, 7, 10), 3000.0, "Finance"),
				new Employee("Bob", "White", 104, LocalDate.of(1988, 12, 5), 1200.0, "IT"));
		
		// Consumer to print Employee details
		Consumer<Employee> employeePrinter = emp -> System.out.println(emp);
		
		// Predicate to check if salary > 2000
		Predicate<Employee> highSalaryPredicate = emp -> emp.salary > 2000;
		
		// BiPredicate to check if salary is above a given amount
		//BiPredicate<Employee, Double> salaryAboveThreshold = (emp, threshold) -> emp.salary > threshold;
		
		// Printing employees with salary > 2000
		System.out.println("Employees with salary above 2000:");
		employees.stream().filter(highSalaryPredicate).forEach(employeePrinter);
		
		// Supplier to generate a random 16-character password
		Supplier<String> passwordSupplier = () -> {
			String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
			Random rand = new Random();
			return rand.ints(16, 0, chars.length()).mapToObj(i -> String.valueOf(chars.charAt(i)))
					.collect(Collectors.joining());
		};
		
		// Functional interface to map Employee to User
		Function<Employee, User> employeeToUserFunction = emp -> {
			String userName = emp.firstName + emp.lastName + emp.dateOfBirth.getYear() + emp.id;
			return new User(emp.id, userName, passwordSupplier.get());
		};
		
		// Creating users from employees
		List<User> users = employees.stream().map(employeeToUserFunction).collect(Collectors.toList());
		
		// Printing users
		System.out.println("\nGenerated Users:");
		users.forEach(System.out::println);
		
		// Sorting employees by month of birth using lambda in comparator
		employees.sort(Comparator.comparing(emp -> emp.dateOfBirth.getMonthValue()));
		System.out.println("\nEmployees sorted by month of birth:");
		employees.forEach(employeePrinter);
		
		// Creating threads using lambda expressions
		Thread employeeThread = new Thread(() -> {
			System.out.println("\nPrinting Employees in Thread:");
			employees.forEach(employeePrinter);
		});
		Thread userThread = new Thread(() -> {
			System.out.println("\nPrinting Users in Thread:");
			users.forEach(System.out::println);
		});
		
		// Starting threads
		employeeThread.start();
		userThread.start();
		
		// Functional Interface to generate userName
		UserNameGenerator userNameGenerator = (firstName, lastName, yearOfBirth, id) -> firstName + lastName
				+ yearOfBirth + id;
		
		// Using the custom functional interface to generate a username
		Employee exampleEmployee = employees.get(0);
		String generatedUserName = userNameGenerator.generate(exampleEmployee.firstName, exampleEmployee.lastName,
				exampleEmployee.dateOfBirth.getYear(), exampleEmployee.id);
		System.out.println("\nGenerated Username using UserNameGenerator: " + generatedUserName);
	}
}

// Custom functional interface
@FunctionalInterface
interface UserNameGenerator {
	String generate(String firstName, String lastName, int yearOfBirth, int id);
}

class Employee {
	String firstName;
	String lastName;
	int id;
	LocalDate dateOfBirth;
	double salary;
	String dept;

	public Employee(String firstName, String lastName, int id, LocalDate dateOfBirth, double salary, String dept) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = id;
		this.dateOfBirth = dateOfBirth;
		this.salary = salary;
		this.dept = dept;
	}

	@Override
	public String toString() {
		return String.format("Employee{id=%d, name='%s %s', DOB=%s, salary=%.2f, dept='%s'}", id, firstName, lastName,
				dateOfBirth, salary, dept);
	}
}

class User {
	int id;
	String userName;
	String password;

	public User(int id, String userName, String password) {
		this.id = id;
		this.userName = userName;
		this.password = password;
	}

	@Override
	public String toString() {
		return String.format("User{id=%d, userName='%s', password='%s'}", id, userName, password);
	}
}