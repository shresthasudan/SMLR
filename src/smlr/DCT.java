package smlr;



import java.awt.image.BufferedImage;
import java.util.Arrays;

public class DCT {
    private static final int BLOCKSIZE = 8;

    // Transform coefficients
    private final static int TRANSFORM_COEFFICIENT = 16;
    private final static int LUMA = 7;
    private final static int U_CHROMINANCE = 2;
    private final static int V_CHROMINANCE = 2;

    // Scaling tables.
    private static int scalingTableInverse[] = {64516, 91239, 129032};
    private static double scalingTable[] = {4, 5.656854249492381, 8};

    // Cosine tables.
    private static int cosTableInverse[][] = {
            {127, 125, 117, 106, 90, 71, 49, 25},
            {127, 106, 49, -25, -90, -125, -117, -71},
            {127, 71, -49, -125, -90, 25, 117, 105},
            {127, 25, -117, -71, 90, 106, -49, -125},
            {127, -25, -117, 71, 90, -106, -49, 125},
            {127, -71, -49, 125, -90, -25, 117, -106},
            {127, -106, 49, 25, -90, 125, -117, 71},
            {127, -125, 117, -106, 90, -71, 49, -25},
    };

    private static double cosTable[][] = {
            {1, 0.980785280403230, 0.923879532511287, 0.831469612302545, 0.707106781186548, 0.555570233019602, 0.382683432365090, 0.195090322016128},
            {1, 0.831469612302545, 0.382683432365090, -0.195090322016128, -0.707106781186547, -0.980785280403230, -0.923879532511287, -0.555570233019602},
            {1, 0.555570233019602, -0.382683432365090, -0.980785280403230, -0.707106781186547, 0.195090322016128, 0.923879532511287, 0.831469612302546},
            {1, 0.195090322016128, -0.923879532511287, -0.555570233019602, 0.707106781186548, 0.831469612302546, -0.382683432365090, -0.980785280403231},
            {1, -0.195090322016128, -0.923879532511287, 0.555570233019602, 0.707106781186548, -0.831469612302545, -0.382683432365091, 0.980785280403230},
            {1, -0.555570233019602, -0.382683432365090, 0.980785280403230, -0.707106781186547, -0.195090322016128, 0.923879532511287, -0.831469612302545},
            {1, -0.831469612302545, 0.382683432365090, 0.195090322016128, -0.707106781186547, 0.980785280403231, -0.923879532511286, 0.555570233019602},
            {1, -0.980785280403230, 0.923879532511287, -0.831469612302545, 0.707106781186548, -0.555570233019602, 0.382683432365090, -0.195090322016129},
    };

    private static int[] getDCTBlock(int inBlock[], int size) {
        int[] outBlock = new int[size * size];
        Arrays.fill(outBlock, 0);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < BLOCKSIZE; k++) {
                    for (int l = 0; l < BLOCKSIZE; l++) {
                        outBlock[i * size + j] += (double) inBlock[k * BLOCKSIZE + l] * cosTable[k][i] * cosTable[l][j];
                    }
                }
                int sc_ind = 0;
                if (i == 0) sc_ind++;
                if (j == 0) sc_ind++;

