/*
 * Copyright 2013 Google Inc.
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

import co.rsk.bitcoinj.core.BtcBlock;

import java.math.BigInteger;

import static com.google.common.base.Preconditions.checkState;

/**
 * Network parameters for the regression test mode of bitcoind in which all blocks are trivially solvable.
 */
public class RegTestParams extends TestNet3Params {
    private static final BigInteger MAX_TARGET = new BigInteger("00000fffffffffffffffffffffffffffffffffffffffffffffffffffffffffff", 16); //BTX powLimit

    public RegTestParams() {
        super();
        // Difficulty adjustments are disabled for regtest. 
        // By setting the block interval for difficulty adjustments to Integer.MAX_VALUE we make sure difficulty never changes.    
        interval = Integer.MAX_VALUE;
        maxTarget = MAX_TARGET;
        subsidyDecreaseBlockCount = 150; //BTX
        port = 19444; //BTX
        id = ID_REGTEST;

        majorityEnforceBlockUpgrade = MainNetParams.MAINNET_MAJORITY_ENFORCE_BLOCK_UPGRADE;
        majorityRejectBlockOutdated = MainNetParams.MAINNET_MAJORITY_REJECT_BLOCK_OUTDATED;
        majorityWindow = MainNetParams.MAINNET_MAJORITY_WINDOW;
    }

    @Override
    public boolean allowEmptyPeerChain() {
        return true;
    }

    private static BtcBlock genesis;

    @Override
    public BtcBlock getGenesisBlock() {
        synchronized (RegTestParams.class) {
            if (genesis == null) {
                genesis = super.getGenesisBlock();
                genesis.setNonce(9377); //BTX
                genesis.setDifficultyTarget(0x1e0ffff0L); //BTX
                genesis.setTime(1492973331L); //BTX
                checkState(genesis.getHashAsString().toLowerCase().equals("604148281e5c4b7f2487e5d03cd60d8e6f69411d613f6448034508cea52e9574")); //BTX
            }
            return genesis;
        }
    }

    private static RegTestParams instance;
    public static synchronized RegTestParams get() {
        if (instance == null) {
            instance = new RegTestParams();
        }
        return instance;
    }

    @Override
    public String getPaymentProtocolId() {
        return PAYMENT_PROTOCOL_ID_REGTEST;
    }
}
