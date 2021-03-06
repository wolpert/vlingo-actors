// Copyright 2012-2017 For Comprehension, Inc.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.

package io.vlingo.actors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ActorLifecycleTest extends ActorsTest {
  private World world;

  @Test
  public void testBeforeStart() throws Exception {
    world.actorFor(Definition.has(LifecycleActor.class, Definition.NoParameters), Stoppable.class);
    pause();
    assertTrue(LifecycleActor.ReceivedBeforeStart);
    assertFalse(LifecycleActor.ReceivedAfterStop);
  }

  @Test
  public void testAfterStop() throws Exception {
    final Stoppable actor = world.actorFor(Definition.has(LifecycleActor.class, Definition.NoParameters), Stoppable.class);
    actor.stop();
    pause();
    assertTrue(LifecycleActor.ReceivedBeforeStart);
    assertTrue(LifecycleActor.ReceivedAfterStop);
  }

  @Before
  public void setUp() {
    world = World.start("test-lifecycle");
    
    LifecycleActor.ReceivedAfterStop = false;
    LifecycleActor.ReceivedBeforeStart = false;
  }

  @After
  public void tearDown() {
    world.terminate();
  }
  
  public static class LifecycleActor extends Actor implements Stoppable {
    public static boolean ReceivedBeforeStart = false;
    public static boolean ReceivedAfterStop = false;
    
    @Override
    protected void beforeStart() {
      ReceivedBeforeStart = true;
    }

    @Override
    protected void afterStop() {
      ReceivedAfterStop = true;
    }
  }
}
