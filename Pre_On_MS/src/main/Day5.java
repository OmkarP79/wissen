package main;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Employee1 {
	private String firstName;
	private String department;
	private int salary;
	private int joiningYear;
	private String dateOfBirth;

	public Employee1(String firstName, String department, int salary, int joiningYear, String dateOfBirth) {
		this.firstName = firstName;
		this.department = department;
		this.salary = salary;
		this.joiningYear = joiningYear;
		this.dateOfBirth = dateOfBirth;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getDepartment() {
		return department;
	}

	public int getSalary() {
		return salary;
	}

	public int getJoiningYear() {
		return joiningYear;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public Employee1 incrementSalary() {
		return new Employee1(this.firstName, this.department, (int) (this.salary * 1.1), this.joiningYear,
				this.dateOfBirth);
	}
}

public class Day5 {
	
	public static void main(String[] args) {
		
		
		List<Employee1> employees = List.of(new Employee1("Alice", "IT", 50000, 2023, "1990-05-20"),
				new Employee1("Bob", "HR", 60000, 2022, "1985-07-15"),
				new Employee1("Charlie", "IT", 70000, 2023, "1995-12-10"),
				new Employee1("David", "Finance", 55000, 2021, "1992-03-25"),
				new Employee1("Eve", "IT", 80000, 2020, "1988-09-10"));
		
		String targetDepartment = "IT";
		
		
		// 1. Print First Name of Employees Who Joined in 2023
		System.out.println("Employees who joined in 2023:");
		employees.stream().filter(emp -> emp.getJoiningYear() == 2023).map(Employee1::getFirstName)
				.forEach(System.out::println);
		System.out.println();
		
		
		// 2. Count, Min, Max, Sum, and Average Salary of Employees in a Particular
		// Department
		System.out.println("Salary Statistics for department: " + targetDepartment);
		IntSummaryStatistics stats = employees.stream().filter(emp -> emp.getDepartment().equals(targetDepartment))
				.collect(Collectors.summarizingInt(Employee1::getSalary));
		System.out.println("Count: " + stats.getCount());
		System.out.println("Min: " + stats.getMin());
		System.out.println("Max: " + stats.getMax());
		System.out.println("Sum: " + stats.getSum());
		System.out.println("Average: " + stats.getAverage());
		System.out.println();
		
		
		// 3. Sorted List of Employees by First Name (Excluding HR Department)
		System.out.println("Sorted employees (excluding HR department):");
		employees.stream().filter(emp -> !emp.getDepartment().equals("HR")).map(Employee1::getFirstName).sorted()
				.forEach(System.out::println);
		System.out.println();
		
		
		// 4. Increment Salary of Employees in a Particular Department by 10%
		System.out.println("Updated Salaries after 10% increment in " + targetDepartment + " department:");
		List<Employee1> updatedEmployees = employees.stream()
				.map(emp -> emp.getDepartment().equals(targetDepartment) ? emp.incrementSalary() : emp)
				.collect(Collectors.toList());
		updatedEmployees.forEach(
				emp -> System.out.println("Department: " + emp.getDepartment() + ", Salary: " + emp.getSalary()));
		System.out.println();
		
		
		// 5. Print 50 Odd Numbers After 100
		System.out.println("50 Odd numbers after 100:");
		IntStream.iterate(101, n -> n + 2).limit(50).forEach(n -> System.out.print(n + " "));
		System.out.println("\n");
		
		
		// 6. Create a Comma-Separated List of First Names Ordered by Date of Birth
		System.out.println("Comma-separated first names ordered by date of birth:");
		String result = employees.stream().sorted(Comparator.comparing(Employee1::getDateOfBirth))
				.map(Employee1::getFirstName).collect(Collectors.joining(", "));
		System.out.println(result);
	}
}
