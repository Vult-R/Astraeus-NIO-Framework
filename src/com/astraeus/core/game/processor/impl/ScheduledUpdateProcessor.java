package com.astraeus.core.game.processor.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.astraeus.core.game.model.entity.mobile.npc.Npc;
import com.astraeus.core.game.model.entity.mobile.player.Player;
import com.astraeus.core.game.processor.ScheduledProcessor;
import com.astraeus.core.game.processor.ProcessorConstants;
import com.astraeus.core.utility.IndexContainer;

public final class ScheduledUpdateProcessor extends ScheduledProcessor {

	/**
	 * A collection supporting full concurrency of retrievals and high expected
	 * concurrency for updates. This collection is used to store active players
	 * in a index to instance relationship.
	 */
	private final ConcurrentHashMap<Integer, Player> players = new ConcurrentHashMap<Integer, Player>();

	/**
	 * A collection of the active mobs in the virtual world.
	 */
	private final Map<Integer, Npc> npcs = new ConcurrentHashMap<Integer, Npc>();
	
	/**
	 * A container for the assignment and organization of the player
	 * identification numbers.
	 */
	private final IndexContainer playerIndexes = new IndexContainer(2000);

	/**
	 * The index of npcs in the game world.
	 */
	private final IndexContainer npcIndexes = new IndexContainer(2000);

	/**
	 * The overloaded class constructor used for instantiation of this class
	 * file.
	 */
	public ScheduledUpdateProcessor() {
		super(ProcessorConstants.UPDATE_PROCESSOR_RATE);
	}

	/**
	 * Places a new player into the concurrent collection.
	 * 
	 * @param index
	 *            The index of the new player.
	 * 
	 * @param entity
	 *            The instance of the new player.
	 */
	public final void addPlayer(Player player) {
		players.put(0, player);
	}

	/**
	 * Removes an existing player from the concurrent collection.
	 * 
	 * @param index
	 *            The instance of the existing player.
	 */
	public final void removePlayer(Player player) {
		players.remove(player.getIndex());
		playerIndexes.discardIndex(player.getIndex());
	}

	@Override
	public void execute() {
		synchronized (this) {
			for (final Player player : getPlayers().values()) {
				player.prepare();
			}
			
			for(final Npc npc : getNpcs().values()) {
				npc.prepare();
			}

			for (final Player player : players.values()) {
				player.getEventListener().update(player);
			}

			for (final Player player : players.values()) {
				player.getUpdateFlags().clear();
			}
			
			for(final Npc npc : getNpcs().values()) {
				npc.getUpdateFlags().clear();
			}
		}
	}
	
	/**
	 * Returns an instance of the concurrent player collection.
	 * 
	 * @return The returned instance.
	 */
	public ConcurrentHashMap<Integer, Player> getPlayers() {
		return players;
	}
	
	public Map<Integer, Npc> getNpcs() {
		return npcs;
	}
	
	/**
	 * @return the playerIndexes
	 */
	public IndexContainer getPlayerIndexes() {
		return playerIndexes;
	}

	/**
	 * @return the npcIndexes
	 */
	public IndexContainer getNpcIndexes() {
		return npcIndexes;
	}
}