/*
 * Copyright contributors to Besu.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package org.hyperledger.besu.consensus.qbft;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.hyperledger.besu.consensus.common.bft.SynchronizerUpdater;
import org.hyperledger.besu.ethereum.p2p.rlpx.connections.PeerConnection;
import org.hyperledger.besu.ethereum.p2p.rlpx.wire.Message;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FutureMessageSynchronizerHandlerTest {
  @Mock private SynchronizerUpdater synchronizerUpdater;
  @Mock private Message message;
  @Mock private PeerConnection peerConnection;

  @Test
  void updatesPeerHeightEstimateToParentBlockWhenMessageReceived() {
    when(message.getConnection()).thenReturn(peerConnection);

    final FutureMessageSynchronizerHandler futureMessageSynchronizerHandler =
        new FutureMessageSynchronizerHandler(synchronizerUpdater);
    futureMessageSynchronizerHandler.handleFutureMessage(10, message);

    verify(synchronizerUpdater).updatePeerChainState(9, peerConnection);
  }
}
