package be.pxl.superhero.commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CipherSkipALetterTest {

    @Test
    void skipALetterWordWithEvenLength() {
        assertEquals("SREECT", Cipher.skipALetter("SECRET"));
    }

    @Test
    void skipALetterWordWithOddLength() {
        assertEquals("SEETCSR", Cipher.skipALetter("SECRETS"));
    }

    @Test
    void skipALetterSentence() {
        assertEquals("sreect cdoe fro SETnutDs", Cipher.skipALetter("secret code for STuDEnts"));
    }

    @Test
    void skipALetterBlankString() {
        assertEquals("", Cipher.skipALetter("  "));
    }
}
