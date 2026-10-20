import java.net.DatagramPacket; //Holds the message and destination or sender information
import java.net.DatagramSocket; //Opens the UDP communication endpoint
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class UdpPeer {

    private static volatile boolean verbose = true;

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Usage: Java UdpPeer <localPort>");
        } else {
            int localPort = Integer.parseInt(args[0]);

            try (DatagramSocket socket = new DatagramSocket(localPort);
            Scanner sc = new Scanner(System.in)) {

                System.out.println("UDP peer started on port " + localPort);

                System.out.println("Commands: ");
                System.out.println(" <host> <port> <message>");
                System.out.println(" /verbose on");
                System.out.println(" /verbose off");

                Thread receiver = new Thread(() -> {
                    byte[] buffer = new byte[4096];

                    while (!socket.isClosed()) {
                        try {
                            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                            socket.receive(packet); //Waits for the incoming datagrams

                            String message = new String(
                                    packet.getData(),
                                    packet.getOffset(),
                                    packet.getLength(),
                                    StandardCharsets.UTF_8
                            );

                            if (verbose) {
                                System.out.println("\n[RECEIVED] " +
                                        packet.getAddress() +
                                        ":" + packet.getPort() +
                                        " -> " + message);
                            } else {
                                System.out.println("\nMessage received: " + message);
                            }

                        } catch (Exception e) {
                            if (!socket.isClosed()) {
                                System.out.println("Received error: " + e.getMessage());
                            }
                        }
                    }
                });

                receiver.setDaemon(true);
                receiver.start();

                while (true) {
                    String input = sc.nextLine();

                    if (input.equalsIgnoreCase("/quit")) {
                        break;
                    }

                    if (input.equalsIgnoreCase("/verbose on")) {
                        verbose = true;
                        System.out.println("Verbose mode enabled");
                    } else if (input.equalsIgnoreCase("/verbose off")) {
                        verbose = false;
                        System.out.println("Verbose mode disabled");
                    } else {
                        String[] parts = input.split("\\s+", 3);

                        if (parts.length < 3) {
                            System.out.println("Use: <host> <port> <message>");
                        } else {
                            try {
                                InetAddress address = InetAddress.getByName(parts[0]);

                                int targetPort = Integer.parseInt(parts[1]);

                                byte[] data = parts[2].getBytes(StandardCharsets.UTF_8);

                                DatagramPacket packet =  new DatagramPacket(
                                        data,
                                        data.length,
                                        address,
                                        targetPort
                                );

                                socket.send(packet); //Sends a UDP program

                                if (verbose) {
                                    System.out.println("[SENT] " + address + ":" +
                                            targetPort + " -> " +parts[2]);
                                }
                            } catch (Exception e) {
                                System.out.println("Send error: " + e.getMessage());
                            }

                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
