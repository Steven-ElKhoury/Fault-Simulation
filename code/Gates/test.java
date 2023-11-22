package Gates;

public class test {
    public static void main (String[] args){
        Xor g = new Xor(true,true);
        System.out.println("out1: " + g.performXor());
    }
}
