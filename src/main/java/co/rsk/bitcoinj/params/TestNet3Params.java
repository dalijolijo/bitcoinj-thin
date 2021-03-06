/*
 * Copyright 2013 Google Inc.
 * Copyright 2014 Andreas Schildbach
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package co.rsk.bitcoinj.params;

import java.math.BigInteger;
import java.util.Date;

import co.rsk.bitcoinj.core.BtcBlock;
import co.rsk.bitcoinj.core.NetworkParameters;
import co.rsk.bitcoinj.core.StoredBlock;
import co.rsk.bitcoinj.core.Utils;
import co.rsk.bitcoinj.core.VerificationException;
import co.rsk.bitcoinj.store.BtcBlockStore;
import co.rsk.bitcoinj.store.BlockStoreException;

import static com.google.common.base.Preconditions.checkState;

/**
 * Parameters for the testnet, a separate public instance of Bitcoin that has relaxed rules suitable for development
 * and testing of applications and new Bitcoin versions.
 */
public class TestNet3Params extends AbstractBitcoinNetParams {
    public TestNet3Params() {
        super();
        id = ID_TESTNET;
        // BTX Genesis hash is 0x02c5d66e8edb49984eb743c798bca069466ce457b7febfa3c3a01b33353b7bc6
        packetMagic = 0xfbd2c8f1; //BTX
        interval = INTERVAL;
        targetTimespan = TARGET_TIMESPAN;
        maxTarget = Utils.decodeCompactBits(0x1e0ffff0L); //BTX
        port = 8666; //BTX
        addressHeader = 111; //BTX
        p2shHeader = 196; //BTX
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader };
        dumpedPrivateKeyHeader = 239; //BTX
        genesisBlock.setTime(1493124695L); //BTX
        genesisBlock.setDifficultyTarget(0x1e0ffff0L); //BTX
        genesisBlock.setNonce(1728920); //BTX
        spendableCoinbaseDepth = 100; // (?)
        subsidyDecreaseBlockCount = 210000; //BTX
        String genesisHash = genesisBlock.getHashAsString();
        checkState(genesisHash.equals("02c5d66e8edb49984eb743c798bca069466ce457b7febfa3c3a01b33353b7bc6")); //BTX

        dnsSeeds = new String[] {
                "188.68.52.172", 
                "51.15.84.165",
        }; //BTX
        addrSeeds = null;
        bip32HeaderPub = 0x043587CF; //BTX
        bip32HeaderPriv = 0x04358394; //BTX

        majorityEnforceBlockUpgrade = TestNet2Params.TESTNET_MAJORITY_ENFORCE_BLOCK_UPGRADE;
        majorityRejectBlockOutdated = TestNet2Params.TESTNET_MAJORITY_REJECT_BLOCK_OUTDATED;
        majorityWindow = TestNet2Params.TESTNET_MAJORITY_WINDOW;
    }

    private static TestNet3Params instance;
    public static synchronized TestNet3Params get() {
        if (instance == null) {
            instance = new TestNet3Params();
        }
        return instance;
    }

    @Override
    public String getPaymentProtocolId() {
        return PAYMENT_PROTOCOL_ID_TESTNET;
    }

    // February 16th 2012
    private static final Date testnetDiffDate = new Date(1329264000000L); // (?)

    @Override
    public void checkDifficultyTransitions(final StoredBlock storedPrev, final BtcBlock nextBlock,
        final BtcBlockStore blockStore) throws VerificationException, BlockStoreException {
        if (!isDifficultyTransitionPoint(storedPrev) && nextBlock.getTime().after(testnetDiffDate)) {
            BtcBlock prev = storedPrev.getHeader();

            // After 15th February 2012 the rules on the testnet change to avoid people running up the difficulty
            // and then leaving, making it too hard to mine a block. On non-difficulty transition points, easy
            // blocks are allowed if there has been a span of 20 minutes without one.
            final long timeDelta = nextBlock.getTimeSeconds() - prev.getTimeSeconds();
            // There is an integer underflow bug in bitcoin-qt that means mindiff blocks are accepted when time
            // goes backwards.
            if (timeDelta >= 0 && timeDelta <= NetworkParameters.TARGET_SPACING * 2) {
        	// Walk backwards until we find a block that doesn't have the easiest proof of work, then check
        	// that difficulty is equal to that one.
        	StoredBlock cursor = storedPrev;
        	while (!cursor.getHeader().equals(getGenesisBlock()) &&
                       cursor.getHeight() % getInterval() != 0 &&
                       cursor.getHeader().getDifficultyTargetAsInteger().equals(getMaxTarget()))
                    cursor = cursor.getPrev(blockStore);
        	BigInteger cursorTarget = cursor.getHeader().getDifficultyTargetAsInteger();
        	BigInteger newTarget = nextBlock.getDifficultyTargetAsInteger();
        	if (!cursorTarget.equals(newTarget))
                    throw new VerificationException("Testnet block transition that is not allowed: " +
                	Long.toHexString(cursor.getHeader().getDifficultyTarget()) + " vs " +
                	Long.toHexString(nextBlock.getDifficultyTarget()));
            }
        } else {
            super.checkDifficultyTransitions(storedPrev, nextBlock, blockStore);
        }
    }
}
