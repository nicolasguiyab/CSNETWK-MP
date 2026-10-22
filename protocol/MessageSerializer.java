package protocol;

import java.nio.charset.StandardCharsets;

/**
 * The purpose of Message Serializer is to convert a Message object
 * into a byte array so it can be sent over the network, and convert
 * the received bytes back into a Message object
 */
public class MessageSerializer {

    //converts a Message object into a byte array
    //so it can be transmitted over the network
    public static byte[] serializer(Message message) {

        //combines the message ID and payload into one string
        //separated by a vertical bar
        String data = message.getType().getId() + "|" + message.getPayload();

        //coverts the string into bytes using UTF-8 encoding
        return data.getBytes(StandardCharsets.UTF_8);
    }

    //Converts received bytes back into a Message object
    public static Message deserialize(byte[] data) {

        //Converts the received byte array into a UTF-8 strig
        String text = new String(data, StandardCharsets.UTF_8);

        //Splits the string into the message ID and payload
        //The limit of 2 ensures the payload can obtain "|"
        String[] parts = text.split("\\|", 2);


        //Checks if the message contains both expected parts
        if (parts.length != 2) {
            return null;
        }

        try {

            //Converts the Message ID from a string to an integer
            int id = Integer.parseInt(parts[0]);

            //Finds the message type associated with the ID
            MessageType type = MessageType.fromId(id);

            //Rejects the message if its ID is unknown
            if (type == null) {
                return null;
            }

            //Creates a Message object using decoded type
            //and payload, then returns it
            return new Message(type, parts[1]);

        } catch (Exception e) {
            return null;
        }
    }
}
