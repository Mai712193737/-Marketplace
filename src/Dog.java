import com.sun.source.tree.WhileLoopTree;

class Node {
    int data;
    Node next;

    public Node (int data) {
        this.data=data;
        this.next=null;
    }
}
class LinckedList {
    Node head;

    public void insertAtFirst(int i) {
        Node newNode = new Node(i);
        newNode.next = head;
        head = newNode;

    }

    public void insertAtEnd( int i ){
        Node n =new Node(i);
        if ( head==null ){head = n ;
        } else {
            Node current =head;
            while (current.next!=null)
                current =current.next;
            current.next=n;
    }}
    public void insertAtPosition( int position, int i ){

    }
   public void printArray(){
        Node temp= head;
            while (temp!=null){
            System.out.println(temp.data+" and");
            temp=temp.next;}
            System.out.println("Null");
        }
    public void delete(int i ){
        if (head==null) System.out.println("empty list");;
        Node yahya = head;
        if (head.data==i){
        head=head.next;
        }
        while (yahya.next!=null){
        if(yahya.next.data==i){
            yahya.next=yahya.next.next;
            return;
        }
        yahya=yahya.next;
        }
    }










}