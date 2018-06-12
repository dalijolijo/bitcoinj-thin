package co.rsk.bitcoinj.params;

public class Bitcore {
    public static final String BITCOIN_SCHEME = "bitcore"; //BTX
    public static final String BITCOIN_SIGNED_MESSAGE_HEADER = "BitCore Signed Message:\n"; //BTX
    public static final String GENESIS_BLOCK_TIMESTAMP_STRING = "04678afdb0fe5548271967f1a67130b7105cd6a828e03909a67962e0ea1f61deb649f6bc3f4cef38c4f35504e51ec112de5c384df7ba0b8d578a4c702b6bf11d5f"; // BTX "Powerde by Bitsend-Europecoin-Diamond-MAC-B3 23/Apr/2017"
    public static final int GENESIS_BLOCK_REWARD = 1470000000;
    public static final int BLOCK_REWARD = 1500;
    public static final int BLOCK_VERSION_GENESIS = 1; //BTX
    public static final int MAX_COINS = 21000000; //BTX


    public static class MainNet {
        public static final int dumpedPrivateKeyHeader = 128; //BTX
        public static final int addressHeader = 3; //BTX 0x03
        public static final int p2shHeader = 125; //BTX 0x7D
        public static final int port = 8555; //BTX
        public static final long packetMagic = 0xd9b4bef9L; //BTX 0xf9beb4d9
        public static final int bip32HeaderPub = 0x03DD47AF; //The 4 byte header that serializes in base58 to "pub". BTX
        public static final int bip32HeaderPriv = 0x03DD47D9; //The 4 byte header that serializes in base58 to "priv". BTX

        public static final long GENESIS_BLOCK_NONCE = 2083236893; //BTX
        public static final long GENESIS_BLOCK_UNIX_TIMESTAMP = 1504706776L; //BTX 1231006505 ??
        public static final long GENESIS_BLOCK_NBITS = 0x1d00ffff; //BTX

        public static final String GENESIS_BLOCK_SIGNATURE = "95ba0161eb524f97d3847653057baaef7d7ba0ff";
        public static final String CONSENSUS_HASH_GENESIS_BLOCK = "604148281e5c4b7f2487e5d03cd60d8e6f69411d613f6448034508cea52e9574"; //BTX

        public static String[] dnsSeeds = {
            "seed.bitcore.biz",
            "37.120.190.76",
            "37.120.186.85",
            "185.194.140.60",
            "188.71.223.206",
            "185.194.142.122"
        }; //BTX
    };
    
    public static class TestNet {
        public static final int dumpedPrivateKeyHeader = 239; //0xEF
        public static final int addressHeader = 111; //BTX 0x6F
        public static final int p2shHeader = 196; //BTX 0xC4
        public static final int port = 8666; //BTX
        public static final long packetMagic = 0xf1c8d2fbL; // BTX 0xFBD2C8F1
        public static final int bip32HeaderPub = 0x043587CF; // ptpu BTX
        public static final int bip32HeaderPriv = 0x04358394; // ptpv BTX

        public static final long GENESIS_BLOCK_NONCE = 2253953817L;
        public static final long GENESIS_BLOCK_UNIX_TIMESTAMP = 1504706516L;
        public static final long GENESIS_BLOCK_NBITS = 0x1c09fe61;

        public static final String GENESIS_BLOCK_SIGNATURE = "9a8abac6c3d97d37d627e6ebcaf68be72275168b"; 
        public static final String CONSENSUS_HASH_GENESIS_BLOCK = "02c5d66e8edb49984eb743c798bca069466ce457b7febfa3c3a01b33353b7bc6"; //BTX

        public static String[] dnsSeeds = {
            "188.68.52.172",
            "37.120.186.85",
            "188.71.223.206",
            "185.194.142.122",
            "51.15.84.165"
        }; //BTX
    };
    
    public static class RegTest {
        public static final int dumpedPrivateKeyHeader = TestNet.dumpedPrivateKeyHeader; //BTX
        public static final int addressHeader = TestNet.addressHeader; //BTX
        public static final int p2shHeader = TestNet.p2shHeader; //BTX
        public static final int port = 19444; //BTX
        public static final long packetMagic = 0xdab5bffaL; //BTX 0xFABFB5DA
        public static final int bip32HeaderPub = TestNet.bip32HeaderPub; //BTX
        public static final int bip32HeaderPriv = TestNet.bip32HeaderPriv; //BTX

        public static final long GENESIS_BLOCK_NONCE = 0L;
        public static final long GENESIS_BLOCK_UNIX_TIMESTAMP = 1509798928L;
        public static final long GENESIS_BLOCK_NBITS = 0x207fffff;

        public static final String GENESIS_BLOCK_SIGNATURE = "23103f0e2d2abbaad0d79b7a37759b1a382b7821";
        public static final String CONSENSUS_HASH_GENESIS_BLOCK = "604148281e5c4b7f2487e5d03cd60d8e6f69411d613f6448034508cea52e9574"; //BTX
    };
}
