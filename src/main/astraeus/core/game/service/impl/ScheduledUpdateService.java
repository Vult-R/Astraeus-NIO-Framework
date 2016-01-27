package main.astraeus.core.game.service.impl;

import main.astraeus.core.game.GameConstants;
import main.astraeus.core.game.model.World;
import main.astraeus.core.game.model.entity.mobile.npc.Npc;
import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.game.service.ScheduledService;

public final class ScheduledUpdateService extends ScheduledService {

      /**
       * The overloaded class constructor used for instantiation of this class file.
       */
      public ScheduledUpdateService() {
            super(GameConstants.CYCLE_RATE);
      }

      @Override
      public void execute() {
            synchronized (this) {

                  // player movement
                  for (final Player player : World.getPlayers()) {
                        if (player == null || !player.isRegistered()) {
                              continue;
                        }
                        player.prepare();
                  }

                  // npc movement
                  for (final Npc npc : World.getNpcs()) {
                        if (npc == null || !npc.isRegistered()) {
                              continue;
                        }
                        npc.prepare();
                  }

                  // update player and npc both in parallel
                  for (final Player player : World.getPlayers()) {
                        if (player == null || !player.isRegistered()) {
                              continue;
                        }
                        player.getEventListener().update(player);
                  }

                  // clear player update flags
                  for (final Player player : World.getPlayers()) {
                        if (player == null || !player.isRegistered()) {
                              continue;
                        }
                        player.getUpdateFlags().clear();
                        player.setRegionChange(false);
                  }

                  // clear npc update flags
                  for (final Npc npc : World.getNpcs()) {
                        if (npc == null || !npc.isRegistered()) {
                              continue;
                        }
                        npc.getUpdateFlags().clear();
                  }

            }
      }

}