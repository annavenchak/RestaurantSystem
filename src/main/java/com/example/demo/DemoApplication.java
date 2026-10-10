package com.example.demo;

import com.example.demo.domain.*;
import com.example.demo.presentation.AdminConsole;
import com.example.demo.presentation.CustomerConsole;
import com.example.demo.service.CustomerManager;
import com.example.demo.service.TableManager;
import com.example.demo.util.Validation;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Scanner;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
	@org.springframework.beans.factory.annotation.Value("${run.menu:true}")
	private boolean runMenu;

	private final CustomerConsole customerUI;
	private final AdminConsole adminUI;
	private final Validation v;
	private final CustomerManager cm;
	private final TableManager tm;

	@Autowired
	public DemoApplication(CustomerConsole customerUI, AdminConsole adminUI,
						   Validation v, CustomerManager cm, TableManager tm) {
		this.customerUI = customerUI;
		this.adminUI = adminUI;
		this.v = v;
		this.cm = cm;
		this.tm = tm;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		if (args.length > 0 && args[0].equals("test")) {
//			return;
//		}
//		if (!runMenu) {
//			return;
//		}
//		if (tm.getTablesSize() == 0) {
//			tm.create(new RegularTable("Indoor table", "Regular", 4));
//			tm.create(new VIPTable("Table in VIP room", "VIP", 7));
//			tm.create(new StudentTable("Exam Survival Zone", "Student Budget", 2));
//		}
//		Scanner sc = new Scanner(System.in);
//		while (true) {
//			System.out.println("\n====Welcome!====");
//			System.out.println("1. Customer");
//			System.out.println("2. Admin");
//			System.out.println("3. Exit");
//
//			int choice = v.getValidInt(sc, "Select an option:");
//
//			if (choice == 1) {
//				System.out.println("Enter your name:");
//				String name = sc.nextLine();
//
//				String phone;
//				do {
//					System.out.println("Enter your phone number:");
//					phone = sc.nextLine();
//				} while (!v.valPhone(phone));
//
//				Customer currentCustomer = cm.findOrCreate(name, phone);
//				customerUI.start(currentCustomer);
//
//			} else if (choice == 2) {
//				adminUI.start();
//
//			} else if (choice == 3) {
//				System.out.println("Exiting the system. Goodbye!");
//				System.exit(0);
//			} else {
//				System.out.println("Wrong input! Try again");
//			}
//		}

		System.out.println("=========================================================");
		System.out.println("Spring Boot додаток успішно запущено!");
		System.out.println("Для взаємодії використовуйте Swagger UI.");
		System.out.println("=========================================================");
	}
}