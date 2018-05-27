package io.milk.jim;

public class Jim {
    public static void main(String[] args) {
        int port = 4761;
        JimPeer jimmy = new JimPeer();
        if (args.length == 2) {
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        jimmy.start(port);
    }
}
