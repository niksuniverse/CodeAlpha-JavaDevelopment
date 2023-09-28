import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

enum BugStatus {
    OPEN,
    IN_PROGRESS,
    FIXED,
    CLOSED
}

class Bug {
    private static int nextId = 1;

    private int id;
    private String title;
    private String description;
    private BugStatus status;

    public Bug(String title, String description) {
        this.id = nextId++;
        this.title = title;
        this.description = description;
        this.status = BugStatus.OPEN;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public BugStatus getStatus() {
        return status;
    }

    public void setStatus(BugStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Bug #" + id + ": " + title + " (" + status + ")";
    }
}

class User {
    private String username;
    private String role;

    public User(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}

class Developer extends User {
    public Developer(String username) {
        super(username, "Developer");
    }

    public void fixBug(Bug bug) {
        bug.setStatus(BugStatus.FIXED);
    }
}

class Admin extends User {
    public Admin(String username) {
        super(username, "Admin");
    }

    public void assignBug(Bug bug, Developer developer) {
        bug.setStatus(BugStatus.IN_PROGRESS);
        System.out.println("Bug #" + bug.getId() + " assigned to " + developer.getUsername());
    }
}

class Management extends User {
    public Management(String username) {
        super(username, "Management");
    }

    public void closeBug(Bug bug) {
        bug.setStatus(BugStatus.CLOSED);
    }
}

public class Task3 {
    private List<Bug> bugs;
    private List<Developer> developers;
    private List<Admin> admins;
    private List<Management> managers;
    private int nextBugId;

    public Task3() {
        bugs = new ArrayList<>();
        developers = new ArrayList<>();
        admins = new ArrayList<>();
        managers = new ArrayList<>();
        nextBugId = 1;
    }

    public void addBug(String title, String description) {
        Bug bug = new Bug(title, description);
        bugs.add(bug);
    }

    public void registerDeveloper(Developer developer) {
        developers.add(developer);
    }

    public void registerAdmin(Admin admin) {
        admins.add(admin);
    }

    public void registerManagement(Management management) {
        managers.add(management);
    }

    public void showBugList() {
        System.out.println("Bug List:");
        for (Bug bug : bugs) {
            System.out.println(bug);
        }
        System.out.println();
    }

    public static void main(String[] args) {
    	Task3 bugTrackingSystem = new Task3();
        Developer developer = new Developer("dev1");
        Admin admin = new Admin("admin");
        Management manager = new Management("manager");

        bugTrackingSystem.registerDeveloper(developer);
        bugTrackingSystem.registerAdmin(admin);
        bugTrackingSystem.registerManagement(manager);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Bug Tracking System");
            System.out.println("1. Report Bug");
            System.out.println("2. Assign Bug");
            System.out.println("3. Fix Bug");
            System.out.println("4. Close Bug");
            System.out.println("5. View Bugs");
            System.out.println("6. Exit");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (option) {
                case 1:
                    System.out.print("Bug Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Bug Description: ");
                    String description = scanner.nextLine();
                    bugTrackingSystem.addBug(title, description);
                    System.out.println("Bug reported.\n");
                    break;

                case 2:
                    bugTrackingSystem.showBugList();
                    System.out.print("Bug ID: ");
                    int bugId = scanner.nextInt();
                    Bug assignBug = bugTrackingSystem.bugs.stream()
                            .filter(bug -> bug.getId() == bugId)
                            .findFirst()
                            .orElse(null);

                    if (assignBug != null) {
                        System.out.println("Available Developers:");
                        for (Developer dev : bugTrackingSystem.developers) {
                            System.out.println(dev.getUsername());
                        }
                        System.out.print("Select a developer: ");
                        String devUsername = scanner.next();
                        Developer selectedDeveloper = bugTrackingSystem.developers.stream()
                                .filter(dev -> dev.getUsername().equals(devUsername))
                                .findFirst()
                                .orElse(null);

                        if (selectedDeveloper != null) {
                            admin.assignBug(assignBug, selectedDeveloper);
                            System.out.println("Bug assigned.\n");
                        } else {
                            System.out.println("Developer not found.\n");
                        }
                    } else {
                        System.out.println("Bug not found.\n");
                    }
                    break;

                case 3:
                    bugTrackingSystem.showBugList();
                    System.out.print("Bug ID: ");
                    int fixBugId = scanner.nextInt();
                    Bug fixBug = bugTrackingSystem.bugs.stream()
                            .filter(bug -> bug.getId() == fixBugId)
                            .findFirst()
                            .orElse(null);

                    if (fixBug != null) {
                        developer.fixBug(fixBug);
                        System.out.println("Bug fixed.\n");
                    } else {
                        System.out.println("Bug not found.\n");
                    }
                    break;

                case 4:
                    bugTrackingSystem.showBugList();
                    System.out.print("Bug ID: ");
                    int closeBugId = scanner.nextInt();
                    Bug closeBug = bugTrackingSystem.bugs.stream()
                            .filter(bug -> bug.getId() == closeBugId)
                            .findFirst()
                            .orElse(null);

                    if (closeBug != null) {
                        manager.closeBug(closeBug);
                        System.out.println("Bug closed.\n");
                    } else {
                        System.out.println("Bug not found.\n");
                    }
                    break;

                case 5:
                    bugTrackingSystem.showBugList();
                    break;

                case 6:
                    System.out.println("Exiting Bug Tracking System.");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option.\n");
            }
        }
    }
}