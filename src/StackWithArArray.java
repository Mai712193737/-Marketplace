public class StackWithArArray {
    private int maxSize;
    private int top;
    private int[] array;
    public int value;

    public StackWithArArray(int maxSize) {
        this.maxSize = maxSize;
        top = -1;
        array = new int[maxSize];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public boolean isfull() {
        return top == maxSize - 1;
    }

    public void push() {
        if (isfull()) {
            System.out.println("Stack is overflow!...delete one item to add other..\uD83E\uDD0D");

        } else {
            array[++top] = value;
            System.out.println(" done ... \uD83E\uDD0D ");
        }
    }

    public int pop() {
        if (isEmpty()) {
            System.out.println("Array  orredy is empty  ");
            return -1;
        } else {
            int popedvalue = array[top--];
            return popedvalue;
        }
    }

    public int top() {
        if (isEmpty()) {
            System.out.println("Array  orredy is empty  ");
            return -1;
        } else {
            return array[top];
        }

    }
    public void printarray() {
        if (isEmpty()) {
            System.out.println("Array  orredy is empty  ");

        } else {
            for (int i = 0; i < top; i++) {
                System.out.println(array[i] + " ");
            }
            System.out.println();
        }
    }
}
