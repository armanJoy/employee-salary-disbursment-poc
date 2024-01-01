package com.ibcs.salaryapp.util;

import com.ibcs.salaryapp.exceptions.CustomException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.text.RandomStringGenerator;
import org.apache.tomcat.util.buf.UDecoder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.HtmlUtils;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import java.util.Formatter;

@Service
public class UtilService {

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static String generateHmacSha256Hex(String key, String data) {
        try {
            // Create a SecretKeySpec with the given key and algorithm
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");

            // Initialize the Mac instance with the key and algorithm
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(secretKeySpec);

            // Compute the HMAC hash
            byte[] hmacBytes = mac.doFinal(data.getBytes("UTF-8"));

            // Convert the result to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            try (Formatter formatter = new Formatter(hexString)) {
                for (byte b : hmacBytes) {
                    formatter.format("%02x", b);
                }
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException | InvalidKeyException | java.io.UnsupportedEncodingException e) {
            // Handle exceptions here
            e.printStackTrace();
            return null;
        }
    }

    public static double formatDoubleValue(Double value) {
        double checkedValue = (value != null) ? value : 0.0;
        DecimalFormat df = new DecimalFormat("0.00");
        return Double.parseDouble(df.format(checkedValue));
    }

    public static float formatFloatValue(Float value) {
        double checkedValue = (value != null) ? value : 0.0;
        DecimalFormat df = new DecimalFormat("0.00");
        return Float.parseFloat(df.format(checkedValue));
    }

