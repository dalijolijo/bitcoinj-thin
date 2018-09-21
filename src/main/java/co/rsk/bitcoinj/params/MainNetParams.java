/*
 * Copyright 2013 Google Inc.
 * Copyright 2015 Andreas Schildbach
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

import co.rsk.bitcoinj.core.*;

import java.net.*;

import static com.google.common.base.Preconditions.*;

/**
 * Parameters for the main production network on which people trade goods and services.
 */
public class MainNetParams extends AbstractBitcoinNetParams {
    public static final int MAINNET_MAJORITY_WINDOW = 1000; //BTX
    public static final int MAINNET_MAJORITY_REJECT_BLOCK_OUTDATED = 950; // (?)
    public static final int MAINNET_MAJORITY_ENFORCE_BLOCK_UPGRADE = 750; // (?)

    public MainNetParams() {
        super();
        interval = INTERVAL;
        targetTimespan = TARGET_TIMESPAN;
        maxTarget = Utils.decodeCompactBits(0x1d00ffffL); //BTX nBits=1d00ffff
        dumpedPrivateKeyHeader = 128; //BTX
        addressHeader = 3; //BTX
        p2shHeader = 125; //BTX
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader };
        port = 8555; //BTX
        packetMagic = 0xd9b4bef9L; //BTX 0xf9beb4d9
        bip32HeaderPub = 0x0488B21E; //The 4 byte header that serializes in base58 to "xpub". BTX
        bip32HeaderPriv = 0x0488ADE4; //The 4 byte header that serializes in base58 to "xprv" BTX

        majorityEnforceBlockUpgrade = MAINNET_MAJORITY_ENFORCE_BLOCK_UPGRADE;
        majorityRejectBlockOutdated = MAINNET_MAJORITY_REJECT_BLOCK_OUTDATED;
        majorityWindow = MAINNET_MAJORITY_WINDOW;

        genesisBlock.setDifficultyTarget(0x1d00ffffL); //BTX: nBits=1d00ffff
        genesisBlock.setTime(1231006505L); //BTX
        genesisBlock.setNonce(2083236893); //BTX
        id = ID_MAINNET;
        subsidyDecreaseBlockCount = 210000; //BTX
        spendableCoinbaseDepth = 100; // (?)
        String genesisHash = genesisBlock.getHashAsString();
        checkState(genesisHash.equals("604148281e5c4b7f2487e5d03cd60d8e6f69411d613f6448034508cea52e9574"),
                genesisHash); //BTX

        // This contains (at a minimum) the blocks which are not BIP30 compliant. BIP30 changed how duplicate
        // transactions are handled. Duplicated transactions could occur in the case where a coinbase had the same
        // extraNonce and the same outputs but appeared at different heights, and greatly complicated re-org handling.
        // Having these here simplifies block connection logic considerably.
        checkpoints.put(2, Sha256Hash.wrap    ("580a107e111bea326b64dc098c057a3b96622c1521c23e4f5b51647aa5e41ecb")); //BTX
        checkpoints.put(50000, Sha256Hash.wrap("d3cdc4bd5bdafa908fae273f5cca9fda527b49e3fe727c6fe9122f28f2afbf6d")); //BTX
        checkpoints.put(100000, Sha256Hash.wrap("99103dc00414fd4c90b03cd1a770626e2ac681baa10e5f7f7ce089a6d98f0dad")); //BTX
        checkpoints.put(150000, Sha256Hash.wrap("a4245ff8d8a3d7fbbfde37a961fc74228ab142282d3c4f6104c69d28a5414c7c")); //BTX
        checkpoints.put(175000, Sha256Hash.wrap("4b6cc8d2b186d4aaecab3b72bfa88469690e775ae586c35947d77bd6dabd1607")); //BTX
        checkpoints.put(200000, Sha256Hash.wrap("65c89c662dbdad4aeeb4215076884be736a7512984741b353bbdef47478305e5")); //BTX
        checkpoints.put(215000, Sha256Hash.wrap("18accc496518e6f3008eeaab7e04123939d66eb7f8a02f2fcb3d69093c1df38c")); //BTX
    }

    private static MainNetParams instance;
    public static synchronized MainNetParams get() {
        if (instance == null) {
            instance = new MainNetParams();
        }
        return instance;
    }

    @Override
    public String getPaymentProtocolId() {
        return PAYMENT_PROTOCOL_ID_MAINNET;
    }
}
