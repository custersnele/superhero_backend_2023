package be.pxl.superhero.service.impl;

import be.pxl.superhero.api.SuperheroDTO;
import be.pxl.superhero.commons.Cipher;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class SuperheroIdCardGenerator {
    private static final Logger logger = LoggerFactory.getLogger(SuperheroIdCardGenerator.class);

    public ByteArrayInputStream superheroIdCard(SuperheroDTO superhero) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            Path path = Paths.get(ClassLoader.getSystemResource("superheroidcard.pdf").toURI());
            PdfReader pdfReader = new PdfReader(path.toUri().toURL());
            PdfStamper pdfStamper = new PdfStamper(pdfReader, out);
            PdfContentByte canvas = pdfStamper.getOverContent(1);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(superhero.firstName() + " " + superhero.lastName()), 200, 620, 0);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(superhero.superheroName()), 200, 550, 0);
            byte[] qrCodeImage = getQRCodeImage(Cipher.skipALetter(superhero.superheroName()), 130, 130);
            Image qrCode = Image.getInstance(qrCodeImage);
            qrCode.setAbsolutePosition(190, 360);
            canvas.addImage(qrCode);
            pdfStamper.close();
            pdfReader.close();
        } catch (DocumentException | URISyntaxException | IOException | WriterException ex) {
            logger.error("Error occurred: {0}", ex);
        }
        return new ByteArrayInputStream(out.toByteArray());
    }

    public static byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();

        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        return pngOutputStream.toByteArray();
    }
}
