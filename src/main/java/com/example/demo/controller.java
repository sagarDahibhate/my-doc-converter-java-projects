package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class controller {
          
    @GetMapping("/")
    public String getIndex() {
        return getMessage();
    }

    @GetMapping("/message")
    public String getMessage() {
        return "<!DOCTYPE html>\n" +
               "<html lang=\"en\">\n" +
               "<head>\n" +
               "    <meta charset=\"UTF-8\">\n" +
               "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
               "    <title>Sagar's Document Conversion Tools</title>\n" +
               "    <style>\n" +
               "        * {\n" +
               "            margin: 0;\n" +
               "            padding: 0;\n" +
               "            box-sizing: border-box;\n" +
               "            font-family: 'Poppins', sans-serif;\n" +
               "        }\n" +
               "        body {\n" +
               "            background: linear-gradient(45deg, #FF512F, #DD2476, #FF512F);\n" +
               "            background-size: 400% 400%;\n" +
               "            animation: gradient 15s ease infinite;\n" +
               "            min-height: 100vh;\n" +
               "            color: #fff;\n" +
               "        }\n" +
               "        @keyframes gradient {\n" +
               "            0% { background-position: 0% 50%; }\n" +
               "            50% { background-position: 100% 50%; }\n" +
               "            100% { background-position: 0% 50%; }\n" +
               "        }\n" +
               "        header {\n" +
               "            background: rgba(0, 0, 0, 0.3);\n" +
               "            padding: 2.5rem 5%;\n" +
               "            box-shadow: 0 4px 30px rgba(0, 0, 0, 0.2);\n" +
               "            backdrop-filter: blur(10px);\n" +
               "            text-align: center;\n" +
               "            border-bottom: 2px solid rgba(255,255,255,0.1);\n" +
               "        }\n" +
               "        header h1 {\n" +
               "            font-size: 3.5rem;\n" +
               "            margin-bottom: 1rem;\n" +
               "            text-shadow: 3px 3px 6px rgba(0,0,0,0.4);\n" +
               "            background: linear-gradient(to right, #fff, #ffd700);\n" +
               "            -webkit-background-clip: text;\n" +
               "            -webkit-text-fill-color: transparent;\n" +
               "            animation: shine 2s linear infinite;\n" +
               "        }\n" +
               "        @keyframes shine {\n" +
               "            0% { background-position: -100%; }\n" +
               "            100% { background-position: 200%; }\n" +
               "        }\n" +
               "        .welcome-section {\n" +
               "            text-align: center;\n" +
               "            padding: 4rem 2rem;\n" +
               "            background: rgba(255, 255, 255, 0.15);\n" +
               "            margin: 3rem auto;\n" +
               "            max-width: 1200px;\n" +
               "            border-radius: 25px;\n" +
               "            backdrop-filter: blur(15px);\n" +
               "            box-shadow: 0 8px 32px rgba(0,0,0,0.3);\n" +
               "            border: 1px solid rgba(255,255,255,0.2);\n" +
               "        }\n" +
               "        .welcome-section h2 {\n" +
               "            font-size: 2.5rem;\n" +
               "            margin-bottom: 2.5rem;\n" +
               "            color: #fff;\n" +
               "            text-shadow: 2px 2px 4px rgba(0,0,0,0.3);\n" +
               "        }\n" +
               "        .tools-grid {\n" +
               "            display: grid;\n" +
               "            grid-template-columns: repeat(auto-fit, minmax(350px, 1fr));\n" +
               "            gap: 2.5rem;\n" +
               "            padding: 2rem;\n" +
               "        }\n" +
               "        .tool-card {\n" +
               "            background: rgba(255, 255, 255, 0.2);\n" +
               "            border-radius: 20px;\n" +
               "            padding: 2.5rem;\n" +
               "            text-align: center;\n" +
               "            transition: all 0.4s ease;\n" +
               "            cursor: pointer;\n" +
               "            border: 1px solid rgba(255,255,255,0.2);\n" +
               "            box-shadow: 0 5px 15px rgba(0,0,0,0.2);\n" +
               "        }\n" +
               "        .tool-card:hover {\n" +
               "            transform: translateY(-15px) scale(1.03);\n" +
               "            background: rgba(255, 255, 255, 0.25);\n" +
               "            box-shadow: 0 8px 25px rgba(0,0,0,0.3);\n" +
               "        }\n" +
               "        .tool-card i {\n" +
               "            font-size: 4rem;\n" +
               "            margin-bottom: 1.5rem;\n" +
               "            color: #ffd700;\n" +
               "            text-shadow: 2px 2px 4px rgba(0,0,0,0.3);\n" +
               "        }\n" +
               "        .tool-card h3 {\n" +
               "            font-size: 1.8rem;\n" +
               "            margin-bottom: 1.5rem;\n" +
               "            color: #fff;\n" +
               "            text-shadow: 1px 1px 3px rgba(0,0,0,0.3);\n" +
               "        }\n" +
               "        .tool-card p {\n" +
               "            color: rgba(255, 255, 255, 0.9);\n" +
               "            line-height: 1.8;\n" +
               "            font-size: 1.1rem;\n" +
               "        }\n" +
               "        .action-btn {\n" +
               "            display: inline-block;\n" +
               "            padding: 1.2rem 2.5rem;\n" +
               "            margin-top: 1.5rem;\n" +
               "            background: linear-gradient(45deg, #ffd700, #ff8c00);\n" +
               "            color: white;\n" +
               "            text-decoration: none;\n" +
               "            border-radius: 50px;\n" +
               "            transition: all 0.3s;\n" +
               "            font-weight: bold;\n" +
               "            text-transform: uppercase;\n" +
               "            letter-spacing: 1px;\n" +
               "            box-shadow: 0 4px 15px rgba(0,0,0,0.2);\n" +
               "        }\n" +
               "        .action-btn:hover {\n" +
               "            transform: translateY(-3px);\n" +
               "            box-shadow: 0 6px 20px rgba(0,0,0,0.3);\n" +
               "            background: linear-gradient(45deg, #ff8c00, #ffd700);\n" +
               "        }\n" +
               "        footer {\n" +
               "            background: rgba(0, 0, 0, 0.4);\n" +
               "            color: white;\n" +
               "            text-align: center;\n" +
               "            padding: 1.5rem;\n" +
               "            position: fixed;\n" +
               "            bottom: 0;\n" +
               "            width: 100%;\n" +
               "            backdrop-filter: blur(10px);\n" +
               "            border-top: 1px solid rgba(255,255,255,0.1);\n" +
               "        }\n" +
               "        .file-input {\n" +
               "            display: none;\n" +
               "        }\n" +
               "    </style>\n" +
               "    <link href=\"https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap\" rel=\"stylesheet\">\n" +
               "    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css\">\n" +
               "</head>\n" +
               "<body>\n" +
               "    <header>\n" +
               "        <h1>Sagar's Document Conversion Tools</h1>\n" +
               "        <p>Transform your documents with our premium conversion tools</p>\n" +
               "    </header>\n" +
               "    <div class=\"welcome-section\">\n" +
               "        <h2>Premium Tools</h2>\n" +
               "        <div class=\"tools-grid\">\n" +
               "            <div class=\"tool-card\">\n" +
               "                <i class=\"fas fa-file-pdf\"></i>\n" +
               "                <h3>DOC to PDF Converter</h3>\n" +
               "                <p>Convert your Word documents to PDF format with just a few clicks. Maintains formatting and supports multiple languages.</p>\n" +
               "                <form action=\"/api/convert\" method=\"post\" enctype=\"multipart/form-data\">\n" +
               "                    <input type=\"file\" name=\"file\" id=\"fileInput\" class=\"file-input\" accept=\".doc,.docx\">\n" +
               "                    <label for=\"fileInput\" class=\"action-btn\">Choose File</label>\n" +
               "                    <button type=\"submit\" class=\"action-btn\" style=\"display: none;\" id=\"submitBtn\">Convert Now</button>\n" +
               "                </form>\n" +
               "                <script>\n" +
               "                    document.getElementById('fileInput').addEventListener('change', function() {\n" +
               "                        document.getElementById('submitBtn').style.display = this.files.length ? 'inline-block' : 'none';\n" +
               "                    });\n" +
               "                </script>\n" +
               "            </div>\n" +
               "            <div class=\"tool-card\">\n" +
               "                <i class=\"fas fa-file-word\"></i>\n" +
               "                <h3>PDF to DOC Converter</h3>\n" +
               "                <p>Coming Soon! Convert PDF documents back to editable Word format while preserving the original layout.</p>\n" +
               "                <a href=\"#\" class=\"action-btn\" style=\"background: linear-gradient(45deg, #808080, #666);\">Coming Soon</a>\n" +
               "            </div>\n" +
               "            <div class=\"tool-card\">\n" +
               "                <i class=\"fas fa-image\"></i>\n" +
               "                <h3>Image Converter</h3>\n" +
               "                <p>Coming Soon! Convert between various image formats including JPG, PNG, and more.</p>\n" +
               "                <a href=\"#\" class=\"action-btn\" style=\"background: linear-gradient(45deg, #808080, #666);\">Coming Soon</a>\n" +
               "            </div>\n" +
               "        </div>\n" +
               "    </div>\n" +
               "    <footer>\n" +
               "        <p>&copy; 2024 Sagar's Document Conversion Tools. All rights reserved.</p>\n" +
               "    </footer>\n" +
               "</body>\n" +
               "</html>";
    }

    @PostMapping("/convert")
    public ResponseEntity<byte[]> convertToPdf(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            // Load the DOC/DOCX file
            XWPFDocument document = new XWPFDocument(file.getInputStream());
            
            // Create PDF document
            PDDocument pdfDocument = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            pdfDocument.addPage(page);
            
            // Create content stream for writing
            PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, page);
            
            // Write document content to PDF
            float yPosition = page.getMediaBox().getHeight() - 50;
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12); // Changed font to Times Roman which has better Unicode support
            contentStream.newLineAtOffset(50, yPosition);
            
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                String text = paragraph.getText();
                // Remove tab characters, line feeds and non-breaking spaces
                text = text.replace("\t", "    ")
                         .replace("\n", " ") // Replace line feed with space
                         .replace("\r", "") // Remove carriage return
                         .replace("\\u000A", " ") // Replace line feed unicode with space
                         .replace("\u00A0", " "); // Replace non-breaking space with regular space
                
                // Split text into chunks and handle special characters
                String[] words = text.split(" ");
                for (String word : words) {
                    try {
                        // Try to normalize the text to remove problematic characters
                        String normalizedWord = java.text.Normalizer.normalize(word, java.text.Normalizer.Form.NFKD)
                                                  .replaceAll("[^\\p{ASCII}]", "?");
                        contentStream.showText(normalizedWord + " ");
                    } catch (IOException e) {
                        // If still fails, show placeholder
                        contentStream.showText("? ");
                    }
                }
                contentStream.newLineAtOffset(0, -15);
            }
            
            contentStream.endText();
            contentStream.close();
            document.close();
            
            // Save the PDF to byte array
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            pdfDocument.save(byteArrayOutputStream);
            pdfDocument.close();
            
            byte[] pdfBytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            
            // Verify PDF is valid
            PDDocument.load(new java.io.ByteArrayInputStream(pdfBytes)).close();
            
            // Set up response headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", file.getOriginalFilename().replaceFirst("[.][^.]+$", "") + ".pdf");
            
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(pdfBytes);
            
        } catch (Exception e) {
            throw new IOException("Failed to convert file to PDF: " + e.getMessage(), e);
        }
    }
}