    public static String encodeUrl(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decodeUrl(String value) {
        try {
            return UDecoder.URLDecode(value, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String urlSafeBase64Encode(String encodeValue) {
        return encodeUrl(Base64.getEncoder().encodeToString(encodeValue.getBytes(StandardCharsets.UTF_8)));
    }

    public static String encodeForInvitationUrl(String docId, String patientId) {
        Base64.Encoder encoder = Base64.getUrlEncoder();
        String value = docId + "###" + patientId;
        return encoder.encodeToString(value.getBytes());
    }

    public static String decodeForInvitationUrl(String url) {
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String dStr = new String(decoder.decode(url));
        return dStr;
        //  String[] spt = dStr.split("###");
        // String docId = spt[0];
        // String patnum = spt[1];

    }

    public static String encodePassword(String password) throws Exception {
        String encodedpass = null;
        Optional passw = Optional.ofNullable(password);
        if (passw.isPresent()) {
            encodedpass = generatePassword(password);
            System.out.println("encoded password is" + encodedpass);
        } else {
            throw new Exception("Password cant be empty or null");
        }
        return encodedpass;
    }

    private static String generatePassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    private static String decodePassword(String password) throws Exception {
        String decodepass = null;
        Optional passw = Optional.ofNullable(password);
        if (passw.isPresent()) {
            decodepass = new String(Base64.getDecoder().decode(password));
            System.out.println("decoded string is" + decodepass);
        } else {
            throw new Exception("Password can't be empty or null");
        }
        return decodepass;
    }

    public static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        /* MessageDigest instance for hashing using SHA512*/
        MessageDigest md = MessageDigest.getInstance("SHA-512");

        /* digest() method called to calculate message digest of an input and return array of byte */
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String toHexString(byte[] hash) {
        /* Convert byte array of hash into digest */
        BigInteger number = new BigInteger(1, hash);

        /* Convert the digest into hex value */
        StringBuilder hexString = new StringBuilder(number.toString(16));

        /* Pad with leading zeros */
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    public static String generatePassword() {

        int leftLimit = 65; // letter 'a'
        int rightLimit = 90; // letter 'z'
        int targetStringLength = 6;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    public static String generateCode() {

        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;

    }

    public static String generateUniqueId() {
        return java.util.UUID.randomUUID().toString();
    }

    public static byte[] generateByteQRCode(String data, int width, int height) {
        ByteArrayOutputStream outputStream = null;
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height);
            outputStream = new ByteArrayOutputStream();
            MatrixToImageConfig config = new MatrixToImageConfig(0xFF000000, 0xFFFFFFFF);
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream, config);

        } catch (WriterException | IOException e) {
            throw new CustomException(e.getMessage());
        }

        return outputStream.toByteArray();

    }

    public static String listToString(List<String> list) {
        String str = "";
        for (String i : list) {
            str = str.concat(i + "|");
        }
        return str;
    }

    public static String[] joinedStringToStringArray(String arrayString, String separator) {
        String[] stringArray = new String[]{""};
        return (arrayString != null) ? arrayString.replace(separator, ",").split(",") : stringArray;
    }

    public static List<String> joinedStringToStringList(String arrayString, String separator) {
        String[] stringArray = new String[]{""};
        return (arrayString != null) ? Arrays.asList(arrayString.replace(separator, ",").split(",")) : Arrays.asList(stringArray);
    }

    public static String getDateInString() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String formattedDate = now.format(myFormatObj);
        return formattedDate;
    }

    public static String formateDateInHour(String timeString) {
//		time_string = "2022-06-24T11:31:10";
//		SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//		Date date = isoFormat.parse(time_string);
//
//		SimpleDateFormat amPmFormat = new SimpleDateFormat("hh:mm a");
//		String formattedTime = amPmFormat.format(date);
        String formattedTime = "";
        if (timeString != null) {
            DateTimeFormatter isoFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(timeString, isoFormat);

            DateTimeFormatter amPmFormat = DateTimeFormatter.ofPattern("hh:mm a");
            formattedTime = dateTime.format(amPmFormat);
        }
        return formattedTime;
    }

    private JsonElement sanitizeJsonNode(JsonElement node) {
        try {
            if (node != null && node.isJsonObject()) {
                JsonObject objectNode = node.getAsJsonObject();
                objectNode.entrySet().iterator().forEachRemaining(fieldName -> {
                    JsonElement fieldValue = fieldName.getValue();
                    if (fieldValue != null && fieldValue.isJsonPrimitive()) {
                        objectNode.addProperty(fieldName.getKey(), HtmlUtils.htmlEscape(fieldValue.getAsString()));
                    } else {
                        sanitizeJsonNode(fieldValue);
                    }
                });
                return objectNode;
            } else if (node.isJsonArray()) {
                JsonArray arrayNode = node.getAsJsonArray();
                arrayNode.forEach(this::sanitizeJsonNode);
                return arrayNode;
            }
//            else if (node != null && node.isJsonPrimitive()) {
//                return objectMapper.convertValue(HtmlUtils.htmlEscape(node.asText()), JsonNode.class);
//            }
        } catch (Exception e) {
        }
        return node;
    }

    public Object sanitize(Object dataObj, Class classs) {
        Gson gson = new Gson();
        JsonElement sanitizedJson = sanitizeJsonNode(gson.toJsonTree(dataObj));

        Object sanitizedObj = gson.fromJson(sanitizedJson, classs);
        return sanitizedObj;
    }

    public static int getRandomInRange(int low, int high) {
        Random random = new Random();
        int upRange = (high - low) > low ? (high - low) : high;
        return random.nextInt(upRange) + low;
    }

    public static String generateSixDigitOtp() {
        Random rnd = new Random();
        int number = rnd.nextInt(900000) + 100000;
        return Integer.toString(number);
    }

    public static String generateStrongPassword() {
        String pwString = generateRandomUpperCaseAlphabet(2)
                .concat(generateRandomLowerCaseAlphabet(2))
                .concat(generateRandomUpperCaseAlphabet(2))
                .concat(generateRandomLowerCaseAlphabet(2));
        List<Character> pwChars = pwString.chars().mapToObj(data -> (char) data).collect(Collectors.toList());
        Collections.shuffle(pwChars);
        String password = pwChars.stream().collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
        return password;
    }

    public static String generateRandomSpecialCharacters(int length) {
        RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder().withinRange(35, 38)
                .build();
        return pwdGenerator.generate(length);
    }

    public static String generateRandomUpperCaseAlphabet(int length) {
        RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder().withinRange(65, 90)
                .build();
        return pwdGenerator.generate(length);
    }

    public static String generateRandomLowerCaseAlphabet(int length) {
        RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder().withinRange(97, 121)
                .build();
        return pwdGenerator.generate(length);
    }

}
