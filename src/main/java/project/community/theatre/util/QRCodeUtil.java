package project.community.theatre.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
/**
 * Utility class for generating QR codes for community theatre tickets.
 */
@Slf4j
public class QRCodeUtil {

    /**
     * Generates a QR code for a given ticket UUID and returns it as a byte array in PNG format.
     *
     * @param uuid    The unique identifier of the ticket.
     * @param width   The width of the generated QR code image.
     * @param height  The height of the generated QR code image.
     * @return A byte array containing the QR code image in PNG format.
     * @throws RuntimeException If an error occurs during QR code generation or writing to the output stream.
     */
    public static byte[] generateTicketQRCode(String uuid, int width, int height) {
        log.info("Generating QR code for ticket: {}", uuid);
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(uuid, BarcodeFormat.QR_CODE, width, height);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", baos);
            return baos.toByteArray();
        } catch (WriterException e) {
            // Handle QR code generation errors
            throw new RuntimeException("Failed to generate QR code: " + e.getMessage(), e);
        } catch (IOException e) {
            // Handle I/O errors from ByteArrayOutputStream
            throw new RuntimeException("Failed to write QR code to stream: " + e.getMessage(), e);
        }
    }
}