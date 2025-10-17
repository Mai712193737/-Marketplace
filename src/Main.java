import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        LinckedList list = new LinckedList();

        System.out.print("Enter size of linkedList ");
        int size = input.nextInt();

        System.out.println("Enter members");
        for (int i = 0; i < size; i++) {
            System.out.print("Enter number in index" + (i + 1) + ": ");
            int value = input.nextInt();
            list.insertAtEnd(value);
        }

        int choice;
        do {
            System.out.println("\n--- القائمة ---");
            System.out.println("1. إضافة عنصر في البداية");
            System.out.println("2. إضافة عنصر في النهاية");
            System.out.println("3. حذف عنصر");
            System.out.println("4. طباعة القائمة");
            System.out.println("5. خروج");
            System.out.print("اختيارك: ");
            choice = input.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter value: ");
                    int firstVal = input.nextInt();
                    list.insertAtFirst(firstVal);
                    break;
                case 2:
                    System.out.print("Enter value: ");
                    int endVal = input.nextInt();
                    list.insertAtEnd(endVal);
                    break;
                case 3:
                    System.out.print("Enter value: ");
                    int delVal = input.nextInt();
                    list.delete(delVal);
                    break;
                case 4:
                    list.printArray();
                    break;
                case 5:
                    System.out.println("Bye");
                    break;
                default:
                    System.out.println("Error");
            }

        } while (choice != 5);
    }
}