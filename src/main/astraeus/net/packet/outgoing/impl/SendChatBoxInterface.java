package main.astraeus.net.packet.outgoing.impl;

import main.astraeus.game.model.entity.mobile.player.Player;
import main.astraeus.net.packet.PacketWriter;
import main.astraeus.net.packet.outgoing.OutgoingPacket;
import main.astraeus.net.protocol.codec.ByteOrder;

/**
 * The {@link OutgoingPacket} that shows an interface in the chat-box.
 * 
 * @author SeVen
 */
public class SendChatBoxInterface extends OutgoingPacket {
	
	/**
	 * The id of the interface to display in the chat-box.
	 */
	private final int interfaceId;

	/**
	 * Creates a new {@link SendChatBoxInterface}.
	 * 
	 * @param interfaceId
	 * 		The id of the interface to show in the chat-box.
	 */
	public SendChatBoxInterface(int interfaceId) {
		super(164, 3);
		this.interfaceId = interfaceId;
	}

	@Override
	public PacketWriter encode(Player player) {
		player.getContext().prepare(this, writer);
		writer.writeShort(interfaceId, ByteOrder.LITTLE);
		return writer;
	}

}