                outBlock[i * size + j] /= (scalingTable[sc_ind] * (double) TRANSFORM_COEFFICIENT);
            }
        }
        return outBlock;
    }

    private static int[] getInverseDCTBlock(int inBlock[]) {
        int outBlock[] = new int[BLOCKSIZE * BLOCKSIZE];
        int len = (int) Math.sqrt(inBlock.length);
        Arrays.fill(outBlock, 0);

        for (int i = 0; i < BLOCKSIZE; i++) {
            for (int j = 0; j < BLOCKSIZE; j++) {
                for (int k = 0; k < len; k++) {
                    for (int l = 0; l < len; l++) {
                        int sc_ind = 0;
                        if (k == 0) sc_ind++;
                        if (l == 0) sc_ind++;
                        outBlock[i * BLOCKSIZE + j] += (inBlock[k * len + l] * cosTableInverse[i][k] * cosTableInverse[j][l] * TRANSFORM_COEFFICIENT) / scalingTableInverse[sc_ind];
                    }
                }
            }
        }
        return outBlock;
    }


    public static byte[] compress(BufferedImage image) {
        // Calculate size of outArray
        int tmpW = (image.getWidth() % BLOCKSIZE != 0) ? (image.getWidth() / BLOCKSIZE) + 1 : (image.getWidth() / BLOCKSIZE);
        int tmpH = (image.getHeight() % BLOCKSIZE != 0) ? (image.getHeight() / BLOCKSIZE) + 1 : (image.getHeight() / BLOCKSIZE);

        byte[] outBytes = new byte[LUMA * LUMA * ((tmpW) * (tmpH)) + U_CHROMINANCE * U_CHROMINANCE * ((tmpW) * (tmpH)) + V_CHROMINANCE * V_CHROMINANCE * ((tmpW) * (tmpH))];

        // Create blocks for the loop
        int lBlock[] = new int[BLOCKSIZE * BLOCKSIZE];
        int uBlock[] = new int[BLOCKSIZE * BLOCKSIZE];
        int vBlock[] = new int[BLOCKSIZE * BLOCKSIZE];

        int rgb, r, g, b, pos = 0;

        // Apply Discrete Cosine Transform on the image.
        for (int i = 0; i < image.getWidth(); i += BLOCKSIZE) {
            for (int j = 0; j < image.getHeight(); j += BLOCKSIZE) {
                for (int k = 0; k < BLOCKSIZE && i + k < image.getWidth(); k++) {
                    for (int l = 0; l < BLOCKSIZE && j + l < image.getHeight(); l++) {
                        // Get color from image.
                        rgb = image.getRGB(i + k, j + l);
                        r = (rgb & 0xFF0000) >>> 16;
                        g = (rgb & 0x00FF00) >>> 8;
                        b = rgb & 0x0000FF;

                        // Set color in the blocks.
                        lBlock[k * BLOCKSIZE + l] = (299 * r + 587 * g + 114 * b) / 1000;
                        uBlock[k * BLOCKSIZE + l] = (-147 * r - 289 * g + 436 * b) / 1000;
                        vBlock[k * BLOCKSIZE + l] = (615 * r - 515 * g - 100 * b) / 1000;
                    }
                }
                // Get DCT Blocks.
                int[] lCoef = getDCTBlock(lBlock, LUMA);
                int[] uCoef = getDCTBlock(uBlock, U_CHROMINANCE);
                int[] vCoef = getDCTBlock(vBlock, V_CHROMINANCE);

                // Write the new colors to the outBytes-array.
                for (int k = 0; k < lCoef.length; k++) outBytes[pos + k] = (byte) lCoef[k];
                pos += lCoef.length;

                for (int k = 0; k < uCoef.length; k++) outBytes[pos + k] = (byte) uCoef[k];
                pos += uCoef.length;

                for (int k = 0; k < vCoef.length; k++) outBytes[pos + k] = (byte) vCoef[k];
                pos += vCoef.length;
            }
        }
        return outBytes;
    }

    public static BufferedImage decompress(byte bytes[], int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Creates blocks with the transform coefficients sizes.
        int lCoef[] = new int[LUMA * LUMA];
        int uCoef[] = new int[U_CHROMINANCE * U_CHROMINANCE];
        int vCoef[] = new int[V_CHROMINANCE * V_CHROMINANCE];

        int r, g, b, pos = 0;

        // Apply Inverse Discrete Cosine Transform on the image.
        for (int i = 0; i < width; i += BLOCKSIZE) {
            for (int j = 0; j < height; j += BLOCKSIZE) {

                // Get the colors from the compressed byte-array and put in arrays.
                for (int k = 0; k < lCoef.length; k++) lCoef[k] = bytes[pos + k];
                pos += lCoef.length;

                for (int k = 0; k < uCoef.length; k++) uCoef[k] = bytes[pos + k];
                pos += uCoef.length;

                for (int k = 0; k < vCoef.length; k++) vCoef[k] = bytes[pos + k];
                pos += vCoef.length;

                // Get inverse DCT blocks.
                int[] lBlock = getInverseDCTBlock(lCoef);
                int[] uBlock = getInverseDCTBlock(uCoef);
                int[] vBlock = getInverseDCTBlock(vCoef);

                // Set the colors to the image from the block.
                for (int k = 0; k < BLOCKSIZE; k++) {
                    for (int l = 0; l < BLOCKSIZE; l++) {
                        r = (1000 * lBlock[BLOCKSIZE * k + l] + 1140 * vBlock[BLOCKSIZE * k + l]) / 1000;
                        g = (1000 * lBlock[BLOCKSIZE * k + l] - 395 * uBlock[BLOCKSIZE * k + l] - 581 * vBlock[BLOCKSIZE * k + l]) / 1000;
                        b = (1000 * lBlock[BLOCKSIZE * k + l] + 2032 * uBlock[BLOCKSIZE * k + l]) / 1000;

                        if (r < 0) r = 0;
                        if (r > 255) r = 255;
                        if (g < 0) g = 0;
                        if (g > 255) g = 255;
                        if (b < 0) b = 0;
                        if (b > 255) b = 255;

                        try {
                            image.setRGB(i + k, j + l, (r << 16) + (g << 8) + b);
                        } catch (ArrayIndexOutOfBoundsException ignored) {
                        }
                    }
                }
            }
        }
        return image;
    }
}