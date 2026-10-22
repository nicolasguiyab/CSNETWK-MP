package protocol;

public enum MessageType { //defines the protocol message types in one place

//each message has an integer ID matching the rubric

    LOBBY_PRESENCE(0x01),
    LOBBY_LEAVE(0x02),
    LOBBY_START_VOTE(0x03),
    LOBBY_START_ACK(0x04),

    HELLO(0x05),
    HELLO_ACK(0x06),
    SEED_COMMIT(0x07),
    SEED_REVEAL(0x08),
    RING_CONFIRM(0x09),

    CARD_COMMIT(0x0A),
    CARD_REVEAL(0x0B),
    ACTION_ANNOUNCE(0x0C),
    HAND_PASS(0x0D),
    RESOURCE_QUERY(0x0E),
    RESOURCE_OFFER(0x0F),
    RESOURCE_BUY(0x10),

    AGE_END(0x11),
    SCORE_REPORT(0x12),
    GAME_END(0x13),
    ERROR(0x14),
    PING(0x15),
    PONG(0x16),
    DISCONNECT(0x17),

    DIRECT_MESSAGE(0x18),
    GROUP_MESSAGE(0x19);

    private final int id;

    MessageType(int id) {
        this.id = id;
    }

    public int getId() { //retrieves a message's numeric ID
        return id;
    }

    public static MessageType fromId(int id) {

        /**
         * converts a numeric ID back to its corresponding message type, returning
         * null for an unknown ID
         */
//        MessageType[] types = values();
//
//        for (int i = 0; i < types.length; i++) {
//            if (types[i].id == id) {
//                return types[i];
//            }
//        }
//
//        return null;

        for (MessageType type : values()) {
            if (type.id == id) {
                return type;
            }
        }

        return null;
    }
}
