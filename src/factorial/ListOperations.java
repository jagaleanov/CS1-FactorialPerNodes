package factorial;

public class ListOperations {

    int nodeSize = 3;

    public String fillZeros(String number) {

        //System.out.println();
        //System.out.println("entrada ");
        //System.out.println(number);

        int zeros = nodeSize - ((number.length() % nodeSize == 0) ? 5 : (number.length() % nodeSize));

        //System.out.println("añadiendo " + zeros + " ceros para crear grupos de " + nodeSize);

        for (int i = 0; i < zeros; i++) {
            number = '0' + number;
        }

        //System.out.println("con ceros " + number);

        return number;
    }

    public Node stringToList(Node head, String number) {

        for (int i = 0; i < number.length(); i = i + nodeSize) {
            head = this.addTail(head, number.substring(i, i + nodeSize));
        }

        return head;
    }

    public Node findTail(Node head) {
        Node tail = head;

        if (head != null) {
            while (tail.getNext() != null) {
                tail = tail.getNext();
            }
        }

        return tail;
    }

    public Node addTail(Node head, String data) {

        if (head != null) {
            Node tail = findTail(head);

            tail.setNext(new Node(data));
            tail.getNext().setPrev(tail);
        } else {
            head = new Node(data);
        }

        return head;
    }

    public Node addHead(Node head, String data) {

        if (head != null) {
            Node temp = head;
            head = new Node(data);
            head.setNext(temp);
            head.getNext().setPrev(head);

        } else {
            head = new Node(data);
        }

        return head;
    }

    public String listToString(Node head) {
        String string = "";
        Node q = head;
        while (q != null) {
            string += q.getData();
            q = q.getNext();
        }
        return string;
    }
    
    public void print(Node head) {
        Node q = head;
        while (q != null) {
            System.out.print(q.getData() + " ");
            q = q.getNext();
        }
        System.out.println();
    }

    public Node sum(Node headA, Node headB) {
        //System.out.println("Iniciando suma de "+listToString(headA)+" + "+listToString(headB));

        Node result = null;

        Node tailA = findTail(headA);
        Node tailB = findTail(headB);

        if (tailA != null || tailB != null) {
            Node a = tailA;
            Node b = tailB;
            int carry = 0;
            while (a != null || b != null || carry > 0) {
                int total = 0;

                if (a == null && b == null) {
                    total = carry;
                } else if (a == null) {
                    total = Integer.parseInt(b.getData()) + carry;
                } else if (b == null) {
                    total = Integer.parseInt(a.getData()) + carry;
                } else {
                    total = Integer.parseInt(a.getData()) + Integer.parseInt(b.getData()) + carry;
                }

                carry = 0;

                String totalStr = String.valueOf(total);

                String subStr = "";
                if (totalStr.length() > nodeSize) {
                    subStr = totalStr.substring(totalStr.length() - nodeSize, totalStr.length());
                    carry = Integer.parseInt(totalStr.substring(0, totalStr.length() - nodeSize));
                } else {
                    subStr = totalStr;
                }

                result = this.addHead(result, fillZeros(subStr));

                if (a != null) {
                    a = a.getPrev();
                }

                if (b != null) {
                    b = b.getPrev();
                }
            }
            
            //System.out.println("suma de "+listToString(headA)+" + "+listToString(headB)+" = "+listToString(result));
            return result;
        } else {
            //System.out.println("suma de "+listToString(headA)+" + "+listToString(headB)+" = 0");
            return stringToList(null, fillZeros("0"));
        }
    }

    public Node product(Node headA, Node headB) {
        //System.out.println("Iniciando producto de "+listToString(headA)+" * "+listToString(headB));

        Node tailB = findTail(headB);
        Node product = stringToList(null, fillZeros("0"));

        if (headA != null || tailB != null) {
            Node b = tailB;
            int c = 0;
            while (b != null) {
                Node total = stringToList(null, fillZeros("0"));

                for (int i = 0; i < Integer.parseInt(b.getData()); i++) {
                    total = sum(total, headA);
                }

                for (int i = 0; i < c; i++) {
                    total = addTail(total, fillZeros("0"));
                }
                
                //System.out.println("PRODUCT=" + listToString(product));
                //System.out.println("TOTAL=" + listToString(total));

                product = sum(product, total);

                c++;
                if (b != null) {
                    b = b.getPrev();
                }
            }
        }
        
        //System.out.println("producto de "+listToString(headA)+" * "+listToString(headB)+" = "+listToString(product));
        return product;
    }
    
    public Node factor(Node head) {
        //System.out.println("Iniciando factorial");

        Node counter = stringToList(null, fillZeros("0"));
        Node factor = stringToList(null, fillZeros("1"));
        
        
        while(! fillZeros(listToString(counter)).equals(fillZeros(listToString(head)))) {
            //System.out.println();
            //System.out.println("***** buscando el factorial de " + listToString(sum(counter,stringToList(null, fillZeros("1")))));
            
            factor = product(factor,sum(counter,stringToList(null, fillZeros("1"))));
            
            counter = sum(counter,stringToList(null, fillZeros("1")));
            //System.out.println("factorial de " + listToString(counter) + " = "+ listToString(factor));
        }
        
        System.out.println("factorial de "+listToString(head)+" = "+listToString(factor));
        return factor;
    }

}
