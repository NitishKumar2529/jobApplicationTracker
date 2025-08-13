

import java.util.*;

// Class to store job application details
class JobApplication {
    String companyName;
    String position;
    String dateApplied; // Format: dd-mm-yyyy
    String status;

    public JobApplication(String companyName, String position, String dateApplied, String status) {
        this.companyName = companyName;
        this.position = position;
        this.dateApplied = dateApplied;
        this.status = status;
    }

    public String toString() {
        return String.format("%-20s %-20s %-15s %-15s",
                companyName, position, dateApplied, status);
    }
}

public class JobApplicationTracker {
    static List<JobApplication> applications = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n===== Job Application Tracker =====");
            System.out.println("1. Add Application");
            System.out.println("2. View Applications");
            System.out.println("3. Search by Company");
            System.out.println("4. Sort by Company Name");
            System.out.println("5. Update Status");
            System.out.println("6. Delete Application");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addApplication();
                case 2 -> viewApplications();
                case 3 -> searchApplication();
                case 4 -> sortApplications();
                case 5 -> updateStatus();
                case 6 -> deleteApplication();
                case 0 -> System.out.println("Exiting... Goodbye!");
                default -> System.out.println("Invalid choice, try again!");
            }
        } while (choice != 0);
    }

    // Add application
    public static void addApplication() {
        System.out.print("Enter Company Name: ");
        String company = sc.nextLine();
        System.out.print("Enter Position: ");
        String position = sc.nextLine();
        System.out.print("Enter Date Applied (dd-mm-yyyy): ");
        String date = sc.nextLine();
        System.out.print("Enter Status (Applied/Interview/Rejected/Selected): ");
        String status = sc.nextLine();

        applications.add(new JobApplication(company, position, date, status));
        System.out.println("Application added successfully!");
    }

    // View all applications
    public static void viewApplications() {
        if (applications.isEmpty()) {
            System.out.println("No applications found.");
            return;
        }
        System.out.printf("%-20s %-20s %-15s %-15s\n", "Company Name", "Position", "Date Applied", "Status");
        System.out.println("-------------------------------------------------------------------------------------");
        for (JobApplication app : applications) {
            System.out.println(app);
        }
    }

    // Binary Search for company name
    public static void searchApplication() {
        if (applications.isEmpty()) {
            System.out.println("No applications found.");
            return;
        }

        System.out.print("Enter Company Name to Search: ");
        String company = sc.nextLine();

        // Sort before binary search
        applications.sort(Comparator.comparing(a -> a.companyName.toLowerCase()));

        int index = binarySearch(applications, company.toLowerCase());
        if (index != -1) {
            System.out.printf("%-20s %-20s %-15s %-15s\n", "Company Name", "Position", "Date Applied", "Status");
            System.out.println("-------------------------------------------------------------------------------------");
            System.out.println(applications.get(index));
        } else {
            System.out.println("Company not found!");
        }
    }

    // Binary Search algorithm
    public static int binarySearch(List<JobApplication> list, String target) {
        int left = 0, right = list.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            String midVal = list.get(mid).companyName.toLowerCase();
            if (midVal.equals(target)) return mid;
            if (midVal.compareTo(target) < 0) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }

    // Sort using Merge Sort (DSA)
    public static void sortApplications() {
        if (applications.isEmpty()) {
            System.out.println("No applications to sort.");
            return;
        }
        applications = mergeSort(applications);
        System.out.println("Applications sorted by Company Name successfully!");
    }

    // Merge Sort Algorithm
    public static List<JobApplication> mergeSort(List<JobApplication> list) {
        if (list.size() <= 1) return list;

        int mid = list.size() / 2;
        List<JobApplication> left = mergeSort(list.subList(0, mid));
        List<JobApplication> right = mergeSort(list.subList(mid, list.size()));

        return merge(left, right);
    }

    public static List<JobApplication> merge(List<JobApplication> left, List<JobApplication> right) {
        List<JobApplication> merged = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            if (left.get(i).companyName.compareToIgnoreCase(right.get(j).companyName) <= 0) {
                merged.add(left.get(i++));
            } else {
                merged.add(right.get(j++));
            }
        }
        while (i < left.size()) merged.add(left.get(i++));
        while (j < right.size()) merged.add(right.get(j++));

        return merged;
    }

    // Update status
    public static void updateStatus() {
        System.out.print("Enter Company Name to Update: ");
        String company = sc.nextLine();
        for (JobApplication app : applications) {
            if (app.companyName.equalsIgnoreCase(company)) {
                System.out.print("Enter New Status: ");
                app.status = sc.nextLine();
                System.out.println("Status updated successfully!");
                return;
            }
        }
        System.out.println("Company not found!");
    }

    // Delete application
    public static void deleteApplication() {
        System.out.print("Enter Company Name to Delete: ");
        String company = sc.nextLine();
        Iterator<JobApplication> iterator = applications.iterator();
        while (iterator.hasNext()) {
            JobApplication app = iterator.next();
            if (app.companyName.equalsIgnoreCase(company)) {
                iterator.remove();
                System.out.println("Application deleted successfully!");
                return;
            }
        }
        System.out.println("Company not found!");
    }
}
