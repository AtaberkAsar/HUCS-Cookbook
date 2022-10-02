package CinemaReservationSystem;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Utils {

    // Constants
    public static final String backupFilePath = "assets/data/backup.dat";
    public static final String propertiesFilePath = "assets/data/properties.dat";
    public static final String errorSoundPath = "assets/effects/error.mp3";
    public static final String emptySeatImagePath = "assets/icons/empty_seat.png";
    public static final String logoPath = "assets/icons/logo.png";
    public static final String reservedSeatPath = "assets/icons/reserved_seat.png";
    public static final String trailersFolderPath = "assets/trailers/";

    public static final String luckyBackupPath = "assets/data/extra.dat";
    public static final String iconsPath = "assets/icons/";

    /**
     * Returns Base64 encoded version of MD5 hashed version of the given password.
     * @param password Password to be hashed
     * @return Base64 encoded version of MD5 hashed version of password.
     */
    public static String hashPassword(String password) {
        byte[] bytesOfPassword = password.getBytes(StandardCharsets.UTF_8);
        byte[] md5Digest = new byte[0];
        try {
            md5Digest = MessageDigest.getInstance("MD5").digest(bytesOfPassword);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return Base64.getEncoder().encodeToString(md5Digest);
    }

}










